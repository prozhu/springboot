package com.open.aop;

import com.open.annotation.RepeatSubmit;
import com.open.exceptions.AccessLimitException;
import com.open.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.core.utils.DigestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * aop 实现防止重复提交
 * @author Administrator
 * @date 2024/9/20 14:06
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class RepeatSubmitAspect {
    private final String REPEAT_SUBMIT_LOCK_KEY_PARAM = "REPEAT_SUBMIT_LOCK_KEY_PARAM";

    private final String REPEAT_SUBMIT_LOCK_KEY_TOKEN = "REPEAT_SUBMIT_LOCK_KEY_TOKEN";

    private final RedissonClient redissonClient;

    //private final RedisRepository redisRepository;

    @Pointcut("@annotation(repeatSubmit)")
    public void pointCutNoRepeatSubmit(RepeatSubmit repeatSubmit) {

    }

    /**
     * 环绕通知, 围绕着方法执行
     * 两种方式
     * 方式一：加锁 固定时间内不能重复提交
     * 方式二：先请求获取token，再删除token,删除成功则是第一次提交
     */
    @Around("pointCutNoRepeatSubmit(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //用于记录成功或者失败
        boolean res = false;

        //获取防重提交类型
        String type = repeatSubmit.limitType().name();
        if (type.equalsIgnoreCase(RepeatSubmit.Type.PARAM.name())) {
            //方式一，参数形式防重提交
            //通过 redissonClient 获取分布式锁，基于IP地址、类名、方法名生成唯一key
            String ipAddr = IPUtils.getIpAddr(request);
            String preKey = repeatSubmit.preKey();
            String redisKey = generateTokenRedisKey(joinPoint, ipAddr, preKey);

            //获取注解中的锁时间
            long lockTime = repeatSubmit.lockTime();
            //获取注解中的时间单位
            TimeUnit timeUnit = repeatSubmit.timeUnit();

            //使用 tryLock 尝试获取锁，如果无法获取（即锁已被其他请求持有），则认为是重复提交，直接返回null
            RLock lock = redissonClient.getLock(redisKey);
            //锁自动过期时间为 lockTime 秒，确保即使程序异常也不会永久锁定资源，尝试加锁，最多等待0秒，上锁以后 lockTime 秒自动解锁 [lockTime默认为5s, 可以自定义]
            res = lock.tryLock(0, lockTime, timeUnit);

        } else {
            //方式二，令牌形式防重提交
            //从请求头中获取 request-token，如果不存在，则抛出异常
            String requestToken = request.getHeader("request-token");
            if (StringUtils.isEmpty(requestToken)) {
                throw new AccessLimitException("请求未包含令牌");
            }
            //使用 request-token 和 serviceId 构造Redis的key，尝试从Redis中删除这个键。如果删除成功，说明是首次提交；否则认为是重复提交
            String key = String.format("%s:%s:%s", REPEAT_SUBMIT_LOCK_KEY_TOKEN, repeatSubmit.serviceId(), requestToken);
            RKeys keys = redissonClient.getKeys();
            res = keys.delete(key) > 0;
            // res = redisRepository.del(key);
        }

        if (!res) {
            log.error("请求重复提交");
            throw new AccessLimitException(repeatSubmit.msg());
        }
        return joinPoint.proceed();
    }

    private String generateTokenRedisKey(ProceedingJoinPoint joinPoint, String ipAddr, String preKey) {
        //根据ip地址、用户id、类名方法名、生成唯一的key
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getName();
        String userId = "seven";
        return String.format("%s:%s:%s", REPEAT_SUBMIT_LOCK_KEY_PARAM, preKey,
                DigestUtil.md5Hex(String.format("%s-%s-%s-%s", ipAddr, className, method, userId)));
    }
}

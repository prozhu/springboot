package com.open.exceptions;

import com.open.utils.dingding.DingManage;
import com.open.utils.dingding.model.DingTextParamBo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常捕获并推送消息切面
 * @author ：zc
 * @createTime ：2021/4/25 16:05
 */
@Aspect
@Component
@Slf4j
public class ExceptionLogAspect {

    @Autowired
    private DingManage dingManage;

    /**
     * 切点：controller和service
     * 需要修改对应的路径-todo
     */
    @Pointcut("execution(* *.*.*.controller..*.*(..))  || execution(public * *.service..*.*(..)))")
    public void pointCutService() {
    }

    /**
     * 前置通知（方法调用前调用）
     * @param joinPoint
     */
    @Before("pointCutService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
    }


    /**
     * 异常通知
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "pointCutService()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Exception exception) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        try {
            Signature signature = joinPoint.getSignature();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURL().toString();
            String className = signature.getDeclaringTypeName();
            StringBuffer sb = new StringBuffer();
            sb.append("路径: ").append(uri).append(";\n");
            sb.append("时间: ").append(currentTime).append(";\n");
            sb.append("类: ").append(className).append(";\n");
            sb.append("方法: ").append(signature.getName()).append(";\n");
            sb.append("异常: ").append(exception.toString()).append("\n");
            sb.append("详情: ").append(traceToString(exception));
            log.error("切面捕获抛出的异常，类名：【{}】-方法名：【{}】，异常：【{}】", signature.getDeclaringTypeName(), signature.getName(), exception.getLocalizedMessage());
            //以下按需修改
            DingTextParamBo paramBo = new DingTextParamBo(sb.toString(), true, null);
            dingManage.sendMsg(paramBo);
        } catch (Exception ex) {
            log.error("切面捕获抛出的异常失败，{}", ex);
        }
    }


    /**
     * 截取异常堆栈信息--可以从这里快速判断问题出现在哪行代码
     * @param t
     * @return
     */
    private String traceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        String result = sw.getBuffer().toString();
        return result;
    }


}

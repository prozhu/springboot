package com.zc.springboot.controller.demo3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestArrayOrLinkEfficiency {

	/**
	 * 测试数组和链表的插入和查询性能
	 * @author ：zc
	 * @createTime ：2020年7月31日 下午1:34:38 
	 * @updateTime ：2020年7月31日 下午1:34:38 
	 * @alterMan：zc：
	 */
	@Test
	public void testOne() {

		int elementCount = 1;
		int loopCount = 1;
		StopWatch stopWatch = new StopWatch();
//		stopWatch.start("linkedListGet");
//		linkedListGet(elementCount, loopCount);
//		stopWatch.stop();
//
//		stopWatch.start("arrayListGet");
//		arrayListGet(elementCount, loopCount);
//		stopWatch.stop();
//		log.info("时间看板为：{}", stopWatch.prettyPrint());

		stopWatch.start("linkedListAdd");
		linkedListAdd(elementCount, loopCount);
		stopWatch.stop();

		stopWatch.start("arrayListAdd");
		arrayListAdd(elementCount, loopCount);
		stopWatch.stop();
		log.info("时间看板为：{}", stopWatch.prettyPrint());

	}

	@Test
	public void testTwo() {
		log.info("fsdfdsfsdf");
//		IntStream.rangeClosed(1, 10).forEach(x -> System.out.println(x));
//		List<Integer> collect = IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList());
//
//		log.info("{}", JSON.toJSONString(collect));
//		list.forEach(i -> {
//			log.info("data is : {}", i);
//		});

	}

	private void linkedListGet(int elementCount, int loopCount) {
		// 定义数据源
		List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed()
				.collect(Collectors.toCollection(LinkedList::new));
		// 从数据源中查找指定数据
		IntStream.rangeClosed(0, loopCount).forEach(i -> {
			int nextInt = ThreadLocalRandom.current().nextInt(elementCount);
			log.info("nextInt:{}", nextInt);
			list.get(nextInt);
		});
	}

	private void arrayListGet(int elementCount, int loopCount) {
		List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed()
				.collect(Collectors.toCollection(ArrayList::new));
		// 从数据源中查找指定数据
		IntStream.rangeClosed(0, loopCount).forEach(i -> {
			int nextInt = ThreadLocalRandom.current().nextInt(elementCount);
			log.info("nextInt:{}", nextInt);
			list.get(nextInt);
		});
	}

	private void linkedListAdd(int elementCount, int loopCount) {
		List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed()
				.collect(Collectors.toCollection(LinkedList::new));
		log.info("shujuwei:{}", JSON.toJSONString(list));
		IntStream.range(0, loopCount).forEach(i -> {
			int nextInt = ThreadLocalRandom.current().nextInt(elementCount);
			list.add(nextInt);
		});
		log.info("linkedListAdd size {}", list.size());
	}

	private void arrayListAdd(int elementCount, int loopCount) {
		List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed()
				.collect(Collectors.toCollection(ArrayList::new));
		log.info("shujuwei:{}", JSON.toJSONString(list));
		IntStream.range(0, loopCount).forEach(i -> {
			int nextInt = ThreadLocalRandom.current().nextInt(elementCount);
			list.add(nextInt);
		});
		log.info("arrayListAdd size {}", list.size());
	}
}

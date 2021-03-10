package com.zc.springboot.controller.demo3;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StopWatch;

public class CasThread implements Runnable {
	private AtomicInteger total;
	private CountDownLatch latch;

	public CasThread(AtomicInteger total, CountDownLatch latch) {
		this.total = total;
		this.latch = latch;
	}

	@Override
	public void run() {
		while (!total.compareAndSet(total.get(), total.get() + 1)) {
		}
		latch.countDown();
	}

	public static void main(String[] args) throws InterruptedException {
		int count = 100000;
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		AtomicInteger atomicInteger = new AtomicInteger(0);
		CountDownLatch latch = new CountDownLatch(count);
		// 定义线程池
		ThreadPoolExecutor executor = new ThreadPoolExecutor(count, count, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		for (int i = 0; i < count; i++) {
			executor.execute(new CasThread(atomicInteger, latch));
		}
		// 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
		latch.await();
		System.out.println(atomicInteger.get());
		System.out.println(latch.getCount());
		System.out.println("消耗：" + stopWatch.getTotalTimeMillis() + "ms");
		LinkedList<String> list = new LinkedList<>();
//		CompletableFuture.runAsync(() -> new Object());
	}

}

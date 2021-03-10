package com.zc.springboot.controller.demo3;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Jdk8DataTimeTest {

	public static ThreadLocal<SimpleDateFormat> simpleDateFormat = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
			.appendValue(ChronoField.YEAR) // 年
			.appendLiteral("-").appendValue(ChronoField.MONTH_OF_YEAR) // 月
			.appendLiteral("-").appendValue(ChronoField.DAY_OF_MONTH) // 日
			.appendLiteral(" ").appendValue(ChronoField.HOUR_OF_DAY) // 时
			.appendLiteral(":").appendValue(ChronoField.MINUTE_OF_HOUR) // 分
			.appendLiteral(":").appendValue(ChronoField.SECOND_OF_MINUTE) // 秒
			//			.appendLiteral(".").appendValue(ChronoField.MILLI_OF_SECOND) // 毫秒
			.toFormatter();

	@Test
	public void test1() {
		Date data = new Date(2019 - 1900, 11, 31, 11, 12, 13);
		log.info(data.toString());

		log.info(new Date(0).toString());

		log.info(TimeZone.getDefault().getID() + ":" + TimeZone.getDefault().getRawOffset());
	}

	@Test
	public void test2() {
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		availableZoneIds.forEach(str -> {
			log.info(str);
		});

		ZoneId timeZoneSh = ZoneId.of("Asia/Shanghai");
		ZoneId timeZoneNy = ZoneId.of("America/New_York");
		ZoneOffset ofHours = ZoneOffset.ofHours(9);

	}

	@Test
	public void test3() {
		ExecutorService execute = Executors.newFixedThreadPool(100);
		IntStream.rangeClosed(1, 20).forEach(i -> {
			execute.execute(() -> {
				IntStream.rangeClosed(1, 10).forEach(j -> {
					try {
						//						System.out.println(simpleDateFormat.get().parse("2020-01-01 11:12:13"));
						System.out.println(dateTimeFormatter.parse("2020-01-01 11:12:13"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			});
		});
		execute.shutdown();
		try {
			execute.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test4() {
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		today.add(Calendar.DAY_OF_MONTH, 30);
		log.info("today: {}", today.getTime());

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime plusDays = currentTime.plusDays(30);
		log.info("today2: {}", plusDays);
		log.info("today2 减去一天: {}", plusDays.minusDays(1));

		System.out.println("//测试操作日期");
		System.out.println(LocalDate.now().minus(Period.ofDays(1)).plus(1, ChronoUnit.DAYS)
				.minusMonths(1).plus(Period.ofMonths(1)));
	}

	@Test
	public void test5() {
		System.out.println("//本月的第一天");
		System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
		System.out.println("//今年的程序员日");
		System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(255));
		System.out.println("//今天之前的一个周六");
		System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));
		System.out.println("//本月最后一个工作日");
		System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

		System.out.println(LocalDate.now().with(temporal -> temporal
				.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));

		System.out.println("//计算日期差");
		LocalDate today = LocalDate.of(2019, 12, 12);
		LocalDate specifyDate = LocalDate.of(2019, 10, 1);
		System.out.println(Period.between(specifyDate, today).getDays());
		System.out.println(Period.between(specifyDate, today));
		System.out.println(ChronoUnit.DAYS.between(specifyDate, today));

		new Date().toString();
	}

	@Test
	public void test6() {
		String path = "D:\\share\\new\\98-设计模式之美\\不定期加餐 (3讲)\\";
		File file = new File(path);

		String absolutePath = file.getAbsolutePath();

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			Arrays.stream(listFiles).forEach(tempFile -> {
				String name = tempFile.getName();
				String newName = name.replace("[天下无鱼][shikey.com]", "");
				log.info(newName);
				File newFile = new File(absolutePath + "\\" + newName);
				tempFile.renameTo(newFile);
			});

		}
		log.info(absolutePath);

	}

}

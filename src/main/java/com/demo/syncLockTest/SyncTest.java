package com.demo.syncLockTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

public class SyncTest {
    private static int count;
    
    private static int num = 1;
    private static int maxValue = 1000;
    private static String lock = "lock";
     
	public static void main(String[] args) throws RunnerException {
		Counter lockTest = new SyncTest().new Counter();
		long startTime = System.currentTimeMillis();
		CountDownLatch latch = new CountDownLatch(num);
		for (int i = 0; i < num; i++) {
			new Thread(() -> {
				for (int cur = 0; cur < maxValue; cur++) {
					lockTest.add();
				}
				latch.countDown();
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println("SyncTest执行时长：" + (endTime - startTime) + ", count" + count);

	}
    
    
	class Counter {
		public void add() {
			 synchronized(lock) {
              	count++;
              	//System.out.println("count：" + count);
              }
		}
	}

}

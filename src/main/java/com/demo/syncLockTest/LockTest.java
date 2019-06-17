package com.demo.syncLockTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	private static int count;

	private static int num = 1;
	private static int maxValue = 1000;
	private final Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		Counter lockTest = new LockTest().new Counter();
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

		System.out.println("LockTest执行时长：" + (endTime - startTime) + ", count" + count);

	}

	class Counter {
		public void add() {
			lock.lock();
			try {
				count++;
			} finally {
				lock.unlock();
			}
		}
	}

}

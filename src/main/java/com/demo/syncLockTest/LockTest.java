package com.demo.syncLockTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	 private static int count;
	    
	    private static int num = 10;
	    private static int maxValue = 1000000;
	    private final Lock lock = new ReentrantLock();
	    
	    public static void main(String[] args) {
	    	LockTest test = new LockTest();

			long start = System.currentTimeMillis();

			List<Thread> list = new ArrayList<Thread>();
			for (int i = 0; i < num; i++) {
				Thread thread = new Thread(test.new Counter());
				thread.start();
				list.add(thread);
			}

			try {
				for (Thread thread : list) {
					thread.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			long end = System.currentTimeMillis();
			System.out.println("LockTest执行时长：" + (end - start) + ", count：" + count);
	    	
	    }
	    
	    class Counter implements Runnable {
			public void run() {
				 for (int j = 0; j < maxValue; j++) {
					 lock.lock();
					 try {
	                 	count++;
	                 	//System.out.println("count：" + count);
	                 }finally {
	                	 lock.unlock();
	                 }
	             }
			}
		}

}

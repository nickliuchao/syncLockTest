package com.demo.syncLockTest;

import java.util.ArrayList;
import java.util.List;

public class SyncTest {
    private static int count;
    
    private static int num = 10;
    private static int maxValue = 1000000;
    private static String lock = "lock";
     
    public static void main(String[] args) {
    	SyncTest test = new SyncTest();

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
		System.out.println("TestSync执行时长：" + (end - start)+ ", count：" + count);
    	
    }
    
    class Counter implements Runnable {
		public void run() {
			 for (int j = 0; j < maxValue; j++) {
                 synchronized(lock) {
                 	count++;
                 	//System.out.println("count：" + count);
                 }
             }
		}
	}

}

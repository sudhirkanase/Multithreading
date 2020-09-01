package com.thread.datarace;

/**
 * Race Conditions & Data Races
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
public class Main {
	public static void main(String[] args) {
		SharedClass sharedClass = new SharedClass();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.increment();
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.checkForDataRace();
			}

		});

		thread1.start();
		thread2.start();
	}

	public static class SharedClass {
		// compiler re-arramge instruction sets if we provide variable as volatile then
		// it will make sure instructions before
		// volatile executed before the volatile variable read and right operation
//		private int x = 0;
//		private int y = 0;

		// to avoid race condition we have to use volatile variables
		private volatile int x = 0;
		private volatile int y = 0;

		public void increment() {
			x++;
			y++;
		}

		public void checkForDataRace() {
			if (y > x) {
				System.out.println("y > x - Data Race is detected");
			}
		}
	}
}

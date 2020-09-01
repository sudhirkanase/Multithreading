package com.thread.creation;

public class ThreadCreationDemo1 {

	public static void main(String[] args) {
		// Runnable r = () -> {System.out.println("called me");};
		Thread t = new Thread(() -> {
			// Code that will run in a new thread
			System.out.println("we are now in thread " + Thread.currentThread().getName());
			System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
			throw new RuntimeException("Intentional Exception");
		});
		t.setName("My new worker thread");
		t.setPriority(Thread.MAX_PRIORITY);
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				 System.out.println("A critical error happened in thread " + t.getName()
                 + " the error is " + e.getMessage());
			}
		
		});
		
		t.start();
		//t.join();
	}
}

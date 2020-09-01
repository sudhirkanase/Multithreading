package com.thread.reentrant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntrantLockDemo {

	public static void main(String[] args) {
		ReadWriteOperation operatio = new ReadWriteOperation();
		new Thread(
				() -> {
					try {
						operatio.read();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				).start();
		
		new Thread(
				() -> {
					try {
						operatio.write();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				).start();
	}
}

class ReadWriteOperation {
	Lock lock = new ReentrantLock();

	public void read() throws InterruptedException {
		lock.lock();
		System.out.println("Got the r lock");
		Thread.sleep(5000);
		System.out.println("Reading");
		lock.unlock();
	}

	public void write() throws InterruptedException {
		lock.lock();
		System.out.println("Got the write lock");
		//Thread.sleep(5000);
		System.out.println("Writing");
		lock.unlock();
	}
}
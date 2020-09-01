package com.thread.reentrant;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReEntrantReadWriteLock {

	public static void main(String[] args) {
		ReadWriteOperationWithReadWriteLock operatio = new ReadWriteOperationWithReadWriteLock();
		new Thread(() -> {
			try {
				operatio.write();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
		// multiple read threads
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			new Thread(() -> {
				try {

					operatio.read();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}
	}

}

class ReadWriteOperationWithReadWriteLock {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void read() throws InterruptedException {
		lock.readLock().lock();
		System.out.println("Got the read lock");
		Thread.sleep(5000);
		System.out.println("Reading");
		lock.readLock().unlock();
	}

	public void write() throws InterruptedException {
		lock.writeLock().lock();
		System.out.println("Got the write lock");
		// Thread.sleep(5000);
		System.out.println("Writing");
		lock.writeLock().unlock();
	}
}

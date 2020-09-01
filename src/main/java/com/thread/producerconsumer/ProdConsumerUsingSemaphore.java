package com.thread.producerconsumer;

import java.util.concurrent.Semaphore;

public class ProdConsumerUsingSemaphore {
	public static void main(String[] args) {
		Semaphore producerSema = new Semaphore(1);
		Semaphore consumerSema = new Semaphore(0);
		Producer prod = new Producer(producerSema, consumerSema);

		Consumer con = new Consumer(producerSema,consumerSema);

		new Thread(prod).start();
		new Thread(con).start();
	}
}

class Producer implements Runnable {

	int cnt = 0;
	private Semaphore producerSema;
	private Semaphore consumerSema;

	public Producer(Semaphore producerSema, Semaphore consumerSema) {
		this.producerSema = producerSema;
		this.consumerSema = consumerSema;
	}

	@Override
	public void run() {
		for(int i  =0 ; i< Integer.MAX_VALUE; i++) {
			try {
				producerSema.acquire();
				System.out.println("Produced : "+i); // Or we can common holder for Item holding
				consumerSema.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

class Consumer implements Runnable{

	private Semaphore producerSema;
	private Semaphore consumerSema;
	
	public Consumer(Semaphore producerSema, Semaphore consumerSema) {
		this.producerSema =producerSema;
		this.consumerSema= consumerSema;
	}
	
	@Override
	public void run() {
		for(int i  =0 ; i< Integer.MAX_VALUE; i++) {
			try {
				consumerSema.acquire();
				System.out.println("Consumed : "+i); // Or we can common holder for Item holding
				Thread.sleep(100);
				producerSema.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

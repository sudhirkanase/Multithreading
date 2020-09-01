package com.thread.producerconsumer;

import java.util.concurrent.atomic.AtomicInteger;


public class ProducerConsumer {

	public static void main(String[] args) throws InterruptedException {
		ItemHolder holder = new ItemHolder();
		new Thread(new ItemProducer(holder)).start();
		Thread.sleep(10000);
		new Thread(new ItemConsumer(holder)).start();
	}
}

class ItemConsumer implements Runnable {
	ItemHolder holder;

	public ItemConsumer(ItemHolder holder) {
		this.holder = holder;
	}

	@Override
	public void run() {
		try {

			System.out.println("In the consumer");
			while (true) {
				Item item = holder.getItem();
				System.out.println("Item consumed:- " + item.itemNumber);
				// Wait for consumer to release lock
				synchronized (holder) {
					holder.notify();
				}
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class ItemProducer implements Runnable {
	ItemHolder holder;
	AtomicInteger counter = new AtomicInteger();

	public ItemProducer(ItemHolder holder) {
		this.holder = holder;
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (holder) {
					int andIncrement = counter.getAndIncrement();
					System.out.println("Generate Item:- " + andIncrement);
					Item item = new Item(andIncrement);
					holder.addItem(item);
					System.out.println("Wait for consumer to release lock");
					holder.wait();
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class ItemHolder {
	Item item;

	public ItemHolder() {

	}

	void addItem(Item item) {
		this.item = item;
	}

	Item getItem() {
		return item;
	}
}

class Item {
	int itemNumber;

	Item(int i) {
		itemNumber = i;
	}

}
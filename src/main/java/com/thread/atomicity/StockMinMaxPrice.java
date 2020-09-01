package com.thread.atomicity;

public class StockMinMaxPrice {

	public static void main(String[] args) {
		Stock stock = new Stock();
		new Thread(new BuyStock(stock)).start();
		new Thread(new SellStock(stock)).start();
		new Thread(new PrintMinMaxStock(stock)).start();
	}
}

class PrintMinMaxStock implements Runnable {
	private Stock stock;

	PrintMinMaxStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Stock max " + stock.getMax() + " min " + stock.getMin() + " Current :- " + stock.currentPrice);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class SellStock implements Runnable {

	private Stock stock;

	SellStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public void run() {
		while (true) {
			try {
				stock.addSample(-10);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class BuyStock implements Runnable {

	private Stock stock;

	BuyStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public void run() {
		while (true) {
			try {
				stock.addSample(10);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Stock {
	long currentPrice = 0;
	long maxPrice = 0;
	long minPrice = 0;

	/**
	 * Initializes all member variables
	 */
	public Stock() {
		// Add code here
	}

	/**
	 * Adds a new sample to our metrics.
	 */
	public void addSample(long newSample) {
		currentPrice += newSample;
		if (currentPrice < minPrice) {
			minPrice = minPrice;
		} else if (currentPrice > maxPrice) {
			maxPrice = currentPrice;
		}
	}

	/**
	 * Returns the smallest sample we've seen so far.
	 */
	public long getMin() {
		return minPrice;
	}

	/**
	 * Returns the biggest sample we've seen so far.
	 */
	public long getMax() {
		return maxPrice;
	}
}

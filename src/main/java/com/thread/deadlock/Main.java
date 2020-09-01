package com.thread.deadlock;

public class Main {

	public static void main(String[] args) {
		Intersection intersection = new Intersection();
		Thread trainAThread = new Thread(new TrainA(intersection));
		Thread trainBThread = new Thread(new TrainB(intersection));

		trainAThread.start();
		trainBThread.start();
	}
}

class TrainB implements Runnable {

	private Intersection intesection;

	public TrainB(Intersection intesection) {
		this.intesection = intesection;
	}

	@Override
	public void run() {
		intesection.takeRoouteB();
	}

}

class TrainA implements Runnable {

	private Intersection intesection;

	public TrainA(Intersection intesection) {
		this.intesection = intesection;
	}

	@Override
	public void run() {
		this.intesection.takeRoouteA();
	}

}

class Intersection {
	private Object routeA = new Object();
	private Object routeB = new Object();

	public void takeRoouteA() {
		synchronized (routeA) {
			System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
			synchronized (routeB) {
				System.out.println("Train is passing through road A");
			}
		}
	}

	public void takeRoouteB() {
		synchronized (routeB) {
			System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
			synchronized (routeA) {
				System.out.println("Train is passing through road B");
			}
		}
	}

}

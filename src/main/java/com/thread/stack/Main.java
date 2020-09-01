package com.thread.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		// With synchronised stack 10,205,928
		//StandardStack<Integer> stack = new StandardStack<Integer>();
		
		// with lock free stack using atomic reference 26,199,165
		LockFreeStack<Integer> stack = new LockFreeStack<Integer>();
		Random rand = new Random();
		for (int i = 0; i < 10000; i++) {
			stack.push(rand.nextInt());
		}
		
		int pushThreads = 2;
		int popThreads = 2;
		List<Thread> threds = new ArrayList<Thread>();
		for(int i =0; i < pushThreads; i++) {
			threds.add(new Thread(()-> {
				 while (true) 
					 stack.push(rand.nextInt());
			}));
		}
		
		for(int i =0; i < popThreads; i++) {
			threds.add(new Thread(()-> {
				 while (true) 
					 stack.pop();
			}));
		}
		
		for (Thread thread : threds) {
			thread.start();
		}
		
		Thread.sleep(1000);
		System.out.println(String.format("%,d operations were performed in 10 seconds ", stack.getCounter()));
	}
}

class StandardStack<T> {
	StackNode<T> head;
	AtomicInteger cnt = new AtomicInteger(0);

	public synchronized void push(T value) {
		StackNode<T> newNode = new StackNode<>(value);
		newNode.next = head;
		head = newNode;
		cnt.incrementAndGet();
	}

	public synchronized T pop() {
		if (head == null) {
			return null;
		}
		StackNode<T> removeNode = head;
		head = head.next;
		cnt.incrementAndGet();
		return removeNode.value;
	}

	public int getCounter() {
		return cnt.get();
	}
}

class StackNode<T> {
	StackNode<T> next;
	T value;

	public StackNode(T value) {
		this.value = value;
		this.next = next;
	}
}
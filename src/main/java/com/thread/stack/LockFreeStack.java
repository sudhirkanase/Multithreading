package com.thread.stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStack<T> {

	AtomicReference<StackNode<T>> head = new AtomicReference<StackNode<T>>();
	private AtomicInteger counter = new AtomicInteger(0);
	public void push(T value) {
		 StackNode<T> newHeadNode = new StackNode<>(value);
		 
		 StackNode<T> currentHeadNode = head.get();
		 while(true) {
			 newHeadNode.next = currentHeadNode;
			 if(head.compareAndSet(currentHeadNode, newHeadNode)){
				 break;
			 }else {
				 LockSupport.parkNanos(1);
				 currentHeadNode = head.get();
			 }
		 }
		 counter.incrementAndGet();
	 }

	public T pop() {
		StackNode<T> currentHeadNode = head.get();
		
		 while(currentHeadNode != null) {
			 if(head.compareAndSet(currentHeadNode, currentHeadNode.next)){
				 break;
			 }else {
				 LockSupport.parkNanos(1);
				 currentHeadNode = head.get();
			 }
		 }
		
		 counter.incrementAndGet();
         return currentHeadNode != null ? currentHeadNode.value : null;

	}
	
	 public int getCounter() {
         return counter.get();
     }
}

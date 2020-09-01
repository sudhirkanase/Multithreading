Atomic
	All primitiv types excepts long and double are atomic
	long and double are 64 bits hence its 2 read or write operations lower 32 bit and upper 32 bit
	All reference assignment are atomic
	e.g 
		object a1 = new object()
		Object a2 =a1;
		
	Volatile is the keyword is used to achieve the atomicity. 
	volatile declared varible reads and write operations through main memory instead of cpu cache memory
	
	static int var=5;  
	 assume that two threads are working on the same class. Both threads run on different processors where each thread has its local copy of var. If any thread modifies its value, the change will not reflect in the original one in the main memory. 
	 It leads to data inconsistency because the other thread is not aware of the modified value.
	 
	 static volatile int var =5;  
	  static variables are class members that are shared among all objects. There is only one copy in the main memory. 
	  The value of a volatile variable will never be stored in the cache. All read and write will be done from and to the main memory.
	  

	  ThreadMXBean class used to programatically detect the deallock
	  Deadlock only detected runtime
	  Alternatively, we can take threaddump and there are some command to look into threaddump to look for deadlock
	  
ReentrantLock have same functionality and property like synchronized block with more control and additional features
Methods for testing internal state of lock
lockinterruptibly()
tryLock()

With lock we can implements across methods
tryLock which will get the reosuce if available
FairLock Management : hands over to long waiting thread
List of waiting thread

Developers need to release manually locka which aquired them where as syncrhonize block take care of itself

Using regular locks with read intensive or more read opertion prevents concurrent reads from shared resources
to overcome that we have ReentrantReadWriteLock which allow read locks to allow multiple threads to read from readblock

These readwrite lockes is used when we have resource with more read intensive than write
When read operation are going on write operation is blocked and vice versa
but multiple read oepration are allowed to enter in the read block

To use wait notify and notifyall we must acquire the lock monitor of that object using synechronze 

Inter Thread communication
wait notify and notifyall join interupt

Using lock interthread communication await singal and singalAll
ReentrantLock rlock =  new ReentrantLock(true);
inal private Condition producedMsg  = lock.newCondition(); 
producedMsg.await();
producedMsg.signal();

Semaphore:-
We can use semaphores to limit the number of concurrent threads accessing a specific resource. Semaphore is not owner of lock
Semaphore s = new Semaphore(2);
s.acquire();
s.release();
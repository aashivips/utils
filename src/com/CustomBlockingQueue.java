package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.MXBean;

public class CustomBlockingQueue<T>
{
	
	
	private List<T>blockingList=null;
	
	private volatile int counter=0;
	
	private final int maxsize;
	
	private int head=0;
	
	private int tail=0;
	
	private ReentrantLock lock=null;
	
	private Lock readLock=null;
	
	private Lock writeLock=null;
	
	
	private Condition isNotFull=null;
	
	private Condition isNotEmpty=null;
	
	
	
	
	public CustomBlockingQueue(int size)
	{
		maxsize=size;
		
		lock=new ReentrantLock(true);
		isNotFull=lock.newCondition();
		isNotEmpty=lock.newCondition();
		blockingList=new ArrayList<>();
		
		
	}
	
	
	public boolean putData(T t)
	{
		boolean result=false;
		try
		{
			lock.lock();
				
			while(counter==maxsize)
			{
				System.err.println(" size more than the fixed value : "+counter+" allowed : "+maxsize+" going to wait : blocking list : "+blockingList+" name : "+Thread.currentThread().getName());
				isNotFull.await();
			}
			result=blockingList.add(t);
			++counter;
			System.out.println("  putdata :  "+t+" added in blocking list : "+blockingList+" add status : "+result);
			isNotEmpty.signal();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
		return result;
	}
	
	public T getData() 
	{
		T t=null;
		try
		{
			lock.lock();
			while(counter==0)
			{
				System.err.println(" list empty: counter : "+counter+" allowed : "+maxsize+" going to wait : "+Thread.currentThread().getName());
				isNotEmpty.await();
			}
			t=blockingList.remove(tail++);
			--counter;
			
			System.out.println("  getData :  "+t+" received from blocking list : "+blockingList+" received: "+t);
			isNotFull.signal();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			lock.unlock();
		}
		return t;
	}
	
	
	 class MyWorker extends  Thread
	 {
		 private CustomBlockingQueue queue=null;
		 private int mode=0;
		 private String name;
		 private String value;

		 public MyWorker(CustomBlockingQueue<T> queue,int mode ,String name,String value)
		 {
			 this.queue=queue;
			 this.mode=mode;
			 this.name=name;
			 setName(name+" mode : "+mode);
			 this.value=value;
			 start();
			
		 }
		 
		@Override
		public void run() 
		{
			if(mode==0)
			{
				queue.putData(value);
			}
			else
			{
				queue.getData();
			}
			
		}
		 
	 }
	 
	 
	 public static void main(String[] args) throws InterruptedException
	 {
		CustomBlockingQueue<String>q=new CustomBlockingQueue(5);
		
		q.new MyWorker(q, 0, "writter_1", "1");
		Thread.sleep(500);
		
		q.new MyWorker(q, 0, "writter_2", "2");
		Thread.sleep(500);
		q.new MyWorker(q, 0, "writter_3", "3");
		Thread.sleep(500);
		q.new MyWorker(q, 0, "writter_4", "4");
		Thread.sleep(500);
		q.new MyWorker(q, 0, "writter_5", "5");
		Thread.sleep(500);
		q.new MyWorker(q, 0, "writter_6", "6");
		Thread.sleep(5000);
		
		q.new MyWorker(q, 1, "reader_1", "1");
		
		
	 }

}

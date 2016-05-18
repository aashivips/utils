package com;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch 
{

	
	
	private final Sync sycn=new Sync();
	
	public void await() throws InterruptedException
	{
		sycn.acquireSharedInterruptibly(0);
	}
	
	public void signal() throws InterruptedException
	{
		sycn.releaseShared(1);
	}
	
	
	
	private class Sync extends AbstractQueuedSynchronizer
	{
		public Sync()
		{
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		protected int tryAcquireShared(int arg) 
		{
			// TODO Auto-generated method stub
			return getState()==1?1:-1;
		}
		
		@Override
		protected boolean tryReleaseShared(int arg) 
		{
			// TODO Auto-generated method stub
			setState(arg);
			return true;
		}
	
		
	}
	
	
	class MyThread extends Thread
	{
		private OneShotLatch latch;
		private final int mode;
		
		
		public MyThread(OneShotLatch latch, int mode,String name)
		{
			this.latch=latch;
			this.mode=mode;
			setName(name);
			start();
			
			
		}
		
		@Override
		public void run() 
		{
				System.out.println(" going with execution for : "+getName());
				if(mode==0)
				{
					try 
					{
						latch.await();
						System.out.println(" await over : "+getName());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					try {
						System.out.println(" going to call signal : "+getName());
						latch.signal();
						
						System.out.println(" called signal "+getName());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException
	{
		
		OneShotLatch latch =new OneShotLatch();
		MyThread w=latch.new MyThread(latch, 0, "Waiter");
		snooze();
		latch.new MyThread(latch, 1, "Releaser");
		snooze();
		
		
		
	}

	private static void snooze() throws InterruptedException 
	{
		Thread.sleep(10000);
	}
	

	

}

package com;

public class CompareObjects 
{

	
	
	public static boolean equals(Object o1,Object o2)
	{
		boolean toReturn=false;
		if(o1!=null && o2 !=null)
		{
			toReturn=o1.equals(o2);
		}
		else if(o1==null && o2==null)
		{
			toReturn=true;
		}
		
		return toReturn;
	}
	
	
	public  static void print()
	{
		System.out.println(" text");
	}
	
	public  static void print1()
	{
		System.out.println(" wrong");
	}
	
	public  static void print2()
	{
		System.out.println(" wrong");
	}

	
	
}

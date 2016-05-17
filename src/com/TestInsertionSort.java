package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.Finishings;

public class TestInsertionSort {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		int [] data={1,6,3,2,9,4};
		
//		performInsertionSort(data);
		
		performQuickSort(data);
	}
	
	
	
	private static void performInsertionSort(int[] data)
	{
		LinkedList<Integer>newlist=new LinkedList<>();
		
		orignal:for(int i=0;i<data.length;i++)
		{
			int number=data[i];
			for(int j=0;j<newlist.size();j++)
			{
				if(number<newlist.get(j))
				{
					newlist.add(j,number);
					continue orignal;
				}
				
				
			}
			
			newlist.add(newlist.size(),number);
		}
		
		
		System.out.println(Arrays.toString(newlist.toArray()));
		
	}
	
	private static void performQuickSort(int data[])
	{
		List<Integer>list=new ArrayList<>();
		for(int i=0;i<data.length;i++)
		{
			list.add(data[i]);
		}
		
		list=quickSort(list);
		
		System.out.println(list.toString());
		
		
		
	}



	private static List<Integer> quickSort(List<Integer> data) 
	{
		int [] toReturn=null;
		if(data.size()<2)
		return data;
		else
		{
			int midindex=0;
			ArrayList<Integer>left=new ArrayList<>();
			ArrayList<Integer>right=new ArrayList<>();
			int value=data.get(midindex);
			for(int i=1;i<data.size();i++)
			{
				if(data.get(i)<value)
				{
					left.add(data.get(i));
				}
				else
				{
					right.add(data.get(i));
					
				}
				
			}
			
	
			List<Integer> finalSortedList=new ArrayList<>();
			finalSortedList.addAll(quickSort(left));
			finalSortedList.add(value);
			finalSortedList.addAll(quickSort(right));
	  
		
			return finalSortedList;
		}
		
		
	}

}

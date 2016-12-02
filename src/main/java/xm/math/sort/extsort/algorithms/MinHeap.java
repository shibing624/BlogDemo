package xm.math.sort.extsort.algorithms;

import java.lang.reflect.Array;

public class MinHeap<E extends Comparable<E>>
{
		
		private E[] values;
		int len;
		int reclaimPos;
		
		public MinHeap(Class<E> clazz,int num)
		{
			
			this.values=(E[])Array.newInstance(clazz,num);
			len=0;
			reclaimPos=num;
			
		}
			
		
		public final E removeMin()
		{
			E ret=values[0];
			values[0]=values[--len];
			shift_down(0);
			return ret;
		}
	
			
		public final boolean isFull()
		{
			return values.length==len;
		}
		//insert to tail
		public final void insert(E val)
		{
			values[len++]=val;
			shift_up(len-1);
			
		}
		
		public final void reverse()
		{
			len=values.length-reclaimPos;
			
			if(len==0)return;
			System.arraycopy(values,reclaimPos, values, 0, len);
			reclaimPos=values.length;
			int pos=(len-1)/2;
			for(int i=pos;i>=0;i--)
			{
				shift_down(i);
			}
		}
		public final void reset()
		{
			len=values.length;
			int pos=(len-1)/2;
			for(int i=pos;i>=0;i--)
			{
				shift_down(i);
			}
		}
		
		public final boolean isEmpty()
		{
			return len==0;
		}
		
		/**
		 * When insert element we  need shiftUp
		 * @param pos
		 */
		private final void shift_up(int pos)
		{
	
			E tmp=values[pos];
			int index=(pos-1)/2;
			while(index>=0)
			{
				if(tmp.compareTo(values[index])<0)
				{
					values[pos]=values[index];
					pos=index;
					if(pos==0)break;
					index=(pos-1)/2;
				}
				else break;
			}
			values[pos]=tmp;
		}
		
		
	    public final boolean addToTail(E ele)
	    {
	    	if(reclaimPos>len)
	    	{
	    		values[--reclaimPos]=ele;
	    		return true;
	    	}return false;
	    }
	    
		private final void shift_down(int pos)
		{
			
			E tmp=values[pos];
			int index=pos*2+1;//use left child
		    while(index<len)//until no child
		    {
		    	if(index+1<len&&values[index+1].compareTo(values[index])<0)//right child is smaller
				{
					index+=1;//switch to right child
				}
		    	if(tmp.compareTo(values[index])>0)
		    	{
		    		values[pos]=values[index];
		    		pos=index;
			    	index=pos*2+1;
			    	
		    	}
		    	else
		    	{
		    		break;
		    	}
		    	
		    }
		    values[pos]=tmp;
		    
					
		}
	}
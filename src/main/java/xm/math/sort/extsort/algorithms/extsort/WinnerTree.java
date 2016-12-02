/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author yovn
 *
 */
public class WinnerTree {
	
	private volatile Record[] nodes;
	private RecordStore[] stores;
	private volatile int[] indexes;
	
	private int lowExt;//how many leafs at the lowest level
	private int offset;//the first lowest level leaf's position
	private int s;
	
	/**
	 * leaf node's number is nLeaf, so branch node's number is nLeaf-1
	 */
	private int nLeaf;
	
	
	
	/**
	 * 
	 */
	public WinnerTree(RecordStore[] stores)throws IOException {
		
		init(stores);
		
	
	}
	
	
	/**
	 * hold zero position for global winner
	 * and leaf start from nodes.length/2
	 */
	private void init(RecordStore[] stores)throws IOException {
		
		
		nLeaf=stores.length;
	
		nodes=new Record[nLeaf];
		indexes=new int[nLeaf-1];
	    this.stores=stores;
		for(int i=0;i<nLeaf;i++)
		{
			nodes[i]=stores[i].readNextRecord();
		}
		
	
		for(s=1;2*s<=nLeaf-1;s+=s);
		
		
		
		/**
		 *  lowExt= total - (2*s-1)
		 *  and total=nLeaf+(nLeaf-1)
		 *  so, lowExt=2*(nLeaf-s)
		 */
		lowExt=2*(nLeaf-s);
		
		/**
		 * because the loser tree was a complete binary tree
		 */
		offset=2*s-1;
		Arrays.fill(indexes,-1);
		for(int i=nLeaf-2;i>=0;i--)
		{
			_play(i);
			
		}
		
			
		
	}

	
	
	private final void _play(int p)
	{
		
		int lc,rc;
	
		int loff=-1,roff=-1;
	
		lc=2*p+1;
		rc=2*p+2;
		
		
		if(lc>=nLeaf-1)
		{
			if(lc>=offset)
			{
				loff=lc-offset;
				
				
			}
			else
			{
				loff=lowExt+lc-nLeaf+1;
			
			}
		}
		else
		{
			loff=indexes[lc];
			
			
		}
		
		
		
		if(rc>=nLeaf-1)
		{
			if(rc>=offset)
			{
				roff=rc-offset;
				
			}
			else
			{
				roff=lowExt+rc-nLeaf+1;
				
			}
		}
		else
		{
			roff=indexes[rc];
			
			
		}
		
		if(nodes[loff].compareTo(nodes[roff])<0)
		{
			indexes[p]=loff;
		}
		else
		{
			indexes[p]=roff;
		}
	
	}
	
	public  Record nextLeastRecord() throws IOException
	{
		int winner=indexes[0];
		Record rec=nodes[winner];
		
		nodes[winner]=stores[winner].readNextRecord();

		play(winner);

		return rec;
	}
	
	private void play(int child) {
		// nodes's index was start from 0.

		int p;
		if(child<lowExt)
		{
			
			p=(offset+child-1)/2;
			
		}
		else
		{
			p=(child-lowExt+nLeaf-2)/2;
			
		}
		for(;p>0;p=(p-1)/2)
		{
			_play(p);
		}
		_play(0);

	}

}

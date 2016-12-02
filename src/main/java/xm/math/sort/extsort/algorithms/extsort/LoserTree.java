/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public class LoserTree {

	/**
	 * all leaf nodes 
	 * indexes[0] keep the global winner's index
	 */
	private volatile Record[] nodes;
	private RecordStore[] stores;
	private volatile int[] indexes;
	
	private int lowExt;//how many leafs at the lowest level
	private int offset;//the first lowest level leaf's position
	
	
	/**
	 * leaf node's number is nLeaf, so branch node's number is nLeaf-1
	 */
	private int nLeaf;
	private int s;//2^log(nLeaf-1)
	/**
	 * 
	 */
	public LoserTree(RecordStore[] stores)throws IOException {
		
		init(stores);
		
	
	}

	/**
	 * hold zero position for global winner
	 * and leaf start from nodes.length/2
	 */
	private void init(RecordStore[] stores)throws IOException {
		
		
		nLeaf=stores.length;
	
		nodes=new Record[nLeaf];
		indexes=new int[nLeaf];
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
		
		
		int i;
	
		
		for(i=2;i<=lowExt;i+=2)
		{
			play((offset+i)/2,i-1,i);

		}
		if(nLeaf%2==1)
		{
			//now indexes[(nLeaf-1)/2] keep the winner of its left siblings
			play(nLeaf/2,indexes[(nLeaf-1)/2],lowExt+1); 
			i=lowExt+3;
		}
		else
		{
			i=lowExt+2;
		}
		
		for(;i<=nLeaf;i+=2)play((i-lowExt+nLeaf-1)/2,i-1,i);
		
		
	}
	
	
	
	private void play(int parentIndex, int leftChild, int rightChild) {
		// nodes's index was start from 1.
		
		int winner=rightChild;
		int loser=leftChild;
		if(leftChild>0&&rightChild>0&&nodes[leftChild-1].compareTo(nodes[rightChild-1])<0)
		{
			winner=leftChild;
			loser=rightChild;
		}
		indexes[parentIndex]=loser;
		parentIndex/=2;
		while(parentIndex>0&&(parentIndex%2==1))
		{
			//indexes[parentIndex]==0 means its empty
			if(indexes[parentIndex]!=0&&nodes[indexes[parentIndex]-1].compareTo(nodes[winner-1])<0)
			{
				loser=winner;
				winner=indexes[parentIndex];
				
				indexes[parentIndex]=loser;
			}
			else if(indexes[parentIndex]==0)
			{
				//first one?, we should go ahead and occupy more!!!
				indexes[parentIndex]=winner;
			}
			parentIndex/=2;
		}
		indexes[parentIndex]=winner;
	}
	
	
	
	public  Record nextLeastRecord() throws IOException
	{
		int winner=indexes[0];
		Record rec=nodes[winner-1];
		
		nodes[winner - 1] = stores[winner - 1].readNextRecord();

		if (winner <= lowExt) {

			replay((offset + winner) / 2, winner);
		} else {

			replay((winner - lowExt + nLeaf - 1) / 2, winner);
		}

		return rec;
	}
	
	private final void replay(int parentIndex, int child)
	{
		int loser=indexes[parentIndex];
		int winner=child;
		if(nodes[loser-1].compareTo(nodes[winner-1])<0)
		{
			winner=loser;
			loser=child;
		}
		indexes[parentIndex]=loser;
		parentIndex/=2;
		while(parentIndex>0)
		{
			if(nodes[indexes[parentIndex]-1].compareTo(nodes[winner-1])<0)
			{
				loser=winner;
				winner=indexes[parentIndex];
				indexes[parentIndex]=loser;
			
			}
						
			
			parentIndex/=2;
		}
		indexes[0]=winner;
		
		
	}

}

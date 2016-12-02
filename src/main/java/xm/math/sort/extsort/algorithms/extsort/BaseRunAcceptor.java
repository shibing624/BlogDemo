/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public abstract class BaseRunAcceptor implements RunAcceptor {

	protected RecordStore[] stores;
	protected int index;
	
	
	private boolean active;
	
	
	protected BaseRunAcceptor()
	{
		stores=new RecordStore[0];
		index=-1;
		active=false;
	}
	
	/* (non-Javadoc)
	 * @see algorithms.extsort.RunAcceptor#acceptRecord(algorithms.extsort.Record)
	 */
	@Override
	public void acceptRecord(Record recs) throws IOException {
		if(!active)
		{
			throw new IOException("Not open");
		}
		
	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.RunAcceptor#closeRun()
	 */
	@Override
	public void closeRun() {
		active=false;

	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.RunAcceptor#getNumProductedStores()
	 */
	@Override
	public final int getNumProductedStores() {

		return index+1;
	}

	
	public final RecordStore[] getProductedStores() {

		RecordStore[] ret=new RecordStore[index+1];
		System.arraycopy(stores, 0, ret, 0, index+1);
		return ret;
	}
	/* (non-Javadoc)
	 * @see algorithms.extsort.RunAcceptor#getProductedStore(int)
	 */
	@Override
	public final RecordStore getProductedStore(int index) {
		
		return stores[index];
	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.RunAcceptor#startNewRun()
	 */
	@Override
	public final void startNewRun() {
	 
	    if(index>=stores.length-1)
	    {
	    	increamentStoresBy(stores.length/2+1);
	    }
	    initStore(++index);
	  
	    active=true;

	}

	protected abstract void initStore(int i);

	protected  abstract void increamentStoresBy(int len);

}

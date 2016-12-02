/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public class MemResultAcceptor implements ResultAcceptor {

	int count=0;
	
	Record prev=null;
	/* (non-Javadoc)
	 * @see algorithms.extsort.ResulterAcceptor#acceptRecord(algorithms.extsort.Record)
	 */
	@Override
	public void acceptRecord(Record rec) throws IOException {
	    count++;
	    if(prev==null)
	    {
	    	prev=rec;
	    }
	    else if(prev.compareTo(rec)>0)
	    {
	    	System.err.println(" sorted error!!!");
	    	System.exit(-1);
	    }
	    prev=rec;
//	    System.out.print(rec+",");
	    

	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.ResulterAcceptor#end()
	 */
	@Override
	public void end() throws IOException {
		System.out.println("\nend,totally :"+count+" records!");

	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.ResulterAcceptor#start()
	 */
	@Override
	public void start() throws IOException {
		System.out.println("begin:");

	}

}

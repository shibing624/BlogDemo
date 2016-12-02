/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public interface ResultAcceptor {
	
	
	void start()throws IOException;
	void end()throws IOException;
	void acceptRecord(Record rec)throws IOException;

}

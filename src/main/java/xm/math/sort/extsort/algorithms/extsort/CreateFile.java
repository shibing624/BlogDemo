/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * @author yovn
 *
 */
public class CreateFile {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		OutputStream out=new FileOutputStream("data/test_sort.txt");
		PrintStream ps=new PrintStream(new BufferedOutputStream(out,12*1024));

		Random r=new Random();
		
		for(int i=0;i<10000;i++)
		{
			ps.println(r.nextInt(10000000)+"");
		}
		ps.close();
	}

}

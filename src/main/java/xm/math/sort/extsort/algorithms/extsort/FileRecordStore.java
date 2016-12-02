/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;



/**
 * @author yovn
 *
 */
public class FileRecordStore implements RecordStore ,ResultAcceptor{

	private static class _Record implements Record
	{

		static final _Record NULL_RECORD=new _Record(Integer.MAX_VALUE,"");
		int value;
		private String txt;
		_Record(int v,String txt)
		{
			value=v;
			this.txt=txt;
		}
		@Override
		public boolean isNull() {
			
			return this==NULL_RECORD;
		}

		@Override
		public int compareTo(Record o) {
			_Record other=(_Record)o;
			if(other==this)return 0;
		
			return value-other.value;
		}
		
		
		public String toString()
		{
			if(this==NULL_RECORD)return "NULL_RECORD";
			return txt;
		}
		
	}
	
	

	private String fileName;
	private BufferedReader reader;
	private PrintStream ps;
	
	boolean eof;
	


	int count=0;
	
	Record prev=null;
	
	public FileRecordStore(String name)
	{
	
		fileName=name;
		
		
	}
	
	public void storeRecord(Record r)throws IOException
	{
		if(ps==null)
		{
			OutputStream out=new FileOutputStream(fileName);
			ps=new PrintStream(new BufferedOutputStream(out,12*1024));
		}
		ps.println(r.toString());
	}
	
	
	

	public void compact()
	{
		if (ps != null) {
			ps.flush();
			ps.close();
			ps = null;
		}
		if(reader!=null)
		{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader=null;
		}
		
	}
	

	

	
	
	/* (non-Javadoc)
	 * @see algorithms.extsort.RecordStore#readNextRecord()
	 */
	@Override
	public Record readNextRecord() throws IOException {
	    if(eof)  return _Record.NULL_RECORD;
		if(reader==null)
	   {
		   InputStream in=new FileInputStream(fileName);
		   reader=new BufferedReader(new InputStreamReader(in),12*1024);
	   }
	   if(!reader.ready())
	   {
		   eof=true;
		   return _Record.NULL_RECORD;
	   }
	   String line=reader.readLine();
	   String head=line.trim();
	   int val=Integer.valueOf(head);
	   _Record ret=new _Record(val,line);
	   return ret;
		
	}

	@Override
	public void acceptRecord(Record rec) throws IOException {
		count++;
		if (prev == null) {
			prev = rec;
		} else if (prev.compareTo(rec) > 0) {
			throw new IOException(" sorted error!!!");
			
		}
		ps.println(rec.toString());
		prev=rec;
	}

	@Override
	public void end() throws IOException {
		
		if (ps != null) {
			ps.flush();
			ps.close();
			ps = null;
		}
	}

	@Override
	public void start() throws IOException {
		if(ps==null)
		{
			OutputStream out=new FileOutputStream(fileName);
			ps=new PrintStream(new BufferedOutputStream(out,12*1024));
		}
		
	}

	@Override
	public void destroy() {
		compact();
		File f=new File(fileName);
		f.delete();
		
	}

	
	

	
}

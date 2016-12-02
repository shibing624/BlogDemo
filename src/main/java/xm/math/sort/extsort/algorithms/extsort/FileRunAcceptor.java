/**
 * 
 */
package xm.math.sort.extsort.algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public class FileRunAcceptor extends BaseRunAcceptor {

	int count=0;
	private String name;
	/**
	 * 
	 */
	public FileRunAcceptor(String fileName) {
		stores=new FileRecordStore[50];
		name=fileName;
	}
	
	

	@Override
	public void acceptRecord(Record rec) throws IOException {
		count++;
		((FileRecordStore)stores[index]).storeRecord(rec);
	}



	@Override
	public void closeRun() {
		System.out.println("\nend run:("+(index+1)+")total:"+count);

	   ((FileRecordStore)stores[index]).compact();
	}



	/* (non-Javadoc)
	 * @see algorithms.extsort.BaseRunAcceptor#increamentStoresBy(int)
	 */
	@Override
	protected void increamentStoresBy(int len) {
		RecordStore[] tmp=new RecordStore[len+stores.length];
		System.arraycopy(stores, 0, tmp, 0, stores.length);
		stores=tmp;

	}

	/* (non-Javadoc)
	 * @see algorithms.extsort.BaseRunAcceptor#initStore(int)
	 */
	@Override
	protected void initStore(int i) {
//		count=0;
		stores[i]=new FileRecordStore(name+"_"+i);
		System.out.println("start new run:"+name+"_"+i);
	}

}

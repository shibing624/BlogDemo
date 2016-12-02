/**
 *
 */
package xm.math.sort.extsort.algorithms.extsort;

import xm.math.sort.extsort.algorithms.MinHeap;

import java.io.IOException;


/**
 * @author yovn
 */
public class ExternalSortMain {


    public void sort(int heapSize, RecordStore source, RunAcceptor mediator, ResultAcceptor ra) throws IOException {
        MinHeap<Record> heap = new MinHeap<Record>(Record.class, heapSize);
        for (int i = 0; i < heapSize; i++) {
            Record r = source.readNextRecord();
            if (r.isNull()) break;
            heap.insert(r);
        }

        Record readR = source.readNextRecord();
        while (!readR.isNull() || !heap.isEmpty()) {

            Record curR = null;
            //begin output one run
            mediator.startNewRun();
            while (!heap.isEmpty()) {
                curR = heap.removeMin();

                mediator.acceptRecord(curR);

                if (!readR.isNull()) {
                    if (readR.compareTo(curR) < 0) {
                        heap.addToTail(readR);
                    } else
                        heap.insert(readR);
                }
                readR = source.readNextRecord();

            }
            //done one run
            mediator.closeRun();

            //prepare for next run
            heap.reverse();
            while (!heap.isFull() && !readR.isNull()) {

                heap.insert(readR);
                readR = source.readNextRecord();

            }


        }
        RecordStore[] stores = mediator.getProductedStores();
//		LoserTree  lt=new LoserTree(stores);
        WinnerTree lt = new WinnerTree(stores);

        Record least = lt.nextLeastRecord();
        ra.start();
        while (!least.isNull()) {
            ra.acceptRecord(least);
            least = lt.nextLeastRecord();
        }
        ra.end();

        for (int i = 0; i < stores.length; i++) {
            stores[i].destroy();
        }
    }


    public static void main(String[] args) throws IOException {
//		RecordStore store=new MemRecordStore(60004,true);
//		RunAcceptor mediator=new MemRunAcceptor();
//		ResultAcceptor ra=new MemResultAcceptor();
        ExternalSortMain sorter = new ExternalSortMain();

        RecordStore store = new FileRecordStore("data/test_sort.txt");
        RunAcceptor mediator = new FileRunAcceptor("data/test_sort");
        ResultAcceptor ra = new FileRecordStore("data/test_sorted.txt");


        sorter.sort(700, store, mediator, ra);
    }

}

package xm.math.sort.extsort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * Purely informational so use at your own risk, it is not optimized and it can
 * only handle data sets that have a size that is are divible by BUFFER_SIZE.
 *
 * @author xuming
 */
public class ExternalSorter {

    public static int BUFFER_SIZE = 10;

    public File sort(File file) throws IOException {
        ArrayList<File> files = split(file);
        return process(files);
    }

    // recursive method to merge the lists until we are left with a
    // single merged list
    private File process(ArrayList<File> list) throws IOException {
        if (list.size() == 1) {
            return list.get(0);
        }
        ArrayList<File> inter = new ArrayList<File>();
        for (Iterator<File> itr = list.iterator(); itr.hasNext(); ) {
            File one = itr.next();
            if (itr.hasNext()) {
                File two = itr.next();
                inter.add(merge(one, two));
            } else {
                return one;
            }
        }
        return process(inter);
    }

    /**
     * Splits the original file into a number of sub files.
     */
    private ArrayList<File> split(File file) throws IOException {
        ArrayList<File> files = new ArrayList<File>();
        int[] buffer = new int[BUFFER_SIZE];
        FileInputStream fr = new FileInputStream(file);
        boolean fileComplete = false;
        while (!fileComplete) {
            int index = buffer.length;
            for (int i = 0; i < buffer.length && !fileComplete; i++) {
                buffer[i] = readInt(fr);
                if (buffer[i] == -1) {
                    fileComplete = true;
                    index = i;
                }
            }
            if (buffer[0] > -1) {
                Arrays.sort(buffer, 0, index);
                File f = new File("set" + new Random().nextInt());
                FileOutputStream writer = new FileOutputStream(f);
                for (int j = 0; j < index; j++) {
                    writeInt(buffer[j], writer);
                }
                writer.close();
                files.add(f);
            }

        }
        fr.close();
        return files;
    }

    /**
     * Merges two sorted files into a single file.
     *
     * @param one
     * @param two
     * @return
     * @throws IOException
     */
    private File merge(File one, File two) throws IOException {
        FileInputStream fis1 = new FileInputStream(one);
        FileInputStream fis2 = new FileInputStream(two);
        File output = new File("data/merged" + new Random().nextInt());
        FileOutputStream os = new FileOutputStream(output);
        int a = readInt(fis1);
        int b = readInt(fis2);
        boolean finished = false;
        while (!finished) {
            if (a != -1 && b != -1) {
                if (a < b) {
                    writeInt(a, os);
                    a = readInt(fis1);
                } else {
                    writeInt(b, os);
                    b = readInt(fis2);
                }
            } else {
                finished = true;
            }

            if (a == -1 && b != -1) {
                writeInt(b, os);
                b = readInt(fis2);
            } else if (b == -1 && a != -1) {
                writeInt(a, os);
                a = readInt(fis1);
            }
        }
        os.close();
        return output;
    }

    private void writeInt(int value, FileOutputStream merged)
            throws IOException {
        merged.write(value);
        merged.write(value >> 8);
        merged.write(value >> 16);
        merged.write(value >> 24);
        merged.flush();
    }

    private static int readInt(FileInputStream fis) throws IOException {
        int buffer = fis.read();
        if (buffer == -1) {
            return -1;
        }
        buffer |= (fis.read() << 8);
        buffer |= (fis.read() << 16);
        buffer |= (fis.read() << 24);
        return buffer;
    }

    private static void dumpFile(File f)
            throws IOException {
        FileInputStream fis = new FileInputStream(f);
        int i = readInt(fis);
        while (i != -1) {
            System.out.println(Integer.toString(i));
            i = readInt(fis);
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File file = new File("data/set.txt");
        Random random = new Random(System.currentTimeMillis());
        FileOutputStream fw = new FileOutputStream(file);
        for (int i = 0; i < BUFFER_SIZE * 3; i++) {
            int ger = random.nextInt();
            ger = ger < 0 ? -ger : ger;
            fw.write(ger);
            fw.write(ger >> 8);
            fw.write(ger >> 16);
            fw.write(ger >> 24);
        }
        fw.close();
        ExternalSorter externalSorter = new ExternalSorter();
        System.out.println("Original:");
        dumpFile(file);
        File f = externalSorter.sort(file);
        System.out.println("Sorted:");
        dumpFile(f);

    }


}

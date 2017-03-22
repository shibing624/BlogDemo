package xm.usepy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author xuming
 */
public class Jpython {
    public static void main(String[] args) {
        try {
            System.out.println("start");
            Process pr = Runtime.getRuntime().exec("python  D:\\PyCredit\\Blog\\BlogCode-source\\data\\extract_file.py");

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

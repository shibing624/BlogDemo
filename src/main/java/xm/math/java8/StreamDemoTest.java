package xm.math.java8;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author xuming
 */
public class StreamDemoTest {
    public static void main(String[] args) {
        String str1 = new String("xx");
        String str2 = new String("xx");
        System.out.println(str1 == str2);

        Map<String ,String> map = new IdentityHashMap<String ,String>();
        map.put(str1, "hello");
        map.put(str2, "world");


        for(Map.Entry<String,String> entry : map.entrySet())
        {
            System.out.println(entry.getKey()+"   " + entry.getValue());
        }

        String[] s ={"ds","dds"};
        int b = Arrays.binarySearch(s,"ds");
        System.out.println(b+"");

        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

// 以前的循环方式
        for (String player : players) {
            System.out.print(player + "; ");
        }

// 使用 lambda 表达式以及函数操作(functional operation)
        players.forEach((player) -> System.out.println("lambda:"+player + "; "));

// 在 Java 8 中使用双冒号操作符(double colon operator)
        players.forEach(System.out::println);

        Dic();
        oldA();
        rebuildA();
        rebuildB();
    }

    public static void Dic(){
        Dictionary cities = new Hashtable();

        cities.put("New York", "USA");

        cities.put("Toronto", "Canada");

        cities.put("Manchester", "UK");

        cities.put("Berlin", "Germany");

        for (Enumeration city = cities.keys(); city.hasMoreElements();) {

            String key = (String)city.nextElement();

            System.out.println("Key: "+key +"； Value: " + cities.get(key));
        }

    }

    public static void oldA(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        for (int number : numbers) {
            if (number % 2 == 0) {
                int n2 = number * 2;
                if (n2 > 5) {
                    System.out.println(n2);
                    break;
                }
            }
        }

    }


    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static int doubleIt(int number) {
        return number * 2;
    }

    public static boolean isGreaterThan5(int number) {
        return number > 5;
    }

    public static void rebuildA(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> l1 = new ArrayList<Integer>();
        for (int n : numbers) {
            if (isEven(n)) l1.add(n);
        }
        List<Integer> l2 = new ArrayList<Integer>();
        for (int n : l1) {
            l2.add(doubleIt(n));
        }

        List<Integer> l3 = new ArrayList<Integer>();
        for (int n : l2) {
            if (isGreaterThan5(n)) l3.add(n);
        }

        System.out.println(l3.get(0)+" "+l3.get(1));

    }
    public static void rebuildB(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Stream<Integer> numStream  = numbers.stream()
                .filter(num-> isEven(num))
                .map(num->doubleIt(num))
                .filter(num->isGreaterThan5(num));
        String numstr = numStream.findFirst().toString();
        System.out.println(numstr);

    }
}
package xm.math.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


/**
 * lambda
 * Created by xuming on 2016/8/8.
 */
public class LambdaDemo {
    public static void main(String[] args) {
        String[] newWay = "Improving code with Lambda expressions in Java 8".split(" ");
        Arrays.sort(newWay, (s1, s2) -> {
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        });
        System.out.println(String.join("/ ", newWay));


        // old way:
        Runnable oldRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": Old Runnable");
            }
        };
        Runnable newRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + ": New Lambda Runnable");
        };
        new Thread(oldRunnable).start();
        new Thread(newRunnable).start();

        // Java 8之前：
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String f : features) {
            System.out.println("before 8:" + f);
        }

        //Java 8
        features.forEach(n -> System.out.println("after 8:" + n));

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示
        features.forEach(System.out::println);

        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLength = (n) -> n.length() == 4;
        languages
                .stream()
                .filter(startsWithJ.and(fourLetterLength))
                .forEach((n) -> System.out.println("array, which starts with J and noly 4 length:" + n));


    }

    public static void filter(List<String> names, Predicate condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    public static void filter1(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }
}

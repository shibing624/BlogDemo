package xm.math.java8;


import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.stream;


/**
 * StreamAPI
 * Created by xuming on 2016/8/8.
 */
public class StreamDemo {
    private Class<?> returnType;

    private static <T> BinaryOperator<T> throwingMerger1() {
        return (u, v) -> {
            throw new IllegalStateException(format("Duplicate key %s", u));
        };
    }

    public static void main(String... args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 4, 45, 7, 20);
        Stream<Integer> stream = numbers.stream();
        stream.filter(x -> x % 2 == 0).map(x -> x * x).forEach(System.out::println);

        // 内部迭代代码 从而允许类库进行各种各样的优化（例如乱序执行、惰性求值和并行等等）。总的来说，内部迭代使得外部迭代中不可能实现的优化成为可能。
        numbers.forEach(num -> System.out.print(num + ","));
        // 流的操作可以被组合成流水线（Pipeline）
        numbers.stream()
                .filter(n -> n.toString().length() == 1)
                .forEach(i -> System.out.println(i + ";"));

        List<String> strList = numbers
                .stream()
                .filter(i -> i.toString().length() == 2)
                .map(i -> String.valueOf(i))
                .collect(Collectors.toList());

        System.out.println(strList);

//        numbers.forEach(System.out::println);

        int sum = numbers.stream()
                .filter(i -> i.toString().length() == 1)
                .mapToInt(i -> i.intValue())
                .reduce(0, Integer::sum);
        System.out.println(sum + ":sum of num len is 1 .");
        String[] strs = {"s", "sd", "ssdf", "qwer"};

        Optional<Integer> firstVal = numbers.stream()
                .filter(i -> i.toString().length() == 1)
                .filter(i -> i.equals("1"))
                .findAny();

        int val = numbers.stream()
                .filter(i -> i.toString().length() == 1)
                .filter(i -> i == 7)
                .mapToInt(i -> i.intValue())
                .sum();
        System.out.println(val + ":val");

        // parallelism
        double sumpar = numbers.parallelStream()
                .filter(i -> i.toString().length() == 1)
                .mapToDouble(i -> i.intValue())
                .sum();
        System.out.println(sumpar + ":sumpar");
        StreamDemo demo = new StreamDemo();
//        int sumAB=(int x,int y)->{x+y};
        List<String> f = new ArrayList<>();
        f.add("df");
        String user = f.stream().mapToInt(i -> i.length()).toString();
        FileFilter java = i -> i.getName().endsWith(".jaba");
        List<String> ls = Collections.EMPTY_LIST;
        List<Integer> li = Collections.emptyList();
        Map<String, Integer> m1 = new HashMap<>();
        Map<Integer, String> m2 = new HashMap<>();

        Comparator<String> c = (String s1, String s2) -> s1.compareToIgnoreCase(s2);

        Boolean flag = false;
        Set<Integer> si = flag ? Collections.singleton(23) : Collections.emptySet();

        System.out.println(user);
        int sumnums = 0;
        List<Integer> aList = new ArrayList<>();


        numbers.stream().filter(i -> i.toString().length() == 1).forEach(i -> aList.add(i));

        System.out.println(aList.size() + aList.toString());

        int sumn = numbers
                .stream()
                .mapToInt(i -> i.intValue())
                .reduce(0, (x, y) -> x + y);
        System.out.println(sumn + ":sum");
    }


    public Method method(Class<Object> enclosingInfo, Object[] parameterClasses) {
        for (Method method : enclosingInfo.getEnclosingClass().getDeclaredMethods()) {
            if (method.getName().equals(enclosingInfo.getName())) {
                Class<?>[] candidateParamClasses = method.getParameterTypes();

                if (candidateParamClasses.length == parameterClasses.length) {
                    boolean matches = true;
                    for (int i = 0; i < candidateParamClasses.length; i += 1) {
                        if (!candidateParamClasses[i].equals(parameterClasses[i])) {
                            matches = false;
                            break;
                        }
                    }

                    if (matches) { // finally, check return type
                        if (method.getReturnType().equals(returnType)) {
                            return method;
                        }
                    }
                }
            }
        }
        throw new InternalError("Enclosing method not found");
    }

    public Method methodStream(Class<Object> enclosingInfo, Object[] parameterClasses) {
        return stream(enclosingInfo.getEnclosingClass().getDeclaredMethods())
                .filter(i -> Objects.equals(i.getName(), enclosingInfo.getName()))
                .filter(i -> Arrays.equals(i.getParameterTypes(), parameterClasses))
                .filter(i -> Objects.equals(i.getReturnType(), returnType))
                .findFirst()
                .orElseThrow(() -> new InternalError("error not found enclosing method"));
    }

    static String[] strs1 = {"df14s", "dfssss", "ass"};
    static String[] strs2 = {"dfs1", "212", "1232"};
    static List<String[]> numbers = Arrays.asList(strs1, strs2);

    public static List<String> compareFav(String[] ss) {

        List<String> numList = new ArrayList<>();

        for (String[] nums : numbers) {
            numList = Arrays.asList(nums);

            boolean hasFavorite = false;
            for (String n : numList) {
                if (n.length() >= 4) {
                    hasFavorite = true;
                    break;
                }
            }
            if (hasFavorite)
                numList.add("new ");
        }
        return numList;
    }



}

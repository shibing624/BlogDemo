package xm.java8;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Fib
 * Created by xuming on 2016/8/8.
 */
public class FibonacciSupplier implements Supplier<Long> {
    long a = 0;
    long b = 1;

    @Override
    public Long get() {
        long x = a + b;
        a = b;
        b = x;
        return a;
    }

    public static void main(String[] args) {
        Stream<Long> fib = Stream.generate(new FibonacciSupplier());
        fib.limit(10).forEach(System.out::println);
    }

}


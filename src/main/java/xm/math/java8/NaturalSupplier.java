package xm.math.java8;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 自然数
 * Created by xuming on 2016/8/8.
 */
public class NaturalSupplier implements Supplier<Long> {
    long value = 0;

    public Long get() {
        this.value = this.value + 1;
        return this.value;
    }

    public static void main(String[] args) {
        Stream<Long> natural = Stream.generate(new NaturalSupplier());
        natural.map((x) -> {
            return x * x;
        }).limit(10).forEach(System.out::println);
    }


}


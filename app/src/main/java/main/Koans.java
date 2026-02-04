package main;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Koans {
    private final static int MAX_ARRAY_LENGTH = 10000;

    public static int getMaxArrayLength() {
        return MAX_ARRAY_LENGTH;
    }

    public static void mapArray(int[] array, Function<Integer, Integer> f) {
        for (int i = 0; i < array.length; i++) {
            array[i] = f.apply(array[i]);
        }

    }

    public static double[] fillArray(int length, Supplier<Double> s) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be zero or greater.");
        }

        if (length > MAX_ARRAY_LENGTH) {
            throw new IllegalArgumentException("Length exceeds maximum allowed size.");
        }

        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = s.get();
        }
        return array;
    }

    public static int[] iterateFunction(int length, int first, Function<Integer, Integer> f) {
        int[] array = new int[length];
        int value = first;

        for (int i = 0; i < length; i++) {
            array[i] = value;
            value = f.apply(value);
        }
        return array;
    }

    public static Function<Double, Double> createMultiplier(double d) { // DoubleUnaryOperator: more efficient, bc no
                                                                        // unboxing of Double, Double
        return (x) -> d * x;
    }

    public static void forEachArray(String[] strings, Consumer<String> consumer) {
        for (String s : strings) {
            consumer.accept(s);
        }
    }

    public static <T> Predicate<T> duplicateChecker() {
        // shorter version:
        // HashSet<T> cache = new HashSet<>();
        // return x -> !cache.add(x);

        // even shorter:
        // Predicate also contains:
        // Predicate.not(new HashSet<>()::add);

        return new Predicate<T>() {
            private final java.util.Set<T> seen = new java.util.HashSet<>();

            @Override
            public boolean test(T t) {
                return seen.add(t);
            }
        };
    }
}

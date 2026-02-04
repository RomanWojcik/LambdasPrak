package main;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KoansTest {
    int[] array1;
    int[] array2;

    Function<Integer, Integer> increment = x -> x + 1;
    Function<Integer, Integer> square = x -> x * x;
    Function<Integer, Integer> doubleFunction = x -> 2 * x;

    Supplier<Double> piSupplier = () -> Math.PI;
    Supplier<Double> randomSupplier = () -> Math.random();

    @BeforeEach
    void setup() {
        array1 = new int[] { 0, 1, 2, 3, 4 };
        array2 = new int[] { 0, -1, -2, -3, -4 };
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    // mapArray tests:

    /**
     * Test mapArray with increment function
     */
    @Test
    void mapArray_IncrementFuncTest() {
        Koans.mapArray(array1, increment);
        Koans.mapArray(array2, increment);
        int[] expected1 = new int[] { 1, 2, 3, 4, 5 };
        int[] expexted2 = new int[] { 1, 0, -1, -2, -3 };

        assertArrayEquals(expected1, array1);
        assertArrayEquals(expexted2, array2);
    }

    /**
     * Test mapArray with square function
     */
    @Test
    void mapArray_SquareFuncTest() {
        Koans.mapArray(array1, square);
        Koans.mapArray(array2, square);
        int[] expected = new int[] { 0, 1, 4, 9, 16 };

        assertArrayEquals(expected, array1);
        assertArrayEquals(expected, array2);

    }

    /**
     * Test mapArray with square function, checking for overflow error.
     * This test will check that mapArray throws an IllegalArgumentException with
     * the message "Overflow"
     * when given an array that contains a value that will cause overflow when
     * squared.
     */
    @Test
    void mapArray_SquareFuncOverflowTest() {
        int[] overflowArray = new int[] { 46341, 1, 2 }; // 46341^2 > Integer.MAX_VALUE
        try {
            Koans.mapArray(overflowArray, square);
        } catch (IllegalArgumentException e) {
            assert (e.getMessage().equals("Overflow"));
        }
    }

    // fillArray tests:
    @Test
    void fillArray_PITest() {
        double[] expected0 = new double[] {};
        double[] expected1 = new double[] { Math.PI };
        double[] expected2 = new double[] { Math.PI, Math.PI };
        assertArrayEquals(expected0, Koans.fillArray(0, piSupplier));
        assertArrayEquals(expected1, Koans.fillArray(1, piSupplier));
        assertArrayEquals(expected2, Koans.fillArray(2, piSupplier));
    }

    @Test
    void fillArray_NegativeLengthTest() {
        assertThrows(IllegalArgumentException.class, () -> Koans.fillArray(-1, piSupplier));
    }

    @Test
    void fillArray_ZeroLengthTest() {
        double[] result = Koans.fillArray(0, piSupplier);
        assertArrayEquals(new double[] {}, result);
    }

    @Test
    void fillArray_LimitExceededTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Koans.fillArray(Koans.getMaxArrayLength() + 1, piSupplier));
    }

    @Test
    void fillArray_RandomSupplierTest() {
        double[] result;
        int repeat = 100;

        for (int i = 0; i < repeat; i++) {
            result = Koans.fillArray(5, randomSupplier);
            for (int j = 0; j < result.length; j++) {
                assertTrue(result[j] >= 0.0 && result[j] < 1.0, "Value out of range: " + result[j]);

            }
        }
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    // iterateFunction tests:

    @Test
    void iterateFunction_IncrementFuncTest() {
        int[] result = Koans.iterateFunction(5, 0, increment);
        int[] expected = new int[] { 0, 1, 2, 3, 4 };
        assertArrayEquals(expected, result);
    }

    @Test
    void iterateFunction_DoubleFuncTest() {
        int[] result = Koans.iterateFunction(5, 1, doubleFunction);
        int[] expected = new int[] { 1, 2, 4, 8, 16 };

        int[] resultNegative = Koans.iterateFunction(5, -1, doubleFunction);
        int[] expectedNegative = new int[] { -1, -2, -4, -8, -16 };

        assertArrayEquals(expected, result);
        assertArrayEquals(expectedNegative, resultNegative);
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    // createMultiplier tests:

    @Test
    void createMultiplier_Test() {
        Function<Double, Double> doubler = Koans.createMultiplier(2.0);
        Function<Double, Double> triplerNegative = Koans.createMultiplier(-3.0);
        Function<Double, Double> zeroer = Koans.createMultiplier(0.0);

        assertTrue(doubler.apply(2.0) == 4.0);
        assertTrue(doubler.apply(-3.0) == -6.0);
        assertTrue(triplerNegative.apply(3.0) == -9.0);
        assertTrue(triplerNegative.apply(-4.0) == 12.0);
        assertTrue(zeroer.apply(5.0) == 0.0);
        assertTrue(zeroer.apply(-5.0) == 0.0);
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    @Test
    void forEachArray_Test() {
        // This is just a placeholder to show where additional tests could go.
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    // forEachArray tests:
    @Test
    void forEachArray_StringBuilderTest1() {
        String[] input = new String[] { "a", "b", "c" };
        StringBuilder result = new StringBuilder();
        Koans.forEachArray(input, s -> result.append(s));
        String expected = "abc";
        assertEquals(expected, result.toString());
    }

    @Test
    void forEachArray_StringBuilderTest2() {
        String[] input = new String[] { "a", "b", "c" };
        StringBuilder result = new StringBuilder();
        Koans.forEachArray(input, s -> result.append(s).append(" "));
        String expected = "a b c ";
        assertEquals(expected, result.toString());
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    // duplicateChecker tests:
    @Test
    void duplicateChecker_Test() {
        Predicate<Integer> checker = Koans.duplicateChecker();

        assertTrue(checker.test(1)); // First time seeing 1 --> false
        assertTrue(checker.test(2)); // First time seeing 2 --> false
        assertTrue(!checker.test(1)); // Duplicate 1 --> true
        assertTrue(checker.test(3)); // First time seeing 3 --> false
        assertTrue(!checker.test(2)); // Duplicate 2 --> true
    }
}

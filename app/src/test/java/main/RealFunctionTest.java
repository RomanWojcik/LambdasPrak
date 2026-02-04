package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RealFunctionTest {

    @Test
    public void testConstantFunction() {
        RealFunction constantFunction = RealFunction.constant(5.0);
        assertEquals(5.0, constantFunction.apply(5.0), 1e-9);
    }

    @Test
    public void composeTest() {
        RealFunction square = (x) -> x * x;
        RealFunction increment = (x) -> x + 1;
        RealFunction squareThenIncrement = square.compose(increment);
        RealFunction expected = (x) -> (x * x) + 1;

        assertEquals(expected.apply(5), squareThenIncrement.apply(5.0), 1e-9);
    }

    @Test
    public void multiplyWithTest() {
        RealFunction f = (x) -> x;
        RealFunction g = (x) -> 2 * x;
        RealFunction h = (x) -> x * x;
        RealFunction expected = (x) -> x * (2 * x) * (x * x); // =5*(10)*(25) = 1250 for x = 5
        RealFunction expected2 = (x) -> RealFunction.sine(1, 1).apply(x) * RealFunction.exp().apply(x);

        assertEquals(expected.apply(5), f.multiplyWith(g, h).apply(5));
        assertEquals(expected2.apply(1.5), RealFunction.sine(1, 1).multiplyWith(RealFunction.exp()).apply(1.5), 1e-9);
    }

    @Test
    public void maxTest() {
        RealFunction expected1 = (x) -> x + 2;
        RealFunction f1 = RealFunction.linear(1, 2).max(RealFunction.sine(1, 1), RealFunction.constant(5));
        double result1 = f1.apply(5);

        RealFunction square = (x) -> x * x;
        RealFunction cube = (x) -> x * x * x;

        RealFunction f2 = square.max(cube);
        double result2 = f2.apply(-2);
        RealFunction expected2 = (x) -> x * x;

        assertEquals(expected1.apply(5), result1, 1e-9);
        assertEquals(expected2.apply(-2), result2);
    }
}

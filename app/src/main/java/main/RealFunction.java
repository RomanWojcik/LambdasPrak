package main;

@FunctionalInterface // only one method, and any number of static or default methods allowed
public interface RealFunction {

    public double apply(double x);

    public static RealFunction constant(double c) {
        return x -> c;
    }

    public static RealFunction linear(double a, double b) {
        return x -> a * x + b;
    }

    public static RealFunction sine(double a, double f) {
        return x -> a * Math.sin(f * x);
    }

    public static RealFunction exp() {
        return x -> Math.exp(x); // return "Math::exp;" also possible
    }

    public default RealFunction addTo(RealFunction g) {
        return x -> this.apply(x) + g.apply(x);
    }

    public default RealFunction compose(RealFunction f) {
        if (f == null) {
            return this;
        }
        return x -> f.apply(this.apply(x)); // mixed up order due to confusion
    }

    public default RealFunction multiplyWith(RealFunction... funs) {
        if (funs.length == 0) {
            return this; // return identity if array empty
        }

        RealFunction result = this;
        for (RealFunction f : funs) {
            if (f == null) {
                throw new IllegalArgumentException("Function must not be null!");
            }

            RealFunction prevResult = result;
            result = x -> prevResult.apply(x) * f.apply(x);
        }

        return result;
    }

    public default RealFunction max(RealFunction... funs) {
        if (funs.length == 0) {
            return this;
        }

        return x -> {
            double max = this.apply(x);
            for (RealFunction f : funs) {
                if (f == null) {
                    throw new IllegalArgumentException("Function cannot be null");
                }

                max = Math.max(max, f.apply(x));
            }
            return max;
        };
    }
}

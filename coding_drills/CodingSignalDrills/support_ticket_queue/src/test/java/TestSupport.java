import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

// Shared runner for the level test classes. Each case is an ordered list of operations
// with an expected result per operation. A null expected value means "don't check".
final class TestSupport {
    private TestSupport() {}

    static final class Case {
        final int level;
        final String name;
        final Object[][] ops;
        final Object[] expected;

        Case(int level, String name, Object[][] ops, Object[] expected) {
            this.level = level;
            this.name = name;
            this.ops = ops;
            this.expected = expected;
        }
    }

    static void run(Case c) throws Exception {
        if (c.ops.length != c.expected.length) {
            throw new IllegalStateException("bad test data in '" + c.name + "': "
                    + c.ops.length + " ops but " + c.expected.length + " expected values");
        }
        TicketQueue target = new TicketQueue();
        for (int i = 0; i < c.ops.length; i++) {
            Object[] op = c.ops[i];
            String name = (String) op[0];
            Object[] callArgs = Arrays.copyOfRange(op, 1, op.length);
            Method m = findMethod(target.getClass(), name, callArgs.length);
            Object result;
            try {
                result = m.invoke(target, callArgs);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) throw (RuntimeException) cause;
                if (cause instanceof Error) throw (Error) cause;
                throw e;
            }
            Object expected = c.expected[i];
            if (expected != null && !same(result, expected)) {
                throw new AssertionError("op " + i + " " + name + Arrays.toString(callArgs)
                        + " expected " + expected + " but got " + result);
            }
        }
    }

    static Method findMethod(Class<?> cls, String name, int paramCount) {
        for (Method m : cls.getMethods()) {
            if (m.getName().equals(name) && m.getParameterCount() == paramCount) {
                return m;
            }
        }
        throw new IllegalStateException("missing method " + name + " with " + paramCount + " args");
    }

    static boolean same(Object actual, Object expected) {
        if (actual instanceof Number && expected instanceof Number) {
            return ((Number) actual).longValue() == ((Number) expected).longValue();
        }
        return Objects.equals(actual, expected);
    }
}

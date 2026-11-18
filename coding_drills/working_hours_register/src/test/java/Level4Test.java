import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Level 4: Grant-period double pay. Run alone with: mvn test -Dtest=Level4Test
public class Level4Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(4, "shift fully inside grant is doubled",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addGrant", 1100L, 1200L},
                {"swipe", "A", 1110L}, {"swipe", "A", 1170L},
                {"totalDoublePay"}},
            new Object[]{null, null, null, null, 6000L}),
        new TestSupport.Case(4, "shift overlapping grant start is not doubled",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addGrant", 1100L, 1200L},
                {"swipe", "A", 1090L}, {"swipe", "A", 1150L},
                {"totalDoublePay"}},
            new Object[]{null, null, null, null, 0L}),
        new TestSupport.Case(4, "shift ending exactly at grant end is doubled",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addGrant", 1100L, 1200L},
                {"swipe", "A", 1140L}, {"swipe", "A", 1200L},
                {"totalDoublePay"}},
            new Object[]{null, null, null, null, 6000L}),
        new TestSupport.Case(4, "double pay accumulates across shifts",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addGrant", 1100L, 1400L},
                {"swipe", "A", 1110L}, {"swipe", "A", 1170L},
                {"swipe", "A", 1200L}, {"swipe", "A", 1260L},
                {"totalDoublePay"}},
            new Object[]{null, null, null, null, null, null, 12000L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Level 3: Promotion and salary. Run alone with: mvn test -Dtest=Level3Test
public class Level3Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(3, "promotion stays pending until next swipe-in",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L}, {"swipe", "A", 1060L},
                {"promote", "A", "Senior", 9000L, 1070L},
                {"positionOf", "A"}},
            new Object[]{null, null, null, null, "Engineer"}),
        new TestSupport.Case(3, "promotion applies on next swipe-in and pays new rate",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L}, {"swipe", "A", 1060L},
                {"promote", "A", "Senior", 9000L, 1070L},
                {"swipe", "A", 1120L}, {"swipe", "A", 1180L},
                {"positionOf", "A"},
                {"salaryBetween", "A", 1000L, 1200L}},
            new Object[]{null, null, null, null, null, null, "Senior", 15000L}),
        new TestSupport.Case(3, "later pending promotion replaces earlier one",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"promote", "A", "Lead", 7000L, 1010L},
                {"promote", "A", "Principal", 8000L, 1020L},
                {"swipe", "A", 1030L}, {"swipe", "A", 1090L},
                {"positionOf", "A"},
                {"salaryBetween", "A", 1000L, 1100L}},
            new Object[]{null, null, null, null, null, "Principal", 8000L}),
        new TestSupport.Case(3, "salary counts shifts by start time",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L}, {"swipe", "A", 1060L},
                {"swipe", "A", 1120L}, {"swipe", "A", 1180L},
                {"salaryBetween", "A", 1000L, 1100L},
                {"salaryBetween", "A", 1000L, 1200L}},
            new Object[]{null, null, null, null, null, 6000L, 12000L}),
        new TestSupport.Case(3, "promotion requested mid-shift applies only on next swipe-in",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L},
                {"promote", "A", "Senior", 9000L, 1030L},
                {"swipe", "A", 1060L},
                {"positionOf", "A"},
                {"salaryBetween", "A", 1000L, 1090L},
                {"swipe", "A", 1100L}, {"swipe", "A", 1160L},
                {"positionOf", "A"},
                {"salaryBetween", "A", 1000L, 1200L}},
            new Object[]{null, null, null, null, "Engineer", 6000L, null, null, "Senior", 15000L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

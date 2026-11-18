import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Level 1: Basic CRUD. Run alone with: mvn test -Dtest=Level1Test
public class Level1Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(1, "new employee has zero minutes",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"totalMinutes", "A"}},
            new Object[]{null, 0L}),
        new TestSupport.Case(1, "single shift counts its minutes",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L},
                {"swipe", "A", 1120L},
                {"totalMinutes", "A"}},
            new Object[]{null, null, null, 120L}),
        new TestSupport.Case(1, "multiple shifts are summed",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L}, {"swipe", "A", 1060L},
                {"swipe", "A", 1100L}, {"swipe", "A", 1160L},
                {"totalMinutes", "A"}},
            new Object[]{null, null, null, null, null, 120L}),
        new TestSupport.Case(1, "open swipe-in does not count",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L},
                {"totalMinutes", "A"}},
            new Object[]{null, null, 0L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


// Level 2: Top N. Run alone with: mvn test -Dtest=Level2Test
public class Level2Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(2, "ties broken by id ascending",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addEmployee", "B", "Engineer", 6000L, 1000L},
                {"addEmployee", "C", "Engineer", 6000L, 1000L},
                {"swipe", "A", 1000L}, {"swipe", "A", 1120L},
                {"swipe", "B", 1000L}, {"swipe", "B", 1060L},
                {"swipe", "C", 1000L}, {"swipe", "C", 1120L},
                {"topN", 2}},
            new Object[]{null, null, null, null, null, null, null, null, null, Arrays.asList("A", "C")}),
        new TestSupport.Case(2, "n larger than employee count returns all",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"addEmployee", "B", "Engineer", 6000L, 1000L},
                {"swipe", "B", 1000L}, {"swipe", "B", 1060L},
                {"topN", 10}},
            new Object[]{null, null, null, null, Arrays.asList("B", "A")}),
        new TestSupport.Case(2, "n of zero returns empty",
            new Object[][]{
                {"addEmployee", "A", "Engineer", 6000L, 1000L},
                {"topN", 0}},
            new Object[]{null, Collections.emptyList()})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

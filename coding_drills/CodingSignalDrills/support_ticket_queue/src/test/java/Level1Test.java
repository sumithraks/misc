import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// Level 1: create, updateTitle, getTitle. Run alone with: mvn test -Dtest=Level1Test
public class Level1Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(1, "getTitle returns the created title",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"getTitle", 1}},
            new Object[]{null, "login bug"}),

        new TestSupport.Case(1, "getTitle of a missing ticket is empty string",
            new Object[][]{
                {"getTitle", 99}},
            new Object[]{""}),

        new TestSupport.Case(1, "updateTitle changes the title",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"updateTitle", 1, "login crash"},
                {"getTitle", 1}},
            new Object[]{null, null, "login crash"}),

        new TestSupport.Case(1, "updateTitle on a missing ticket is ignored",
            new Object[][]{
                {"updateTitle", 99, "x"},
                {"getTitle", 99}},
            new Object[]{null, ""}),

        new TestSupport.Case(1, "tickets are independent",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "billing", 5, 101L},
                {"updateTitle", 2, "billing slow"},
                {"getTitle", 1},
                {"getTitle", 2}},
            new Object[]{null, null, null, "login bug", "billing slow"})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

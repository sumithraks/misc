import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 2: search and topOpen. Run alone with: mvn test -Dtest=Level2Test
public class Level2Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(2, "search orders by priority desc, timestamp asc, id asc",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "login slow", 5, 101L},
                {"create", 3, "billing login", 5, 99L},
                {"search", "login"}},
            new Object[]{null, null, null, Arrays.asList(3, 2, 1)}),

        new TestSupport.Case(2, "equal priority and timestamp falls back to id ascending",
            new Object[][]{
                {"create", 7, "a bug", 3, 100L},
                {"create", 5, "b bug", 3, 100L},
                {"search", "bug"}},
            new Object[]{null, null, Arrays.asList(5, 7)}),

        new TestSupport.Case(2, "search with no match returns empty list",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"search", "zzz"}},
            new Object[]{null, Collections.emptyList()}),

        new TestSupport.Case(2, "search returns only tickets whose title contains the keyword",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "billing", 5, 101L},
                {"search", "bug"}},
            new Object[]{null, null, Arrays.asList(1)}),

        new TestSupport.Case(2, "search uses the updated title",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"updateTitle", 1, "billing"},
                {"search", "login"},
                {"search", "billing"}},
            new Object[]{null, null, Collections.emptyList(), Arrays.asList(1)}),

        new TestSupport.Case(2, "topOpen returns the first n in the same order",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "login slow", 5, 101L},
                {"create", 3, "billing login", 5, 99L},
                {"topOpen", 2}},
            new Object[]{null, null, null, Arrays.asList(3, 2)}),

        new TestSupport.Case(2, "topOpen with n larger than ticket count returns all",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "login slow", 5, 101L},
                {"topOpen", 10}},
            new Object[]{null, null, Arrays.asList(2, 1)}),

        new TestSupport.Case(2, "topOpen of zero returns empty list",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"topOpen", 0}},
            new Object[]{null, Collections.emptyList()}),

        new TestSupport.Case(2, "topOpen on an empty queue returns empty list",
            new Object[][]{
                {"topOpen", 3}},
            new Object[]{Collections.emptyList()})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

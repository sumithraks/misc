import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Level 2: topSpenders. Run alone with: mvn test -Dtest=Level2Test
public class Level2Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(2, "ranks by outgoing transfers, highest first",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"createAccount", 3, "c"},
                {"deposit", 4, "a", 1000},
                {"deposit", 5, "b", 1000},
                {"transfer", 6, "a", "c", 300},
                {"transfer", 7, "b", "c", 700},
                {"topSpenders", 8, 3}},
            new Object[]{true, true, true, Optional.of(1000), Optional.of(1000), Optional.of(700), Optional.of(300),
                Arrays.asList("b(700)", "a(300)", "c(0)")}),

        new TestSupport.Case(2, "ties are broken by account id ascending",
            new Object[][]{
                {"createAccount", 1, "zed"},
                {"createAccount", 2, "amy"},
                {"createAccount", 3, "bob"},
                {"deposit", 4, "zed", 100},
                {"deposit", 5, "amy", 100},
                {"transfer", 6, "zed", "bob", 100},
                {"transfer", 7, "amy", "bob", 100},
                {"topSpenders", 8, 3}},
            new Object[]{true, true, true, null, null, null, null,
                Arrays.asList("amy(100)", "zed(100)", "bob(0)")}),

        new TestSupport.Case(2, "n larger than account count returns every account",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"topSpenders", 3, 10}},
            new Object[]{true, true, Arrays.asList("a(0)", "b(0)")}),

        new TestSupport.Case(2, "n of zero returns empty",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"topSpenders", 2, 0}},
            new Object[]{true, Collections.emptyList()}),

        new TestSupport.Case(2, "failed transfers do not count",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 50},
                {"transfer", 4, "a", "b", 80},
                {"topSpenders", 5, 2}},
            new Object[]{true, true, null, Optional.empty(), Arrays.asList("a(0)", "b(0)")}),

        new TestSupport.Case(2, "outgoing totals accumulate across several transfers",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 1000},
                {"transfer", 4, "a", "b", 100},
                {"transfer", 5, "a", "b", 200},
                {"transfer", 6, "b", "a", 50},
                {"topSpenders", 7, 2}},
            new Object[]{true, true, null, null, null, null, Arrays.asList("a(300)", "b(50)")})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

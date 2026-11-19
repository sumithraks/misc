import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// Level 3: assign with expiry and per-agent quota. Run alone with: mvn test -Dtest=Level3Test
// An assignment made at `now` with `durationMs` expires at now + durationMs and is active while now < expiry.
public class Level3Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(3, "assign succeeds on an unassigned ticket",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L}},
            new Object[]{null, true}),

        new TestSupport.Case(3, "assign fails for a missing ticket",
            new Object[][]{
                {"assign", 99, "ana", 200L, 50L}},
            new Object[]{false}),

        new TestSupport.Case(3, "assign fails when the ticket is actively assigned to another agent",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 1, "ben", 201L, 50L}},
            new Object[]{null, true, false}),

        new TestSupport.Case(3, "assign fails when the same agent already holds the active ticket",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 1, "ana", 210L, 50L}},
            new Object[]{null, true, false}),

        new TestSupport.Case(3, "agent is limited to 3 active assignments",
            new Object[][]{
                {"create", 1, "a", 1, 100L},
                {"create", 2, "b", 1, 101L},
                {"create", 3, "c", 1, 102L},
                {"create", 4, "d", 1, 103L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 2, "ana", 200L, 50L},
                {"assign", 3, "ana", 200L, 50L},
                {"assign", 4, "ana", 210L, 50L}},
            new Object[]{null, null, null, null, true, true, true, false}),

        new TestSupport.Case(3, "quota is per agent",
            new Object[][]{
                {"create", 1, "a", 1, 100L},
                {"create", 2, "b", 1, 101L},
                {"create", 3, "c", 1, 102L},
                {"create", 4, "d", 1, 103L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 2, "ana", 200L, 50L},
                {"assign", 3, "ana", 200L, 50L},
                {"assign", 4, "ben", 210L, 50L}},
            new Object[]{null, null, null, null, true, true, true, true}),

        new TestSupport.Case(3, "expired assignments free the quota (expiry boundary is inclusive)",
            new Object[][]{
                {"create", 1, "a", 1, 100L},
                {"create", 2, "b", 1, 101L},
                {"create", 3, "c", 1, 102L},
                {"create", 4, "d", 1, 103L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 2, "ana", 200L, 50L},
                {"assign", 3, "ana", 200L, 50L},
                {"assign", 4, "ana", 249L, 50L},
                {"assign", 4, "ana", 250L, 50L}},
            new Object[]{null, null, null, null, true, true, true, false, true}),

        new TestSupport.Case(3, "ticket can be reassigned once its assignment has expired",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 1, "ben", 249L, 50L},
                {"assign", 1, "ben", 250L, 50L}},
            new Object[]{null, true, false, true})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

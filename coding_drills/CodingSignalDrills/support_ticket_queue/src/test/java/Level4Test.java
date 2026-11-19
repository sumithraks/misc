import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 4: complete and overdueReport. Run alone with: mvn test -Dtest=Level4Test
public class Level4Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(4, "complete succeeds on an assigned ticket, then fails the second time",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"complete", 1, 220L},
                {"complete", 1, 221L}},
            new Object[]{null, true, true, false}),

        new TestSupport.Case(4, "complete fails for a missing ticket",
            new Object[][]{
                {"complete", 99, 220L}},
            new Object[]{false}),

        new TestSupport.Case(4, "complete fails for a ticket that was never assigned",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"complete", 1, 220L}},
            new Object[]{null, false}),

        new TestSupport.Case(4, "complete still succeeds after the assignment has expired",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"complete", 1, 255L}},
            new Object[]{null, true, true}),

        new TestSupport.Case(4, "overdue boundary: expiry <= now",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"overdueReport", 249L},
                {"overdueReport", 250L}},
            new Object[]{null, true, Collections.emptyList(), Arrays.asList(1)}),

        new TestSupport.Case(4, "never-assigned tickets are never overdue",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"overdueReport", 100000L}},
            new Object[]{null, Collections.emptyList()}),

        new TestSupport.Case(4, "completed tickets are excluded from the overdue report",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"complete", 1, 210L},
                {"overdueReport", 1000L}},
            new Object[]{null, true, true, Collections.emptyList()}),

        new TestSupport.Case(4, "overdue uses the most recent assignment",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"assign", 1, "ana", 200L, 50L},
                {"assign", 1, "ben", 260L, 100L},
                {"overdueReport", 300L},
                {"overdueReport", 360L}},
            new Object[]{null, true, true, Collections.emptyList(), Arrays.asList(1)}),

        new TestSupport.Case(4, "overdue ids are sorted ascending regardless of creation order",
            new Object[][]{
                {"create", 5, "e", 1, 100L},
                {"create", 2, "b", 9, 101L},
                {"assign", 5, "ana", 200L, 10L},
                {"assign", 2, "ben", 200L, 10L},
                {"overdueReport", 500L}},
            new Object[]{null, null, true, true, Arrays.asList(2, 5)}),

        new TestSupport.Case(4, "full worked example",
            new Object[][]{
                {"create", 1, "login bug", 2, 100L},
                {"create", 2, "login slow", 5, 101L},
                {"create", 3, "billing login", 5, 99L},
                {"search", "login"},
                {"topOpen", 2},
                {"assign", 3, "ana", 200L, 50L},
                {"assign", 2, "ana", 200L, 50L},
                {"assign", 1, "ana", 200L, 50L},
                {"create", 4, "password reset", 1, 102L},
                {"assign", 4, "ana", 210L, 50L},
                {"assign", 2, "ben", 201L, 50L},
                {"assign", 4, "ana", 250L, 50L},
                {"complete", 3, 255L},
                {"overdueReport", 260L},
                {"overdueReport", 300L},
                {"complete", 3, 300L}},
            new Object[]{null, null, null, Arrays.asList(3, 2, 1), Arrays.asList(3, 2),
                true, true, true, null, false, false, true, true,
                Arrays.asList(1, 2), Arrays.asList(1, 2, 4), false})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 3: users and capacity. Run alone with: mvn test -Dtest=Level3Test
public class Level3Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(3, "addUser succeeds once per user id",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u1", 500L},
                {"remainingCapacity", "u1"}},
            new Object[]{true, false, 100L}),

        new TestSupport.Case(3, "remainingCapacity of a missing user is -1",
            new Object[][]{
                {"remainingCapacity", "ghost"}},
            new Object[]{-1L}),

        new TestSupport.Case(3, "addFileBy uses up capacity",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "a", 60L},
                {"remainingCapacity", "u1"},
                {"getFileSize", "a"}},
            new Object[]{true, true, 40L, 60L}),

        new TestSupport.Case(3, "a file that exactly fills the remaining capacity fits",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "a", 100L},
                {"remainingCapacity", "u1"}},
            new Object[]{true, true, 0L}),

        new TestSupport.Case(3, "a file over the remaining capacity is rejected and nothing is added",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "a", 101L},
                {"getFileSize", "a"},
                {"remainingCapacity", "u1"}},
            new Object[]{true, false, -1L, 100L}),

        new TestSupport.Case(3, "addFileBy for a missing user is false",
            new Object[][]{
                {"addFileBy", "ghost", "a", 10L},
                {"getFileSize", "a"}},
            new Object[]{false, -1L}),

        new TestSupport.Case(3, "addFileBy with a name that already exists is false and charges nothing",
            new Object[][]{
                {"addFile", "x", 10L},
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "x", 10L},
                {"remainingCapacity", "u1"}},
            new Object[]{true, true, false, 100L}),

        new TestSupport.Case(3, "deleting an owned file gives the capacity back",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "a", 60L},
                {"deleteFile", "a"},
                {"remainingCapacity", "u1"},
                {"addFileBy", "u1", "b", 100L}},
            new Object[]{true, true, true, 100L, true}),

        new TestSupport.Case(3, "files added without an owner use nobody's capacity",
            new Object[][]{
                {"addFile", "big", 1000L},
                {"addUser", "u1", 100L},
                {"remainingCapacity", "u1"}},
            new Object[]{true, true, 100L}),

        new TestSupport.Case(3, "capacity is tracked per user",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u2", 100L},
                {"addFileBy", "u1", "a", 60L},
                {"remainingCapacity", "u1"},
                {"remainingCapacity", "u2"}},
            new Object[]{true, true, true, 40L, 100L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 4: copyFile and mergeUsers. Run alone with: mvn test -Dtest=Level4Test
public class Level4Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(4, "copyFile creates a destination with the same size",
            new Object[][]{
                {"addFile", "s", 40L},
                {"copyFile", "s", "d"},
                {"getFileSize", "d"},
                {"getFileSize", "s"}},
            new Object[]{true, true, 40L, 40L}),

        new TestSupport.Case(4, "copyFile of a missing source is false",
            new Object[][]{
                {"copyFile", "ghost", "d"},
                {"getFileSize", "d"}},
            new Object[]{false, -1L}),

        new TestSupport.Case(4, "copyFile onto an existing name is false",
            new Object[][]{
                {"addFile", "s", 40L},
                {"addFile", "d", 5L},
                {"copyFile", "s", "d"},
                {"getFileSize", "d"}},
            new Object[]{true, true, false, 5L}),

        new TestSupport.Case(4, "a copy is charged to the source's owner, and a copy that does not fit is rejected",
            new Object[][]{
                {"addUser", "u", 100L},
                {"addFileBy", "u", "s", 40L},
                {"copyFile", "s", "d"},
                {"remainingCapacity", "u"},
                {"copyFile", "s", "e"},
                {"remainingCapacity", "u"},
                {"getFileSize", "e"}},
            new Object[]{true, true, true, 20L, false, 20L, -1L}),

        new TestSupport.Case(4, "a copy that exactly fills the remaining capacity fits",
            new Object[][]{
                {"addUser", "u", 80L},
                {"addFileBy", "u", "s", 40L},
                {"copyFile", "s", "d"},
                {"remainingCapacity", "u"}},
            new Object[]{true, true, true, 0L}),

        new TestSupport.Case(4, "deleting a copy refunds the owner",
            new Object[][]{
                {"addUser", "u", 100L},
                {"addFileBy", "u", "s", 40L},
                {"copyFile", "s", "d"},
                {"deleteFile", "d"},
                {"remainingCapacity", "u"}},
            new Object[]{true, true, true, true, 60L}),

        new TestSupport.Case(4, "mergeUsers moves files and usage to the target user",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u2", 100L},
                {"addFileBy", "u1", "a", 30L},
                {"addFileBy", "u2", "b", 20L},
                {"mergeUsers", "u1", "u2"},
                {"remainingCapacity", "u2"},
                {"remainingCapacity", "u1"},
                {"getFileSize", "a"}},
            new Object[]{true, true, true, true, true, 50L, -1L, 30L}),

        new TestSupport.Case(4, "a merged file refunds the new owner when deleted",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u2", 100L},
                {"addFileBy", "u1", "a", 30L},
                {"mergeUsers", "u1", "u2"},
                {"deleteFile", "a"},
                {"remainingCapacity", "u2"}},
            new Object[]{true, true, true, true, true, 100L}),

        new TestSupport.Case(4, "mergeUsers fails when the target lacks room and changes nothing",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u2", 50L},
                {"addFileBy", "u1", "a", 60L},
                {"mergeUsers", "u1", "u2"},
                {"remainingCapacity", "u1"},
                {"remainingCapacity", "u2"}},
            new Object[]{true, true, true, false, 40L, 50L}),

        new TestSupport.Case(4, "mergeUsers fits exactly when the target has just enough room",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addUser", "u2", 50L},
                {"addFileBy", "u1", "a", 50L},
                {"mergeUsers", "u1", "u2"},
                {"remainingCapacity", "u2"}},
            new Object[]{true, true, true, true, 0L}),

        new TestSupport.Case(4, "mergeUsers of a user with themselves is false",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"addFileBy", "u1", "a", 30L},
                {"mergeUsers", "u1", "u1"},
                {"remainingCapacity", "u1"}},
            new Object[]{true, true, false, 70L}),

        new TestSupport.Case(4, "mergeUsers with a missing user is false",
            new Object[][]{
                {"addUser", "u1", 100L},
                {"mergeUsers", "u1", "ghost"},
                {"mergeUsers", "ghost", "u1"},
                {"remainingCapacity", "u1"}},
            new Object[]{true, false, false, 100L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

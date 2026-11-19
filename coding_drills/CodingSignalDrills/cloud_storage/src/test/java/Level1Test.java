import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 1: addFile, getFileSize, deleteFile. Run alone with: mvn test -Dtest=Level1Test
public class Level1Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(1, "addFile adds a new file and getFileSize returns its size",
            new Object[][]{
                {"addFile", "a.txt", 100L},
                {"getFileSize", "a.txt"}},
            new Object[]{true, 100L}),

        new TestSupport.Case(1, "addFile of an existing name fails and keeps the original size",
            new Object[][]{
                {"addFile", "a.txt", 100L},
                {"addFile", "a.txt", 500L},
                {"getFileSize", "a.txt"}},
            new Object[]{true, false, 100L}),

        new TestSupport.Case(1, "getFileSize of a missing file is -1",
            new Object[][]{
                {"getFileSize", "nope.txt"}},
            new Object[]{-1L}),

        new TestSupport.Case(1, "deleteFile removes an existing file",
            new Object[][]{
                {"addFile", "a.txt", 100L},
                {"deleteFile", "a.txt"},
                {"getFileSize", "a.txt"}},
            new Object[]{true, true, -1L}),

        new TestSupport.Case(1, "deleteFile of a missing file is false",
            new Object[][]{
                {"deleteFile", "nope.txt"}},
            new Object[]{false}),

        new TestSupport.Case(1, "deleting twice succeeds once",
            new Object[][]{
                {"addFile", "a.txt", 100L},
                {"deleteFile", "a.txt"},
                {"deleteFile", "a.txt"}},
            new Object[]{true, true, false}),

        new TestSupport.Case(1, "a name can be reused after deletion",
            new Object[][]{
                {"addFile", "a.txt", 100L},
                {"deleteFile", "a.txt"},
                {"addFile", "a.txt", 7L},
                {"getFileSize", "a.txt"}},
            new Object[]{true, true, true, 7L})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

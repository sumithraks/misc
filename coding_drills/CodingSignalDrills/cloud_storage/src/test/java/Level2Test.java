import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// Level 2: getLargestFiles. Run alone with: mvn test -Dtest=Level2Test
public class Level2Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(2, "orders by size descending then name ascending",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"addFile", "docs/b.txt", 200L},
                {"addFile", "docs/c.txt", 200L},
                {"addFile", "img/p.png", 900L},
                {"getLargestFiles", "docs/", 2}},
            new Object[]{true, true, true, true, Arrays.asList("docs/b.txt", "docs/c.txt")}),

        new TestSupport.Case(2, "only files under the prefix are listed",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"addFile", "img/p.png", 900L},
                {"getLargestFiles", "img/", 5}},
            new Object[]{true, true, Arrays.asList("img/p.png")}),

        new TestSupport.Case(2, "empty prefix matches every file",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"addFile", "docs/b.txt", 200L},
                {"addFile", "img/p.png", 900L},
                {"getLargestFiles", "", 2}},
            new Object[]{true, true, true, Arrays.asList("img/p.png", "docs/b.txt")}),

        new TestSupport.Case(2, "n larger than the number of matches returns all matches",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"addFile", "docs/b.txt", 200L},
                {"getLargestFiles", "docs/", 10}},
            new Object[]{true, true, Arrays.asList("docs/b.txt", "docs/a.txt")}),

        new TestSupport.Case(2, "no match returns an empty list",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"getLargestFiles", "zzz/", 3}},
            new Object[]{true, Collections.emptyList()}),

        new TestSupport.Case(2, "n of zero returns an empty list",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"getLargestFiles", "docs/", 0}},
            new Object[]{true, Collections.emptyList()}),

        new TestSupport.Case(2, "deleted files are not listed",
            new Object[][]{
                {"addFile", "docs/a.txt", 50L},
                {"addFile", "docs/b.txt", 200L},
                {"addFile", "docs/c.txt", 200L},
                {"deleteFile", "docs/b.txt"},
                {"getLargestFiles", "docs/", 2}},
            new Object[]{true, true, true, true, Arrays.asList("docs/c.txt", "docs/a.txt")}),

        new TestSupport.Case(2, "the prefix must be at the start of the name, not anywhere in it",
            new Object[][]{
                {"addFile", "a/readme", 10L},
                {"addFile", "b/a/readme", 20L},
                {"getLargestFiles", "a/", 5}},
            new Object[]{true, true, Arrays.asList("a/readme")})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

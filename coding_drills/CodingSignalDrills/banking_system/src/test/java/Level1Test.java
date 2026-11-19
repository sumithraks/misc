import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Level 1: createAccount, deposit, transfer. Run alone with: mvn test -Dtest=Level1Test
public class Level1Test {

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(1, "createAccount succeeds once, duplicate id fails",
            new Object[][]{
                {"createAccount", 1, "acc1"},
                {"createAccount", 2, "acc1"},
                {"createAccount", 3, "acc2"}},
            new Object[]{true, false, true}),

        new TestSupport.Case(1, "deposit returns the new balance",
            new Object[][]{
                {"createAccount", 1, "acc1"},
                {"deposit", 2, "acc1", 500},
                {"deposit", 3, "acc1", 250}},
            new Object[]{true, Optional.of(500), Optional.of(750)}),

        new TestSupport.Case(1, "deposit to a missing account is empty",
            new Object[][]{
                {"deposit", 1, "ghost", 100}},
            new Object[]{Optional.empty()}),

        new TestSupport.Case(1, "transfer moves money and returns the source balance",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 1000},
                {"transfer", 4, "a", "b", 400},
                {"deposit", 5, "b", 0 + 1}},
            new Object[]{true, true, Optional.of(1000), Optional.of(600), Optional.of(401)}),

        new TestSupport.Case(1, "transfer of the whole balance is allowed (exact fit)",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 2, "a", 300},
                {"transfer", 3, "a", "b", 300}},
            new Object[]{true, true, Optional.of(300), Optional.of(0)}),

        new TestSupport.Case(1, "transfer with insufficient funds is empty and changes nothing",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 2, "a", 100},
                {"transfer", 3, "a", "b", 101},
                {"deposit", 4, "a", 1}},
            new Object[]{true, true, Optional.of(100), Optional.empty(), Optional.of(101)}),

        new TestSupport.Case(1, "transfer to self or to a missing account is empty",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 100},
                {"transfer", 3, "a", "a", 10},
                {"transfer", 4, "a", "ghost", 10},
                {"transfer", 5, "ghost", "a", 10}},
            new Object[]{true, Optional.of(100), Optional.empty(), Optional.empty(), Optional.empty()})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

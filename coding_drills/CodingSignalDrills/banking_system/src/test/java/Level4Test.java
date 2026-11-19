import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Level 4: mergeAccounts, getBalance. Run alone with: mvn test -Dtest=Level4Test
public class Level4Test {

    static final int DAY = 86400000;

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(4, "mergeAccounts moves the balance and removes the second account",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 100},
                {"deposit", 4, "b", 50},
                {"mergeAccounts", 5, "a", "b"},
                {"deposit", 6, "a", 0 + 1},
                {"deposit", 7, "b", 10}},
            new Object[]{true, true, null, null, true, Optional.of(151), Optional.empty()}),

        new TestSupport.Case(4, "mergeAccounts fails for same, missing accounts",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"mergeAccounts", 2, "a", "a"},
                {"mergeAccounts", 3, "a", "ghost"},
                {"mergeAccounts", 4, "ghost", "a"}},
            new Object[]{true, false, false, false}),

        new TestSupport.Case(4, "merged account keeps the second account's spending history in topSpenders",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"createAccount", 3, "c"},
                {"deposit", 4, "a", 1000},
                {"deposit", 5, "b", 1000},
                {"transfer", 6, "a", "c", 100},
                {"transfer", 7, "b", "c", 300},
                {"mergeAccounts", 8, "a", "b"},
                {"topSpenders", 9, 5}},
            new Object[]{true, true, true, null, null, null, null, true, Arrays.asList("a(400)", "c(0)")}),

        new TestSupport.Case(4, "a pending cashback of the second account is credited to the first",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "b", 1000},
                {"pay", 10, "b", 500},
                {"mergeAccounts", 20, "a", "b"},
                {"deposit", 10 + DAY, "a", 0 + 1}},
            new Object[]{true, true, null, Optional.of("payment1"), true, Optional.of(511)}),

        new TestSupport.Case(4, "after a merge the payment belongs to the first account",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "b", 1000},
                {"pay", 10, "b", 500},
                {"mergeAccounts", 20, "a", "b"},
                {"getPaymentStatus", 21, "a", "payment1"},
                {"getPaymentStatus", 22, "b", "payment1"},
                {"getPaymentStatus", 10 + DAY, "a", "payment1"}},
            new Object[]{true, true, null, Optional.of("payment1"), true, Optional.of("IN_PROGRESS"), Optional.empty(),
                Optional.of("CASHBACK_RECEIVED")}),

        new TestSupport.Case(4, "getBalance returns the balance as of an earlier time",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 10, "a", 100},
                {"deposit", 20, "a", 50},
                {"getBalance", 30, "a", 10},
                {"getBalance", 30, "a", 15},
                {"getBalance", 30, "a", 20},
                {"getBalance", 30, "a", 30}},
            new Object[]{true, null, null, Optional.of(100), Optional.of(100), Optional.of(150), Optional.of(150)}),

        new TestSupport.Case(4, "getBalance before the account was created is empty, at creation is zero",
            new Object[][]{
                {"createAccount", 10, "a"},
                {"getBalance", 20, "a", 9},
                {"getBalance", 20, "a", 10}},
            new Object[]{true, Optional.empty(), Optional.of(0)}),

        new TestSupport.Case(4, "getBalance of a missing account is empty",
            new Object[][]{
                {"getBalance", 5, "ghost", 1}},
            new Object[]{Optional.empty()}),

        new TestSupport.Case(4, "getBalance reflects transfers and pay at the right time",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 10, "a", 1000},
                {"transfer", 20, "a", "b", 300},
                {"pay", 30, "a", 200},
                {"getBalance", 40, "a", 10},
                {"getBalance", 40, "a", 20},
                {"getBalance", 40, "a", 30},
                {"getBalance", 40, "b", 20}},
            new Object[]{true, true, null, null, null, Optional.of(1000), Optional.of(700), Optional.of(500), Optional.of(300)}),

        new TestSupport.Case(4, "getBalance includes a cashback only from its credit time onward",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 500},
                {"getBalance", 11, "a", 10},
                {"getBalance", 10 + DAY, "a", 10 + DAY - 1},
                {"getBalance", 10 + DAY, "a", 10 + DAY}},
            new Object[]{true, null, Optional.of("payment1"), Optional.of(500), Optional.of(500), Optional.of(510)}),

        new TestSupport.Case(4, "after a merge getBalance combines both histories",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 10, "a", 100},
                {"deposit", 20, "b", 50},
                {"mergeAccounts", 30, "a", "b"},
                {"getBalance", 40, "a", 10},
                {"getBalance", 40, "a", 20},
                {"getBalance", 40, "a", 30}},
            new Object[]{true, true, null, null, true, Optional.of(100), Optional.of(150), Optional.of(150)})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

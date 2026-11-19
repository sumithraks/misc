import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Level 3: pay, getPaymentStatus, cashback. Run alone with: mvn test -Dtest=Level3Test
// Cashback waiting period: 24h = 86400000 ms. Timestamps never go backwards inside one test.
public class Level3Test {

    static final int DAY = 86400000;

    static final List<TestSupport.Case> CASES = Arrays.asList(
        new TestSupport.Case(3, "pay deducts the balance and returns a payment id",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 3, "a", 400},
                {"deposit", 4, "a", 0 + 1}},
            new Object[]{true, Optional.of(1000), Optional.of("payment1"), Optional.of(601)}),

        new TestSupport.Case(3, "payment ordinal is global across accounts and skips failed pays",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 1000},
                {"deposit", 4, "b", 1000},
                {"pay", 5, "a", 10},
                {"pay", 6, "b", 10},
                {"pay", 7, "a", 5000},
                {"pay", 8, "ghost", 10},
                {"pay", 9, "b", 10}},
            new Object[]{true, true, null, null, Optional.of("payment1"), Optional.of("payment2"), Optional.empty(), Optional.empty(),
                Optional.of("payment3")}),

        new TestSupport.Case(3, "pay with insufficient funds is empty and changes nothing",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 100},
                {"pay", 3, "a", 101},
                {"deposit", 4, "a", 1}},
            new Object[]{true, null, Optional.empty(), Optional.of(101)}),

        new TestSupport.Case(3, "payments count toward topSpenders",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 1000},
                {"deposit", 4, "b", 1000},
                {"pay", 5, "a", 300},
                {"transfer", 6, "b", "a", 100},
                {"topSpenders", 7, 2}},
            new Object[]{true, true, null, null, null, null, Arrays.asList("a(300)", "b(100)")}),

        new TestSupport.Case(3, "status is IN_PROGRESS until the cashback is processed",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 500},
                {"getPaymentStatus", 11, "a", "payment1"},
                {"getPaymentStatus", 10 + DAY - 1, "a", "payment1"},
                {"getPaymentStatus", 10 + DAY, "a", "payment1"}},
            new Object[]{true, null, Optional.of("payment1"), Optional.of("IN_PROGRESS"), Optional.of("IN_PROGRESS"),
                Optional.of("CASHBACK_RECEIVED")}),

        new TestSupport.Case(3, "cashback is 2 percent rounded down and is credited at timestamp + 24h",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 999},
                {"deposit", 10 + DAY - 1, "a", 1},
                {"deposit", 10 + DAY, "a", 1}},
            new Object[]{true, null, Optional.of("payment1"), Optional.of(2), Optional.of(22)}),

        new TestSupport.Case(3, "cashback is credited before any other operation at the same timestamp",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 500},
                {"transfer", 10 + DAY, "a", "b", 510}},
            new Object[]{true, true, null, Optional.of("payment1"), Optional.of(0)}),

        new TestSupport.Case(3, "cashback is not counted as spending",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 500},
                {"topSpenders", 10 + DAY, 1}},
            new Object[]{true, null, Optional.of("payment1"), Arrays.asList("a(500)")}),

        new TestSupport.Case(3, "several pending cashbacks are all processed, in order",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 1000},
                {"pay", 10, "a", 100},
                {"pay", 20, "a", 100},
                {"pay", 30, "a", 100},
                {"deposit", 40 + DAY, "a", 0 + 1}},
            new Object[]{true, null, Optional.of("payment1"), Optional.of("payment2"), Optional.of("payment3"),
                Optional.of(707)}),

        new TestSupport.Case(3, "getPaymentStatus is empty for a missing account, a missing payment, or the wrong account",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"createAccount", 2, "b"},
                {"deposit", 3, "a", 100},
                {"pay", 4, "a", 50},
                {"getPaymentStatus", 5, "ghost", "payment1"},
                {"getPaymentStatus", 6, "a", "payment9"},
                {"getPaymentStatus", 7, "b", "payment1"}},
            new Object[]{true, true, null, Optional.of("payment1"), Optional.empty(), Optional.empty(), Optional.empty()}),

        new TestSupport.Case(3, "a payment of less than 50 earns zero cashback but still reaches CASHBACK_RECEIVED",
            new Object[][]{
                {"createAccount", 1, "a"},
                {"deposit", 2, "a", 100},
                {"pay", 3, "a", 49},
                {"getPaymentStatus", 3 + DAY, "a", "payment1"},
                {"deposit", 4 + DAY, "a", 0 + 1}},
            new Object[]{true, null, Optional.of("payment1"), Optional.of("CASHBACK_RECEIVED"), Optional.of(52)})
    );

    @TestFactory
    Stream<DynamicTest> cases() {
        return CASES.stream().map(c ->
            DynamicTest.dynamicTest("L" + c.level + " " + c.name, () -> TestSupport.run(c)));
    }
}

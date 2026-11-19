# Banking System

Practice for the Airbnb CodeSignal Industry Coding format: 90 minutes, four levels, Java. A level must pass all its tests before you start the next.
Run one level: `mvn test -Dtest=Level1Test` (then Level2Test, Level3Test, Level4Test). Run all: `mvn test`.
The class is `BankingSystemImpl` (stub in `src/main/java`, keep the signatures). Amounts are `int`. Timestamps are `int` milliseconds and never decrease across calls.

## Timer (do this, it is the point of the drill)
Level 1 done by 0:10, Level 2 by 0:30, Level 3 by 1:00, Level 4 by 1:20, last 10 minutes for cleanup. Before each level write one line: the hard part and your plan for it.
Implement only what is written here. No extra validation (null, blank, timestamp, amount checks) until all four levels pass.

## Level 1: accounts
- `boolean createAccount(int timestamp, String accountId)`: false if the id already exists.
- `Optional<Integer> deposit(int timestamp, String accountId, int amount)`: the new balance, or empty if the account does not exist.
- `Optional<Integer> transfer(int timestamp, String sourceAccountId, String targetAccountId, int amount)`: the source balance after the transfer, or empty if either account is missing, both are the same, or the source balance is less than `amount` (an exact fit is allowed). A failed transfer changes nothing.

## Level 2: ranking
- `List<String> topSpenders(int timestamp, int n)`: the top `n` accounts by total outgoing money (money sent by `transfer`; `pay` joins this in Level 3), highest first, ties by account id ascending. Format `"id(total)"`, e.g. `"acc1(700)"`. Accounts with no spending appear with `(0)`. If fewer than `n` accounts exist, return all of them. `n` of 0 returns an empty list.

## Level 3: pay with cashback
- `Optional<String> pay(int timestamp, String accountId, int amount)`: withdraws `amount`. Returns `"payment<k>"`, where `k` is the global count of successful pays across all accounts (1, 2, 3, ...). Empty if the account is missing or has insufficient funds; a failed pay does not use up a number.
- A payment earns cashback of 2% of `amount` rounded down (`amount * 2 / 100`), credited to the account at `timestamp + 86400000` (24 hours). Cashback is credited BEFORE any other operation at that same timestamp. Cashback does not count as spending.
- Payments count toward `topSpenders` outgoing total.
- `Optional<String> getPaymentStatus(int timestamp, String accountId, String payment)`: `"IN_PROGRESS"` until the cashback has been credited, then `"CASHBACK_RECEIVED"`. Empty if the account is missing, or the payment does not exist for that account. A payment with zero cashback still moves to `CASHBACK_RECEIVED`.
- Cashback due times must be honoured on every call, not only on pay and getPaymentStatus.

## Level 4: merge and history
- `boolean mergeAccounts(int timestamp, String accountId1, String accountId2)`: false if either is missing or they are the same. Otherwise `accountId2` is merged into `accountId1`: balances are added, `accountId2`'s outgoing total is added to `accountId1`'s, `accountId2`'s payments (and any pending cashback) now belong to `accountId1`, and `accountId2` no longer exists.
- `Optional<Integer> getBalance(int timestamp, String accountId, int timeAt)`: the balance of the account at time `timeAt`, counting every balance change with timestamp <= `timeAt` (cashback counts from its credit time). Empty if the account does not exist or was created after `timeAt`. After a merge, the account's history includes the merged account's history.

## Left open on purpose (tests do not assert these; state your choice)
- Zero or negative amounts.
- Null or blank ids.
- Reusing an account id after it was merged away.

# Working Hours Register

CodeSignal Industry Coding practice, four levels. Each level must pass all its tests before you start the next.

Employees swipe their cards when entering and leaving the office. The system records working hours and supports promotions, salary calculation, and grant-period double pay.

## Units
- Time is epoch minutes (`long`). The `time` in `addEmployee` is the current time when the employee is added; it must be positive, and later swipes use times at or after it.
- Pay is in cents. Hourly rates are in cents per hour.
- Pay for a shift = minutes × rateCents / 60. Tests use whole hours so integer division is exact.

## Proposed API

```java
class WorkingHoursRegister {
    // Level 1
    void addEmployee(String id, String position, long hourlyRateCents, long time);
    void swipe(String id, long time);            // alternates in/out for that employee
    long totalMinutes(String id);

    // Level 2
    List<String> topN(int n);

    // Level 3
    void promote(String id, String newPosition, long newRateCents, long time);
    String positionOf(String id);                // current position; pending promotion not yet applied
    long salaryBetween(String id, long from, long to);

    // Level 4
    void addGrant(long start, long end);
    long totalDoublePay();
}
```

The signatures are a proposal. Replace them with the real ones from the assessment if they differ.

## Level 1: Basic CRUD
- Add employees. Record swipe-in and swipe-out. Query total working minutes.
- A shift is a swipe-in paired with the next swipe-out for the same employee.
- An open swipe-in (no swipe-out yet) does not count toward totals.
- A swipe-out with no open swipe-in is ignored.

## Level 2: Top N
- `topN(n)` returns up to n employee ids, ordered by total minutes descending, then id ascending.

## Level 3: Promotion and salary
- `promote` stores a pending position and rate. It does not change the current position.
- The pending promotion applies on the employee's next swipe-in. That shift is paid at the new rate.
- If two promotions are requested before the next swipe-in, the later one replaces the earlier one.
- Each shift's pay uses the rate in effect when that shift started.
- `salaryBetween(id, from, to)` sums pay for shifts whose start time is in [from, to].

## Level 4: Grant-period double pay
- `addGrant(start, end)` defines a grant period. Boundaries are inclusive.
- A shift counts only if it lies entirely inside a grant: start ≥ grant start and end ≤ grant end.
- Double pay for a doubled shift = that shift's pay again. Add it to the running total.
- `totalDoublePay()` returns the total extra pay issued across all doubled shifts.

## Checks before you call it done
- Trace one normal case and one edge case per level by hand.
- Level 3: include a promotion requested mid-shift. It should apply only on the next swipe-in.
- Level 4: include a shift that overlaps a grant boundary. It should not be doubled.

# Support Ticket Queue

Practice for the Airbnb CodeSignal Industry Coding format: 90 minutes, four levels, Java. A level must pass all its tests before you start the next.
Run one level: `mvn test -Dtest=Level1Test` (then Level2Test, Level3Test, Level4Test). Run all: `mvn test`.

Timestamps and times are `long`. Ids are `int`.

## Level 1
- `create(int id, String title, int priority, long timestamp)`
- `updateTitle(int id, String title)`: ignored if the ticket is missing.
- `getTitle(int id)`: returns `""` if the ticket is missing.

## Level 2
- `search(String keyword)`: ids whose title contains the keyword.
- `topOpen(int n)`: the first n tickets across all tickets.
- Both use the same order: priority descending, then timestamp ascending, then id ascending.

## Level 3
- `assign(int ticketId, String agent, long now, long durationMs)` returns boolean.
- Expiry is `now + durationMs`. An assignment is active while `now < expiry`.
- An agent may hold at most 3 active assignments.
- Check in this order, returning false on the first failure: ticket missing, ticket actively assigned, agent at quota.
- Expired assignments do not count toward quota, and the ticket can be reassigned.

## Level 4
- `complete(int ticketId, long now)`: false if the ticket is missing, unassigned, or already completed.
- `overdueReport(long now)`: ids of non-completed tickets whose most recent assignment has `expiry <= now`, sorted ascending.

## Assumptions the tests make
- `complete` works on an assigned ticket even if its assignment has expired.
- A never-assigned ticket is never overdue.
- Search is a case-sensitive substring match (the tests use lowercase only).

## Left open on purpose (the tests do not assert these; decide, state your choice, and note it in your debrief)
- Creating a ticket with an id that already exists.
- Whether `topOpen` should skip completed tickets (its name suggests so; the spec says "across all tickets").
- Whether a completed ticket can be assigned again.
- Validation of null or blank inputs.

## Before calling a level done
Write one normal trace and one edge trace, then run that level's tests.

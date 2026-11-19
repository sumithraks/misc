# Senior-bar code review rubric (100 points)

| Area | Points | What the reviewer looks for |
|---|---|---|
| Correctness | 30 | All four levels pass; boundaries right (exact-fit capacity is allowed); no hidden-test traps |
| Structure and extensibility | 25 | One `Ticket` domain object owning its state, one lookup map; later levels add to it instead of adding parallel maps; small `Assignment` type is fine |
| Restraint | 10 | Level 1 stays small: no speculative interfaces, no unrequested validation or checked exceptions |
| Readability | 15 | Clear names, short methods, no duplicated logic (one ordering comparator, one place that charges or refunds capacity), no dead code or leftover comments |
| Encapsulation and Java hygiene | 10 | Private fields, no leaking internal collections, `final` where natural, no `Exception` throws for normal results |
| Complexity awareness | 5 | Can state the cost of `getLargestFiles`, `addFileBy`, `copyFile` and `mergeUsers`, and when it would matter |
| Process and communication | 5 | Open decisions stated per level in NOTES.md, one numeric normal trace and one numeric boundary trace per level, written before running the tests |

Score bands: 90+ strong hire signal, 75-89 pass, 60-74 borderline, below 60 needs rework.

# Cloud Storage

Practice for the Airbnb CodeSignal Industry Coding format: 90 minutes, four levels, Java. A level must pass all its tests before you start the next.
Run one level: `mvn test -Dtest=Level1Test` (then Level2Test, Level3Test, Level4Test). Run all: `mvn test`.

Sizes and capacities are `long`. Names and user ids are non-null strings. Names are case-sensitive.

## Level 1: files
- `boolean addFile(String name, long size)`: true if added; false if a file with that name already exists (the existing file is left unchanged).
- `long getFileSize(String name)`: the size, or -1 if there is no such file.
- `boolean deleteFile(String name)`: true if the file existed and was removed; false otherwise.

## Level 2: listing
- `List<String> getLargestFiles(String prefix, int n)`: names that start with `prefix` (an empty prefix matches every file), ordered by size descending, then name ascending, at most `n` of them. Returns an empty list if nothing matches or `n` is 0.

## Level 3: users and capacity
- `boolean addUser(String userId, long capacity)`: true if added; false if the user already exists.
- `boolean addFileBy(String userId, String name, long size)`: false if the user does not exist, a file with that name already exists, or the file does not fit in the user's remaining capacity (a file that exactly fills the remaining capacity fits). Otherwise the file is added, owned by that user, and counts toward their used capacity.
- `long remainingCapacity(String userId)`: capacity minus used, or -1 if the user does not exist.
- Deleting an owned file (`deleteFile`) gives its size back to the owner. Files added with `addFile` have no owner and use nobody's capacity.

## Level 4: copy and merge
- `boolean copyFile(String source, String dest)`: false if `source` does not exist, `dest` already exists, or `source` has an owner whose remaining capacity is less than the file's size. Otherwise `dest` is created with the same size and the same owner (an unowned source gives an unowned copy), and counts toward that owner's used capacity.
- `boolean mergeUsers(String from, String to)`: false if either user does not exist, if `from` and `to` are the same user, or if `to`'s remaining capacity is less than everything `from` currently uses. Otherwise all of `from`'s files become owned by `to` (counting toward `to`'s used capacity), user `from` is removed, and the result is true. `to` keeps its own capacity.

## Left open on purpose (the tests do not assert these; decide, state your choice in NOTES.md)
- Zero-size files: allowed or rejected.
- Negative sizes or capacities.
- `getLargestFiles` with a negative `n`.
- Null or blank names and user ids.

## How to work (this is what is being graded most closely)
For each level, before you run its tests, fill in that level's section in `NOTES.md`:
1. Your decision on every open item that applies to that level, one sentence each.
2. One normal trace and one boundary trace with real numbers, walking the state after each call.
Send me NOTES.md for a check before you run the tests if you want feedback on your traces.

# Notes (fill in before running each level's tests)

## Level 1
Decisions (one sentence each):
-
Having a map is easy to lookup a file by name. So having it a map of files.

Normal trace (real numbers, state after each call):
addFile(a,100) -> true
addFile(b,200) -> true
getFileSize(a) -> 100
deleteFile(a) -> true


Boundary trace:
addFile(a,0) -> true
addFile(z,-100) -> false
deleterFile(z) -> false   

## Level 2
Decisions:
iterate over map values and sort and limit

Normal trace:
addFile(a1,100) -> true
addFile(b1,200) -> true
addFile(a11,300) -> true
addFile(b11,400) -> true
getLargestFiles(a,2) -> a1,a11
getLargestFiles(a,1) -> a11

Boundary trace:

getLargestFiles(a,-11) -> empty


## Level 3
Decisions:
We will add a user object. Maintain a map of user id and user object. Files that is owned by the user will be as reference in the set within user object. Add function that will take care add it to files name and addFile in user will take care of adding same object to this set and adjust remaining capacity.


Normal trace:
addUser(u1, 100)                  -> true.  u1 remaining 100
addFileBy(u1, "a", 60)            -> true.  remaining 40
addFileBy(u1, "b", 41)            -> false. 41 > 40, nothing added
addFileBy(u1, "b", 40)            -> true.  exact fit, remaining 0
deleteFile("a")                   -> true.  remaining 60




Boundary trace (include exact fit and a delete refund):

addUser(u1,100) -> true
addFileBy(u1, "a", 60) -> true
addFileBy(u1, "b", 60) -> false
addFileBy(u1, "b", -1) -> false
addFileBy(u1, "b", 40) -> true
addFileBy(u1, "c", 0) -> true
deleteFile(d) -> false
deleteFile(b) -> true
remainingCapacity() -> 40



## Level 4
Decisions:
-
CopyFile - recreate another object after checking if source exists. If the file belongs to a owner, and does not have capacity to create a copy, return false. Else add another to the owner's list and files's map.

Merge will simply change the owner of existing files after preliminary against users and receiver's capacity

Normal trace:
addUser(u1,100) -> true
addFileBy(u1, "a", 30) -> true
copyFile("a","b") -> true
addUser(u2,100) -> true
merge(u1,u2) -> true




Boundary trace (include a copy that does not fit and a merge that does not fit):
addFileBy(u1, "b", 10) -> true
copyFile("b","c") -> true
deleteFile("b) -> true
addUser(u2,30)
addFileBy(u2, "e", 10) -> true
addFileBy(u2,"f",10)->true
merge(u1,u2) -> false
deleteFile("f") -> true
merge(u1,u2) -> true





## Complexity (state the cost and when it would matter)
- getLargestFiles: - O(nlog(n)) 
- addFileBy / deleteFile: - O(1)
- copyFile: - O(1)
- mergeUsers: - O(n)

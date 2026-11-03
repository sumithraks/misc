from dataclasses import dataclass


def pair_sum(nums:list,target:int):
    if len(nums) < 2:
        return []
    left=0;
    right = len(nums)-1
    while (left<right):
        sum=nums[left]+nums[right]
        if (sum==target):
            return [left,right]
        elif sum<target:
            left+=1
        else:
            right-=1
    return[]

@dataclass(frozen=True)
class TestCase:
    name: str
    nums: list[int]
    target: int
    expected_to_find: bool


def check(tc: TestCase) -> bool:
    result = pair_sum(tc.nums, tc.target)
    if not tc.expected_to_find:
        return result == []
    if len(result) != 2:
        return False
    i, j = result
    return tc.nums[i] + tc.nums[j] == tc.target

def main() -> None:
    tests = [
        TestCase("basic pair",     [-5, -2, 3, 4, 6], 7,  True),
        TestCase("negatives only", [-8, -3, -1],      -4, True),
        TestCase("no pair exists", [1, 2, 3],         10, False),
        TestCase("empty input",    [],                0,  False),
        TestCase("single element", [5],               5,  False),
        TestCase("duplicates",     [1, 1, 4],         2,  True),
    ]

    passed = 0
    for tc in tests:
        ok = check(tc)
        print(("PASS " if ok else "FAIL ") + tc.name)
        if ok:
            passed += 1
    print(f"{passed}/{len(tests)} passed")


if __name__ == "__main__":
    main()
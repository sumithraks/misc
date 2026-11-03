import java.util.List;

/**
 * TestCase
 */

public record TestCase(String name, List<Integer> nums, int target, boolean expectedToFind) {}


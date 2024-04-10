package oy.tol.tra;

/**
 * Uses the StackInterface implementation to check that parentheses in text files
 * match.
 * <p>
 * TODO: Students, implement the checkParentheses() method using your StackImplementation
 * to check if parentheses in the two test files match or not.
 * <p>
 * NOTE: The Person.json test file has an error, but the tests expect that. So the test will
 * not fail with that file -- the erroneous json file is _expected_.
 * <p>
 * Note that you do not have to instantiate this class anywhere. The ParenthesisTests:
 * <ul>
 * <li>Constructs your implementation of the {@code StackImplementation<Character>}, and
 * <li>Calls ParenthesisChecker.checkParentheses.
 * </ul>
 * So your job is just to have a working StackImplementation and implement the ParenthesisChecker.checkParentheses.
 * Then execute the ParenthesisTests.
 */
public class ParenthesisChecker {

    private ParenthesisChecker() {
    }

    /**
     * TODO: Implement this function which checks if the given string has matching opening and closing
     * parentheses. It should check for matching parentheses:<p>
     * <code>Lorem ipsum ( dolor sit {  amet, [ consectetur adipiscing ] elit, sed } do eiusmod tempor ) incididunt ut...</code>,
     * <p>
     * which can be found for example in Java source code and JSON structures.
     * <p>
     * If the string has issues with parentheses, you should throw a {@code ParenthesisException} with a
     * descriptive message and error code. Error codes are already defined for you in the ParenthesesException
     * class to be used.
     * <p>
     * What is to be tested:
     * <ul>
     * <li>when an opening parenthesis is found in the string, it is successfully pushed to the stack.
     * <li>when a closing parenthesis is found in the string, a matching opening parenthesis is popped from the stack.
     * <li>when popping a parenthesis from the stack, it must not be null. Otherwise string has too many closing parentheses.
     * <li>when the string has been handled, and if the stack still has parentheses, there are too few closing parentheses.
     * </ul>
     *
     * @param stack The stack object used in checking the parentheses from the string.
     * @param fromString A string containing parentheses to check if it is valid.
     * @return Returns the number of parentheses found from the input in total (both opening and closing).
     * @throws ParenthesesException if the parentheses did not match as intended.
     * @throws StackAllocationException If the stack cannot be allocated or reallocated if necessary.
     */
    public static int checkParentheses(StackInterface<Character> stack, String fromString) throws ParenthesesException {
        int parenCount = 0;
        try{
            for (char c : fromString.toCharArray()) {
                if (c == '(' || c == '{' || c == '[') {
                    stack.push(c);
                    parenCount++;
                } else if (c == ')' || c == '}' || c == ']') {
                    if (stack.isEmpty()) {
                        throw new ParenthesesException("There are too many closing parentheses in the string", ParenthesesException.TOO_MANY_CLOSING_PARENTHESES);
                    }
                    char popped = stack.pop();
                    if ((c == ')' && popped != '(') || (c == '}' && popped != '{') || (c == ']' && popped != '[')) {
                        throw new ParenthesesException("The parentheses are not in the correct order", ParenthesesException.PARENTHESES_IN_WRONG_ORDER);
                    }
                    parenCount++;
                }
            }
            if (!stack.isEmpty()) {
                throw new ParenthesesException("There are too few closing parentheses in the string", ParenthesesException.  TOO_FEW_CLOSING_PARENTHESES);
            }
        } catch (StackAllocationException sae) {
            throw new ParenthesesException("There was an error allocating or reallocating the stack", ParenthesesException.STACK_FAILURE);
        }
        return parenCount;
    }
}
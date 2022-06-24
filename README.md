# Simple-Calculator

Write a function in Java that evaluates the result of a simple expression. 

Valid tokens are listed in the table below:
<number> A <number> is a sequence of characters in the range ‘0’..’9’.
( ) Nested expressions should be evaluated first.
+, -, *, / Basic operators are addition, subtraction, multiplication and division. 
• The expression is parsed from left to right, evaluating operators in that order (e.g. 1 + 3 
* 4 = 16).
• If there is an error in the expression, the function will return false.
• The function can handle negative numbers (e.g. -1 + 3 = 2).
• The function can handle common error cases.

Example test cases (not limited to):
Input Result Return code
1 + 3 → true
(1 + 3) * → 8 true
(4 / 2) + 6  → true
4 + (12 / (1 * 2)) → 10 true
(1 + (12 * 2) → N/A false (missing bracket)

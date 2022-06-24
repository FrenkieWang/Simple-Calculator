package Sony;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;
/**
 * Use Reverse Polish Notation (Suffix Expression) to get the calculation result
 */
public class Calculator {
    public static void main(String[] args) {
        
        //Converts the user input from string to an ArrayList type for later stack manipulation
        // such as  ¡°(3+4)*5-2¡±   --> ¡°[(,3,+,4,),*,5,-,2]¡±
    	Scanner sc = new Scanner(System.in);
    	String input = sc.nextLine();
    	
    	//main method
    	boolean result = evaluate(input);
    	System.out.println("The return code is: "+result);
    	
        sc.close();
    }
    
    // Main Method!
    public static boolean evaluate(String input) {
    	//check whether the symbols in the expression is valid!
    	if(!checkSymbol(input)) {
    		System.out.println("invalid symbol!");
    		return false;
    	}
    	
    	//check whether the expression misses bracket!
    	if(!checkBracket(input)) {
    		System.out.println("missing bracket");
    		return false;
    	}

        //Converts strings to Collections
        List<String> sl = toInfixExpressionList(input);
        System.out.println("Convert String into Collection£º" + sl);
        //Converts Collection into Suffix Expression
        List<String> s2 = parseSuffixExpreesionList(sl);
        System.out.println("Parse into Suffix Expression£º" + s2);
        //Calculate the output of Suffix Expression
        double res=calculator(s2);
        System.out.println("Final result: "+res);
        
    	return true;
    }
    
    //check whether the expression misses bracket!
    public static boolean checkBracket(String input) {
    	int leftBracket = 0;
    	int rightBracket = 0;
    	for(int i=0; i<input.length(); i++) {
    		if(input.charAt(i)=='(') {
    			leftBracket++;
    		}
    		if(input.charAt(i)==')') {
    			rightBracket++;
    		}
    	}
    	if(leftBracket!=rightBracket) {
    		return false;
    	}
    	return true;
    }
    
    //check whether the symbols in the expression is valid!
    public static boolean checkSymbol(String input) {
    	String checkList = "0123456789()+-*x/";
    	for(int i=0; i<input.length(); i++) {
    		if(checkList.indexOf(input.charAt(i))==-1) {
    			return false;
    		}
    	}
    	return true;
    }

    //Converts the user input from string to an ArrayList type for later stack manipulation
    // such as  ¡°(3+4)*5-2¡±   --> ¡°[(,3,+,4,),*,5,-,2]¡±
    public static List<String> toInfixExpressionList(String s) {
        List<String> list = new ArrayList<String>();
        int index = 0;  //Index,  a pointer to each element of the string
        char c;  //Take out the element from the String into 'c'
        while (index < s.length()) {
            //Judge whether 'c' is number first
            if ((c = s.charAt(index)) < 48 || (c = s.charAt(index)) > 57) {
                //If 'c' is not a number, put it into the collection.
                list.add("" + c);
                index++;
            } else {
            	// If 'c' is multi-digits, convert multi-digit into String.
                String num = "";
                while (index < s.length() && (c = s.charAt(index)) >= 48 && (c = s.charAt(index)) <= 57) {
                    num += c;
                    index++;
                }
                list.add(num);
            }
        }
        return list;
    }


    /**
     * 1. Initialize two stacks: stack s1 -> stores operator, stack s2 -> stores intermediate results;
     * 2. Scan the "infix expression" from left to right£»
     * 3. If scanner meets an operand (number), push it into stack s2 (intermediate results);
     * 4. If scanner meets an operator, compare the priority with that on the peek of stack s1;
     *    (a) If s1 is empty or the top-of-stack operator is the left bracket "(", then push this operator into stack s1.
     *    (b) If the priority of the operator is higher than the top-of-stack operator, then push this operator into stack s1.
     *    (c) If the priority of the operator is lower than the top-of-stack operator, pop the operator from stack s1 and push it into stack s2.
     *     Then compare the priority of the scanned operator with the new the top-of-stack operator (return back to step 4-a).
     * 5. When meeting bracket:
     *    (a) If meets left bracket "(", push it into stack s1.
     *    (b) If meets right bracket ")", pop all the operators from stack s1 and push them into stack s2 until meeting a left bracket "(".
     *        Then discard that pair of bracket --  "(" ")" 
     * 6. Repeat steps 2 - 5, until meeting the rightmost side of the expression.
     * 7. Pop all the remaining operators from stack s1 and push them into stack s2.
     * 8. Pop the elements from stack s2 one by one,
     *    and the output is the conversion from infix expression to the corresponding suffix expression.
     */
    public static List<String> parseSuffixExpreesionList(List<String> ls) {

        Stack<String> s1 = new Stack<String>();
        List<String> s2 = new ArrayList<String>();
        for (String item : ls) {
            if (item.matches("\\d+")) {
                s2.add(item);
            }else if(s1.empty()){
                s1.push(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();
            } else if (Operation.getValue(s1.peek()) < Operation.getValue(item)) {
                s1.push(item);
            } else if (Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                do {
                    s2.add(s1.pop());
                } while (!s1.empty()&&Operation.getValue(s1.peek()) >= Operation.getValue(item));
                s1.push(item);
            } else {
                System.out.println("The characters entered are incorrect");
            }
        }
        for (String s:s1){
            s2.add(s);
        }
        return s2;
    }




    //Calculate the result of Suffix Expression
    public static double calculator(List<String> list) {

        Stack<String> stack = new Stack<String>();
        for (String item : list) {
            if (item.matches("\\d+")) {
                stack.push(item);
            } else {
                double num1 = Double.parseDouble(stack.pop());
                double num2 = Double.parseDouble(stack.pop());
                double res = 0.0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num2 - num1;
                } else if (item.equals("*")||item.equals("x")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num2 / num1;
                } else {
                    throw new RuntimeException("The symbol entered is incorrect");
                }
                stack.push("" + res);
            }
        }
        return Double.parseDouble(stack.pop());
    }

}

//This class is to operate!
//Level 1 is Add and Subtract
//Level 2 is Multiple and Division
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String s) {
        if (s.equals("+")) {
            return ADD;
        } else if (s.equals("-")) {
            return SUB;
        } else if (s.equals("*")||s.equals("x")) {
            return MUL;
        } else if (s.equals("/")) {
            return DIV;
        }else if(s.equals("(")){
            return 0;
        } else{
            System.out.println("Cannot find a valid operator£¡");
        }
        return 0;
    }
}

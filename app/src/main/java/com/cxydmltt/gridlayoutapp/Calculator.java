package com.cxydmltt.gridlayoutapp;

/**
 * Created by yadong.wang.pl on 2016/12/20.
 */

import android.app.AlertDialog;

import java.util.Stack;

public class Calculator {

    // public static void main(String[] args) {
    // // TODO Auto-generated method stub
    //
    // }

    // Singleton
    private Calculator() {
    }

    ;

    private static final Calculator calculator = new Calculator();

    public static Calculator getInstance() {
        return calculator;
    }


    // Calculate
    private String formula = "";
    private String message = "";
    private char firstChar;
    private int leftBracket = 0;
    private int rightBracket = 0;
    private String result = "";

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    public void transferSymbol(char s) {
        if (checkChar(s))
            formula = formula + String.valueOf(s);
    }

    // 保证表达式的输入格式正确
    private boolean checkChar(char s) {
        if (formula == null || formula.isEmpty()) {
            if (Character.isDigit(s) || s == '-' || s == '(') {
                firstChar = s;
                if (s == '(')
                    leftBracket++;
                return true;
            } else
                return false;
        }
        if (firstChar == '0') {
            if (formula.length() == 1 && formula.charAt(0) == '0') {
                if (s == '.' || isOperator(s)) {
                    firstChar = s;
                    return true;
                } else
                    return false;
            } else {
                char tempchar = formula.charAt(formula.length() - 2);
                if (isOperator(tempchar) || tempchar == '(') {
                    if (s == '.' || isOperator(s)) {
                        firstChar = s;
                        return true;
                    } else
                        return false;
                } else {
                    if (s != ')' && s != '(') {
                        firstChar = s;
                        return true;
                    } else if (s == ')' && leftBracket > rightBracket) {
                        rightBracket++;
                        firstChar = s;
                        return true;
                    } else if (s == ')' && leftBracket <= rightBracket)
                        return false;
                }
            }
        } else if (Character.isDigit(firstChar) && firstChar != '0') {
            if (s != ')' && s != '(') {
                firstChar = s;
                return true;
            } else if (s == ')' && leftBracket > rightBracket) {
                rightBracket++;
                firstChar = s;
                return true;
            } else if (s == ')' && leftBracket <= rightBracket)
                return false;
        } else if (isOperator(firstChar)) {
            if (Character.isDigit(s) || s == '(') {
                if (s == '(') {
                    leftBracket++;
                }
                firstChar = s;
                return true;
            } else if (isOperator(s) || s == '.' || s == ')')
                return false;
        } else if (firstChar == '(') {
            if (Character.isDigit(s) || s == '-') {
                firstChar = s;
                return true;
            } else
                return false;
        } else if (firstChar == ')') {
            if (isOperator(s)) {
                firstChar = s;
                return true;
            } else
                return false;
        } else if (firstChar == '.') {
            if (Character.isDigit(s)) {
                firstChar = s;
                return true;
            } else
                return false;
        }
        return false;
    }

    public void deleteChar() {
        if (formula.isEmpty())
            return;
        if (1 == formula.length()) {
            formula = "";
            return;
        }
        String tempFormula = formula.substring(0, formula.length() - 1);
        firstChar = tempFormula.charAt(tempFormula.length() - 1);
        formula = tempFormula;
    }

    // 输入“=”号时，表达式最后一位为数字或“）”,并且左括号数量和右括号数量一致，且表达式长度最少为3时，才能进行计算
    public boolean checkEqual(char s) {
        if (s == '=' && leftBracket == rightBracket && formula.length() > 2) {
            if (Character.isDigit(formula.charAt(formula.length() - 1))
                    || formula.charAt(formula.length() - 1) == ')') {
                calculate();
                return true;
            }
            else
                return false;
        }
        return false;
    }

    private void calculate() {
        if (formula.length() > 2) {
            setResult(toPostfixFormula(formula));
        }
    }

    private String toPostfixFormula(String formula) {
        int length = formula.length();
        char c, tempChar;
        Stack<Character> s1 = new Stack<>();
        Stack<Double> s2 = new Stack<>();
        double number;
        int lastIndex = -1;
        for (int i = 0; i < length; ++i) {
            c = formula.charAt(i);
            if (Character.isDigit(c)) {
                lastIndex = readDouble(formula, i);
                String string = formula.substring(i, lastIndex);
                number = Double.parseDouble(string);
                s2.push(number);
                i = lastIndex - 1;
            } else if (c == '-') {
                if (i == 0) {
                    lastIndex = readDouble(formula, i + 1);
                    number = -1 * (Double.parseDouble(formula.substring(i + 1, lastIndex)));
                    s2.push(number);
                    i = lastIndex - 1;
                } else if (i > 0) {
                    if (formula.charAt(i - 1) == '(') {
                        lastIndex = readDouble(formula, i + 1);
                        number = -1 * (Double.parseDouble(formula.substring(i + 1, lastIndex)));
                        s2.push(number);
                        i = lastIndex - 1;
                    } else {
                        while (!s1.isEmpty() && s1.peek() != '(' && priorityCompare(c, s1.peek()) < 0) {
                            double num1 = s2.pop();
                            double num2 = s2.pop();
                            if (num1 == 0 && s1.pop() == '/') {
                                message = "出错，除数不能为0！";
                                return message;
                            } else
                                s2.push(calc(num2, num1, s1.pop()));
                        }
                        s1.push(c);
                    }
                }
            } else if (isOperator(c) && c != '-') {
                while (!s1.isEmpty() && s1.peek() != '(' && priorityCompare(c, s1.peek()) < 0) {
                    double num1 = s2.pop();
                    double num2 = s2.pop();
                    if (num1 == 0 && s1.pop() == '/') {
                        message = "出错，除数不能为0！";
                        return message;
                    } else
                        s2.push(calc(num2, num1, s1.pop()));
                }
                s1.push(c);
            } else if (c == '(') {
                s1.push(c);
            } else if (c == ')') {
                while ((tempChar = s1.pop()) != '(') {
                    double num1 = s2.pop();
                    double num2 = s2.pop();
                    if (num1 == 0 && tempChar == '/') {
                        message = "出错，除数不能为0！";
                        return message;
                    } else
                        s2.push(calc(num2, num1, tempChar));
                }
            }

        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
            double num1 = s2.pop();
            double num2 = s2.pop();
            if (num1 == 0 && tempChar == '/') {
                message = "出错，除数不能为0！";
                return message;
            } else
                s2.push(calc(num2, num1, tempChar));
        }
        double result = s2.pop();
        return Double.toString(result);
    }

    private int readDouble(String formula, int start) {
        int length = formula.length();
        char c;
        int lastIndex = -1;
        for (int i = start; i < length; i++) {
            if (i == length - 1) {
                lastIndex = length;
                break;
            } else {
                c = formula.charAt(i + 1);
                if (isOperator(c) || c == '(' || c == ')') {
                    lastIndex = i + 1;
                    break;
                }
            }
        }
        return lastIndex;
    }

    private boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private int priorityCompare(char op1, char op2) {
        switch (op1) {
            case '+':
            case '-':
                return (op2 == '*' || op2 == '/' ? -1 : 0);
            case '*':
            case '/':
                return (op2 == '+' || op2 == '-' ? 1 : 0);
        }
        return 1;
    }

    private double calc(double num1, double num2, char op) {
        switch (op) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                return 0; // will never catch up here
        }
    }
}


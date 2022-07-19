import java.util.LinkedList;

class Calculator {
    /*
     * Given an input string, return the result of evaluating the expression.
     *
     * Example Input:
     * ( ( -6 + 2 ) / - 3 ) + ( ( 1 + 3 ) / 3 ) + ( ( 2 * 2 ) / 3 ) -> 4
     */
    public String calculate(LinkedList<String> expression) {
        LinkedList<String> rpnExpression = toRPN(expression);

        for (int i=0; i<rpnExpression.size(); i++) {
            String val = rpnExpression.get(i);
            switch (val) {
                case "-" -> {
                    rpnExpression.set(i - 2, String.valueOf(Double.parseDouble(rpnExpression.get(i - 2)) - Double.parseDouble(rpnExpression.get(i - 1))));
                    rpnExpression.remove(i - 1);
                    rpnExpression.remove(i - 1);
                    i -= 2;
                }
                case "+" -> {
                    rpnExpression.set(i - 2, String.valueOf(Double.parseDouble(rpnExpression.get(i - 2)) + Double.parseDouble(rpnExpression.get(i - 1))));
                    rpnExpression.remove(i - 1);
                    rpnExpression.remove(i - 1);
                    i -= 2;
                }
                case "x" -> {
                    rpnExpression.set(i - 2, String.valueOf(Double.parseDouble(rpnExpression.get(i - 2)) * Double.parseDouble(rpnExpression.get(i - 1))));
                    rpnExpression.remove(i - 1);
                    rpnExpression.remove(i - 1);
                    i -= 2;
                }
                case "/" -> {
                    rpnExpression.set(i - 2, String.valueOf(Double.parseDouble(rpnExpression.get(i - 2)) / Double.parseDouble(rpnExpression.get(i - 1))));
                    rpnExpression.remove(i - 1);
                    rpnExpression.remove(i - 1);
                    i -= 2;
                }
                default -> {
                }
            }
        }

        return rpnExpression.get(0);
    }

    /*
     * Convert a list of strings representing an expression into a list of strings representing the expression in reverse polish notation.
     * By using reverse polish notation, we can evaluate the expression without the need for order of operations.
     * A simple "left to right" calculation is all that's required.
     *
     * For example, the expression "1 + 2 + 5" would be converted to ["1","2","+","5","+"]
     */
    private static LinkedList<String> toRPN(LinkedList<String> expression) {
        LinkedList<String> symbols = new LinkedList<>();
        LinkedList<String> newExpression = new LinkedList<>();

        for (String val : expression) {
            if (val.equals("")) continue;

            switch (val) {
                case "(":
                    symbols.add(val);
                    break;
                case ")":
                    boolean isOk = true;

                    while (isOk) {
                        String _symbol = symbols.pollLast();
                        assert _symbol != null;
                        if (_symbol.equals("(")) isOk = false;
                        else newExpression.add(_symbol);
                    }

                    break;
                case "/", "+", "-", "x":
                    if (symbols.size() == 0) {
                        symbols.add(val);
                    } else if (compareSymbols(val, symbols.get(symbols.size() - 1))) symbols.add(val);
                    else {
                        while (symbols.size() > 0 && !compareSymbols(val, symbols.get(symbols.size() - 1))) {
                            newExpression.add(symbols.pollLast());
                        }
                        symbols.add(val);
                    }
                    break;
                default:
                    newExpression.add(val);
            }
        }
        while(symbols.size()>0) newExpression.add(symbols.pollLast());
        return newExpression;
    }

    private static boolean compareSymbols(String newSymbol, String existSymbol){
        if(existSymbol.equals("(")) return true;
        if(newSymbol.equals(existSymbol)) return false;
        else if((newSymbol.equals("*")||newSymbol.equals("/"))&&(existSymbol.equals("*")||existSymbol.equals("/"))) return false;
        else if((newSymbol.equals("+")||newSymbol.equals("-"))&&(existSymbol.equals("+")||existSymbol.equals("-"))) return false;
        else return (!newSymbol.equals("-") && !newSymbol.equals("+")) || (!existSymbol.equals("*") && !existSymbol.equals("/"));
    }
}
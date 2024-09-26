package calculator.operations;

import calculator.Command;
import calculator.Context;
import collection.ArrayStack;

public class Sqrt implements Command {
    @Override
    public void make(Context context) {
        ArrayStack<Double> stack = context.getStack();
        Double sum = (Double) stack.pop();
        stack.push(Math.sqrt(sum));
    }
}
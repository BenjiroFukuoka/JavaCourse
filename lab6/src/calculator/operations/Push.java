package calculator.operations;

import java.util.Map;

import calculator.Command;
import calculator.Context;
import collection.ArrayStack;

public class Push implements Command {
    private String str;

    public Push(String str) {
        this.str = str;
    }

    @Override
    public void make(Context context) {
        ArrayStack<Double> stack = context.getStack();
        Map<String, Double> table = context.getMap();
        if(table.containsKey(str))
            stack.push(table.get(str));
        else
            stack.push(Double.valueOf(str));
    }
}
package calculator.operations;

import calculator.Command;
import calculator.Context;

public class Print implements Command {
    @Override
    public void make(Context context) {
        System.out.println(context.getStack().peek());
    }
}
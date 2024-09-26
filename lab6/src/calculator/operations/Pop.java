package calculator.operations;

import calculator.Command;
import calculator.Context;

public class Pop implements Command {
    @Override
    public void make(Context context) {
        context.getStack().pop();
    }
}
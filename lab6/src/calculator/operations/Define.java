package calculator.operations;

import java.util.Map;

import calculator.Command;
import calculator.Context;

public class Define implements Command {
    private String str;
    private double value;

    public Define(String left, String right) {
        this.str = left;
        value = Double.valueOf(right);
    }

    @Override
    public void make(Context context) {
        Map<String, Double> table = context.getMap();
        table.put(str, value);
    }
}

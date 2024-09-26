package calculator.operations;

import calculator.Command;
import calculator.Context;

public class Commit implements Command {
    String str = "";

    public Commit(String[] str) {
        for (int i = 0; i < str.length; ++i) {
            this.str += str[i] + " ";
        }
    }

    @Override
    public void make(Context context) {
        System.out.println(str);
    }
}

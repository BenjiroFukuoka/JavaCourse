package calculator;

import java.util.HashMap;
import java.util.Map;

import collection.ArrayStack;

public class Context {
    private final Factory factory = new Selector();
    private final ArrayStack<Double> stack = new ArrayStack<>();
    private final Map<String, Double> map = new HashMap<>();

    public ArrayStack<Double> getStack() {
        return stack;
    }

    public Map<String, Double> getMap() {
        return map;
    }

    public Factory getFactory() {
        return factory;
    }

}
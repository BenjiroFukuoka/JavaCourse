package calculator;

import calculator.operations.*;
import exceptions.*;

class Selector implements Factory {
    @Override
    public Command createCommand(String lines) throws UnknownСommandException {
        String[] lineByLine;
        lineByLine = lines.split("\\s");
        switch (lineByLine[0]) {
            case "#":
                return new Commit(lineByLine);
            case "PUSH":
                return new Push(lineByLine[1]);
            case "POP":
                return new Pop();
            case "ADD":
                return new Add();
            case "SUB":
                return new Sub();
            case "MUL":
                return new Mul();
            case "DIV":
                return new Div();
            case "SQRT":
                return new Sqrt();
            case "PRINT":
                return new Print();
            case "DEFINE":
                return new Define(lineByLine[1], lineByLine[2]);
            case "EXIT":
                break;
            default:
                throw new UnknownСommandException("Такой команды нету!");
        }
        return null;
    }
}
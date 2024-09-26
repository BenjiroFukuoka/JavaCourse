package calculator;

import exceptions.UnknownСommandException;

public interface Factory {
    Command createCommand(String line) throws UnknownСommandException;
}
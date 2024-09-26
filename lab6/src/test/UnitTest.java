package test;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import calculator.Command;
import calculator.Context;
import exceptions.UnknownСommandException;

public class UnitTest {
    private Context context;
    private String str;

    @Before
    public void before() {
        context = new Context();
        str = "";
    }

    @After
    public void after() {
        context = null;
        str = "";
    }

    @Test
    public void testDEFINE() throws UnknownСommandException {
        str = "DEFINE X 25";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(true, context.getMap().containsKey("X"));
    }

    @Test
    public void testPUSH() throws UnknownСommandException {
        str = "PUSH 20";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(20d, context.getStack().peek());
    }

    @Test
    public void testADD() throws UnknownСommandException {
        context.getStack().push(20d);
        context.getStack().push(10d);
        str = "ADD";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(30d, context.getStack().peek());
    }

    @Test
    public void testDIV() throws UnknownСommandException {
        context.getStack().push(10d);
        context.getStack().push(20d);
        str = "DIV";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(20d / 10d, context.getStack().peek());
    }

    @Test
    public void testMUL() throws UnknownСommandException {
        context.getStack().push(20d);
        context.getStack().push(10d);
        str = "MUL";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(20d * 10d, context.getStack().peek());
    }

    @Test
    public void testPOP() throws UnknownСommandException {
        context.getStack().push(10d);
        context.getStack().push(20d);
        str = "POP";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(10d, context.getStack().peek());
    }

    @Test
    public void testPrint() throws UnknownСommandException {
        context.getStack().push(10d);
        str = "PRINT";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(10d, context.getStack().peek());
    }

    @Test
    public void testSQRT() throws UnknownСommandException {
        context.getStack().push(10d);
        str = "SQRT";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(Math.sqrt(10d), context.getStack().peek());
    }

    @Test
    public void testSUB() throws UnknownСommandException {
        context.getStack().push(10d);
        context.getStack().push(20d);
        str = "SUB";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals(20d - 10d, context.getStack().peek());
    }

    @Test
    public void testCOMMIT() throws UnknownСommandException {
        context.getStack().push("HELLO, WORLD!");
        str = "# HELLO, WORLD!";
        Command test = context.getFactory().createCommand(str);
        test.make(context);
        assertEquals("HELLO, WORLD!", context.getStack().peek());
    }
}
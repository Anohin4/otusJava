package ru.otus.examples;

import static ru.otus.assertions.Assertions.assertEquals;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClassWithState {

    private String testString;

    public TestClassWithState() {
        this.testString = "initialState";
    }

    @Before
    public void before() {
        this.testString = "beforeState";
    }

    @Test
    public void testOne() {
        assertEquals("beforeState", testString);
    }

    @Test
    public void secondTest() {
        assertEquals("beforeState", testString);
    }

    @After
    public void after() {
        this.testString = "afterState";

    }

}

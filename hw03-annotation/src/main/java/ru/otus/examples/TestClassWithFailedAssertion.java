package ru.otus.examples;

import static ru.otus.assertions.Assertions.assertEquals;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClassWithFailedAssertion {

    private String testString;

    public TestClassWithFailedAssertion() {
        this.testString = "initialState";
    }

    @Before
    public void before() {
        this.testString = "beforeState";
    }

    @Test
    public void testOne() {
        assertEquals("incorrectResult", testString);
    }

    @Test
    public void testTwo() {
        assertEquals("incorrectResult", testString);
    }
    @Test
    public void testThree() {
        assertEquals("incorrectResult", testString);
    }
    @Test
    public void testFour() {
        assertEquals("incorrectResult", testString);
    }

    @Test
    public void goodTest() {
        assertEquals("beforeState", testString);
    }

    @After
    public void after() {
        this.testString = "afterState";

    }

}

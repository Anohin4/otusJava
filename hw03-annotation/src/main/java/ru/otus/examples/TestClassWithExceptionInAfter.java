package ru.otus.examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClassWithExceptionInAfter {

    @Before
    public void before() {
        System.out.println("this is before");

    }

    @Test
    public void test() {
        System.out.println("this is test");
    }

    @After
    public void after() {
        System.out.println("this is after");
        throw new RuntimeException("this is exception");
    }

}

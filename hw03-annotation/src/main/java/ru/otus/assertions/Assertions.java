package ru.otus.assertions;

import ru.otus.exceptions.AssertException;

public class Assertions {

    public static void assertEquals(Object one, Object two) {
        if (!one.equals(two)) {
            throw new AssertException(one.toString() + " not equals " + two.toString());
        }
    }

}

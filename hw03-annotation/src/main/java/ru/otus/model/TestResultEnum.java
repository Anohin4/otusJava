package ru.otus.model;

import ru.otus.exceptions.AssertException;
import ru.otus.exceptions.SetUpException;
import ru.otus.exceptions.TearDownException;

public enum TestResultEnum {
    OK(OkException.class, "Успешно пройденные тесты"),
    TEST_FAILED(AssertException.class, "Проваленные тесты "),
    TEST_FAILED_WITH_EXCEPTION(Exception.class, "Тесты завершившиеся ошибкой"),
    SET_UP_EXCEPTION(SetUpException.class, "Тесты завершившиеся ошибкой во время метода Before"),
    TEAR_DOWN_EXCEPTION(TearDownException.class, "Тесты завершившиеся ошибкой во время метода After"),
    UNKNOWN(Exception.class, "Тесты с неизвестным поведением ");

    private Class<? extends Exception> correspondingException;
    public String description;

    TestResultEnum(Class<? extends Exception> okExceptionClass, String description) {
        this.correspondingException = okExceptionClass;
        this.description = description;
    }

    public static TestResultEnum getByException(Exception e) {
        for (TestResultEnum enumValue : TestResultEnum.values()) {
            if (e.getClass().equals(enumValue.correspondingException)) {
                return enumValue;
            }
        }
        return TEST_FAILED_WITH_EXCEPTION;
    }

    private class OkException extends Exception {

    }

}

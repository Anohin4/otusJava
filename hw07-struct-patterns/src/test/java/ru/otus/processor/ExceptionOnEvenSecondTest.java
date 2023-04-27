package ru.otus.processor;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.otus.model.Message;

class ExceptionOnEvenSecondTest {

    private ExceptionOnEvenSecond processor;

    @BeforeEach
    void setUp() {
        processor = new ExceptionOnEvenSecond();
    }

    @Test
    public void throwExceptionOnEvenSecond() {
        LocalDateTime of = LocalDateTime.of(2020, 2, 2, 3, 3, 0);

        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(of);
            assertThatThrownBy(() -> processor.process(new Message.Builder(1L).build()))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    @Test
    public void doesntThrowExceptionOnNotEvenSecond() {
        LocalDateTime of = LocalDateTime.of(2020, 2, 2, 3, 3, 1);

        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(of);
            assertDoesNotThrow(() -> processor.process(new Message.Builder(1L).build()));
        }
    }

}
package ru.otus.service.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import ru.otus.exceptions.TestValidationException;
import ru.otus.utils.Multimap;

public class TestClassValidatorImpl implements TestClassValidator {

    private Multimap<Class<? extends Annotation>, Method> repoMap;

    public TestClassValidatorImpl(Multimap<Class<? extends Annotation>, Method> repoMap) {
        this.repoMap = repoMap;
    }

    @Override
    public void validate() {
        Set<Entry<Class<? extends Annotation>, List<Method>>> entrySet = repoMap.getEntrySet();
        for (var entry : entrySet) {

            validatePublicModifier(entry.getValue());
        }
    }

    private static void validatePublicModifier(List<Method> orEmptyList) {
        for (Method method : orEmptyList) {
            if (!Modifier.isPublic(method.getModifiers())) {
                throw new TestValidationException("Методы должны быть public");
            }
        }
    }

}

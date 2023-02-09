package ru.otus.command;

public class LogStep implements TestStep {

    private final String methodName;

    public LogStep(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public void execute() {
        System.out.println(
                "------------------------------------------------------------------------------------------");
        System.out.println("Начинаем выполнение теста " + methodName);
    }
}

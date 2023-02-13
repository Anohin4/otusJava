package proxy;

public class Main {

    public static void main(String[] args) {

        MyTestClass myClass = Ioc.createMytestClass();
        myClass.doSmth("this is one");
        myClass.doSmth("one", 2.0, true);
        myClass.doSmth("qwer", 2);

    }
}

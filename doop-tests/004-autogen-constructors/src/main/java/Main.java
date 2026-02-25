
public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "autogen constructors" + "\033[0m]\n");

        new ImplicitConstructor().foo();
        new ExplicitConstructor().foo();
    }
}

class ImplicitConstructor {
    void foo() {
        System.out.println("ImplicitConstructor.foo()");
    }
}

class ExplicitConstructor {
    public ExplicitConstructor() {
        System.out.println("ExplicitConstructor.ExplicitConstructor()");
    }

    void foo() {
        System.out.println("ExplicitConstructor.foo()");
    }
}

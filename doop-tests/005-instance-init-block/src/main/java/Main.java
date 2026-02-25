
public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "instance initializer block" + "\033[0m]\n");

        new Test().printme();
    }
}

class Integerius {
    int value;
    Integerius(Integer i) {
        value = i;
    }
}

class Test {

    int foo = new Integer(111);
    int bar = foo * new Integerius(2).value;
    int zoo = bar * epicRand();

    {
        foo = new Integer(222);
    }

    Test() {

    }

    Test(int foov) {
        foo = foov;
    }

    int epicRand() {
        return 2;
    }

    void printme() {
        System.out.println("foo: " + foo);
        System.out.println("bar: " + bar);
        System.out.println("zoo: " + zoo);
    }
}

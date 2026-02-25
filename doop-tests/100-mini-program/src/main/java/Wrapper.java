
public class Wrapper {

    public A a = new A();
    public B b;

    Wrapper() {
        b = new B();
    }

    public void generateNewA(boolean... isB) {
        a = (isB.length > 0 && isB[0]) ? new B() : new A();
    }

    public void generateNewB() {
        b = new B();
    }

}

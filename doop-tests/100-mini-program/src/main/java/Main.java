
public class Main {

    public static A a;
    public static B b;

    /**
     * main
     */
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "mini program (100)" + "\033[0m]\n");

        A a = new A();
        a.speak();

        B b = new B();
        a = b;
        a.speak();

        a = identityFunctionA(new A());
        a.speak();

        b = createNewB();
        b.speak();
        a = b;
        a.speak();

        Main.a = b;
        Main.a.speak();

        irrelevantMethod();
        irrelevantMethod(a, b);
        a.speak();
        b.speak();

        relevantMethod();
        Main.a.speak();
        Main.b.speak();


        Wrapper rapper = new Wrapper();
        rapper.generateNewA();
        rapper.a.speak();

        rapper.generateNewA(true);
        Main.a = rapper.a;
        Main.a.speak();

        b = rapper.b;
        b.speak();

        new CrazyPath().walk(rapper);
        rapper.a.speak();
    }

    /**********************************
     * Possibly relevant methods & functions.
     */

    private static A relevantMethod() {
        Main.a = new B();
        Main.b = new B();
        return new A();
    }

    private static A identityFunctionA(A a) {
        return a;
    }

    private static B identityFunctionB(B b) {
        return b;
    }

    private static A identityFunctionBtoA(B b) {
        return b;
    }

    private static A createNewA() {
        return new A();
    }

    private static B createNewB() {
        return new B();
    }

    /**********************************
     * Irrelevant methods and functions.
     */

    private static void irrelevantMethod() {
        A a = new B();
        a.speak();

        B b = new B(); // "a" should point here, because of the following command "a = b".
        a = b;
        a.speak();
    }

    private static void irrelevantMethod(A _a, B _b) {
        A a = new B();
        a.speak();

        B b = new B(); // "a" should not point here.
        b.speak();
    }

}

// Test for "access$N" methods that are created behind the scenes by
// javac.

public class Main {
    public static void main(String[] args) {
        A testA = new A();
        I b = testA.getB();
        b.test();
    }
}

class A {
    private Object obj1;

    public A() { this.obj1 = new Object(); }

    private static class B implements I {
        private A a;
        public B() { this.a = new A(); }
        public void test() {
            // To read field obj1 from A, javac creates the following
            // method in A and calls it below:
            //   static java.lang.Object access$000(A)
            Object o1 = a.obj1;
            System.out.println("o1: " + o1.hashCode());
            // To write field obj1 from A, javac creates the following
            // method in A and calls it below:
            //   static java.lang.Object access$002(A, java.lang.Object)
            a.obj1 = new Object();
            Object o2 = a.obj1;
            System.out.println("o2: " + o2.hashCode());
        }
    }

    public I getB() { return new B(); }
}

interface I { void test(); }

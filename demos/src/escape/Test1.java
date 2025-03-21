class A {
	B fld;
}

class B {
}

public class Test1 {
    static void meth0() {
        A a0 = new A();
        B b1;
        B b2 = new B();
        B b3;
        A a4;

        b1 = b2;
        a4 = a0;
        b3 = a0.fld; // Don't know about its value yet
        a0.fld = b1;
    }

    static void meth1() {
        A a1 = new A();
        B b1 = new B();
        A a2 = a1;
        a1.fld = b1;
        B b2 = a2.fld;
    }

	static void meth2() {
		A a0 = new A();
		B b1 = new B();
		a0.fld = b1;
		B b2 = b1;
	}

    static A staticA;
    static B staticB;

    static void meth3() {
        A a1 = new A();
        B b1 = new B();
		a1.fld = b1;
        staticA = a1;
        A a2 = staticA;
    }

	public static void main(String args[]) {}
}

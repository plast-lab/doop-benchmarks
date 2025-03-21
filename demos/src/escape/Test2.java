class A {
    B f0;
}

class B {
    C f1;
    D f2;
}

class C {}

class D {}

public class Test2 {

    static void meth0() {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        A x = a;
        a.f0 = b;
        b.f1 = c;
        b.f2 = d;
        B z = b;
        B zz = x.f0;
        C cc = z.f1;
    }

	static B staticB;

	static void meth1() {
		A a0 = new A();
		B b0 = new B();
		a0.f0 = b0;
		staticB = a0.f0;
		B b1 = new B();
		a0.f0 = b1;
	}

	public static void main(String args[]) {}
}

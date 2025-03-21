class A {
    static B staticB = null;
    A next;
    B member;
    A(A next, B member) { this.next = next; this.member = member; }
}

class B {
    A container;
	B() {}
    B(A container) { this.container = container; }
}

class C {
	final B b;
	C(B b) { this.b = b; foo(); } // does the assignment survive the call to an easy "foo"?
	void foo() { for (int i = 0 ; i < 100; i++) ; }
}

public class Test2 {
	A test1() {
		B b = new B(null);
		A a = new A(null, b); // test constructors
        return a;
	}

	static A test2(boolean b) {
		A a = null;
		if (b)
			a = new A(a, null); 
		return a;  // test phi nodes
	}

	static A test3() {
		Object a = new A(null, null);
		A a2 = (A) a; // test casts
		return a2;
	}

	static A test4(B b) {
		String s = "foo"; // test immutable objects
		A a = new A(null, b);
		a.next = a;    // test store
		return a;
	}

	static void test5() {
		A.staticB = new B();
	}

	static void test6(A a) {
		a.next.member = new B();
	}
	
    public static void main(String[] args) {
		Test2 t = new Test2();
		A a1 = t.test1(); // test virtual calls
		B b1 = a1.member; // test loading fields

		A a2 = test2(true); // test phi nodes
		B b2 = a2.member; // test members survive control-flow joining

		A a3 = test4(b2); // test parameter passing
		a3.next = a2;     // test stores
		B b3 = a3.member;

		test5();
		B b4 = A.staticB; // test static loads, access path propagating after call

		test6(a3);  // test access path of length 3 coming back. Works only with MustAlias logic enabled
		B b5 = a3.next.member;

		C c1 = new C(b4);
		c1.toString().notify(); // just adding noise
		B b6 = c1.b;  // final field still read right?
    }
}

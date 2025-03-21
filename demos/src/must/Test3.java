class A {
    static B staticB;
    A next;
    B member;

    A(A next, B member) { this.next = next; this.member = member; staticB = member; }

    int foo(A a) { 
	member.container = a; 
	if (a != null) {
	    member.container = null; 
	}
	return 0;
    }
}

class B {
    A container;
    B(A container) { this.container = container; }
}

public class Test3 {
    public static void main(String[] args) {
	B b1 = new B(null);
	A a1 = new A(null, b1);
	A a2 = new A(a1, b1);
	b1.container = a1;
	a1.foo(a2);
    }
}
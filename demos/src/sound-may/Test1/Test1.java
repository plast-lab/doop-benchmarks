class A {
    static B staticB = null;
    A next;
    B member;
	java.util.Map<A,A> m = new java.util.HashMap();
	
    A(A next, B member) { this.next = next; this.member = member; staticB = member; }

    B foo(A a) { 
		member.container = a;
		m.put(this, a);
		if (a != null) {
			member.container = null;
			return member;
		}
		//		return member;
		return next.member;
    }
}

class B {
    A container;
	B() {}
    B(A container) { this.container = container; }
	static void bar(B b, A a) {
		b.container = a.next;
	}
}

public class Test1 {
    public static void main(String[] args) {
		B b1 = new B();
		A a1 = new A(null, b1);
		A a2 = new A(a1, b1);
		Object o  = a2;
		A a0 = (A) o; // just ensure casts work
		A a3 = a2.next;
		b1.container = a1;
		B b2 = a2.foo(a2);
		B.bar(b2, a2);
		B b3 = A.staticB;
		B[] barr = new B[10];
		barr[0] = new B(a1);
		A a4 = barr[0].container;
		A a5 = a2.m.get(a2);
    }
}

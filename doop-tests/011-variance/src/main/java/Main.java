// Test of variance in Java: do the changed signatures in the
// subclasses confuse Soot/Doop/clue-server? For example, is t.meth2()
// below correctly resolved?
//
// Note: "The overriding method is covariant in the return type and
// invariant in the argument types."
// (https://briangordon.github.io/2014/09/covariance-and-contravariance.html)

public class Main {
    public static void main(String[] args) {
        I t = new Test();

        Object ret1a = t.meth1(new Clazz());
        System.out.println(ret1a.getClass().getName());
        Object ret1b = t.meth1(new SubClazz());
        System.out.println(ret1b.getClass().getName());

        Object ret2 = t.meth2(new Object());
        System.out.println(ret2.getClass().getName());
    }
}

class Clazz { }
class SubClazz extends Clazz { }

interface I {
    Object meth1(Clazz obj1);
    Clazz meth2(Object obj2);
}

class Test implements I {
    @Override
    public Object meth1(Clazz obj1) {
        System.out.println("Object Test.meth1(Clazz) called.");
        return new Object();
    }

    // We cannot annotate this using @Override: "If you change any
    // argument type, then you are not really overriding a method --
    // you are actually overloading it."
    // (https://blogs.oracle.com/sundararajan/covariant-return-types-in-java)
    // @Override
    public Object meth1(SubClazz obj1) {
        System.out.println("Test.meth1(SubClazz) called.");
        return new Object();
    }

    @Override
    public SubClazz meth2(Object obj2) {
        System.out.println("SubClazz Test.meth2(Object) called.");
        return new SubClazz();
    }        
}

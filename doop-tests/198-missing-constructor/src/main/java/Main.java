// Test for CLUE-198: field initialization when default constructor is missing.

public class Main {

    Object obj = new Object();
    int[] ints = new int[10];

    public void print() {
        System.out.println(obj.hashCode());
        System.out.println(ints.length);
    }
    
    public static void main(String[] args) {
        Main m = new Main();
        m.print();

        // Anonymous class based on interface.
        I anonI = new I() {
                public    Object field1 = new Object();
                private   Object field2 = new Object();
                protected Object field3 = new Object();
                          Object field4 = new Object();
                          Object field5;

                // Instance initializer.
                {
                    field5 = new Object();
                }

                // Overrides method in I.
                public void intMeth(int x) {
                    System.out.println("x = " + x);
                    report();
                }
                // New method, can only be found via reflection (see below).
                public void report() {
                    System.out.println("Anonymous class based on interface I:");
                    System.out.println("field1 = " + field1.hashCode());
                    System.out.println("field2 = " + field2.hashCode());
                    System.out.println("field3 = " + field3.hashCode());
                    System.out.println("field4 = " + field4.hashCode());
                    System.out.println("field5 = " + field5.hashCode());

                    Inner1 i1 = new Inner1();
                    i1.o = new Object();
                    System.out.println("i1 -> " + i1.hashCode());
                    Inner2 i2 = new Inner2();
                    System.out.println("i2 -> " + i2.hashCode());
                    i2.print(new Object());
                }

                class Inner1 {
                    Object o;
                }

                class Inner2 {
                    void print(Object obj0) {
                        System.out.println("obj0 -> " + obj0.hashCode());
                        System.out.println("Inner2.print(): captured field4 is " + field4);
                    }
                }
            };
        anonI.intMeth(10);

        // We cannot call report() directly, since anonI is of type I
        // and I does not declare report() (but see further below for
        // how to call a 'test()' method that does not belong to the
        // base type). We have to use reflection here (the following
        // code may not be fully handled in Doop when analyzing
        // without reflection support):
        String r = "report";
        try {
            java.lang.reflect.Method method = anonI.getClass().getMethod(r);
            method.invoke(anonI, new Object[] { });
        } catch (Exception e) {
            System.err.println("Could not call: " + r);
            e.printStackTrace();
        }

        // Anonymous class based on (abstract) class.
        A anonA = new A() {
                Object getObject() {
                    return new Object();
                }
            };
        Object objA = anonA.getObject();
        System.out.println(objA.hashCode());

        // Calling a method that is not declared in the base type of
        // the anonymous class.
        (new I() {
                public void intMeth(int x) {
                }
                public I test() {
                    System.out.println("test!");
                    return this;
                }
            }).test();
    }
}

interface I {
    void intMeth(int x);
}

abstract class A {
    abstract Object getObject();
}

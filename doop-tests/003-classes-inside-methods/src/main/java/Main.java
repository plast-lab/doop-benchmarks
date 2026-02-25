
public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "classes inside methods" + "\033[0m]\n");

        class InnerA {
            public int foo() {
                int iii = 1111;
                return iii;
            }
        }

        class InnerX {
            public int foo() {
                int iii = 2222;
                return iii;
            }
        }

        System.out.println("Main.main().InnerA.foo().iii = " + new InnerA().foo());
        System.out.println("Main.main().InnerX.foo().iii = " + new InnerX().foo());
        method();
    }

    public static void method() {
        class InnerA {
            public int foo() {
                int iii = 3333;
                return iii;
            }
        }

        class InnerX {
            public int bar() {
                int iii = 4444;
                return iii;
            }
        }

        System.out.println("Main.method().InnerA.foo().iii = " + new InnerA().foo());
        System.out.println("Main.method().InnerX.bar().iii = " + new InnerX().bar());

        Runnable aRunnable = (new A()).getRunnable();
        (new Thread(aRunnable)).start();
    }
}

// CLUE-204: handling of "val$" fields.
class A {
    private Runnable r = new Runnable() {
            @Override
            public void run() {
                run2(new Object());
            }
            private void run2(Object param) {
                // This tests captures of both parameters and
                // variables defined in the body of this method. From
                // https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html:
                // "An anonymous class cannot access local variables
                // in its enclosing scope that are not declared as
                // final or effectively final."  If 'final' is omitted
                // below and 'obj' is mutated, the compiler aborts
                // with "error: local variables referenced from an
                // inner class must be final or effectively final".
                final Object obj = new Object();
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println("obj.hashCode()   = " + obj.hashCode());
                        System.out.println("param.hashCode() = " + param.hashCode());
                    }

                }.start();
            }
        };
    public Runnable getRunnable() {
        return r;
    }
}

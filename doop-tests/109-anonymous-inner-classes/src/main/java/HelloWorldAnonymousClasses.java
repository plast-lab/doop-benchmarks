// Example based on the one from https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
public class HelloWorldAnonymousClasses {

    interface HelloWorld {
        public void greet();
        public void greetSomeone(String someone);
    }

    public void sayHello() {

        class EnglishGreeting implements HelloWorld {
            String name = "world";
            public void greet() {
                greetSomeone("world");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }
        }

        HelloWorld englishGreeting = new EnglishGreeting();

        HelloWorld frenchGreeting = new HelloWorld() {
                String name = "tout le monde";
                public void greet() {
                    greetSomeone("tout le monde");
                }
                public void greetSomeone(String someone) {
                    name = someone;
                    System.out.println("Salut " + name);
                }
            };

        HelloWorld spanishGreeting = new HelloWorld() {
                String name = "mundo";
                public void greet() {
                    greetSomeone("mundo");
                }
                public void greetSomeone(String someone) {
                    name = someone;
                    System.out.println("Hola, " + name);
                }
            };
        englishGreeting.greet();
        frenchGreeting.greetSomeone("Fred");
        spanishGreeting.greet();
    }

    public static void main(String... args) {
        HelloWorldAnonymousClasses myApp = new HelloWorldAnonymousClasses();
        myApp.sayHello();

        (new A()).shake();
    }
}

// CLUE-203: anonymous inner classes accessing outer class instances.
class A {
    A fieldA;

    public void shake() {
        Runnable r = new Runnable() {
                @Override
                public void run() {
                    A a = A.this;
                    fieldA = a;
                    System.out.println(a.toString());
                }
            };
        (new Thread(r)).start();
    }

    public String toString() {
        String s = fieldA == null ? "null" : "some A";
        return "a : " + s;
    }
}


public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "classes inside classes" + "\033[0m]\n");

        int iii = 1111;
        System.out.println(iii * 2);

        new Main().new InnerLevel1().new InnerLevel2().foo();
    }

    class InnerLevel1 {
        class InnerLevel2 {
            void foo() {
                int iii = 8888;
                System.out.println("Main.InnerLevel1.InnerLevel2.foo().iii = " + iii);
            }
        }
    }

}

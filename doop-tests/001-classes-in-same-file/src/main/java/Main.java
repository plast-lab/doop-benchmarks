
public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "classes in same file" + "\033[0m]\n");

        int iii = 1111;
        System.out.println(iii * 2);

        new SameFileA().foo();
        new SameFileB().foo();
    }

}

class SameFileA {
    public void foo() {
        int iii = 8888;
        System.out.println("SameFileA.foo().iii = " + iii);
    }
}

class SameFileB extends SameFileA {
    @Override
    public void foo() {
        int iii = 9999;
        System.out.println("SameFileB.foo().iii = " + iii);
    }
}

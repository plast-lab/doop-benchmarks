
public class Main {
    public static void main(String[] args) {
        System.out.println("[module: \033[34m" + "classes in different files" + "\033[0m]\n");

        int iii = 1111;
        System.out.println(iii * 2);

        new OtherFileA().foo();
        new OtherFileB().foo();
    }
}

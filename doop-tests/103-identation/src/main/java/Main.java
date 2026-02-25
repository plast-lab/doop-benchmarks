// Test for bug CLUE-53.
public class Main {
    static String foo;
    public static void main(String[] args) {
            String var = "asdasda";
            foo = var;
        	Object baz = new Object();
        	foo = (String) baz;
        String foobar = new String("ASDAS");
        foo = foobar;
    }
}

/*
 * Test that exceptions work. Also exhibits bug where variable names
 * (such as "rex" in the code below) are lost in Jimple.
 * https://bitbucket.org/yanniss/doop-nexgen/issues/88/variable-names-are-lost
 */
public class Main {
    public static void main(String[] args) {
        try {
            RuntimeException exc = new RuntimeException();
            throw exc;
        } catch (RuntimeException rex) {
            rex.printStackTrace();
        }
    }
}

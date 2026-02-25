// Test method descriptors that mention the subtype instead of the
// supertype (CLUE-184). In the code below, check that clicking on
// getMessage() shows the correct information in the UI. Does the CHA
// approach (CHA-225) work?
public class Main {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("Test!");
        } catch (Exception ex1) {
            System.out.println(ex1.getMessage());
        }

        try {
            Exception ex = null;
            throw ex;
        } catch (Exception ex2) {
            System.out.println(ex2.getMessage());
        }
    }
}

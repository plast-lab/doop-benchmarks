import java.util.*;

public class Main {

    public static void main(String[] args) {
        A a = new A();
        int sz = a.getInts().size();
        System.out.println("ints size = " + sz);
    }
}

interface I1 {
    default List<Integer> getInts() {
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(new Integer(10));
        ret.add(new Integer(20));
        return ret;
    }
}
class A implements I1 {
}

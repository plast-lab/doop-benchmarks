// In the example below, the first import is used but not the
// second. What happens when the user clicks on the second import?

import java.util.HashMap;
import java.lang.reflect.ParameterizedType;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Integer> hm = new HashMap();
        hm.put(new Integer(1), new Integer(2));
        System.out.println(hm.hashCode());
    }
}

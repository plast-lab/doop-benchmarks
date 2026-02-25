import java.util.Map;
import java.util.HashMap;

// Test for bugs CLUE-150, CLUE-228.
public class Main {
    
	Map<String, String> map = new HashMap<>();

	Inner bar;

    Main() {
        bar = new Inner();
    }

	Main(int i) {
		map.put("foo", "bar"+i);
	}

    public static void main(String[] args) {
		Main m = new Main();
		m.bar.foo = new Integer(1234);
        System.out.println(m.bar.foo);
		m = new Main(42);
    }

    class Inner {
        public Integer foo;

        Inner() {
            foo = new Integer(0);
        }
    }
}

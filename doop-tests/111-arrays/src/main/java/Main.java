public class Main {
    public static void main(String[] args) {

        // Part 1 : create arrays using syntax "new ...[...]".

        // Array of primitive values.
        int[] ints1 = new int[10];
        fillIntArray(ints1);

        // Array of objects.
        // BUG: jcplugin assigns wrong type to heap allocation.
        A[] as1 = new A[10];
        fillAArray(as1);

        // Array with generic signature and nested classes (given in full). This
        // is not permitted by Java ("error: generic array creation").
        // A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] pairs1a = new A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[10];
        // fillPairPairArray(pairs1a);

        // Array with generic signature and nested classes (ommitted after 'new').
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] pairs1b = new A.Pair[10];
        fillPairPairArray(pairs1b);

        // Part 2 : create arrays using syntax "new ...[] { ... }".
        int[] ints2 = new int[] { 4, 3, 2, 1 };
        // BUG: jcplugin assigns wrong type to heap allocation.
        A[] as2 = new A[] { new A(10), new A(20), new A(30) };

        // Array with generic signature and nested classes (given in full). This
        // is not permitted by Java ("error: generic array creation").
        // A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] pairs2a = new A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] { new A.Pair(new A.Pair(new A(1), new A(2)), new A.Pair(new A(3), new Integer(10))) };

        // Array with generic signature and nested classes (ommitted after 'new').
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] pairs2a = new A.Pair[] { new A.Pair(new A.Pair(new A(1), new A(2)), new A.Pair(new A(3), new Integer(10))) };

        // Part 3: same as above but create arrays using "{ ... }" literals.
        int[] ints3 = new int[] { 34, 33, 32, 31 };
        A[] as3 = { new A(10), new A(20), new A(30) };
        // This is not permitted by Java ("error: generic array creation").
        // A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] pairs3 = { new A.Pair(new A.Pair(new A(1), new A(2)), new A.Pair(new A(3), new Integer(10))) };

        // Part 4: more arrays of nested classes.
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Inner[] ais1 = new A.Inner[10];
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Inner[] ais2 = new A.Inner[] { new A.Inner(1.2), new A.Inner(2.1) };
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Inner[] ais3 = { new A.Inner(1.2), new A.Inner(2.1) };
        // This is not permitted by Java ("error: generic array creation").
        // A.Pair.Box<Integer>[] boxpairs1 = new A.Pair.Box<Integer>[10];
        // BUG: jcplugin assigns wrong type to heap allocation.
        A.Pair.Box<Integer>[] boxpairs2 = new A.Pair.Box[10];

        // Part 5: check if array methods (mostly inherited from
        // java.lang.Object) and fields are found.
        Object obj1 = new Object();

        // Calling clone() on Object is not permitted: "error: clone() has
        // protected access in Object".
        // Object obj2 = obj1.clone();

        // Doop does not handle correctly clone() on arrays:
        // https://bitbucket.org/yanniss/doop-nexgen/issues/15/clone-in-array-objects
        A[] as2_copy = as2.clone();

        int obj1_hashCode = obj1.hashCode();
        int as2_hashCode = as2.hashCode();

        String obj1_toString = obj1.toString();
        String as2_toString = as2.toString();

        Class obj1_c = obj1.getClass();
        Class as2_c = as2.getClass();

        // Special array field 'length'.
        int as2_length = as2.length;
    }

    static void fillPairPairArray(A.Pair<A.Pair<A, A>, A.Pair<A, Integer>>[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new A.Pair(new A.Pair(new A(7), new A(8)), new A.Pair(new A(9), new Integer(6)));
        }
    }

    static void fillIntArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 2 * i;
        }
    }

    static void fillAArray(A[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new A(2 * i);
        }
    }
}

class A {
    int i;

    public A(int i) { this.i = i; }

    void meth(String h) { System.out.println("A: " + h); }

    static class Inner {
        double d;
        public Inner(double d) { this.d = d; }
    }

    static class Pair<X, Y> {
        X x;
        Y y;
        public Pair(X x, Y y) { this.x = x; this.y = y; }
        static class Box<T> {
            T t;
            public Box(T t) { this.t = t; }
            T getT() { return t; }
        }
    }
}

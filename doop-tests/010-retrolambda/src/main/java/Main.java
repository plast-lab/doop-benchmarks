// Test inspired by
// https://github.com/evant/gradle-retrolambda/tree/master/sample-java/src/main/java/me/tatarka/retrolambda/sample

public class Main {
    public static void main(String[] args) {
        NullaryFunction nf = getHello();
        Object obj1 = nf.callNullFunc();
        System.out.println(obj1.hashCode());

        MapFunction mf = getMapper();
        Object obj2 = mf.apply(obj1);
        System.out.println(obj2.hashCode());
    }

    public static NullaryFunction getHello() {
        return () -> new String("Hello, retrolambda!");
    }

    public static MapFunction getMapper() {
        return (Object obj) -> new Integer(obj.hashCode() + 2);
    }
}

@FunctionalInterface
interface NullaryFunction {
    Object callNullFunc();
}

@FunctionalInterface
interface MapFunction {
    Object apply(Object obj);
}

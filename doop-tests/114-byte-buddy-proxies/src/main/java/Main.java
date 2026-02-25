// Code inspired from:
// https://stackoverflow.com/questions/40292185/how-to-create-a-dynamic-proxy-using-bytebuddy

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Dynamic proxies with Byte Buddy");

        InvocationHandler invocationHandler = new AHandler(new A());

        Class<? extends Object> proxy = new ByteBuddy()
            .subclass(Object.class)
            .implement(G.class)
            .method(ElementMatchers.any())
            .intercept(InvocationHandlerAdapter.of(invocationHandler))
            .make()
            .load(G.class.getClassLoader())
            .getLoaded();

        G g = (G)proxy.newInstance();
        testProxy(g);
    }

    private static void testProxy(G g) {
        float f = 3.14f;
        System.out.println("Called count: " + g.count());
        System.out.println("Called countInteger: " + g.countInteger());
        System.out.println("Called mult: " + g.mult(f, f));
        System.out.println("Called toString(): " + g.toString());
        System.out.println("Called getClass(): " + g.getClass());
    }
}

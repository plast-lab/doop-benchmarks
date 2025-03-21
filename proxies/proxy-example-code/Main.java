/* A Proxy example to test Doop. The code is inspired from:
   http://docs.oracle.com/javase/7/docs/technotes/guides/reflection/proxy.html
*/

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws Exception {
	float f = 3.14f;

        System.out.println("Proxy example.");
        // A a1 = new A();
        // a1.report("Test message.");

	System.out.println("== Java-generated dynamic proxy ==");
        G a2 = genProxyForA(new A());
        a2.report("Hello from report().");
	System.out.println("Called count: " + a2.count());
	System.out.println("Called countInteger: " + a2.countInteger());
	System.out.println("Called mult: " + a2.mult(f, f));
	// System.out.println("Read field i: " + a2.i);
	// System.out.println("Called staticMeth: " + G.staticMeth());

	//// Uncomment this code to see our simulated proxy.
	// System.out.println("== Simulated dynamic proxy ==");
        // G a3 = new AProxyForG(new AHandler(new A()));
        // a3.report("Hello from report().");
	// System.out.println("Called count: " + a3.count());
	// System.out.println("Called countInteger: " + a3.countInteger());
	// System.out.println("Called mult: " + a3.mult(f, f));
	// // System.out.println("Read field i: " + a3.i);
	// // System.out.println("Called staticMeth: " + G.staticMeth());
    }

    static G genProxyForA(A a) {
        // Case 1.
        // G g = new A();

        // Case 2.
        // G g = (A)Proxy.newProxyInstance(a.getClass().getClassLoader(),
        //                              a.getClass().getInterfaces(),
        //                              new AHandler(a));

        // // Case 3. Statically known class interfaces for the proxy.
	// // Class[] interfaces = A.class.getInterfaces();
	// Class[] interfaces = new Class[] { G.class };
        // G g = (G)Proxy.newProxyInstance(A.class.getClassLoader(),
        //                                 interfaces,
        //                                 new AHandler(a));

        // Case 4. Very dynamic, getClass() returns the dynamic type,
        // so needs heavyweight analysis.
        G g = (G)Proxy.newProxyInstance(a.getClass().getClassLoader(),
                                     a.getClass().getInterfaces(),
                                     new AHandler(a));

        // This test code shows that the name of the proxy can be
        // different on each invocation. We make a proxy similar to
        // the one above, but we add another interface; the code will
        // now print "$Proxy0 vs. $Proxy1". If we keep the interfaces
        // the same, the new proxy belongs to the same class as the
        // one above.
        Class[] interfaces = a.getClass().getInterfaces();
        Class[] interfaces2 = interfaces; // new Class[interfaces.length + 1];
        System.arraycopy(interfaces, 0, interfaces2, 0, interfaces.length);
        // interfaces2[interfaces.length] = H.class;
        G h = (G)Proxy.newProxyInstance(a.getClass().getClassLoader(),
                                     interfaces2,
                                     new AHandler(a));
        System.out.println("Proxy class names: " + g.getClass().getName() + " vs. " + h.getClass().getName());
	boolean equ = g == h;
	int gHash = System.identityHashCode(g);
	int hHash = System.identityHashCode(h);
	System.out.println("Hash codes: " + gHash + " vs. " + hHash + ", equal=" + equ);

	System.out.println(((Object)g).toString());
	
        return h;
    }
}

interface G {
    // Test for static/final interface field.
    // static final Integer i = new Integer(10);
    // Test for Java 8 static method.
    // static int staticMeth() { return 42; }
    void report(String c);
    int count();
    Integer countInteger();
    float mult(float x, float y);
}

class A implements G {
    public void report(String c) {
        System.out.println(c);
    }
    public int count() {
	return 42;
    }
    public Integer countInteger() {
	return new Integer(43);
    }
    public float mult(float x, float y) {
	return x * y;
    }
}

class AHandler implements InvocationHandler {
    private A a;
    public AHandler(A a) { this.a = a; }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	String mn = method.getName();
        System.out.println("The invocation handler was called for '" + mn + "'...");
        if (mn.equals("report") && (args.length==1) && args[0] instanceof String) {
            // Two ways to call the wrapped method, choice doesn't
            // affect analysis:
            // (1) Call method via Method object.
            // method.invoke(a, args);
            // (2) Call method with its name and a typed receiver.
            a.report((String)args[0]);
	    return null;
        }
	else if (mn.equals("count") && ((args==null) || (args.length == 0)))
	    return a.count();
	else if (mn.equals("countInteger") && ((args==null) || (args.length == 0)))
	    return a.countInteger();
	else if (mn.equals("mult") && ((args!=null) && (args.length == 2))) {
	    float x = ((Float)args[0]).floatValue();
	    float y = ((Float)args[1]).floatValue();
	    return a.mult(x, y);
	}
	else if (mn.equals("hashCode") && (args==null || args.length == 0))
	    return System.identityHashCode(proxy);
	if (method == null)
	    System.out.println("WARNING: Null method paramer, " + proxy.hashCode());
	else if (args == null)
	    System.out.println("WARNING: Null args paramer for " +
			       method.getName() + ".");
	else
	    System.out.println("WARNING: Cannot handle method: " +
			       method.getName() + ", arity: " + args.length);
        return null;
    }
}

// Test proxy for G. This should be 'public' (but in this example, we
// do everything in this file). Also, the interface it implements is
// non-public, so both the proxy and the interface must have the same
// package.
final class AProxyForG extends java.lang.reflect.Proxy implements G {
    // We assume that these method objects already exist. We declare
    // them here as null objects and populate them in the constructor.
    // In Doop we already have them.
    private Method G$V$report$Ljava$lang$String;
    private Method G$I$count$V;
    private Method G$Integer$countInteger$V;
    private Method G$F$mult$FF;

    public void report(String msg) {
        try {
	    Method interfaceMethod = G$V$report$Ljava$lang$String;
	    Object[] args = new Object[] {msg};
            h.invoke(this, interfaceMethod, args);
        } catch (Throwable t) {
	    t.printStackTrace();
            throw new Error("Failed invoking report().");
        }
    }

    // This demonstrates unboxing for the return value.
    public int count() {
	try {
	    Method interfaceMethod = G$I$count$V;
	    Object[] args = new Object[] { };
            Integer ret = (Integer)h.invoke(this, interfaceMethod, args);
	    return ret.intValue();
	} catch (Throwable t) {
	    t.printStackTrace();
	    throw new Error("Failed invoking count().");
	}
    }

    // A simple method returning an object.
    public Integer countInteger() {
	try {
	    Method interfaceMethod = G$Integer$countInteger$V;
	    Object[] args = new Object[] { };
            return (Integer)h.invoke(this, interfaceMethod, args);
	} catch (Throwable t) {
	    t.printStackTrace();
	    throw new Error("Failed invoking countInteger().");
	}
    }

    // Boxing the arguments and unboxing the result.
    public float mult(float x, float y) {
	try {
	    Method interfaceMethod = G$F$mult$FF;
	    Object[] args = new Object[] { Float.valueOf(x), Float.valueOf(y) };
            Float ret = (Float)h.invoke(this, interfaceMethod, args);
	    return ret.floatValue();
	} catch (Throwable t) {
	    t.printStackTrace();
	    throw new Error("Failed invoking mult().");
	}
    }

    // ============ Code useful for running, not modelling ==============

    // The constructor is only useful for running this code; in Doop
    // its assignments should be directly modelled. In particular, the
    // method objects should be already there in Doop.
    public AProxyForG(InvocationHandler h) {
	super(h);
	G$V$report$Ljava$lang$String = getNamedMethod("report");
	G$I$count$V = getNamedMethod("count");
	G$Integer$countInteger$V = getNamedMethod("countInteger");
	G$F$mult$FF = getNamedMethod("mult");
    }

    // This returns the Method object of the method named 'mName' in
    // this proxy class. The last 'return' instruction should be
    // unreachable.
    private Method getNamedMethod(String mName) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods)
            if (m.getName().equals(mName)) {
		System.out.println("Found method: " + mName);
                return m;
	    }
        // For correctly generated interface proxies, this is
        // unreachable code; the for-loop above should always succeed.
        return methods[0];
    }

}

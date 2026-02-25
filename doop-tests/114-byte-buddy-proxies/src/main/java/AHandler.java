import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AHandler implements InvocationHandler {
    private A a;
    public AHandler(A a) { this.a = a; }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	String mn = method.getName();
	String t = method.getDeclaringClass().getName();
        System.out.println("The invocation handler was called for '" + mn + "' of type " + t + "...");
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
	else if (mn.equals("toString") && (args==null || args.length == 0))
	    return toString();
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

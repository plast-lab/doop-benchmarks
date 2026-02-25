// Test points-to for inlined String variables. JLS 4.12.4 considers
// static final String variables initialized with String literals as
// "constant variables" and JLS 15.28 ("Constant expressions") inlines
// them so Doop cannot find them.

public class Main {
    public    static final Object obj1 = new Object();
    private   static final Object obj2 = new Object();
    protected static final Object obj3 = new Object();
              static final Object obj4 = new Object();

    public    static final String str1 = "Str-1";
    private   static final String str2 = "Str-2";
    protected static final String str3 = "Str-3";
              static final String str4 = "Str-4";

    public    static final String str5 = new String("Str-5");
    private   static final String str6 = new String("Str-6");
    protected static final String str7 = new String("Str-7");
              static final String str8 = new String("Str-8");

    public    static final String _str1;
    private   static final String _str2;
    protected static final String _str3;
              static final String _str4;

    public    static final String _str5;
    private   static final String _str6;
    protected static final String _str7;
              static final String _str8;

    static {
        _str1 = "_Str-1";
        _str2 = "_Str-2";
        _str3 = "_Str-3";
        _str4 = "_Str-4";
        _str5 = new String("_Str-5");
        _str6 = new String("_Str-6");
        _str7 = new String("_Str-7");
        _str8 = new String("_Str-8");
    }
	public String foo = "abc";

    public static void main(String[] args) {
        Main m = new Main();

        System.out.println(m.obj1);
        System.out.println(m.obj2);
        System.out.println(m.obj3);

        System.out.println(m.str1);
        System.out.println(m.str2);
        System.out.println(m.str3);
        System.out.println(m.str4);
        System.out.println(m.str5);
        System.out.println(m.str6);
        System.out.println(m.str7);
        System.out.println(m.str8);

        // These don't have a problem. Doop should say that
        // 'localStr1' points to a merged string value.
        final String localStr1 = "Local-Str-1";
        final String localStr2 = new String("Local-Str-2");

        System.out.println(localStr1);
        System.out.println(localStr2);
    }
}

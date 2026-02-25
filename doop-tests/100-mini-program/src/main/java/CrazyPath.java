
public class CrazyPath {

    public void walk(Wrapper w) {
        road1(w);
    }

    private void road1(Wrapper w) {
        road2(w);
    }

    private void road2(Wrapper w) {
        destination(w);
    }

    private void destination(Wrapper w) {
        w.a = new A();
        w.b = new B();
    }

}

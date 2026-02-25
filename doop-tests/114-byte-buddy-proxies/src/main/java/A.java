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

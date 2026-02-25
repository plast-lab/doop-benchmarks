public class Main {
	public static void main(String[] args) {
		System.out.println(new Z());
	}
}

interface Y {
	Y fooooooooo = new Y() {
		public String getName() { return "A"; }
	};

	String getName();
}

class Z implements Y {
	public Z() {
		System.out.println(fooooooooo);
	}

	public String getName() { return "B"; }
}

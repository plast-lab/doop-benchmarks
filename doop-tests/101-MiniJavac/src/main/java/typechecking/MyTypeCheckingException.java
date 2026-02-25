package typechecking;

public class MyTypeCheckingException extends Exception {
	String exceptionType;
	
	public MyTypeCheckingException( String type ) {
		exceptionType = type;
	}
	
	public void print() {
		System.err.println( "Type Checking failed: " + exceptionType );
	}

}

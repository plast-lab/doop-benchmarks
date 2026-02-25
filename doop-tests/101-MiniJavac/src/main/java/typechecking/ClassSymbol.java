package typechecking;
import java.util.HashMap;

public class ClassSymbol {
	HashMap<String, String> memberSymbolMap;
	HashMap<String, MethodSymbol> methodSymbolMap;
	String parentClass = null;
	
	/* ClassSymbol Constructor */
	public ClassSymbol( String parentClass ) {
		memberSymbolMap = new HashMap<>();
		methodSymbolMap = new HashMap<>();
		this.parentClass = parentClass;
	}
	
	public int putMember( String name, String type ) {
		if ( memberSymbolMap.get( name ) == null )
			memberSymbolMap.put( name, type );
		else 
			return -1;
		
		return 0;
	}
	
	public int putMethod( String signature, MethodSymbol ms ) {
		if ( methodSymbolMap.get( signature ) == null )
			methodSymbolMap.put( signature, ms );
		else 
			return -1;
		return 0;
		
	}
	
	public String getParentClass() {
		return parentClass;
	}
	
	public MethodSymbol getMethod( String signature ) {
		return methodSymbolMap.get( signature );
		
	}
	
	public String getMemberType( String memberName ) {
		return memberSymbolMap.get( memberName );
	}
	
}

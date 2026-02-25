package typechecking;

import java.util.HashMap;

class CurrentScope {
	String parentScope;
	HashMap<String,String> currentScopeMap;
	
	public CurrentScope( String parentScope ) {
		this.parentScope = parentScope;
		currentScopeMap = new HashMap<String,String>();
	}
	
	public int put( String name, String type ) {
		if ( currentScopeMap.get( name ) == null )
			currentScopeMap.put( name, type );
		else 
			return -1;
		
		return 0;
	}
	
	
	public String get( String name ) {
		return currentScopeMap.get( name );
	}
	
	public String getParentScopeName() {
		return parentScope;
	}
	
	public HashMap<String,String> print() {
		return currentScopeMap;
	}
	
}
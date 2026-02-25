package typechecking;
import java.util.HashMap;


public class AllScopeShadowedTypeSearch {
	HashMap<String, ClassSymbol> globalMap;
	CurrentScope currentScope;
	
	
	public AllScopeShadowedTypeSearch( HashMap<String,ClassSymbol> gm ) {
		this.globalMap = gm;
	}
	
	public String getType( String identifier, String className, String methodName ) {
            String type;
            
            if (identifier == null)
                return null;
            if ( identifier.contains( "Resolved:") ) { 
                String[] pieces = identifier.split( ":" );
                identifier = pieces[1];
                if ( pieces[1].equals( "int" ) || pieces[1].equals( "int[]" ) || pieces[1].equals( "boolean" ) )
                    return pieces[1];
                else if ( globalMap.get( identifier ) != null ) 
                    return pieces[1];
                else
                    return null;
            }
            else if ( identifier.equals( "int" ) || identifier.equals( "int[]" ) || identifier.equals( "boolean" ) )
                return identifier;
	    else if ( identifier.equals("this") ) 
                return className;
	    else {
                MethodSymbol currentMethod = globalMap.get(className).getMethod(methodName);
		if ((type = currentMethod.getLocalVariableType(identifier)) == null ) {
                    ClassSymbol currentClass = globalMap.get(className);
                    do {
                        if ((type = currentClass.getMemberType(identifier)) == null)
                            currentClass = globalMap.get( currentClass.getParentClass() );
                        else
                            return type;
                    } while (currentClass != null);
                } 	
                else 
                    return type;
            }
            return type;
	}
	
	public String parseType( String rawType  ) {
		if ( rawType.contains( "Class:") ) { 
			String[] pieces = rawType.split( ":" );
			rawType = pieces[1];
			if ( globalMap.get( rawType ) != null ) 
				return pieces[1];
			else
				return null;
		}
		else {
			return rawType;
		}
	}

}

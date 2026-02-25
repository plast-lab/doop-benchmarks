package typechecking;

import java.util.Arrays;
import java.util.HashMap;

class MethodSymbol {
	String name;
	String returnType;
	String[] argumentTypes;	
	String[] argumentNames;
        public HashMap<String, String> localVariableMap;
	
	public MethodSymbol( String name, String returnType, String[] argumentTypes, String[] argumentNames ) {
		this.name = name;
		this.returnType = returnType;
		this.argumentTypes = argumentTypes;
		this.argumentNames = argumentNames;
                this.localVariableMap = new HashMap<>();
                if (argumentTypes != null && argumentNames != null)
                    for (int i = 0; i < this.argumentNames.length ; i++ ) 
                        localVariableMap.put(argumentNames[i], argumentTypes[i]);
                
	}
	
	public boolean validateArgumentTypes( String[] argumentTypes, HashMap<String,ClassSymbol> globalMap ) {
		boolean result = Arrays.equals( this.argumentTypes, argumentTypes );
		if ( result == true )
			return result;
		else {
			int length = argumentTypes.length;
			if ( length == this.argumentTypes.length ) {
				for ( int i = 0 ; i < length ; i++ )
					if ( !this.argumentTypes[i].equals( argumentTypes[i] ) ) { 
						
				    	  if ( !this.argumentTypes[i].equals( "int" ) && !this.argumentTypes[i].equals( "int[]" ) && !this.argumentTypes[i].equals( "boolean" ) ) {
				    		  if  ( !argumentTypes[i].equals( "int" ) && !argumentTypes[i].equals( "int[]" ) && !argumentTypes[i].equals( "boolean" ) ) {
				    			  
				    			  String parentType = globalMap.get( argumentTypes[i] ).getParentClass();
				    			  if ( parentType != null ) {
					    			  while ( !parentType.equals( this.argumentTypes[i] )   ) {
					    				  parentType = globalMap.get( parentType ).getParentClass();
					    				  if ( parentType == null )
					    					  break;
					    			  }
				    			  }
				    			  if ( parentType == null  )
				    				  return false;
					    			  
				    		  }
				    		  else 
				    			  return false;
				    	  }
				    	  else 
				    		  return false;
				}
			}
			else 
				return false;
		}
		return true;
	}
	
	public String getReturnType() {
            return returnType;
	}
	
	public String[] getArgumentTypes() {
            return argumentTypes;
	}
	
	public String[] getArguments() {
            return argumentNames;
	}
	
	public int validateOverridingMethod( MethodSymbol ms ) {
            if ( Arrays.equals( argumentTypes, ms.getArgumentTypes() ) && returnType.equals( ms.getReturnType() ) )
            	return 0;
            return -1;
	}
        
        public int addLocalVariable(String identifier, String type) {
            if (localVariableMap.containsKey(identifier))
                return -1;
            else 
                localVariableMap.put(identifier, type);
            return 0;           
        }
        
        public String getLocalVariableType(String identifier) {
            return localVariableMap.get(identifier);
        }
        
}

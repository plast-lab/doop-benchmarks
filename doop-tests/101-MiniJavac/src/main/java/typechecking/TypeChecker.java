package typechecking;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class TypeChecker extends GJDepthFirst<String,String> {
	
	int overloading = 0;
	CurrentScope cs = null;
	HashMap<String,ClassSymbol> globalMap;
	

	public TypeChecker( HashMap<String,ClassSymbol> globalMap ) {
		super();
		this.globalMap = globalMap;
		
	}
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
        @Override
   public String visit(NodeList n, String argu) {
      if (n.size() == 1)
         return n.elementAt(0).accept(this,argu);
      String _ret = "";
      int _count = 0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         _ret += e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

        @Override
   public String visit(NodeListOptional n, String argu) {
      if ( n.present() ) {
         if (n.size() == 1)
            return n.elementAt(0).accept(this,argu);
         String _ret="";
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            _ret += e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

        @Override
   public String visit(NodeOptional n, String argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

        @Override
   public String visit(NodeSequence n, String argu) {
      if (n.size() == 1)
         return n.elementAt(0).accept(this,argu);
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

    /**
     *
     * @param n
     * @param argu
     * @return
     */
    @Override
   public String visit(NodeToken n, String argu) { return n.toString(); }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    * @param n
    * @param argu
    * @return 
    */
        @Override
   public String visit(Goal n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      try {
          for ( String key : globalMap.keySet()) {
              String parentClass = globalMap.get(key).getParentClass();
              if ( parentClass != null) {
                ClassSymbol currentCS;
                if ( ( currentCS = globalMap.get( parentClass ) ) == null) {
                  throw new MyTypeCheckingException( "Error - Parent class does not exist: " + parentClass );
             }
          }
          }
      } catch( MyTypeCheckingException ex ) {
    	  ex.print();
    	  System.exit( -1 );
      }
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(MainClass n, String argu)  {
      String _ret=null;
      n.f0.accept(this, argu);
      argu = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu + ":main");
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(TypeDeclaration n, String argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ClassDeclaration n, String argu) {	            	
      String _ret=null;
      n.f0.accept(this, argu);
      argu = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);      
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ClassExtendsDeclaration n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      argu = n.f1.accept(this, argu);      
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(VarDeclaration n, String argu) {
      String _ret=null;
      String type = n.f0.accept(this, argu);
      String identifier = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      try {     
    	  if ( argu == null )     																			  // If in method declarations
    		  if ( cs.put( identifier, type) == - 1 ) 						  								  // add variable and its type to 
    			  throw new MyTypeCheckingException( "Error duplicate local variable found: " + identifier );
      } catch( MyTypeCheckingException ex ) {
    	  ex.print();
    	  System.exit( -1 );
      }
      
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(MethodDeclaration n, String argu) {
	  cs = new CurrentScope( argu );
	  String _ret=null;
	  AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
	  MethodSymbol ms;
	  String returnType1;
	  String returnType2;
	  String methodName;
	  String[] types;
	  String[] args;
	  
	  n.f0.accept(this, argu);
	  returnType1 = n.f1.accept(this, argu);
	  methodName = n.f2.accept(this, argu);
	  n.f3.accept(this, argu);
	  n.f4.accept(this, argu);
	  
	  ms = globalMap.get( cs.getParentScopeName() ).getMethod( methodName );
	  types = ms.getArgumentTypes();
	  args = ms.getArguments();
	  
	  if ( types != null )
		  for ( int i = 0 ; i < types.length ; i ++ )
			  cs.put( args[i],  types[i] );
	  
	  n.f5.accept(this, argu);
	  n.f6.accept(this, argu);
	  n.f7.accept(this, argu);
	  n.f8.accept(this, argu + ":" + methodName);
	  n.f9.accept(this, argu + ":" + methodName);
	  returnType2 = n.f10.accept(this, argu + ":" + methodName);
	  n.f11.accept(this, argu + ":" + methodName);
	  n.f12.accept(this, argu + ":" + methodName);
	  try {
		  if ( !returnType1.equals( returnType2 ) ) {
			  if ( !returnType1.equals( "int" ) && !returnType1.equals( "int[]" ) && !returnType1.equals( "boolean" ) ) {
	    		  if  ( !returnType2.equals( "int" ) && !returnType2.equals( "int[]" ) && !returnType2.equals( "boolean" ) ) {
	    			  
	    			  String parentType = globalMap.get( returnType2 ).getParentClass();
	    			  if ( parentType != null ) 
		    			  while ( !parentType.equals( returnType1 ) ) {
		    				  parentType = globalMap.get( parentType ).getParentClass();
		    				  if ( parentType == null )
				    			  break;
		    				  
		    			  }
	    				  
	    			  if ( parentType == null  )
	    				  throw new MyTypeCheckingException( "Error in method declaration - incompatible return types" );
	    		  }
	    		  else 
	    			  throw new MyTypeCheckingException( "Error in method declaration, declared return type: " + returnType1 + " returned type: " + returnType2  );
	    	  }
			  else 
				  throw new MyTypeCheckingException( "Error in method declaration, declared return type: " + returnType1 + " returned type: " + returnType2 );
		  }
		  
	  } catch( MyTypeCheckingException ex ) {
		   ex.print();
		   System.exit( -1 );
	  }
	  return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(FormalParameterList n, String argu) {                       //Returns parameter list e.g. "int i, int[] j"
      String argument0 = n.f0.accept(this, argu);
      String argument1 = n.f1.accept(this, argu);
      return argument0+argument1;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(FormalParameter n, String argu) {            // Returns "int|int[]|boolean id"
      String _ret=null;
      String[] argument = new String[2];
      argument[0]= n.f0.accept(this, argu);
      argument[1] = n.f1.accept(this, argu);
      
      return argument[0] + " " + argument[1];
   }

   /**
    * f0 -> ( FormalParameterTerm() )*
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(FormalParameterTail n, String argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(FormalParameterTerm n, String argu) {      // Returns ",int|int[]|boolean id"
	  n.f0.accept(this, argu);
      String argument = n.f1.accept(this, argu);
      return "," + argument;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(Type n, String argu) {          // Returns "int[]" | "int" | "boolean"
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ArrayType n, String argu) {     // Returns "int[]"
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return "int[]";
   }

   /**
    * f0 -> "boolean"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(BooleanType n, String argu) {   // Returns "boolean"
      n.f0.accept(this, argu);
      return "boolean";
   }

   /**
    * f0 -> "int"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(IntegerType n, String argu) {   // Returns "int"
      n.f0.accept(this, argu);
      return "int";
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(Statement n, String argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(Block n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(AssignmentStatement n, String argu) {		// Identifier and expression must be of the same type
	  AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch(globalMap);
	  try {
                String[] classMethodPair = argu.split(":");
		String identifierType =  n.f0.accept(this, argu);
                
		n.f1.accept(this, argu);
		String expressionType = n.f2.accept(this, argu);
		n.f3.accept(this, argu);
		String type1 = s.getType( identifierType, classMethodPair[0], classMethodPair[1] );
		String type2 = expressionType;
		  
		if ( type1 == null || type2 == null )
                    throw new MyTypeCheckingException( "Undefined identifier in Assignment: " + identifierType + " = " + expressionType );
		  	  
                if ( !type1.equals( type2 ) ) { 
                    if ( !type1.equals( "int" ) && !type1.equals( "int[]" ) && !type1.equals( "boolean" ) ) {
                        if  ( !type2.equals( "int" ) && !type2.equals( "int[]" ) && !type2.equals( "boolean" ) ) {
	    			  
	    		String parentType = globalMap.get( type2 ).getParentClass();
	    			  if ( parentType != null ) {
		    			  while ( !parentType.equals( type1 ) ) {
		    				  parentType = globalMap.get( parentType ).getParentClass();
		    				  if ( parentType == null )
		    					  break;
		    			  }
		    			  
	    			  }
	    			  if ( parentType == null  )
	    				  throw new MyTypeCheckingException( "Incompatible types in assignment" );
	    		  }
	    		  else 
	    			  throw new MyTypeCheckingException( "Incompatible types in assignment: " + identifierType + " = " + expressionType );
	    	  }
	    	  else 
	    		  throw new MyTypeCheckingException( "Incompatible types in assignment " + identifierType + " = " + expressionType );
	     }
	  }  catch( MyTypeCheckingException ex ) {
		   ex.print();
		   System.exit( -1 );
	  }
	  return null;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ArrayAssignmentStatement n, String argu) {     // Identifier must be int[], first and second expression int
      AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap);
      String identifierType;
      String expressionType;
      String expression1Type;
      
      String[] classMethodPair = argu.split(":");
      identifierType = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1] );
      n.f1.accept(this, argu);
      expressionType = n.f2.accept( this, argu );
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      expression1Type = n.f5.accept( this, argu );
      n.f6.accept(this, argu);
      try {
    	  if ( identifierType == null || expressionType == null || expression1Type == null )
    		  throw new MyTypeCheckingException( "Undefined identifier in Array Assignment" ); 
	      if ( !identifierType.equals( "int[]" ) || !expressionType.equals( "int" ) || !expression1Type.equals( "int" ) )
	    	  throw new MyTypeCheckingException( "Error in array assignment" );   	
      } catch( MyTypeCheckingException ex ) {
		   ex.print();
		   System.exit( -1 );
	  }
      return null;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(IfStatement n, String argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String expressionType = n.f2.accept( this, argu );
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        try {
            if ( "boolean".equals(expressionType) )
                return null;
            else 
               throw new MyTypeCheckingException( "Invalid if expression in if statement - expected boolean, found: " + expressionType  );
            } catch( MyTypeCheckingException ex ) {
		   ex.print();
		   System.exit( -1 );
            }
            return null;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(WhileStatement n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String expressionType = n.f2.accept( this, argu );
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      try {
           if ( "boolean".equals(expressionType) )
                   return null;
           else 
                   throw new MyTypeCheckingException( "Invalid expression type in while statement - expected boolean, found: " + expressionType );
        } catch( MyTypeCheckingException ex ) {
           ex.print();
           System.exit( -1 );
        }
        return null;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(PrintStatement n, String argu) {
       String[] classMethodPair = argu.split(":");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch(globalMap);
        String expressionType = s.getType(n.f2.accept( this, argu ), classMethodPair[0], classMethodPair[1]);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        try {
            if ( expressionType != null && expressionType.equals( "int" ) )
                   return null;
            else 
                   throw new MyTypeCheckingException( "Error in System.out.println expected int expression, found: " + expressionType );
        } catch( MyTypeCheckingException ex ) {
           ex.print();
           System.exit( -1 );
        }
        return null;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(Expression n, String argu) {
        String[] classMethodPair = argu.split(":");
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch(globalMap);
        return s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1] );
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(AndExpression n, String argu) {
        String[] classMethodPair = argu.split(":");
	AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
        
	String term1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1] );
	n.f1.accept(this, argu);
	String term2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1] );
	   
        try {
            if ( term1Type == null || term2Type == null )
                   throw new MyTypeCheckingException( "Error in && - Term type could not be resolved" );
            if ( term1Type.equals( "boolean" ) && term2Type.equals( "boolean" ) )
                   return "boolean";
            else 
                   throw new MyTypeCheckingException( "Error in comparison - Incompatible types: " + term1Type +  " && " + term2Type );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
        return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(CompareExpression n, String argu) {
        String[] classMethodPair = argu.split(":");
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
        String term1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1] );
        n.f1.accept(this, argu);
        String term2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1] );

        try {
               if ( term1Type == null || term2Type == null )
                       throw new MyTypeCheckingException( "Error in comparison - Term type could not be resolved" );
               if ( term1Type.equals( "int" ) && term2Type.equals( "int" ) )
                       return "boolean";
               else 
                       throw new MyTypeCheckingException( "Error in comparison - Incompatible types: " + term1Type +  " < " + term2Type );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
        return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(PlusExpression n, String argu) {
        String[] classMethodPair = argu.split(":");
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap);
        String term1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1] );
        n.f1.accept(this, argu);
        String term2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1]  );

        try {
               if ( term1Type == null || term2Type == null )
                       throw new MyTypeCheckingException( "Error in addition - Term type could not be resolved" );
               if ( term1Type.equals( "int" ) && term2Type.equals( "int" ) )
                       return "int";
               else 
                       throw new MyTypeCheckingException( "Error in addition - Incompatible types: " + term1Type +  " + " + term2Type );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
        return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(MinusExpression n, String argu) {
        String[] classMethodPair = argu.split(":");
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
        String term1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
        n.f1.accept(this, argu);
        String term2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1]  );

        try {
               if ( term1Type == null || term2Type == null )
                       throw new MyTypeCheckingException( "Error in substraction - Term type could not be resolved" );
               if ( term1Type.equals( "int" ) && term2Type.equals( "int" ) )
                       return "int";
               else 
                       throw new MyTypeCheckingException( "Error in substraction - Incompatible types: " + term1Type +  " - " + term2Type );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
        return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(TimesExpression n, String argu) {
        String[] classMethodPair = argu.split(":");
	   AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
	   String term1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
	   n.f1.accept(this, argu);
	   String term2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1]  );

	   try {
		   if ( term1Type == null || term2Type == null )
			   throw new MyTypeCheckingException( "Error in multiplication - Term type could not be resolved" );
		   if ( term1Type.equals( "int" ) && term2Type.equals( "int" ) )
			   return "int";
		   else 
			   throw new MyTypeCheckingException( "Error in multiplication - Incompatible types: " + term1Type +  " * " + term2Type );
	   } catch( MyTypeCheckingException ex ) {
		   ex.print();
		   System.exit(-1);
	   }
	   return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ArrayLookup n, String argu) {
        String[] classMethodPair = argu.split(":");
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
        String identifier1Type = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
        n.f1.accept(this, argu);
        String identifier2Type = s.getType( n.f2.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
        n.f3.accept(this, argu);  

        try {
              if ( identifier1Type == null || identifier2Type == null )
                       throw new MyTypeCheckingException( "Error in array lookup - Term type could not be resolved" );
              if ( identifier1Type.equals( "int[]" ) && identifier2Type.equals( "int" ) )
                       return "int";
              else 
                       throw new MyTypeCheckingException( "Incompatible types in array Lookup: " + identifier1Type + "[ " + identifier2Type  + " ]" );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
        return null;
      
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
     * @param n
     * @param argu
     * @return 
    */
        @Override
    public String visit(ArrayLength n, String argu) {
        String[] classMethodPair = argu.split(":");
        
        AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
        String identifierType = s.getType( n.f0.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        try {
            if ( identifierType == null )
                throw new MyTypeCheckingException( "Error in array length - Term type could not be resolved" );
            if ( "int[]".equals(identifierType) )
                return "int";
            else 
                throw new MyTypeCheckingException( "Error in array length: " + identifierType + ".length"  );
	} catch( MyTypeCheckingException ex ) {
            ex.print();
            System.exit(-1);
	}
	return null;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(MessageSend n, String argu) {
      AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
      ClassSymbol objectClassSymbol;
      String type;
      String[] expressionList;
      MethodSymbol methodCalled = null;
      String[] classMethodPair = argu.split(":");
      try {
    	  String rawType = n.f0.accept(this, argu);
	      if ( ( type = s.getType( rawType, classMethodPair[0], classMethodPair[1] ) ) == null ) 
	    	  throw new MyTypeCheckingException( "Error in Message Send - Type could not be resolved " + type );
	      
	      if ( (objectClassSymbol = globalMap.get( type )) == null) 
                   throw new MyTypeCheckingException( "Error in Message Send - Invalid receiver " + type );
	      n.f1.accept(this, argu);
	      
	      String methodName = n.f2.accept(this, argu); 
              // Gia na einai ola enta3ei 8a prepei to methodName na einai onoma kapoias me8odou ths class A
	      if ( ( methodCalled = objectClassSymbol.getMethod( methodName ) ) == null ) { 			
	    	  String parentClass = null;
	    	  while ( methodCalled == null ) {
	    		  parentClass = objectClassSymbol.getParentClass();
	    		  if ( parentClass == null )
	    			  break;
	    		  objectClassSymbol = globalMap.get( parentClass );
	    		  methodCalled = globalMap.get( parentClass ).getMethod( methodName );
	    	  }
	    	  if ( parentClass == null )
	    		  throw new MyTypeCheckingException( "Method not declared: " + type + "." + methodName );
	      }
	      n.f3.accept(this, argu);
	      String rawExpressionList = n.f4.accept(this, argu); 
	      
	      if ( rawExpressionList != null ) 
	    	  expressionList = rawExpressionList.split( "," );
	      else 
	    	 expressionList = null;
	      
	      if ( !( methodCalled.validateArgumentTypes( expressionList, globalMap ) ) ) 			// Alla kai na einai sumvata ta argument types kata thn klhsh ths method
	    	  throw new MyTypeCheckingException( "Error in argument for call of method: " + type + "." + methodName );
	      
	      n.f5.accept(this, argu);
	      									
      }
      catch ( MyTypeCheckingException ex ) {
    	  ex.print();
    	  System.exit(-1);
      }
      String typeReturned = methodCalled.getReturnType();
      if ( !typeReturned.equals( "int" ) && !typeReturned.equals( "int[]" ) && !typeReturned.equals( "boolean" ) )
    	  return "Resolved:"+typeReturned;							// Epistrefetai to return type ths method
      else 
    	  return typeReturned;
   }
   
   
   /*************** START - Arguments kata thn klhsh mias method ***********************/
   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ExpressionList n, String argu) {                     // Ta arguments kata thn klhsh ths method
       
      String argument0 = n.f0.accept(this, argu);
      String argument1 = n.f1.accept(this, argu);
     
      if ( argument0 == null && argument1 == null )
    	  return "";
	  else if ( argument1 == null )
		  return argument0;
	  return argument0+argument1;
   }

   /**
    * f0 -> ( ExpressionTerm() )*
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ExpressionTail n, String argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ExpressionTerm n, String argu) {
      n.f0.accept(this, argu);
      String argument = n.f1.accept(this, argu);
      return ","+argument;
   }
   /****************** END - Arguments kata thn klhsh mias method ************************/
   
   
   /********************** START - PrimaryExpressions **********************************/
   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(PrimaryExpression n, String argu) {
	   return n.f0.accept(this, argu);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(IntegerLiteral n, String argu) {
      n.f0.accept(this, argu);
      return "int";
   }

   /**
    * f0 -> "true"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(TrueLiteral n, String argu) {
      n.f0.accept(this, argu);
      return "boolean";
   }

   /**
    * f0 -> "false"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(FalseLiteral n, String argu) {
      n.f0.accept(this, argu);
      return "boolean";
   }

   /**
    * f0 -> <IDENTIFIER>
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(Identifier n, String argu) {
	  n.f0.accept(this, argu);
      return n.f0.toString();
   }

   /**
    * f0 -> "this"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ThisExpression n, String argu) {
      n.f0.accept(this, argu);
      return "this";
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(ArrayAllocationExpression n, String argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      
      String[] classMethodPair = argu.split(":");
      AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
      String identifierType = s.getType( n.f3.accept(this, argu), classMethodPair[0], classMethodPair[1]  );
      n.f4.accept(this, argu); 

        try {
              if ( identifierType == null )
                       throw new MyTypeCheckingException( "Error in array allocation - Incompatible type" );
             if (!(identifierType.equals("int")))
                       throw new MyTypeCheckingException( "Incompatible type in array allocation: " + identifierType );
        } catch( MyTypeCheckingException ex ) {
               ex.print();
               System.exit(-1);
        }
      return "int[]";
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(AllocationExpression n, String argu) {
      n.f0.accept(this, argu);
      String identifierName = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return "Resolved:" + identifierName;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(NotExpression n, String argu) {
      String[] classMethodPair = argu.split(":");
      n.f0.accept(this, argu);
      AllScopeShadowedTypeSearch s = new AllScopeShadowedTypeSearch( globalMap );
      String type = s.getType(n.f1.accept(this, argu), classMethodPair[0], classMethodPair[1] );
      try {
        if ( type == null || !(type.equals("boolean")) ) 
          throw new MyTypeCheckingException( "Invalid boolean expression" );
       
        }
      catch ( MyTypeCheckingException ex ) {
    	  ex.print();
    	  System.exit(-1);
      }
        
      return "boolean";
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
     * @param n
     * @param argu
     * @return 
    */
        @Override
   public String visit(BracketExpression n, String argu) {
      String _ret;
      n.f0.accept(this, argu);
      _ret = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return "Resolved:" +_ret;
   }
   /************** END - Primary Expressions *******************/

}

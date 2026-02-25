import syntaxtree.Goal;
import typechecking.ClassSymbol;
import typechecking.GlobalSymbolTableMaker;
import typechecking.TypeChecker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;


public class Main {
    public static void main(String args[]) {
        System.out.println("[module: \033[34m" + "MiniJavac" + "\033[0m]\n");

        try {
            String fileName = args[0];

            MiniJavaParser parser = new MiniJavaParser( new FileReader(fileName ) );
            String[] pieces = fileName.split("/");
            String mainClassFile = pieces[pieces.length-1];
            pieces = mainClassFile.split("\\.");
            String mainClass = pieces[0];
            Goal root = parser.Goal();
            System.err.println("Minijava program parsed successfully.");
            HashMap<String,ClassSymbol> globalMap = new HashMap<>();
            root.accept( new GlobalSymbolTableMaker(globalMap), mainClass );
            root.accept( new TypeChecker(globalMap), mainClass );

            System.err.println( "Minijava program type checked successfully - no errors found" );
        }
        catch (ParseException e) {
            System.err.println("Encountered errors during parse.");
        }
        catch ( FileNotFoundException e ) {
            System.err.println( "File not found" );
        }
    }
}

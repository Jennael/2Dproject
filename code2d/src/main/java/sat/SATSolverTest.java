package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/



import sat.env.*;
import sat.formula.*;

import java.io.*;
import java.util.*;
import java.lang.Math.*;

import static java.lang.Math.abs;

public class SATSolverTest {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        BufferedReader reader;
        File CNF = new File(filename);
        reader = new BufferedReader(new FileReader(CNF));

        String newLine;

        boolean parsing = false;
        Formula f2 = new Formula();

        while ((newLine = reader.readLine()) != null){
            if ((newLine.isEmpty())== false){
                if (parsing == true) {
                    String[] lineArray = newLine.split(" ");
                    Clause newClause = new Clause();
                    for (String l : lineArray){
                        if (l != ""){
                            int n = Integer.parseInt(l);
                            if (n != 0 && n>0){
                                newClause= newClause.add(PosLiteral.make(l));
                                //System.out.println(PosLiteral.make(l).toString()+"pos");
                                //System.out.println(newClause.toString());
                            } else if (n!= 0 && n<0) {
                                newClause = newClause.add(NegLiteral.make(Integer.toString(abs(n))));
                                //System.out.println(NegLiteral.make(l).toString()+"neg");
                                //System.out.println(newClause.toString());
                            } else {
                                f2= f2.addClause(newClause);
                                newClause = new Clause();
                            }
                        }
                    }
                    //f2= f2.addClause(newClause);
                }
                if (newLine.charAt(0) == 'p'){
                    //System.out.println("parse");
                    //int VarNum = Integer.parseInt(lineArray[2]);
                    //int ClauNum = Integer.parseInt(lineArray[3]);
                    parsing = true;
                }
            }
        }

        System.out.println("SAT solver starts!!!");
        long started = System.nanoTime();
        Environment e = SATSolver.solve(f2);
        long time = System.nanoTime();
        long timeTaken= time - started;
        System.out.println("Time:" + timeTaken/1000000.0 + "ms");

        if (e == null){
            System.out.println("not satisfiable");
        } else {
            System.out.println("satisfiable");

            String output = "";
            String set1 = e.toString().substring(13, e.toString().length() - 1);
            //System.out.println(set1);

            String set2[] = set1.split(", ");
            //System.out.println(set2.toString());

            for(String i : set2){
                //System.out.println(i);
                String[] singleAssignment = i.split("->");
                output = output.concat(singleAssignment[0] + ":" + singleAssignment[1] + "\n");
                //System.out.println(output);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("BoolAssignment.txt"), 32768);
            writer.write(output);
            writer.close();
        }
    }
    /*
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();
    */

/*
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);

    	//MM: makeCl(Literal a,b)--> Clause c, c.add(a), c.add(b), return c;
        //  class Clause implements Iterable<Literal>
        //  private final ImList<Literal> literals
        //  list of literals l1,l2,...
        //  a,b,!c,d = (a or b or !c or d)
        //MM: c.add(l)-->
        //  return literals if literals contain l,
        //  return null if literals contain !l,
        //  return literals.add(l)
        //MM: makeFm(Clause c,d)--> Formula f, f.addClause(c), f.addClause(d), return F;
        //MM:

    	//assertTrue( "one of the literals should be set to true",
    			//Bool.TRUE == e.get(a.getVariable())
    			//|| Bool.TRUE == e.get(b.getVariable())	);


    }*/

/*
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));

    	//assertEquals( Bool.FALSE, e.get(na.getVariable()));

    }*/
/*
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }

    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }*/
}




package sat;

import sat.env.*;
import sat.formula.*;
import java.io.*;

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
                        if (!(l.isEmpty())){
                            int n = Integer.parseInt(l);
                            if (n != 0 && n>0){
                                newClause= newClause.add(PosLiteral.make(l));
                            } else if (n!= 0 && n<0) {
                                newClause = newClause.add(NegLiteral.make(Integer.toString(abs(n))));
                            } else {
                                f2= f2.addClause(newClause);
                                newClause = new Clause();
                            }
                        }
                    }
                }
                if (newLine.charAt(0) == 'p'){
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

            String set2[] = set1.split(", ");

            for(String i : set2){
                String[] singleAssignment = i.split("->");
                output = output.concat(singleAssignment[0] + ":" + singleAssignment[1] + "\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("BoolAssignment.txt"), 32768);
            writer.write(output);
            writer.close();
        }
    }
}




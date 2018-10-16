package sat;

import immutable.ImList;
import immutable.EmptyImList; //
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        Environment env = new Environment();
        return solve(formula.getClauses(), env);
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {


        if ()

    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        /*  Attempt 1
        Formula newF = new Formula();
        for (Clause c: clauses){
            Clause newC = new Clause();
            if (c.contains(l)){
                Literal negl = l.getNegation();
                newC.add(negl);
            }else{
                newC.add(l);
            }
            newF.addClause(newC);
        }
        return (ImList<Clause>) newF;
        */

        //  Attempt 1 Correction:
        //  1. Setting literal to true != negation
        //  2. Use clause.reduce(literal) instead, returns new clause.

        //  Mistake:
        //  ImList<Clause> newC = new ImList<Clause>();
        //  ERROR: ImList cannot be instantiated as it is abstract, use EmptyImList instead.

        /*  Attempt 2
        ImList<Clause> newClauses = new EmptyImList<Clause>();
        for (Clause c: clauses){
            if (c.contains(l)) {
                Clause newC = c.reduce(l);
                newClauses.add(newC);
            } else {
                newClauses.add(c);
            }
        }
        return newClauses;
         */

        //  Attempt 2 Correction:
        //  1. don't addClause for null case clause
        //  2. Reduce will return unchanged clause if l not in clause.
        //      if (c.contains(l)) {...} is redundant.

        ImList<Clause> newClauses = new EmptyImList<Clause>();
        for (Clause c: clauses){
            Clause newC = c.reduce(l);
            if (newC != null) {
                newClauses.add(newC);
            }
        }
        return newClauses;

    }

}

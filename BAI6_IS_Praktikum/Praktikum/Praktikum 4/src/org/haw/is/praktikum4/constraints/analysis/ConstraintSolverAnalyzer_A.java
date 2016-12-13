package org.haw.is.praktikum4.constraints.analysis;

import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.problem.ConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public abstract class ConstraintSolverAnalyzer_A {
	/**
	 * Analysiert das übergebene {@link ConstraintNet} mit dem übergebenen {@link ConstraintSolver}.
	 * @param solver Der Solver
	 * @param net Das {@link ConstraintNet}
	 * @return Das Ergebnis
	 */
	public static AnalysisResult analyze(ConstraintSolver solver, ConstraintNet net) {
		long before = System.currentTimeMillis();
		boolean solutionFound = solver.solve(net);
		long durationMS = System.currentTimeMillis() - before;
		return new AnalysisResult(solver, net, durationMS, solutionFound);
	}
	
	/**
	 * Analysiert die übergebenen {@link ConstraintSolver} gegen das übergebene Problem und gibt eine Statistik darüber
	 * zurück. Die Statistik erlaubt den Vergleich zwischen den Solvern.
	 * @param problem Das Problem das von jedem Solver gelöst werden soll
	 * @param solvers Eine Liste der Solver die analysiert werden sollen
	 * @return Die Ergebnisse
	 */
	public static AnalysisResultList analyzeSolvers(ConstraintProblem problem, ConstraintSolver... solvers) {
		AnalysisResultList results = new AnalysisResultList();
		for(ConstraintSolver solver : solvers) {
			results.add(analyze(solver, problem.createConstraintNet()));
		}
		return results;
	}
	
	/**
	 * Analysiert die übergebenen {@link ConstraintProblem} mit dem übergebenen {@link ConstraintSolver} und gibt eine
	 * Statistik darüber zurück. Die Statistik erlaubt den Vergleich zwischen den Problemen.
	 * @param solver Der Solver der zum Lösen der Probleme genutzt werden soll
	 * @param problems Die Probleme die analysiert werden sollen
	 * @return Die Ergebnisse
	 */
	public static AnalysisResultList analyzeProblems(ConstraintSolver solver, ConstraintProblem... problems) {
		AnalysisResultList results = new AnalysisResultList();
		for(ConstraintProblem problem : problems) {
			results.add(analyze(solver, problem.createConstraintNet()));
		}
		return results;
	}
}

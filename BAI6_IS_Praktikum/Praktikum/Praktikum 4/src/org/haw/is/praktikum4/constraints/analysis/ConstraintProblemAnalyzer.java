package org.haw.is.praktikum4.constraints.analysis;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.problem.ConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class ConstraintProblemAnalyzer extends ConstraintSolverAnalyzer_A {
	private ConstraintSolver _solver;
	
	public ConstraintProblemAnalyzer(ConstraintSolver solver) {
		_solver = Objects.requireNonNull(solver);
	}
	
	public void analyze(ConstraintProblem problem) {
		ConstraintNet net = problem.createConstraintNet();
		
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
		
		long before = System.currentTimeMillis();
		boolean solutionFound = _solver.solve(net);
		long duration = System.currentTimeMillis() - before;
		
		if(solutionFound) {
			System.out.println("Solution");
			for(Variable var : net.getVariables()) {
				System.out.println(var.getName() + " = " + var.getWertebereich());
			}
			System.out.println();
			System.out.println("Solution found in " + duration + "ms");
		} else {
			System.out.println("No Solution found in " + duration + "ms");
		}
	}
	
	public AnalysisResultList analyze(ConstraintProblem... problems) {
		return analyzeProblems(_solver, problems);
	}
}

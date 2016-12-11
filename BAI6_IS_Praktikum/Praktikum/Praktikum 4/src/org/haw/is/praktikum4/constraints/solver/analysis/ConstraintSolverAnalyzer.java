package org.haw.is.praktikum4.constraints.solver.analysis;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.problem.ConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class ConstraintSolverAnalyzer {
	private ConstraintProblem _problem;
	
	public ConstraintSolverAnalyzer(ConstraintProblem problem) {
		_problem = Objects.requireNonNull(problem);
	}
	
	public void analyze(ConstraintSolver solver) {
		ConstraintNet net = _problem.createConstraintNet();
		
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
		
		long before = System.currentTimeMillis();
		solver.solve(net);
		long duration = System.currentTimeMillis() - before;
		
		System.out.println("Solution");
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
		
		System.out.println("Solution found in " + duration + "ms");
	}
}

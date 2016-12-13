package org.haw.is.praktikum4.constraints.analysis.tests;

import org.haw.is.praktikum4.constraints.analysis.ConstraintProblemAnalyzer;
import org.haw.is.praktikum4.constraints.problem.DameProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class VierDameRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		
		DameProblem problem = new DameProblem();
		problem.setDamen(4);
		problem.setGenerateMultipleConstraints(true);
		analyzer.analyze(problem);
		
		problem.setGenerateMultipleConstraints(false);
		analyzer.analyze(problem);
	}
}

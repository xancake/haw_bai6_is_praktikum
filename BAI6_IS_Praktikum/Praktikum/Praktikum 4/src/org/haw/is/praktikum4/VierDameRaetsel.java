package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.problem.DameProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.analysis.ConstraintProblemAnalyzer;

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

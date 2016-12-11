package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.problem.SimpleMultiConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.analysis.ConstraintProblemAnalyzer;

public class SimpleMultiConstraintRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		analyzer.analyze(new SimpleMultiConstraintProblem());
	}
}

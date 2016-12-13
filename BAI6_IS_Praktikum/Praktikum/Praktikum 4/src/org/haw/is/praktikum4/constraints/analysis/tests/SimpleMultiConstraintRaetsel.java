package org.haw.is.praktikum4.constraints.analysis.tests;

import org.haw.is.praktikum4.constraints.analysis.ConstraintProblemAnalyzer;
import org.haw.is.praktikum4.constraints.problem.SimpleMultiConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class SimpleMultiConstraintRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		analyzer.analyze(new SimpleMultiConstraintProblem());
	}
}

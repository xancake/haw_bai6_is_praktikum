package org.haw.is.praktikum4.constraints.analysis.tests;

import org.haw.is.praktikum4.constraints.analysis.ConstraintProblemAnalyzer;
import org.haw.is.praktikum4.constraints.problem.EinsteinProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class EinsteinRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		analyzer.analyze(new EinsteinProblem());
	}
}

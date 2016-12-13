package org.haw.is.praktikum4.constraints.analysis.tests.basic;

import org.haw.is.praktikum4.constraints.analysis.ConstraintProblemAnalyzer;
import org.haw.is.praktikum4.constraints.problem.VorlesungProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class VorlesungRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		analyzer.analyze(new VorlesungProblem());
	}
}

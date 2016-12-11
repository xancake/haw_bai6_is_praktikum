package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.problem.VorlesungProblem;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.analysis.ConstraintProblemAnalyzer;

public class VorlesungRaetsel {
	public static void main(String[] args) {
		ConstraintProblemAnalyzer analyzer = new ConstraintProblemAnalyzer(new LoggingConstraintSolver());
		analyzer.analyze(new VorlesungProblem());
	}
}

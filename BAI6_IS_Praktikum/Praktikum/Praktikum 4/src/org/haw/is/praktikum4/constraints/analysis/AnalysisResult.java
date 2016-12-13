package org.haw.is.praktikum4.constraints.analysis;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class AnalysisResult {
	public final ConstraintSolver solver;
	public final ConstraintNet net;
	public final long duration;
	public final boolean solutionFound;
	
	AnalysisResult(ConstraintSolver s, ConstraintNet n, long d, boolean f) {
		solver = Objects.requireNonNull(s);
		net = Objects.requireNonNull(n);
		duration = d;
		solutionFound = f;
	}
	
	public void print() {
		printSolution();
		printDuration();
	}
	
	public void printSolution() {
		System.out.println("Solution");
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
	}
	
	public void printDuration() {
		System.out.println("Solution found in " + duration + "ms");
	}
}

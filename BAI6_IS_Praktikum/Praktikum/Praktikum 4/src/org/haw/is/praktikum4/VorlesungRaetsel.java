package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.problem.VorlesungProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class VorlesungRaetsel {
	public static void main(String[] args) {
		ConstraintNet net = new VorlesungProblem().createConstraintNet();
		
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
		
		ConstraintSolver solver = new LoggingConstraintSolver();
		solver.solve(net);
		
		System.out.println("Ergebnis");
		for(Variable var : net.getVariables()) {
			System.out.println(var.getName() + " = " + var.getWertebereich());
		}
		System.out.println();
	}
}

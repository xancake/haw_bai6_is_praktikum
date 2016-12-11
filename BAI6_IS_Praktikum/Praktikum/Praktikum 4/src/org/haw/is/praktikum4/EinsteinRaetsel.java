package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.problem.EinsteinProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class EinsteinRaetsel {
	public static void main(String[] args) {
		ConstraintNet netz = new EinsteinProblem().createConstraintNet();
		
		for(Variable v : netz.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
		
		ConstraintSolver solver = new LoggingConstraintSolver();
		solver.solve(netz);
		
		System.out.println("Ergebnis");
		for(Variable v : netz.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
	}
}

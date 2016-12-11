package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.problem.DameProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.LoggingConstraintSolver;

public class VierDameRaetsel {
	public static void main(String[] args) {
		DameProblem problem = new DameProblem();
		problem.setDamen(4);
		problem.setGenerateMultipleConstraints(true);
		ConstraintNet netWithMultipleConstraints = problem.createConstraintNet();
		
		problem.setGenerateMultipleConstraints(false);
		ConstraintNet netWithSingleConstraints = problem.createConstraintNet();
		
		
		
		for(Variable v : netWithMultipleConstraints.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
		
		ConstraintSolver solver = new LoggingConstraintSolver();
		solver.solve(netWithMultipleConstraints);
		
		System.out.println("Ergebnis");
		for(Variable v : netWithMultipleConstraints.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
		
		solver.solve(netWithSingleConstraints);
		
		System.out.println("Ergebnis");
		for(Variable v : netWithSingleConstraints.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
	}
}

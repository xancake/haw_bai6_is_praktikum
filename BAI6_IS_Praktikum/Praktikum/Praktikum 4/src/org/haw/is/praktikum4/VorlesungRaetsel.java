package org.haw.is.praktikum4;

import java.util.Arrays;
import java.util.Collection;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.BinaryCompareFunction;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class VorlesungRaetsel {
	private static final Collection<Integer> WERTEBEREICH = Arrays.asList(1, 2, 3, 4);
	
	public static void main(String[] args) {
		ConstraintNet net = new ConstraintNet();
		
		Variable v = new Variable("V", WERTEBEREICH);
		Variable x = new Variable("X", WERTEBEREICH);
		Variable y = new Variable("Y", WERTEBEREICH);
		Variable z = new Variable("Z", WERTEBEREICH);
		net.addVariable(v);
		net.addVariable(x);
		net.addVariable(y);
		net.addVariable(z);
		
		net.addConstraint(new BinaryConstraint("=", v, x, BinaryCompareFunction.eq()));
		net.addConstraint(new BinaryConstraint("<", x, y, BinaryCompareFunction.less()));
		net.addConstraint(new BinaryConstraint("*2=", x, z, (l,r) -> l*2==r));
		net.addConstraint(new BinaryConstraint("=", z, y, BinaryCompareFunction.eq()));
		
		ConstraintSolver solver = new ConstraintSolver();
		solver.solve(net);
		
		System.out.println();
	}
}

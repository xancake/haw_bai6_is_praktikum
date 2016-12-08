package org.haw.is.praktikum4;

import java.util.Arrays;
import java.util.Collection;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.BinaryCompareFunction;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class VierDameRaetsel {
	private static final Collection<Integer> WERTEBEREICH = Arrays.asList(1, 2, 3, 4);
	
	public static void main(String[] args) {
		ConstraintNet net = new ConstraintNet();
		
		Variable v1 = new Variable("Spalte 1", WERTEBEREICH);
		Variable v2 = new Variable("Spalte 2", WERTEBEREICH);
		Variable v3 = new Variable("Spalte 3", WERTEBEREICH);
		Variable v4 = new Variable("Spalte 4", WERTEBEREICH);
		net.addVariable(v1);
		net.addVariable(v2);
		net.addVariable(v3);
		net.addVariable(v4);
		
		BinaryCompareFunction dif1 = (l,r) -> l+1 != r && l-1 != r;
		BinaryCompareFunction dif2 = (l,r) -> l+2 != r && l-2 != r;
		BinaryCompareFunction dif3 = (l,r) -> l+3 != r && l-3 != r;
		
		net.addConstraint(new AllUniqueConstraint(v1, v2, v3, v4));
		net.addConstraint(new BinaryConstraint("+/-1=", v1, v2, dif1));
		net.addConstraint(new BinaryConstraint("+/-2=", v1, v3, dif2));
		net.addConstraint(new BinaryConstraint("+/-3=", v1, v4, dif3));
		net.addConstraint(new BinaryConstraint("+/-1=", v2, v3, dif1));
		net.addConstraint(new BinaryConstraint("+/-2=", v2, v4, dif2));
		net.addConstraint(new BinaryConstraint("+/-1=", v3, v4, dif1));
		
		
		ConstraintSolver solver = new ConstraintSolver();
		solver.solve(net);
		
		for(Variable v : net.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
	}
}

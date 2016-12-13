package org.haw.is.praktikum4.constraints.problem;

import java.util.Arrays;
import java.util.Collection;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint.BinaryCompareFunction;

/**
 * Diese Klasse modelliert das Constraint-Problem aus der Intelligente Systeme Vorlesung als
 * {@link ConstraintNet Constraint Netz}.
 */
public class VorlesungProblem implements ConstraintProblem {
	private static final Collection<Integer> WERTEBEREICH = Arrays.asList(1, 2, 3, 4);
	
	@Override
	public ConstraintNet createConstraintNet() {
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
		
		return net;
	}
}

package org.haw.is.praktikum4.constraints.problem;

import java.util.Arrays;
import java.util.Collection;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint;

public class SimpleMultiConstraintProblem implements ConstraintProblem {
	private static final Collection<Integer> WERTEBEREICH = Arrays.asList(1, 2, 3, 4);
	
	@Override
	public ConstraintNet createConstraintNet() {
		ConstraintNet net = new ConstraintNet();
		Variable a = new Variable("A", WERTEBEREICH);
		Variable b = new Variable("B", WERTEBEREICH);
		net.addVariable(a);
		net.addVariable(b);
		net.addConstraint(new BinaryConstraint("A*2=B", a, b, (l,r) -> l*2==r));
		net.addConstraint(new BinaryConstraint("A+2=B", a, b, (l,r) -> l+2==r));
		return net;
	}
}

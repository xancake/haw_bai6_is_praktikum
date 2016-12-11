package org.haw.is.praktikum4.constraints.solver.consistency.arc;

import java.util.Collection;
import java.util.HashSet;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolverUtils;
import org.haw.is.praktikum4.util.Pair;

public class ArcConsistency1 implements ArcConsistencyAlgorithm {
	@Override
	public void makeArcConsistent(ConstraintNet net) {
		Collection<Pair<Variable, Variable>> q = makeQ(net);
		boolean change;
		do {
			change = false;
			for(Pair<Variable, Variable> pair : q) {
				Variable vk = pair.getKey();
				Variable vm = pair.getValue();
				change = ConstraintSolverUtils.revise(vk, vm, net) || change;
			}
		} while(change);
	}
	
	private Collection<Pair<Variable, Variable>> makeQ(ConstraintNet net) {
		Collection<Pair<Variable, Variable>> q = new HashSet<>();
		for(BinaryConstraint c : net.getBinaryConstraints()) {
			q.add(new Pair<>(c.getLinks(), c.getRechts()));
			q.add(new Pair<>(c.getRechts(), c.getLinks()));
		}
		return q;
	}
}

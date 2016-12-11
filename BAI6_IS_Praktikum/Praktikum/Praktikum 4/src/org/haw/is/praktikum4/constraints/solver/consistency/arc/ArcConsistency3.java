package org.haw.is.praktikum4.constraints.solver.consistency.arc;

import java.util.Collection;
import java.util.HashSet;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolverUtils;
import org.haw.is.praktikum4.util.Pair;

public class ArcConsistency3 implements ArcConsistencyAlgorithm {
	@Override
	public void makeArcConsistent(ConstraintNet net) {
		Collection<Pair<Variable, Variable>> q = makeQ(net);
		while(!q.isEmpty()) {
			Pair<Variable, Variable> arc = q.iterator().next();
			q.remove(arc);
			Variable vk = arc.getKey();
			Variable vm = arc.getValue();
			if(ConstraintSolverUtils.revise(net, vk, vm)) {
				q.addAll(getNeighbourArcs(net, vk, vm));
			}
		}
	}
	
	private Collection<Pair<Variable, Variable>> makeQ(ConstraintNet net) {
		Collection<Pair<Variable, Variable>> q = new HashSet<>();
		for(BinaryConstraint c : net.getBinaryConstraints()) {
			q.add(new Pair<>(c.getLinks(), c.getRechts()));
			q.add(new Pair<>(c.getRechts(), c.getLinks()));
		}
		return q;
	}
	
	private Collection<Pair<Variable, Variable>> getNeighbourArcs(ConstraintNet net, Variable vk, Variable vm) {
		Collection<Pair<Variable, Variable>> pairs = new HashSet<>();
		for(Variable neighbour : net.getNeighbours(vk)) {
			if(!neighbour.equals(vm)) {
				pairs.add(new Pair<>(neighbour, vk));
			}
		}
		return pairs;
	}
}

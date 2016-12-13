package org.haw.is.praktikum4.constraints.solver.solve;

import java.util.Collection;
import java.util.HashSet;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolverUtils;
import org.haw.is.praktikum4.constraints.util.Pair;

public class AC3BackTracking implements ConstraintNetSolver {
	@Override
	public boolean resolve(ConstraintNet net, int cv) {
		Collection<Pair<Variable, Variable>> q = getPrecedingNeighbourArcs(net, cv);
		boolean consistent = true;
		while(!q.isEmpty() && consistent) {
			Pair<Variable, Variable> arc = q.iterator().next();
			q.remove(arc);
			Variable vk = arc.getKey();
			Variable vm = arc.getValue();
			consistent = !ConstraintSolverUtils.revise(net, vk, vm);
		}
		return consistent;
	}
	
	private Collection<Pair<Variable, Variable>> getPrecedingNeighbourArcs(ConstraintNet net, int cv) {
		Variable vcv = net.getVariables().get(cv);
		Collection<Pair<Variable, Variable>> arcs = new HashSet<>();
		for(Variable vi : net.getNeighbours(vcv, 0, cv)) {
			arcs.add(new Pair<>(vi, vcv));
		}
		return arcs;
	}
}

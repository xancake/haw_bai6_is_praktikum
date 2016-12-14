package org.haw.is.praktikum4.constraints.solver.solve;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolverUtils;
import org.haw.is.praktikum4.constraints.util.Pair;

public class AC3BackTracking implements ConstraintSolveAlgorithm {
	@Override
	public boolean solve(ConstraintNet net, int cv) {
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
	
	/**
	 * {@inheritDoc}
	 * 
	 * Sichert alle Variablen von 0 bis {@code cv}.
	 */
	@Override
	public Map<Variable, Set<Integer>> backupWertebereich(ConstraintNet net, int cv) {
		Map<Variable, Set<Integer>> backup = new HashMap<>();
		for(int i=0; i<=cv; i++) {
			Variable vi = net.getVariables().get(i);
			backup.put(vi, new HashSet<>(vi.getWertebereich()));
		}
		return backup;
	}
}

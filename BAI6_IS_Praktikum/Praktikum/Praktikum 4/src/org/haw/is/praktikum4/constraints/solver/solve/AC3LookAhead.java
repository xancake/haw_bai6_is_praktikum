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

public class AC3LookAhead implements ConstraintSolveAlgorithm {
	@Override
	public boolean solve(ConstraintNet net, int cv) {
		Collection<Pair<Variable, Variable>> q = getFollowingNeighbourArcs(net, cv);
		boolean consistent = true;
		while(!q.isEmpty() && consistent) {
			Pair<Variable, Variable> arc = q.iterator().next();
			q.remove(arc);
			Variable vk = arc.getKey();
			Variable vm = arc.getValue();
			if(ConstraintSolverUtils.revise(net, vk, vm)) {
				q.addAll(getNeighbourArcsOfVk(net, vk, vm, cv));
				consistent = !vk.getWertebereich().isEmpty();
			}
		}
		return consistent;
	}
	
	private Collection<Pair<Variable, Variable>> getFollowingNeighbourArcs(ConstraintNet net, int cv) {
		Variable vcv = net.getVariables().get(cv);
		Collection<Pair<Variable, Variable>> arcs = new HashSet<>();
		for(Variable vi : net.getNeighbours(vcv, cv+1, net.getVariables().size())) {
			arcs.add(new Pair<>(vi, vcv));
		}
		return arcs;
	}
	
	private Collection<Pair<Variable, Variable>> getNeighbourArcsOfVk(ConstraintNet net, Variable vk, Variable vm, int cv) {
		Collection<Pair<Variable, Variable>> arcs = new HashSet<>();
		for(Variable vi : net.getNeighbours(vk, cv+1, net.getVariables().size())) {
			if(!vi.equals(vm)) {
				arcs.add(new Pair<>(vi, vk));
			}
		}
		return arcs;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Sichert alle Variablen von {@code cv} bis size({@link ConstraintNet#getVariables()}).
	 */
	@Override
	public Map<Variable, Set<Integer>> backupWertebereich(ConstraintNet net, int cv) {
		Map<Variable, Set<Integer>> backup = new HashMap<>();
		for(int i=cv; i<net.getVariables().size(); i++) {
			Variable vi = net.getVariables().get(i);
			backup.put(vi, new HashSet<>(vi.getWertebereich()));
		}
		return backup;
	}
}

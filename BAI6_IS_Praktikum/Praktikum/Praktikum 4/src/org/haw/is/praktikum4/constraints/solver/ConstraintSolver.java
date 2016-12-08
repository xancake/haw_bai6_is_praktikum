package org.haw.is.praktikum4.constraints.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.UnaryConstraint;
import org.haw.is.praktikum4.util.Pair;

public class ConstraintSolver {
	public void solve(ConstraintNet net) {
		nodeConsistency(net);
		arcConsistency(net);
		
		// Auflösen
	}
	
	private void nodeConsistency(ConstraintNet net) {
		for(Variable v : net.getVariables()) {
			for(Iterator<Integer> d=v.getWertebereich().iterator(); d.hasNext(); ) {
				Integer x = d.next();
				List<UnaryConstraint> cs = getUnaryConstraintsFor(v, net);
				for(UnaryConstraint c : cs) {
					if(!c.isConsistent(x)) {
						d.remove();
						break;
					}
				}
			}
		}
	}
	
	private List<UnaryConstraint> getUnaryConstraintsFor(Variable v, ConstraintNet net) {
		List<UnaryConstraint> constraints = new ArrayList<>();
		for(UnaryConstraint c : net.getUnaryConstraints()) {
			if(c.usesVariable(v)) {
				constraints.add(c);
			}
		}
		return constraints;
	}
	
	private void arcConsistency(ConstraintNet net) {
		Set<Pair<Variable, Variable>> q = makeQ(net);
		while(!q.isEmpty()) {
			Pair<Variable, Variable> arc = q.iterator().next();
			q.remove(arc);
			if(revise(arc.getKey(), arc.getValue(), net)) {
				q.addAll(getNeighbourArcs(net, arc.getKey(), arc.getValue()));
			}
		}
	}
	
	private Set<Pair<Variable, Variable>> makeQ(ConstraintNet net) {
		Set<Pair<Variable, Variable>> q = new HashSet<>();
		for(BinaryConstraint c : net.getBinaryConstraints()) {
			q.add(new Pair<>(c.getLinks(), c.getRechts()));
			q.add(new Pair<>(c.getRechts(), c.getLinks()));
		}
		return q;
	}
	
	private boolean revise(Variable vi, Variable vj, ConstraintNet net) {
		boolean delete = false;
		for(Iterator<Integer> di=vi.getWertebereich().iterator(); di.hasNext(); ) {
			Integer x = di.next();
			if(noSuchY(vi, vj, net, x)) {
				di.remove();
				delete = true;
			}
		}
		
		return delete;
	}
	
	private boolean noSuchY(Variable vi, Variable vj, ConstraintNet net, Integer x) {
		for(Iterator<Integer> dj=vj.getWertebereich().iterator(); dj.hasNext(); ) {
			Integer y = dj.next();
			for(BinaryConstraint c : getBinaryConstraintsFor(vi, vj, net)) {
				if(c.getLinks().equals(vi) ? c.isConsistent(x, y) : c.isConsistent(y, x)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private List<BinaryConstraint> getBinaryConstraintsFor(Variable vi, Variable vj, ConstraintNet net) {
		List<BinaryConstraint> constraints = new ArrayList<>();
		for(BinaryConstraint c : net.getBinaryConstraints()) {
			if(c.usesVariable(vi) && c.usesVariable(vj)) {
				constraints.add(c);
			}
		}
		return constraints;
	}
	
	private Set<Pair<Variable, Variable>> getNeighbourArcs(ConstraintNet net, Variable vk, Variable vm) {
		Set<Pair<Variable, Variable>> pairs = new HashSet<>();
		for(BinaryConstraint c : net.getBinaryConstraints()) {
			if(c.usesVariable(vk)) {
				Variable v1 = c.getLinks();
				Variable v2 = c.getRechts();
				Variable vi = v1.equals(vk) ? v2 : v1;
				if(!vi.equals(vm)) {
					pairs.add(new Pair<>(vi, vk));
				}
			}
		}
		return pairs;
	}
}

package org.haw.is.praktikum4.constraints.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		resolve(net);
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
			if(!hasConsistentY(vi, vj, net, x)) {
				di.remove();
				delete = true;
			}
		}
		return delete;
	}
	
	private boolean hasConsistentY(Variable vi, Variable vj, ConstraintNet net, Integer x) {
		for(Iterator<Integer> dj=vj.getWertebereich().iterator(); dj.hasNext(); ) {
			Integer y = dj.next();
			for(BinaryConstraint c : getBinaryConstraintsFor(vi, vj, net)) {
				if(c.getLinks().equals(vi) ? c.isConsistent(x, y) : c.isConsistent(y, x)) {
					return true;
				}
			}
		}
		return false;
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
	
	
	
//	private void resolve(ConstraintNet net) {
//		Map<Variable, Set<Integer>> versuchsHistorie = new HashMap<>();
//		for(Variable v : net.getVariables()) {
//			versuchsHistorie.put(v, new HashSet<>());
//		}
//		
//		for(int i=0; i<net.getVariables().size(); i++) {
//			Variable v = net.getVariables().get(i);
//			
//			
//			Integer belegung;
//			Iterator<Integer> iter = v.getWertebereich().iterator();
//			do {
//				belegung = iter.next();
//				
//			} while(iter.hasNext() && versuchsHistorie.get(v).contains(belegung));
//			
//			versuchsHistorie.get(v).add(belegung);
//			
//			Set<Integer> alterWertebereich = new HashSet<>(v.getWertebereich());
//			v.getWertebereich().clear();
//			v.getWertebereich().add(belegung);
//			
//			if(!resolveForwardChecking(net, i, v)) {
//				i--;
//				v.getWertebereich().addAll(alterWertebereich);
//			}
//		}
//	}
	
	private void resolve(ConstraintNet net) {
		resolve(net, 0);
	}
	
	private boolean resolve(ConstraintNet net, int i) {
		if(i == net.getVariables().size()) {
			return true;
		}
		
		Map<Variable, Set<Integer>> backup = backupWertebereich(net, i);
		
		Variable v = net.getVariables().get(i);
		Set<Integer> alterWertebereich = new HashSet<>(v.getWertebereich());
		Iterator<Integer> iter = alterWertebereich.iterator();
		
		while(iter.hasNext()) {
			Integer b = iter.next();
			v.getWertebereich().clear();
			v.getWertebereich().add(b);
			
			if(resolveForwardChecking(net, i, v) && resolve(net, i+1)) {
//				return true;
				System.out.println(net.getVariables());
				return false;
			}
			
//			// Alten Wertebereich wiederherstellen
//			v.getWertebereich().addAll(alterWertebereich);
//			// Eigentlich müsste man alle Wertebereiche der nachfolgenden Variablen wiederherstellen,
//			// weil revise auch die anderen Variablen "zerstört"
			
			restoreWertebereich(backup);
		}
		return false;
	}
	
	private Map<Variable, Set<Integer>> backupWertebereich(ConstraintNet net, int from) {
		Map<Variable, Set<Integer>> backup = new HashMap<>();
		for(int i=from; i<net.getVariables().size(); i++) {
			Variable variable = net.getVariables().get(i);
			backup.put(variable, new HashSet<>(variable.getWertebereich()));
		}
		return backup;
	}
	
	private void restoreWertebereich(Map<Variable, Set<Integer>> backup) {
		for(Entry<Variable, Set<Integer>> entry : backup.entrySet()) {
			entry.getKey().getWertebereich().clear();
			entry.getKey().getWertebereich().addAll(entry.getValue());
		}
	}
	
	private boolean resolveForwardChecking(ConstraintNet net, int cv, Variable v) {
		Set<Pair<Variable, Variable>> q = getFollowingNeighbours(net, cv, v);
		boolean consistent = true;
		while(!q.isEmpty() && consistent) {
			Pair<Variable, Variable> arc = q.iterator().next();
			q.remove(arc);
			if(revise(arc.getKey(), arc.getValue(), net)) {
				consistent = !arc.getKey().getWertebereich().isEmpty();
			}
		}
		return consistent;
	}
	
	private Set<Pair<Variable, Variable>> getFollowingNeighbours(ConstraintNet net, int cv, Variable v) {
		List<BinaryConstraint> constraints = net.getBinaryConstraints();
		Set<Pair<Variable, Variable>> neighbours = new HashSet<>();
		for(int i=cv+1; i<net.getVariables().size(); i++) {
			Variable vi = net.getVariables().get(i);
			for(BinaryConstraint c : constraints) {
				if(c.usesVariable(v) && c.usesVariable(vi)) {
					neighbours.add(c.getLinks().equals(v) ? new Pair<>(vi, v) : new Pair<>(v, vi));
				}
			}
		}
		return neighbours;
	}
}

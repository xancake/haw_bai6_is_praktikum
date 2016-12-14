package org.haw.is.praktikum4.constraints.solver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistency3;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.consistency.node.NodeConsistency;
import org.haw.is.praktikum4.constraints.solver.consistency.node.NodeConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.solve.AC3ForwardChecking;
import org.haw.is.praktikum4.constraints.solver.solve.ConstraintSolveAlgorithm;

public class ConstraintSolver {
	private NodeConsistencyAlgorithm _nc;
	private ArcConsistencyAlgorithm _ac;
	private ConstraintSolveAlgorithm _sa;
	
	public ConstraintSolver() {
		this(new NodeConsistency(), new ArcConsistency3(), new AC3ForwardChecking());
	}
	
	public ConstraintSolver(NodeConsistencyAlgorithm nc, ArcConsistencyAlgorithm ac, ConstraintSolveAlgorithm sa) {
		_nc = Objects.requireNonNull(nc);
		_ac = Objects.requireNonNull(ac);
		_sa = Objects.requireNonNull(sa);
	}
	
	/**
	 * Löst das übergebene {@link ConstraintNet Constraint Netz} und gibt eine Lösung zurück.
	 * <p>Die Lösung wird nicht zurückgegeben, sondern muss anhand der Wertebereiche der Variablen erfragt werden.
	 * <p>Diese Methode arbeitet destruktiv und verändert das übergebene Netz.
	 * @param net Das zu lösende {@link ConstraintNet Constraint Netz}
	 * @return {@code true} wenn es eine Lösung gibt, ansonsten {@code false}
	 */
	public boolean solve(ConstraintNet net) {
		_nc.makeNodeConsistent(net);
		_ac.makeArcConsistent(net);
		return solve(net, 0);
	}
	
	/**
	 * Sucht eine Lösung für das übergebene {@link ConstraintNet Constraint Netz}. Dabei werden die Variablen des
	 * übergebenen Constraint Netzes nach und nach belegt. Diese Methode setzt die Belegung durch Rekursion um, wobei
	 * der Rekursionsbaum den Suchbaum darstellt und damit zum Backtracking verwendet wird.
	 * <p>Diese Methode verwendet den einen {@link ConstraintSolveAlgorithm} um 
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 * @param i Die Variable, die auf der Ebene belegt wird
	 * @return {@code true} wenn die Variable mit Index {@code i} erfolgreich belegt werden konnte, ansonsten {@code false}
	 */
	private boolean solve(ConstraintNet net, int i) {
		if(i == net.getVariables().size()) {
			return true;
		}
		
		Map<Variable, Set<Integer>> backup = _sa.backupWertebereich(net, i);
		
		Variable v = net.getVariables().get(i);
		Set<Integer> alterWertebereich = new HashSet<>(v.getWertebereich());
		Iterator<Integer> iter = alterWertebereich.iterator();
		
		while(iter.hasNext()) {
			Integer b = iter.next();
			v.getWertebereich().clear();
			v.getWertebereich().add(b);
			
			if(_sa.solve(net, i) && solve(net, i+1)) {
				return true;
			}
			
			restoreWertebereich(backup);
		}
		return false;
	}
	
	private void restoreWertebereich(Map<Variable, Set<Integer>> backup) {
		for(Entry<Variable, Set<Integer>> entry : backup.entrySet()) {
			entry.getKey().getWertebereich().clear();
			entry.getKey().getWertebereich().addAll(entry.getValue());
		}
	}
}

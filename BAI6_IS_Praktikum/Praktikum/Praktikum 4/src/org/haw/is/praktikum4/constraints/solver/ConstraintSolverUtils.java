package org.haw.is.praktikum4.constraints.solver;

import java.util.Iterator;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint;

public final class ConstraintSolverUtils {
	private ConstraintSolverUtils() {}
	
	public static boolean revise(ConstraintNet net, Variable vi, Variable vj) {
		boolean delete = false;
		for(Iterator<Integer> di=vi.getWertebereich().iterator(); di.hasNext(); ) {
			Integer x = di.next();
			if(!hasConsistentY(net, vi, vj, x)) {
				di.remove();
				delete = true;
			}
		}
		return delete;
	}
	
	private static boolean hasConsistentY(ConstraintNet net, Variable vi, Variable vj, Integer x) {
		for(Iterator<Integer> dj=vj.getWertebereich().iterator(); dj.hasNext(); ) {
			Integer y = dj.next();
			if(isConsistentForAllConstraints(net, vi, vj, x, y)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isConsistentForAllConstraints(ConstraintNet net, Variable vi, Variable vj, Integer x, Integer y) {
		boolean consistentForAllConstraints = true;
		for(BinaryConstraint c : net.getBinaryConstraintsFor(vi, vj)) {
			if(c.getLinks().equals(vi) ? !c.isConsistent(x, y) : !c.isConsistent(y, x)) {
				consistentForAllConstraints = false;
			}
		}
		return consistentForAllConstraints;
	}
}

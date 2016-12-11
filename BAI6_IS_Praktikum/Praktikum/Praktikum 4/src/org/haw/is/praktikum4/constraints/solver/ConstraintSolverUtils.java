package org.haw.is.praktikum4.constraints.solver;

import java.util.Iterator;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;

public final class ConstraintSolverUtils {
	private ConstraintSolverUtils() {}
	
	public static boolean revise(Variable vi, Variable vj, ConstraintNet net) {
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
	
	public static boolean hasConsistentY(Variable vi, Variable vj, ConstraintNet net, Integer x) {
		for(Iterator<Integer> dj=vj.getWertebereich().iterator(); dj.hasNext(); ) {
			Integer y = dj.next();
			for(BinaryConstraint c : net.getBinaryConstraintsFor(vi, vj)) {
				if(c.getLinks().equals(vi) ? c.isConsistent(x, y) : c.isConsistent(y, x)) {
					return true;
				}
			}
		}
		return false;
	}
}

package org.haw.is.praktikum4.constraints.solver.consistency.node;

import java.util.Iterator;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.net.constraint.UnaryConstraint;

public class NodeConsistency implements NodeConsistencyAlgorithm {
	@Override
	public void makeNodeConsistent(ConstraintNet net) {
		for(Variable v : net.getVariables()) {
			for(Iterator<Integer> d=v.getWertebereich().iterator(); d.hasNext(); ) {
				Integer x = d.next();
				for(UnaryConstraint c : net.getUnaryConstraintsFor(v)) {
					if(!c.isConsistent(x)) {
						d.remove();
						break;
					}
				}
			}
		}
	}
}

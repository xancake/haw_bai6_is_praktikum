package org.haw.is.praktikum4.constraints.solver.consistency.node;

import org.haw.is.praktikum4.constraints.ConstraintNet;

/**
 * Schnittstelle für Knotenkonsistenzalgorithmen.
 */
public interface NodeConsistencyAlgorithm {
	/**
	 * Macht das übergebene {@link ConstraintNet Constraint Netz} knotenkonsistent.
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 */
	void makeNodeConsistent(ConstraintNet net);
}

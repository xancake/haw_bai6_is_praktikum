package org.haw.is.praktikum4.constraints.solver.consistency.arc;

import org.haw.is.praktikum4.constraints.net.ConstraintNet;

/**
 * Schnittstelle für Kantenkonsistenzalgorithmen.
 */
public interface ArcConsistencyAlgorithm {
	/**
	 * Macht das übergebene {@link ConstraintNet Constraint Netz} kantenkonsistent.
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 */
	void makeArcConsistent(ConstraintNet net);
}

package org.haw.is.praktikum4.constraints.problem;

import org.haw.is.praktikum4.constraints.net.ConstraintNet;

/**
 * Generische Schnittstelle für Constraint Probleme.
 */
public interface ConstraintProblem {
	/**
	 * Erzeugt ein {@link ConstraintNet Constraint Netz} das die Eigenschaften dieses Problems abbildet.
	 * @return Ein {@link ConstraintNet Constraint Netz}
	 */
	ConstraintNet createConstraintNet();
}

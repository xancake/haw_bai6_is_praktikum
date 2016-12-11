package org.haw.is.praktikum4.constraints.solver.solve;

import org.haw.is.praktikum4.constraints.ConstraintNet;

/**
 * Schnittstelle für Lösungsalgorithmen.
 */
public interface ConstraintNetSolver {
	/**
	 * Belegt die Variable mit dem übergebenen Index {@code cv} aus dem übergebenen
	 * {@link ConstraintNet Constraint Netz}.
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 * @param cv Der Index der zu belegenden Variable
	 * @return {@code true} wenn eine gültige Belegung für die Variable gefunden werden konnte, ansonsten {@code false}
	 */
	boolean resolve(ConstraintNet net, int cv);
}

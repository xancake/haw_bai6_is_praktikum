package org.haw.is.praktikum4.constraints.solver.solve;

import java.util.Map;
import java.util.Set;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;

/**
 * Schnittstelle für Lösungsalgorithmen.
 */
public interface ConstraintSolveAlgorithm {
	/**
	 * Sichert den Wertebereich der {@link Variable Variablen} im übergebenen {@link ConstraintNet Constraint Netz} der
	 * durch diesen Algorithmus in {@link #solve(ConstraintNet, int)} unter umständen verändert wird.
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 * @param cv Die Variable die gerade betrachtet wird
	 * @return Ein Mapping von Variablen auf ein Backup ihres Wertebereiches
	 */
	Map<Variable, Set<Integer>> backupWertebereich(ConstraintNet net, int cv);
	
	/**
	 * Belegt die Variable mit dem übergebenen Index {@code cv} aus dem übergebenen
	 * {@link ConstraintNet Constraint Netz}.
	 * @param net Das {@link ConstraintNet Constraint Netz}
	 * @param cv Der Index der zu belegenden Variable
	 * @return {@code true} wenn eine gültige Belegung für die Variable gefunden werden konnte, ansonsten {@code false}
	 */
	boolean solve(ConstraintNet net, int cv);
}

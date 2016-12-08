package org.haw.is.praktikum4.constraints.constraint;

import org.haw.is.praktikum4.constraints.Variable;

public interface Constraint {
	
	boolean isSatisfied();
	
	/**
	 * Prüft, ob dieser Constraint die übergebene Variable benutzt.
	 * @param v Die zu prüfende Variable
	 * @return {@code true} wenn dieser Constraint die Variable nutzt, ansonsten {@code false}
	 */
	boolean usesVariable(Variable v);
}

package org.haw.is.praktikum4.constraints.net;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.haw.is.praktikum4.constraints.net.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.UnaryConstraint;

public class ConstraintNet {
	private List<Variable> _variablen;
	private List<UnaryConstraint> _unaryConstraints;
	private List<BinaryConstraint> _binaryConstraints;
	
	public ConstraintNet() {
		_variablen = new ArrayList<>();
		_unaryConstraints = new ArrayList<>();
		_binaryConstraints = new ArrayList<>();
	}
	
	public void addVariable(Variable v) {
		_variablen.add(v);
	}
	
	public void addConstraint(UnaryConstraint c) {
		_unaryConstraints.add(c);
	}
	
	public void addConstraint(BinaryConstraint c) {
		_binaryConstraints.add(c);
	}
	
	public void addConstraint(AllUniqueConstraint c) {
		_binaryConstraints.addAll(c.generateBinaryConstraints());
	}
	
	public void addAllVariables(Collection<? extends Variable> vs) {
		_variablen.addAll(vs);
	}
	
	public void addAllUnaryConstraints(Collection<? extends UnaryConstraint> cs) {
		_unaryConstraints.addAll(cs);
	}
	
	public void addAllBinaryConstraints(Collection<? extends BinaryConstraint> cs) {
		_binaryConstraints.addAll(cs);
	}
	
	public List<Variable> getVariables() {
		return _variablen;
	}
	
	public List<UnaryConstraint> getUnaryConstraints() {
		return _unaryConstraints;
	}
	
	public List<BinaryConstraint> getBinaryConstraints() {
		return _binaryConstraints;
	}
	
	/**
	 * Gibt eine Liste aller {@link UnaryConstraint unären Constraints} für die übergebene Variable zurück.
	 * @param v Die Variable zu der alle unären Constraints geliefert werden sollen
	 * @return Eine Liste aller unären Constraints für {@code v}
	 */
	public List<UnaryConstraint> getUnaryConstraintsFor(Variable v) {
		List<UnaryConstraint> constraints = new ArrayList<>();
		for(UnaryConstraint c : getUnaryConstraints()) {
			if(c.usesVariable(v)) {
				constraints.add(c);
			}
		}
		return constraints;
	}
	
	/**
	 * Gibt eine Liste aller {@link BinaryConstraint binären Constraints}, die die übergebenen Variablen benutzen zurück.
	 * @param vi Die eine Variable
	 * @param vj Die andere Variable
	 * @return Eine Liste aller binären Constraints für {@code vi} und {@code vj}
	 */
	public List<BinaryConstraint> getBinaryConstraintsFor(Variable vi, Variable vj) {
		List<BinaryConstraint> constraints = new ArrayList<>();
		for(BinaryConstraint c : getBinaryConstraints()) {
			if(c.usesVariable(vi) && c.usesVariable(vj)) {
				constraints.add(c);
			}
		}
		return constraints;
	}
	
	/**
	 * Gibt alle Nachbarn der übergebenen Variable zurück.
	 * @param v Die Variable
	 * @return Alle Nachbarn der Variable
	 */
	public List<Variable> getNeighbours(Variable v) {
		return getNeighbours(v, 0, _variablen.size());
	}
	
	/**
	 * Gibt alle Nachbarn der übergebenen Variable zurück, die sich im Indexbereich {@code from - to} befinden.
	 * @param v Die Variable
	 * @param from Der Anfang des Indexbereichs (inklusive)
	 * @param to Das Ende des Indexbereichs (exklusive)
	 * @return Die Nachbarn der Variable im Indexbereich
	 */
	public List<Variable> getNeighbours(Variable v, int from, int to) {
		List<Variable> neighbours = new ArrayList<>();
		for(int i=from; i<to; i++) {
			Variable vi = _variablen.get(i);
			for(BinaryConstraint c : getBinaryConstraints()) {
				if(!v.equals(vi) && c.usesVariable(v) && c.usesVariable(vi)) {
					neighbours.add(vi);
					break;
				}
			}
		}
		return neighbours;
	}
}

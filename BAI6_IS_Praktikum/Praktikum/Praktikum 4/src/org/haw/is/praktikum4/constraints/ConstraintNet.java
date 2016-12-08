package org.haw.is.praktikum4.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.haw.is.praktikum4.constraints.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.UnaryConstraint;

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
	
	public void addAllVariables(Collection<? extends Variable> vs) {
		_variablen.addAll(vs);
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
}

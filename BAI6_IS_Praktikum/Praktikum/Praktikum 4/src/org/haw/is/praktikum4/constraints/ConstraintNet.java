package org.haw.is.praktikum4.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.Constraint;

public class ConstraintNet {
	private List<Variable> _variablen;
	private List<Constraint> _constraints;
	
	public ConstraintNet() {
		this(new ArrayList<>(), new ArrayList<>());
	}
	
	public ConstraintNet(List<Constraint> constraints) {
		this(new ArrayList<>(), constraints);
	}
	
	public ConstraintNet(List<Variable> variablen, List<Constraint> constraints) {
		_variablen   = Objects.requireNonNull(variablen);
		_constraints = Objects.requireNonNull(constraints);
	}
	
	public void addVariable(Variable v) {
		_variablen.add(v);
	}
	
	public void addConstraint(BinaryConstraint c) {
		_constraints.add(c);
	}
	
	
}

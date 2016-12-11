package org.haw.is.praktikum4.constraints.constraint;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.Variable;

public class UnaryConstraint implements Constraint {
	private String _name;
	private Variable _variable;
	private UnaryCompareFunction _function;
	
	public UnaryConstraint(String name, Variable variable, UnaryCompareFunction function) {
		_name = Objects.requireNonNull(name);
		_variable = Objects.requireNonNull(variable);
		_function = Objects.requireNonNull(function);
	}
	
	public String getName() {
		return _name;
	}
	
	public Variable getVariable() {
		return _variable;
	}
	
	@Override
	public boolean isSatisfied() {
		for(Integer x : _variable.getWertebereich()) {
			if(!isConsistent(x)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isConsistent(Integer x) {
		return _function.eval(x);
	}
	
	@Override
	public boolean usesVariable(Variable v) {
		return _variable.equals(v);
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	public static interface UnaryCompareFunction {
		boolean eval(Integer value);
		
		static UnaryCompareFunction not(Integer value) {
			return (v) -> v != value;
		}
		
		static UnaryCompareFunction eq(Integer value) {
			return (v) -> v == value;
		}
		
		static UnaryCompareFunction less(Integer value) {
			return (v) -> v < value;
		}
		
		static UnaryCompareFunction lessEq(Integer value) {
			return (v) -> v <= value;
		}
		
		static UnaryCompareFunction more(Integer value) {
			return (v) -> v > value;
		}
		
		static UnaryCompareFunction moreEq(Integer value) {
			return (v) -> v >= value;
		}
	}
}

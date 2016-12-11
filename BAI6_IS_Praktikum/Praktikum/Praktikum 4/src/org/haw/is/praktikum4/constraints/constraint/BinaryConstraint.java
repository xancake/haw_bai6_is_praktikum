package org.haw.is.praktikum4.constraints.constraint;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.Variable;

public class BinaryConstraint implements Constraint {
	private String _name;
	private Variable _links;
	private Variable _rechts;
	private BinaryCompareFunction _funktion;
	
	public BinaryConstraint(String name, Variable links, Variable rechts, BinaryCompareFunction funktion) {
		if(links.equals(rechts)) {
			throw new IllegalArgumentException("Die übergebenen Variablen müssen unterschiedlich sein!");
		}
		_name = Objects.requireNonNull(name);
		_links = Objects.requireNonNull(links);
		_rechts = Objects.requireNonNull(rechts);
		_funktion = Objects.requireNonNull(funktion);
	}
	
	public String getName() {
		return _name;
	}
	
	public Variable getLinks() {
		return _links;
	}
	
	public Variable getRechts() {
		return _rechts;
	}
	
	@Override
	public boolean isSatisfied() {
		for(Integer l : _links.getWertebereich()) {
			for(Integer r : _rechts.getWertebereich()) {
				if(!isConsistent(l, r)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isConsistent(Integer x, Integer y) {
		return _funktion.eval(x, y);
	}
	
	@Override
	public boolean usesVariable(Variable v) {
		return _links.equals(v) || _rechts.equals(v);
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	public static interface BinaryCompareFunction {
		boolean eval(Integer links, Integer rechts);
		
		static BinaryCompareFunction eq() {
			return (l,r) -> l == r;
		}
		
		static BinaryCompareFunction notEq() {
			return (l,r) -> l != r;
		}
		
		static BinaryCompareFunction less() {
			return (l,r) -> l < r;
		}
		
		static BinaryCompareFunction lessEq() {
			return (l,r) -> l <= r;
		}
		
		static BinaryCompareFunction more() {
			return (l,r) -> l > r;
		}
		
		static BinaryCompareFunction moreEq() {
			return (l,r) -> l >= r;
		}
	}
}

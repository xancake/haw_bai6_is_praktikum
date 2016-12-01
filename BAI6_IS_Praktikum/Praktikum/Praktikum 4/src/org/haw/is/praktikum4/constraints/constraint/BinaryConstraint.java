package org.haw.is.praktikum4.constraints.constraint;

import org.haw.is.praktikum4.constraints.Variable;

public class BinaryConstraint implements Constraint {
	private String _name;
	private Variable _links;
	private Variable _rechts;
	private BinaryCompareFunction _funktion;
	
	public BinaryConstraint(String name, Variable links, Variable rechts, BinaryCompareFunction funktion) {
		_name = name;
		_links = links;
		_rechts = rechts;
		_funktion = funktion;
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
				if(!_funktion.eval(l, r)) {
					return false;
				}
			}
		}
		return true;
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

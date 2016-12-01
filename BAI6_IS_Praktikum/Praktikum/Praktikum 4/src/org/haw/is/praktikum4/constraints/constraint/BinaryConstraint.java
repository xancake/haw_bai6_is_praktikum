package org.haw.is.praktikum4.constraints.constraint;

import org.haw.is.praktikum4.constraints.Variable;

public class BinaryConstraint {
	private String Name;
	private Variable Links;
	private Variable Rechts;
	private Function Funktion;
	
	public BinaryConstraint(String name, Variable links, Variable rechts, Function funktion) {
		Name = name;
		Links = links;
		Rechts = rechts;
		Funktion = funktion;
	}
	
	public String getName() {
		return Name;
	}
	
	public Variable getLinks() {
		return Links;
	}
	
	public Variable getRechts() {
		return Rechts;
	}
	
	public Function getFunktion() {
		return Funktion;
	}
	
	public boolean satisfied() {
		for(Integer l : Links.getWertebereich()) {
			for(Integer r : Rechts.getWertebereich()) {
				if(!Funktion.eval(l, r)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static interface Function {
		public boolean eval(Integer links, Integer rechts);
	}
}

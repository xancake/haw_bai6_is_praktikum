package org.haw.is.praktikum4.constraints.solver;

import java.util.Set;

public class Variable {
	private String Name;
	private Set<Integer> Wertebereich;
	
	public Variable(String name, Set<Integer> w){
		Name = name;
		Wertebereich = w;
	}

	public String getName() {
		return Name;
	}

	public Set<Integer> getWertebereich() {
		return Wertebereich;
	}
}

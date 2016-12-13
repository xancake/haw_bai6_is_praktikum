package org.haw.is.praktikum4.constraints.net;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Variable {
	private String _name;
	private Set<Integer> _wertebereich;
	
	public Variable(String name, Collection<Integer> wertebereich) {
		_name = Objects.requireNonNull(name);
		_wertebereich = new HashSet<>(Objects.requireNonNull(wertebereich));
	}
	
	public String getName() {
		return _name;
	}
	
	public Set<Integer> getWertebereich() {
		return _wertebereich;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Variable other = (Variable)obj;
		if(_name == null) {
			if(other._name != null)
				return false;
		} else if(!_name.equals(other._name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return _name + " (" + _wertebereich + ")";
	}
}

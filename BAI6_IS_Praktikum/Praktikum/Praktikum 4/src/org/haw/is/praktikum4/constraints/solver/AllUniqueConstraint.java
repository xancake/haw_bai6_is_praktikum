package org.haw.is.praktikum4.constraints.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.haw.is.praktikum4.constraints.solver.Constraint.Function;

public class AllUniqueConstraint {
	private static Function ungleich = (l,r) -> l!=r;
	
	private List<Variable> _variables;
	
	public AllUniqueConstraint(Variable... variables) {
		this(Arrays.asList(variables));
	}
	
	public AllUniqueConstraint(List<Variable> variables) {
		_variables = variables;
	}
	
	public List<Constraint> generateBinaryConstraints() {
		List<Constraint> constraints = new ArrayList<>();
		for(int i=0; i<_variables.size(); i++) {
			for(int j=i+1; j<_variables.size(); j++) {
				Variable tierI = _variables.get(i);
				Variable tierJ = _variables.get(j);
				constraints.add(new Constraint(tierI.getName() + " ungleich " + tierJ.getName(), tierI, tierJ, ungleich));
			}
		}
		return constraints;
	}
}

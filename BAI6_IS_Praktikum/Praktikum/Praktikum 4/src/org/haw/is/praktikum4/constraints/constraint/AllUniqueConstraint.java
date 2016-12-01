package org.haw.is.praktikum4.constraints.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.BinaryCompareFunction;

public class AllUniqueConstraint {
	private static BinaryCompareFunction ungleich = (l,r) -> l!=r;
	
	private List<Variable> _variables;
	
	public AllUniqueConstraint(Variable... variables) {
		this(Arrays.asList(variables));
	}
	
	public AllUniqueConstraint(List<Variable> variables) {
		_variables = variables;
	}
	
	public List<BinaryConstraint> generateBinaryConstraints() {
		List<BinaryConstraint> constraints = new ArrayList<>();
		for(int i=0; i<_variables.size(); i++) {
			for(int j=i+1; j<_variables.size(); j++) {
				Variable tierI = _variables.get(i);
				Variable tierJ = _variables.get(j);
				constraints.add(new BinaryConstraint(tierI.getName() + " ungleich " + tierJ.getName(), tierI, tierJ, ungleich));
			}
		}
		return constraints;
	}
}

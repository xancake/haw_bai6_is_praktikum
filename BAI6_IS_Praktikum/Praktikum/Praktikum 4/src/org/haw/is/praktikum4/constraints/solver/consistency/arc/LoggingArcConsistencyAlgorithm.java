package org.haw.is.praktikum4.constraints.solver.consistency.arc;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;

public class LoggingArcConsistencyAlgorithm implements ArcConsistencyAlgorithm {
	private ArcConsistencyAlgorithm _delegate;
	
	public LoggingArcConsistencyAlgorithm(ArcConsistencyAlgorithm delegate) {
		_delegate = Objects.requireNonNull(delegate);
	}
	
	@Override
	public void makeArcConsistent(ConstraintNet net) {
		_delegate.makeArcConsistent(net);
		
		System.out.println("Arc-Consistency");
		for(Variable v : net.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
	}
}

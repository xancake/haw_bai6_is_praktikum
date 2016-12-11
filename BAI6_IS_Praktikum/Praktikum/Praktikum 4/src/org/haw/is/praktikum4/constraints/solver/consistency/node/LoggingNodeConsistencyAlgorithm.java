package org.haw.is.praktikum4.constraints.solver.consistency.node;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;

public class LoggingNodeConsistencyAlgorithm implements NodeConsistencyAlgorithm {
	private NodeConsistencyAlgorithm _delegate;
	
	public LoggingNodeConsistencyAlgorithm(NodeConsistencyAlgorithm delegate) {
		_delegate = Objects.requireNonNull(delegate);
	}
	
	@Override
	public void makeNodeConsistent(ConstraintNet net) {
		_delegate.makeNodeConsistent(net);
		
		System.out.println("Node-Consistency");
		for(Variable v : net.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
		System.out.println();
	}
}

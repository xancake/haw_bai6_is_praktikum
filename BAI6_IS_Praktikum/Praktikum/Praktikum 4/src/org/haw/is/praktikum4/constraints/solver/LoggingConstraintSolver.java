package org.haw.is.praktikum4.constraints.solver;

import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistency3;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.LoggingArcConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.consistency.node.LoggingNodeConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.consistency.node.NodeConsistency;
import org.haw.is.praktikum4.constraints.solver.consistency.node.NodeConsistencyAlgorithm;
import org.haw.is.praktikum4.constraints.solver.solve.AC3ForwardChecking;
import org.haw.is.praktikum4.constraints.solver.solve.ConstraintSolveAlgorithm;

public class LoggingConstraintSolver extends ConstraintSolver {
	public LoggingConstraintSolver() {
		this(new NodeConsistency(), new ArcConsistency3(), new AC3ForwardChecking());
	}
	
	public LoggingConstraintSolver(NodeConsistencyAlgorithm nc, ArcConsistencyAlgorithm ac, ConstraintSolveAlgorithm sa) {
		super(
				new LoggingNodeConsistencyAlgorithm(nc),
				new LoggingArcConsistencyAlgorithm(ac),
				sa
		);
	}
}

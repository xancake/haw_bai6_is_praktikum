package org.haw.is.praktikum4;

import org.haw.is.praktikum4.constraints.problem.EinsteinProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;
import org.haw.is.praktikum4.constraints.solver.analysis.AnalysisResultList;
import org.haw.is.praktikum4.constraints.solver.analysis.ConstraintSolverAnalyzer;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistency1;
import org.haw.is.praktikum4.constraints.solver.consistency.arc.ArcConsistency3;
import org.haw.is.praktikum4.constraints.solver.consistency.node.NodeConsistency;
import org.haw.is.praktikum4.constraints.solver.solve.AC3BackTracking;
import org.haw.is.praktikum4.constraints.solver.solve.AC3ForwardChecking;
import org.haw.is.praktikum4.constraints.solver.solve.AC3LookAhead;

public class DameProblemComparisonMain {
	public static void main(String[] args) {
		EinsteinProblem problem = new EinsteinProblem();
		
		ConstraintSolverAnalyzer analyzer = new ConstraintSolverAnalyzer(problem);
		AnalysisResultList results = analyzer.analyze(
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency1(), new AC3BackTracking()),
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency1(), new AC3ForwardChecking()),
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency1(), new AC3LookAhead()),
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency3(), new AC3BackTracking()),
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency3(), new AC3ForwardChecking()),
				new ConstraintSolver(new NodeConsistency(), new ArcConsistency3(), new AC3LookAhead())
		);
		results.printDuration();
		results.printSolution();
	}
}

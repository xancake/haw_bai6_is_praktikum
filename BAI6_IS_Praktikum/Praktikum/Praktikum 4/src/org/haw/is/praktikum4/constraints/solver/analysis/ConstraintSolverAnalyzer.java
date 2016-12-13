package org.haw.is.praktikum4.constraints.solver.analysis;

import java.util.Objects;
import org.haw.is.praktikum4.constraints.problem.ConstraintProblem;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class ConstraintSolverAnalyzer extends ConstraintSolverAnalyzer_A {
	private ConstraintProblem _problem;
	
	public ConstraintSolverAnalyzer(ConstraintProblem problem) {
		_problem = Objects.requireNonNull(problem);
	}
	
	public AnalysisResultList analyze(ConstraintSolver... solvers) {
		return analyzeSolvers(_problem, solvers);
	}
}

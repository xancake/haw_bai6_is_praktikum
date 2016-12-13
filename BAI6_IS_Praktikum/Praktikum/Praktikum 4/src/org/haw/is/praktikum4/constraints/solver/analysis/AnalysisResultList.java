package org.haw.is.praktikum4.constraints.solver.analysis;

import java.util.LinkedList;
import java.util.List;

public class AnalysisResultList {
	public List<AnalysisResult> results;
	
	AnalysisResultList() {
		results = new LinkedList<>();
	}
	
	void add(AnalysisResult result) {
		results.add(result);
	}
	
	public void print() {
		for(AnalysisResult result : results) {
			result.print();
		}
	}
	
	public void printSolution() {
		for(AnalysisResult result : results) {
			result.printSolution();
		}
	}
	
	public void printDuration() {
		for(AnalysisResult result : results) {
			result.printDuration();
		}
	}
}
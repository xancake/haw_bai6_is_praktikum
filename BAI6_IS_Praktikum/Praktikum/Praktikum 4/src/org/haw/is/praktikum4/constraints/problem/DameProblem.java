package org.haw.is.praktikum4.constraints.problem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.BinaryCompareFunction;

/**
 * Diese Klasse modelliert das Dame-Problem als {@link ConstraintNet Constraint Netz}.
 */
public class DameProblem implements ConstraintProblem {
	private int _dameCount;
	private boolean _generateMultipleConstraints;
	
	public DameProblem() {
		_dameCount = 1;
	}
	
	/**
	 * Legt fest, für welche Größe das Dameproblem modelliert werden soll.
	 * <p>Das Dameproblem lässt sich für 2 und 3 Damen nicht lösen. Dieser Builder liefert wenn gewünscht dennoch ein
	 * {@link ConstraintNet Constraint Netz}, das diesen Fall entsprechend modelliert.
	 * @param damen Die Anzahl der Damen (und damit gleichzeitig der Feldgröße)
	 */
	public void setDamen(int damen) {
		if(damen <= 0) {
			throw new IllegalArgumentException("Die Anzahl der Damen muss größer als 0 sein!");
		}
		if(damen == 2 || damen == 3) {
			System.out.println("Das Dameproblem lässt sich für 2 oder 3 Damen nicht lösen!");
		}
		_dameCount = damen;
	}
	
	/**
	 * Legt fest, ob dieser Builder ein {@link ConstraintNet Constraint Netz} erzeugen soll, das mehrfache Constraints
	 * zwischen den Variablen modelliert, oder alle Abhängigkeiten zwischen zwei Variablen in einem Constraint
	 * zusammenfasst.
	 * @param multiple {@code true} wenn mehrfache Constraints erlaubt sind, ansonsten {@code false}
	 */
	public void setGenerateMultipleConstraints(boolean multiple) {
		_generateMultipleConstraints = multiple;
	}
	
	@Override
	public ConstraintNet createConstraintNet() {
		ConstraintNet net = new ConstraintNet();
		createVariables(net);
		if(_generateMultipleConstraints) {
			createMultipleConstraints(net);
		} else {
			createSingleConstraints(net);
		}
		return net;
	}
	
	private void createVariables(ConstraintNet net) {
		Collection<Integer> wertebereich = new ArrayList<>();
		for(int i=1; i<=_dameCount; i++) {
			wertebereich.add(i);
		}
		for(int i=0; i<_dameCount; i++) {
			net.addVariable(new Variable("Spalte " + (i+1), wertebereich));
		}
	}
	
	private void createSingleConstraints(ConstraintNet net) {
		List<BinaryCompareFunction> dif = new ArrayList<>();
		for(int i=1; i<_dameCount; i++) {
			int x = i;
			dif.add((l,r) -> l != r && l+x != r && l-x != r);
		}
		
		for(int i=0; i<_dameCount; i++) {
			for(int j=i+1; j<_dameCount; j++) {
				Variable vi = net.getVariables().get(i);
				Variable vj = net.getVariables().get(j);
				BinaryCompareFunction function = dif.get(j-i-1);
				net.addConstraint(new BinaryConstraint("(" + vi.getName() + ")[+/-" + (j-i) + "]!=(" + vj.getName() + ")", vi, vj, function));
			}
		}
	}
	
	private void createMultipleConstraints(ConstraintNet net) {
		List<BinaryCompareFunction> dif = new ArrayList<>();
		for(int i=1; i<_dameCount; i++) {
			int x = i;
			dif.add((l,r) -> l+x != r && l-x != r);
		}
		
		for(int i=0; i<_dameCount; i++) {
			for(int j=i+1; j<_dameCount; j++) {
				Variable vi = net.getVariables().get(i);
				Variable vj = net.getVariables().get(j);
				BinaryCompareFunction function = dif.get(j-i-1);
				net.addConstraint(new BinaryConstraint("(" + vi.getName() + ")+/-" + (j-i) + "!=(" + vj.getName() + ")", vi, vj, function));
			}
		}
		net.addConstraint(new AllUniqueConstraint(net.getVariables()));
	}
}

package org.haw.is.praktikum4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.Function;

public class EinsteinRaetsel {
	private static final Set<Integer> WERTEBEREICH = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1,2,3,4,5)));
	
	public static void main(String[] args) {
		Variable Katze = new Variable("Katze", copy(WERTEBEREICH));
		Variable Hund  = new Variable("Hund",  copy(WERTEBEREICH));
		Variable Vogel = new Variable("Vogel", copy(WERTEBEREICH));
		Variable Pferd = new Variable("Pferd", copy(WERTEBEREICH));
		Variable Fisch = new Variable("Fisch", copy(WERTEBEREICH));
		List<Variable> tiere = Arrays.asList(Katze, Hund, Vogel, Pferd, Fisch);
		
		Variable Brite     = new Variable("Brite",     copy(WERTEBEREICH));
		Variable Schwede   = new Variable("Schwede",   copy(WERTEBEREICH));
		Variable Norweger  = new Variable("Norweger",  copy(WERTEBEREICH));
		Variable Daene     = new Variable("Däne",      copy(WERTEBEREICH));
		Variable Deutscher = new Variable("Deutscher", copy(WERTEBEREICH));
		List<Variable> personen = Arrays.asList(Brite, Schwede, Norweger, Daene, Deutscher);
		
		Variable Tee    = new Variable("Tee",    copy(WERTEBEREICH));
		Variable Kaffee = new Variable("Kaffee", copy(WERTEBEREICH));
		Variable Milch  = new Variable("Milch",  copy(WERTEBEREICH));
		Variable Bier   = new Variable("Bier",   copy(WERTEBEREICH));
		Variable Wasser = new Variable("Wasser", copy(WERTEBEREICH));
		List<Variable> getraenke = Arrays.asList(Tee, Kaffee, Milch, Bier, Wasser);
		
		Variable rot   = new Variable("rot",  copy(WERTEBEREICH));
		Variable gruen = new Variable("grün", copy(WERTEBEREICH));
		Variable weiss = new Variable("weiß", copy(WERTEBEREICH));
		Variable gelb  = new Variable("gelb", copy(WERTEBEREICH));
		Variable blau  = new Variable("blau", copy(WERTEBEREICH));
		List<Variable> farben = Arrays.asList(rot, gruen, weiss, gelb, blau);
		
		Variable PallMall  = new Variable("Pall Mall", copy(WERTEBEREICH));
		Variable Dunhill   = new Variable("Dunhill",   copy(WERTEBEREICH));
		Variable Malboro   = new Variable("Malboro",   copy(WERTEBEREICH));
		Variable Winfield  = new Variable("Winfield",  copy(WERTEBEREICH));
		Variable Rothmanns = new Variable("Rothmanns", copy(WERTEBEREICH));
		List<Variable> zigaretten = Arrays.asList(PallMall, Dunhill, Malboro, Winfield, Rothmanns);
		
		Variable erstesHaus    = new Variable("Erstes Haus", new HashSet<>(Arrays.asList(1)));
		Variable mittleresHaus = new Variable("Mittleres Haus", new HashSet<>(Arrays.asList(3)));
		
		Function neben       = (l,r) -> l+1==r || l-1==r;
		Function links_neben = (l,r) -> l+1==r;
		Function gleich      = (l,r) -> l==r;
		
		List<BinaryConstraint> constraints = Arrays.asList(
				new BinaryConstraint("Der Brite lebt im roten Haus", Brite, rot, gleich),
				new BinaryConstraint("Der Schwede hält sich einen Hund", Schwede, Hund, gleich),
				new BinaryConstraint("Der Däne trinkt gern Tee", Daene, Tee, gleich),
				new BinaryConstraint("Das grüne Haus steht links neben dem weißen Haus", gruen, weiss, links_neben),
				new BinaryConstraint("Der Besitzer des grünen Hauses trinkt Kaffee", gruen, Kaffee, gleich),
				new BinaryConstraint("Die Person, die Pall Mall raucht, hat einen Vogel", PallMall, Vogel, gleich),
				new BinaryConstraint("Der Mann im mittleren Haus trinkt Milch", mittleresHaus, Milch, gleich),
				new BinaryConstraint("Der Bewohner des gelben Hauses raucht Dunhill", gelb, Dunhill, gleich),
				new BinaryConstraint("Der Norweger lebt im ersten Haus", Norweger, erstesHaus, gleich),
				new BinaryConstraint("Der Malboro-Raucher wohnt neben der Person mit der Katze", Malboro, Katze, neben),
				new BinaryConstraint("Der Mann mit dem Pferd lebt neben der Person, die Dunhill raucht", Pferd, Dunhill, neben),
				new BinaryConstraint("Der Winfield-Raucher trinkt gern Bier", Winfield, Bier, gleich),
				new BinaryConstraint("Der Norweger wohnt neben dem blauen Haus", Norweger, blau, neben),
				new BinaryConstraint("Der Deutsche raucht Rothmanns", Deutscher, Rothmanns, gleich),
				new BinaryConstraint("Der Malboro-Raucher hat einen Nachbarn, der Wasser trinkt", Malboro, Wasser, neben)
		);
		constraints.addAll(new AllUniqueConstraint(tiere).generateBinaryConstraints());
		constraints.addAll(new AllUniqueConstraint(personen).generateBinaryConstraints());
		constraints.addAll(new AllUniqueConstraint(getraenke).generateBinaryConstraints());
		constraints.addAll(new AllUniqueConstraint(farben).generateBinaryConstraints());
		constraints.addAll(new AllUniqueConstraint(zigaretten).generateBinaryConstraints());
		
		List<Variable> allVariables = new ArrayList<>();
		allVariables.addAll(tiere);
		allVariables.addAll(personen);
		allVariables.addAll(getraenke);
		allVariables.addAll(farben);
		allVariables.addAll(zigaretten);
		
		ConstraintNet netz = new ConstraintNet(allVariables, constraints);
		
		
		
//		ConstraintSolver solver = new ConstraintSolver();
//		Ergebnis ergebnis = solver.solve(netz);
	}
	
	private static Set<Integer> copy(Set<Integer> set) {
		return new HashSet<>(set);
	}
}

package org.haw.is.praktikum4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.haw.is.praktikum4.constraints.ConstraintNet;
import org.haw.is.praktikum4.constraints.Variable;
import org.haw.is.praktikum4.constraints.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.BinaryConstraint.BinaryCompareFunction;
import org.haw.is.praktikum4.constraints.constraint.UnaryConstraint;
import org.haw.is.praktikum4.constraints.constraint.UnaryConstraint.UnaryCompareFunction;
import org.haw.is.praktikum4.constraints.solver.ConstraintSolver;

public class EinsteinRaetsel {
	private static final Set<Integer> WERTEBEREICH = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1,2,3,4,5)));
	
	public static void main(String[] args) {
		ConstraintNet netz = new ConstraintNet();
		
		
		Variable Katze = new Variable("Katze", WERTEBEREICH);
		Variable Hund  = new Variable("Hund",  WERTEBEREICH);
		Variable Vogel = new Variable("Vogel", WERTEBEREICH);
		Variable Pferd = new Variable("Pferd", WERTEBEREICH);
		Variable Fisch = new Variable("Fisch", WERTEBEREICH);
		List<Variable> tiere = Arrays.asList(Katze, Hund, Vogel, Pferd, Fisch);
		netz.addAllVariables(tiere);
		
		Variable Brite     = new Variable("Brite",     WERTEBEREICH);
		Variable Schwede   = new Variable("Schwede",   WERTEBEREICH);
		Variable Norweger  = new Variable("Norweger",  WERTEBEREICH);
		Variable Daene     = new Variable("Däne",      WERTEBEREICH);
		Variable Deutscher = new Variable("Deutscher", WERTEBEREICH);
		List<Variable> personen = Arrays.asList(Brite, Schwede, Norweger, Daene, Deutscher);
		netz.addAllVariables(personen);
		
		Variable Tee    = new Variable("Tee",    WERTEBEREICH);
		Variable Kaffee = new Variable("Kaffee", WERTEBEREICH);
		Variable Milch  = new Variable("Milch",  WERTEBEREICH);
		Variable Bier   = new Variable("Bier",   WERTEBEREICH);
		Variable Wasser = new Variable("Wasser", WERTEBEREICH);
		List<Variable> getraenke = Arrays.asList(Tee, Kaffee, Milch, Bier, Wasser);
		netz.addAllVariables(getraenke);
		
		Variable rot   = new Variable("rot",  WERTEBEREICH);
		Variable gruen = new Variable("grün", WERTEBEREICH);
		Variable weiss = new Variable("weiß", WERTEBEREICH);
		Variable gelb  = new Variable("gelb", WERTEBEREICH);
		Variable blau  = new Variable("blau", WERTEBEREICH);
		List<Variable> farben = Arrays.asList(rot, gruen, weiss, gelb, blau);
		netz.addAllVariables(farben);
		
		Variable PallMall  = new Variable("Pall Mall", WERTEBEREICH);
		Variable Dunhill   = new Variable("Dunhill",   WERTEBEREICH);
		Variable Malboro   = new Variable("Malboro",   WERTEBEREICH);
		Variable Winfield  = new Variable("Winfield",  WERTEBEREICH);
		Variable Rothmanns = new Variable("Rothmanns", WERTEBEREICH);
		List<Variable> zigaretten = Arrays.asList(PallMall, Dunhill, Malboro, Winfield, Rothmanns);
		netz.addAllVariables(zigaretten);
		
		BinaryCompareFunction gleich      = BinaryCompareFunction.eq();
		BinaryCompareFunction neben       = (l,r) -> l+1==r || l-1==r;
		BinaryCompareFunction links_neben = (l,r) -> l+1==r;
		
		netz.addConstraint(new BinaryConstraint("Der Brite lebt im roten Haus", Brite, rot, gleich));
		netz.addConstraint(new BinaryConstraint("Der Schwede hält sich einen Hund", Schwede, Hund, gleich));
		netz.addConstraint(new BinaryConstraint("Der Däne trinkt gern Tee", Daene, Tee, gleich));
		netz.addConstraint(new BinaryConstraint("Das grüne Haus steht links neben dem weißen Haus", gruen, weiss, links_neben));
		netz.addConstraint(new BinaryConstraint("Der Besitzer des grünen Hauses trinkt Kaffee", gruen, Kaffee, gleich));
		netz.addConstraint(new BinaryConstraint("Die Person, die Pall Mall raucht, hat einen Vogel", PallMall, Vogel, gleich));
		netz.addConstraint(new UnaryConstraint("Der Mann im mittleren Haus trinkt Milch", Milch, UnaryCompareFunction.eq(3)));
		netz.addConstraint(new BinaryConstraint("Der Bewohner des gelben Hauses raucht Dunhill", gelb, Dunhill, gleich));
		netz.addConstraint(new UnaryConstraint("Der Norweger lebt im ersten Haus", Norweger, UnaryCompareFunction.eq(1)));
		netz.addConstraint(new BinaryConstraint("Der Malboro-Raucher wohnt neben der Person mit der Katze", Malboro, Katze, neben));
		netz.addConstraint(new BinaryConstraint("Der Mann mit dem Pferd lebt neben der Person, die Dunhill raucht", Pferd, Dunhill, neben));
		netz.addConstraint(new BinaryConstraint("Der Winfield-Raucher trinkt gern Bier", Winfield, Bier, gleich));
		netz.addConstraint(new BinaryConstraint("Der Norweger wohnt neben dem blauen Haus", Norweger, blau, neben));
		netz.addConstraint(new BinaryConstraint("Der Deutsche raucht Rothmanns", Deutscher, Rothmanns, gleich));
		netz.addConstraint(new BinaryConstraint("Der Malboro-Raucher hat einen Nachbarn, der Wasser trinkt", Malboro, Wasser, neben));
		netz.addConstraint(new AllUniqueConstraint(tiere));
		netz.addConstraint(new AllUniqueConstraint(personen));
		netz.addConstraint(new AllUniqueConstraint(getraenke));
		netz.addConstraint(new AllUniqueConstraint(farben));
		netz.addConstraint(new AllUniqueConstraint(zigaretten));
		
		ConstraintSolver solver = new ConstraintSolver();
		solver.solve(netz);
		
		for(Variable v : netz.getVariables()) {
			System.out.println(v.getName() + " = " + v.getWertebereich());
		}
	}
}

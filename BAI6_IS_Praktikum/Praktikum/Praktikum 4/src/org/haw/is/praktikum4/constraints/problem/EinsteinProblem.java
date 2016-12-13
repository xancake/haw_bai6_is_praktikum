package org.haw.is.praktikum4.constraints.problem;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.haw.is.praktikum4.constraints.net.ConstraintNet;
import org.haw.is.praktikum4.constraints.net.Variable;
import org.haw.is.praktikum4.constraints.net.constraint.AllUniqueConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.UnaryConstraint;
import org.haw.is.praktikum4.constraints.net.constraint.BinaryConstraint.BinaryCompareFunction;
import org.haw.is.praktikum4.constraints.net.constraint.UnaryConstraint.UnaryCompareFunction;

/**
 * Diese Klasse modelliert das Einstein-Problem als {@link ConstraintNet Constraint Netz}.
 */
public class EinsteinProblem implements ConstraintProblem {
	private static final Collection<Integer> WERTEBEREICH = Arrays.asList(1,2,3,4,5);
	
	@Override
	public ConstraintNet createConstraintNet() {
		ConstraintNet net = new ConstraintNet();
		
		Variable Katze = new Variable("Katze", WERTEBEREICH);
		Variable Hund  = new Variable("Hund",  WERTEBEREICH);
		Variable Vogel = new Variable("Vogel", WERTEBEREICH);
		Variable Pferd = new Variable("Pferd", WERTEBEREICH);
		Variable Fisch = new Variable("Fisch", WERTEBEREICH);
		List<Variable> tiere = Arrays.asList(Katze, Hund, Vogel, Pferd, Fisch);
		net.addAllVariables(tiere);
		
		Variable Brite     = new Variable("Brite",     WERTEBEREICH);
		Variable Schwede   = new Variable("Schwede",   WERTEBEREICH);
		Variable Norweger  = new Variable("Norweger",  WERTEBEREICH);
		Variable Daene     = new Variable("Däne",      WERTEBEREICH);
		Variable Deutscher = new Variable("Deutscher", WERTEBEREICH);
		List<Variable> personen = Arrays.asList(Brite, Schwede, Norweger, Daene, Deutscher);
		net.addAllVariables(personen);
		
		Variable Tee    = new Variable("Tee",    WERTEBEREICH);
		Variable Kaffee = new Variable("Kaffee", WERTEBEREICH);
		Variable Milch  = new Variable("Milch",  WERTEBEREICH);
		Variable Bier   = new Variable("Bier",   WERTEBEREICH);
		Variable Wasser = new Variable("Wasser", WERTEBEREICH);
		List<Variable> getraenke = Arrays.asList(Tee, Kaffee, Milch, Bier, Wasser);
		net.addAllVariables(getraenke);
		
		Variable rot   = new Variable("rot",  WERTEBEREICH);
		Variable gruen = new Variable("grün", WERTEBEREICH);
		Variable weiss = new Variable("weiß", WERTEBEREICH);
		Variable gelb  = new Variable("gelb", WERTEBEREICH);
		Variable blau  = new Variable("blau", WERTEBEREICH);
		List<Variable> farben = Arrays.asList(rot, gruen, weiss, gelb, blau);
		net.addAllVariables(farben);
		
		Variable PallMall  = new Variable("Pall Mall", WERTEBEREICH);
		Variable Dunhill   = new Variable("Dunhill",   WERTEBEREICH);
		Variable Malboro   = new Variable("Malboro",   WERTEBEREICH);
		Variable Winfield  = new Variable("Winfield",  WERTEBEREICH);
		Variable Rothmanns = new Variable("Rothmanns", WERTEBEREICH);
		List<Variable> zigaretten = Arrays.asList(PallMall, Dunhill, Malboro, Winfield, Rothmanns);
		net.addAllVariables(zigaretten);
		
		BinaryCompareFunction gleich      = BinaryCompareFunction.eq();
		BinaryCompareFunction neben       = (l,r) -> l+1==r || l-1==r;
		BinaryCompareFunction links_neben = (l,r) -> l+1==r;
		
		net.addConstraint(new BinaryConstraint("Der Brite lebt im roten Haus", Brite, rot, gleich));
		net.addConstraint(new BinaryConstraint("Der Schwede hält sich einen Hund", Schwede, Hund, gleich));
		net.addConstraint(new BinaryConstraint("Der Däne trinkt gern Tee", Daene, Tee, gleich));
		net.addConstraint(new BinaryConstraint("Das grüne Haus steht links neben dem weißen Haus", gruen, weiss, links_neben));
		net.addConstraint(new BinaryConstraint("Der Besitzer des grünen Hauses trinkt Kaffee", gruen, Kaffee, gleich));
		net.addConstraint(new BinaryConstraint("Die Person, die Pall Mall raucht, hat einen Vogel", PallMall, Vogel, gleich));
		net.addConstraint(new UnaryConstraint("Der Mann im mittleren Haus trinkt Milch", Milch, UnaryCompareFunction.eq(3)));
		net.addConstraint(new BinaryConstraint("Der Bewohner des gelben Hauses raucht Dunhill", gelb, Dunhill, gleich));
		net.addConstraint(new UnaryConstraint("Der Norweger lebt im ersten Haus", Norweger, UnaryCompareFunction.eq(1)));
		net.addConstraint(new BinaryConstraint("Der Malboro-Raucher wohnt neben der Person mit der Katze", Malboro, Katze, neben));
		net.addConstraint(new BinaryConstraint("Der Mann mit dem Pferd lebt neben der Person, die Dunhill raucht", Pferd, Dunhill, neben));
		net.addConstraint(new BinaryConstraint("Der Winfield-Raucher trinkt gern Bier", Winfield, Bier, gleich));
		net.addConstraint(new BinaryConstraint("Der Norweger wohnt neben dem blauen Haus", Norweger, blau, neben));
		net.addConstraint(new BinaryConstraint("Der Deutsche raucht Rothmanns", Deutscher, Rothmanns, gleich));
		net.addConstraint(new BinaryConstraint("Der Malboro-Raucher hat einen Nachbarn, der Wasser trinkt", Malboro, Wasser, neben));
		net.addConstraint(new AllUniqueConstraint(tiere));
		net.addConstraint(new AllUniqueConstraint(personen));
		net.addConstraint(new AllUniqueConstraint(getraenke));
		net.addConstraint(new AllUniqueConstraint(farben));
		net.addConstraint(new AllUniqueConstraint(zigaretten));
		
		return net;
	}
}

mann(adam).
mann(harald).
mann(horst).
mann(abel).
mann(kain).
mann(anton).
mann(bernhard).
mann(bernd).

frau(eva).
frau(luise).
frau(barbel).
frau(saskia).
frau(ingelore).

kind(harald, adam).
kind(harald, eva).
kind(luise, adam).
kind(luise, eva).
kind(barbel, adam).
kind(barbel, eva).
kind(abel, harald).
kind(abel, luise).
kind(kain, harald).
kind(kain, luise).
kind(anton, barbel).
kind(anton, horst).
kind(bernhard, kain).
kind(bernhard, saskia).
kind(bernd, saskia).
kind(bernd, anton).
kind(ingelore, harald).
kind(ingelore, luise).

verheiratet(adam, eva).
verheiratet(saskia, kain).

verheiratet(X, Y) :- verheiratet(Y, X).

sohn(Sohn, Ich) :- mann(Sohn), kind(Sohn, Ich).
tochter(Tochter, Ich) :- frau(Tochter), kind(Tochter, Ich).

elter(Elter, Ich) :- kind(Ich, Elter).
mutter(Mutter, Ich) :- frau(Mutter), elter(Mutter, Ich).
vater(Vater, Ich) :- mann(Vater), elter(Vater, Ich).

geschwister(X, Ich) :- vater(V, X), mutter(M, X), vater(V, Ich), mutter(M, Ich), X\=Ich. %% X\=Ich muss am Ende stehen, da Ich vor kind(Ich, Vater) nicht belegt bin
bruder(Bruder, Ich) :- mann(Bruder), geschwister(Bruder, Ich).
schwester(Schwester, Ich) :- frau(Schwester), geschwister(Schwester, Ich).

halbgeschwister(X, Ich) :- vater(V, X), mutter(M1, X), vater(V, Ich), mutter(M2, Ich), X\=Ich, M1\=M2.
halbgeschwister(X, Ich) :- mutter(M, X), vater(V1, X), mutter(M, Ich), vater(V2, Ich), X\=Ich, V1\=V2.
halbbruder(HBruder, Ich) :- mann(HBruder), halbgeschwister(HBruder, Ich).
halbschwester(HSchwester, Ich) :- frau(HSchwester), halbgeschwister(HSchwester, Ich).

tante(Tante, Ich) :- elter(Elter, Ich), schwester(Tante, Elter), not(elter(Tante, Ich)).
onkel(Onkel, Ich) :- elter(Elter, Ich), bruder(Onkel, Elter), not(elter(Onkel, Ich)).
neffe(Neffe, Ich) :- geschwister(Geschwister, Ich), sohn(Neffe, Geschwister).
nichte(Nichte, Ich) :- geschwister(Geschwister, Ich), tochter(Nichte, Geschwister).
cousin(Cousin, Ich) :- onkel(Onkel, Ich), sohn(Cousin, Onkel) ; tante(Tante, Ich), sohn(Cousin, Tante).
cousine(Cousine, Ich) :- onkel(Onkel, Ich), tochter(Cousine, Onkel) ; tante(Tante, Ich), tochter(Cousine, Tante).

grosselter(GElter, Ich) :- elter(Elter, Ich), elter(GElter, Elter).
oma(Oma, Ich) :- frau(Oma), grosselter(Oma, Ich).
opa(Opa, Ich) :- mann(Opa), grosselter(Opa, Ich).

grosstante(GTante, Ich) :- elter(Elter, Ich), tante(GTante, Elter).
grossonkel(GOnkel, Ich) :- elter(Elter, Ich), onkel(GOnkel, Elter).

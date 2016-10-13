:- consult('../Praktikum 1/stammbaum').
:- consult(readsentence).

solve(Satz) :-
        s(Term, Satz, []),
        write(Term), nl,
        call(Term).

%% Satz
s(Sem) --> vp(SemVP, Num), np(SemNP, Num), {SemNP=[_,SemVP,_|_], Sem=..SemNP}.

%% Nominalphrasen
np(SemEN, Num) --> en(SemEN, Num).
np(SemN, Num)  --> art(_SemArt, Num), n(SemN, Num).
np(Sem, Num)   --> art(_SemArt, Num), n(SemN, Num), pp(SemPP, Num), {append([SemN], SemPP, Sem)}.
np(Sem, Num)   --> pp(SemPP, Num), np(SemNP, Num), {SemPP=[_|Rest], append([SemNP], Rest, Sem)}.

%% Präpositionalphrasen
pp([_,SemNP], Num) --> p(_SemP, Num), np(SemNP, Num).
%%pp --> p, ?.

%% Verbalphrasen
vp(_,Num)      --> v(_SemV, Num).
vp(SemNP, Num) --> v(_SemV, Num), np(SemNP, _Num).
vp(SemIP, Num) --> ip(SemIP, _Num), v(_SemV, Num).

%% Terminal-Symbole
en(Sem, Num)  --> [X], {lex(X,  en, Num, Sem)}. %% Eigenname
art(Sem, Num) --> [X], {lex(X, art, Num, Sem)}. %% Artikel
n(Sem, Num)   --> [X], {lex(X,   n, Num, Sem)}. %% Nomen
p(Sem, Num)   --> [X], {lex(X,   p, Num, Sem)}. %% Präposition
v(Sem, Num)   --> [X], {lex(X,   v, Num, Sem)}. %% Verb
ip(Sem, Num)  --> [X], {lex(X,  ip, Num, Sem)}. %% Interrogativpronomen

%% Lexikon
lex(ist,   v, singular, ist).
lex(der, art, _, artikel).
lex(die, art, _, artikel).
lex(das, art, _, artikel).
lex(von,   p, _, von).
lex(vom,   p, _, von).
lex(wer,  ip, singular, _Wer).
lex(wessen,  ip, singular, _Wer).

lex(adam,     en, singular, adam).
lex(harald,   en, singular, harald).
lex(horst,    en, singular, horst).
lex(abel,     en, singular, abel).
lex(kain,     en, singular, kain).
lex(anton,    en, singular, anton).
lex(bernhard, en, singular, bernhard).
lex(bernd,    en, singular, bernd).
lex(eva,      en, singular, eva).
lex(luise,    en, singular, luise).
lex(barbel,   en, singular, barbel).
lex(saskia,   en, singular, saskia).
lex(ingelore, en, singular, ingelore).

lex(verheiratet, n, singular, verheiratet).
lex(kind, n, singular, kind).
lex(kinder, n, plural, kind).
lex(sohn,   n, singular, sohn).
lex(tochter, n, singular, tochter).
lex(elter,  n, singular, elter).
lex(eltern, n, plural, elter).
lex(vater,  n, singular, vater).
lex(mutter, n, singular, mutter).
lex(geschwister, n, singular, geschwister).
lex(bruder, n, singular, bruder).
lex(schwester, n, singular, schwester).
lex(halbgeschwister, n, singular, halbgeschwister).
lex(halbbruder, n, singular, halbbruder).
lex(halbschwester, n, singular, halbschwester).
lex(tante, n, singular, tante).
lex(onkel, n, singular, onkel).
lex(neffe, n, singular, neffe).
lex(nichte, n, singular, nichte).
lex(cousin, n, singular, cousin).
lex(cousine, n, singular, cousine).
lex(grosselter, n, singular, grosselter).
lex(grosseltern, n, plural, grosselter).
lex(oma, n, singular, grosselter).
lex(opa, n, singular, grosselter).
lex(grosstante, n, singular, grosstante).
lex(grossonkel, n, singular, grossonkel).
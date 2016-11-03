:- consult(lexicon).

%% Satz
frage(SemNP)   --> v(_, Num), np(SemEN, _, Sex, _), np(SemNP, Num, Sex, nominativ), {SemNP=[_,SemEN,_|_]}.
frage(SemVP)   --> np(SemNP, Num, Sex, _), vp(SemVP, Num, Sex), {SemVP=[_,SemNP,_|_]}.
antwort(SemVP) --> {SemVP=[_,SemNP,_|_]}, np(SemNP, Num, Sex, _), vp(SemVP, Num, Sex).

%% Nominalphrasen
np(SemEN, Num, Sex, _)     --> en(SemEN, Num, Sex).
np(SemN,  Num, Sex, Fall)  --> art(_, Num, Sex, Fall), n(SemN, Num, Sex, Fall).
np(Sem,   Num, SexN, Fall) --> art(_, Num, SexN, Fall), n(SemN, Num, SexN, Fall), {append([SemN], SemPP, Sem)}, pp(SemPP, _, _).  % Das append muss vor dem pp, damit wir nicht endlos in die Tiefe laufen (weil pp -> np -> pp -> np -> ...)
np(SemIP, Num, _, _)       --> ip(SemIP, Num).
np(Sem,   Num, _, _)       --> en(SemEN1, _, _), k(_, Num), en(SemEN2, _, _), {Sem=[SemEN1, SemEN2]}.

%% Präpositionalphrasen
pp([_,SemNP], Num, Sex) --> p(_SemP, Num), np(SemNP, Num, Sex, dativ).

%% Verbalphrasen
vp(SemNP, Num, Sex) --> v(_SemV, Num), np(SemNP, Num, Sex, nominativ).
vp(SemIP, Num, _)   --> ip(SemIP, _Num), v(_SemV, Num).

%% Terminal-Symbole
 en(Sem, Num, Sex)       --> [X], {lex(X,  en, Num, Sex, Sem)}. %% Eigenname
art(Sem, Num, Sex, Fall) --> [X], {lex(X, art, Num, Sex, Fall, Sem)}. %% Artikel
  n(Sem, Num, Sex, Fall) --> [X], {lex(X,   n, Num, Sex, Fall, Sem)}. %% Nomen
  p(Sem, Num)            --> [X], {lex(X,   p, Num, Sem)}. %% Präposition
  v(Sem, Num)            --> [X], {lex(X,   v, Num, Sem)}. %% Verb
 ip(Sem, Num)            --> [X], {lex(X,  ip, Num, Sem)}. %% Interrogativpronomen
  k(Sem, Num)            --> [X], {lex(X,   k, Num, Sem)}. %% Konjunktion

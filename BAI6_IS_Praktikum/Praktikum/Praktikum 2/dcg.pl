:- consult(lexicon).

%% Fragen
frage(Sem, Num) --> entscheidungsfrage(Sem, Num).
frage(Sem, Num) --> ergaenzungsfrage(Sem, Num).
frage(Sem, Num) --> bestaetigungsfrage(Sem, Num).

entscheidungsfrage(SemNP2, Num) --> v(_, Num), np(SemNP1, _, Sex, _), np(SemNP2, Num, Sex, nominativ), {SemNP2=[_,SemNP1,_|_]}.
entscheidungsfrage(SemNP1, Num) --> v(_, Num), np(SemNP1, _, Sex, _), np(SemNP2, Num, Sex, nominativ), {SemNP1=[_,SemNP2,_|_]}.
ergaenzungsfrage(SemVP, Num)   --> ip(SemIP, _), vp(SemVP, Num, _), {SemVP=[_,SemIP,_|_]}.
bestaetigungsfrage(SemVP, Num) --> en(SemEN, Num, Sex), vp(SemVP, Num, Sex), {SemVP=[_,SemEN,_|_]}.

%% Antwort
antwort(SemVP, Num) --> {SemVP=[_,SemNP,_|_]}, np(SemNP, Num, Sex, _), vp(SemVP, Num, Sex).

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

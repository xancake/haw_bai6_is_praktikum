:- consult(lexicon).

%% Sätze
frage(Sem, Num) --> vp(Sem, Num, _).
antwort(SemNP2, Num) --> {SemNP2=[_,SemNP1,_|_]}, np(SemNP1, Num, Sex, _), v(_, Num), np(SemNP2, Num, Sex, _).
antwort(Sem,    Num) --> {append([SemN], SemNP, Sem)}, np(SemNP, Num, Sex, nominativ), v(_, Num), n(SemN, Num, Sex, nominativ).

%% Verbalphrasen
vp(SemNP2, Num, Sex) --> v(_, Num), np(SemNP1, Num, Sex, nominativ), np(SemNP2, Num, Sex, nominativ), {SemNP2=[_,SemNP1,_|_]}. % Entscheidungsfrage: ist X der Onkel von Y?
vp(SemNP1, Num, Sex) --> v(_, Num), np(SemNP1, Num, Sex, nominativ), np(SemNP2, Num, Sex, nominativ), {SemNP1=[_,SemNP2,_|_]}. % Entscheidungsfrage: ist der Onkel von Y X?
vp(Sem,    Num, Sex) --> v(_, Num), np(SemNP, Num, Sex, nominativ), np(SemN, Num, Sex, nominativ), {append([SemN], SemNP, Sem)}. % Entscheidungsfrage: sind X und Y geschwister?
vp(SemNP2, Num, Sex) --> np(SemNP1, Num, Sex, nominativ), v(_, Num), np(SemNP2, Num, Sex, nominativ), {SemNP2=[_,SemNP1,_|_]}. % Bestätigungsfrage: X ist der Onkel von Y?
vp(SemNP1, Num, Sex) --> np(SemNP1, Num, Sex, nominativ), v(_, Num), np(SemNP2, Num, Sex, nominativ), {SemNP1=[_,SemNP2,_|_]}. % Bestätigungsfrage: der Onkel von Y ist X?
vp(SemNP,  Num, Sex) --> ip(SemIP, Num), v(_, Num), np(SemNP, Num, Sex, nominativ), {SemNP=[_,SemIP,_|_]}.                     % Ergänzungsfrage: wer ist der onkel von X?

%% Nominalphrasen
np(SemEN, Num, Sex,  _)    --> en(SemEN, Num, Sex).
np(SemN,  Num, Sex,  _)    --> n(SemN, Num, Sex, _).
np(SemN,  Num, Sex,  Fall) --> art(_, Num, Sex, Fall), n(SemN, Num, Sex, Fall).
np(Sem,   Num, SexN, Fall) --> art(_, Num, SexN, Fall), n(SemN, Num, SexN, Fall), {append([SemN], SemPP, Sem)}, pp(SemPP, _, _).  % Das append muss vor dem pp, damit wir nicht endlos in die Tiefe laufen (weil pp -> np -> pp -> np -> ...)
np(Sem,   Num, _,    _)    --> en(SemEN1, _, _), k(_, Num), en(SemEN2, _, _), {Sem=[SemEN1, SemEN2]}.

%% Präpositionalphrasen
pp([_,SemNP], Num, Sex) --> p(_SemP, Num), np(SemNP, Num, Sex, dativ).

%% Terminal-Symbole
 en(Sem, Num, Sex)       --> [X], {lex(X,  en, Num, Sex, Sem)}. %% Eigenname
art(Sem, Num, Sex, Fall) --> [X], {lex(X, art, Num, Sex, Fall, Sem)}. %% Artikel
  n(Sem, Num, Sex, Fall) --> [X], {lex(X,   n, Num, Sex, Fall, Sem)}. %% Nomen
  p(Sem, Num)            --> [X], {lex(X,   p, Num, Sem)}. %% Präposition
  v(Sem, Num)            --> [X], {lex(X,   v, Num, Sem)}. %% Verb
 ip(Sem, Num)            --> [X], {lex(X,  ip, Num, Sem)}. %% Interrogativpronomen
  k(Sem, Num)            --> [X], {lex(X,   k, Num, Sem)}. %% Konjunktion

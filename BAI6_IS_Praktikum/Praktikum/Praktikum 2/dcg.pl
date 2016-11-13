:- consult(lexicon).

%% Sätze
frage(SemNP2, Num) --> v(_, Num), np(SemNP1, Num, Sex, nominativ), np(SemNP2, _, Sex, nominativ), {SemNP2=[_,SemNP1,_|_]}. % Entscheidungsfrage: ist X der Onkel von Y? / sind X und Y die Eltern von Z?
frage(SemNP1, Num) --> v(_, Num), np(SemNP1, Num, Sex, nominativ), np(SemNP2, _, Sex, nominativ), {SemNP1=[_,SemNP2,_|_]}. % Entscheidungsfrage: ist der Onkel von Y X? / sind die Eltern von Z X und Y?
frage(Sem,    Num) --> v(_, Num), np(SemNP1, Num, _, nominativ), np(SemNP2, Num, _, nominativ), {append([SemNP2], SemNP1, Sem)}. % Entscheidungsfrage: sind X und Y geschwister?
frage(SemVP,  Num) --> np(SemNP, Num, Sex, nominativ), vp(SemVP, Num, Sex), {SemVP=[_,SemNP,_|_]}. % Ergänzungsfrage: wer ist der Onkel von X? & Bestätigungsfrage: X ist der Onkel von Y?
frage(SemNP,  Num) --> np(SemNP, Num, Sex, nominativ), vp(SemVP, Num, Sex), {SemNP=[_,SemVP,_|_]}. % Bestätigungsfrage: der Onkel von Y ist X?

antwort(SemVP, Num) --> {SemVP=[_,SemNP,_|_]},         np(SemNP, Num, Sex, _),         vp(SemVP, Num, Sex).
%antwort(Sem,   Num) --> {append([SemVP], SemNP, Sem)}, np(SemNP, Num, Sex, nominativ), vp(SemVP, Num, Sex).
%antwort(Sem,   Num) --> {append([SemNP], SemVP, Sem)}, vp(SemVP, Num, _), np(SemNP,  Num, _,   nominativ).

%% Nominalphrasen
np(SemEN, Num, Sex,  _)    --> en(SemEN, Num, Sex).
np(SemN,  Num, Sex,  Fall) --> n(SemN, Num, Sex, Fall).
np(SemN,  Num, Sex,  Fall) --> art(_, Num, Sex,  Fall), n(SemN, Num, Sex,  Fall).
np(Sem,   Num, SexN, Fall) --> art(_, Num, SexN, Fall), n(SemN, Num, SexN, Fall), {append([SemN], SemPP, Sem)}, pp(SemPP, _, _).  % Das append muss vor dem pp, damit wir nicht endlos in die Tiefe laufen (weil pp -> np -> pp -> np -> ...)
np(Sem,   Num, _,    _)    --> en(SemEN1, _, _), k(_, Num), en(SemEN2, _, _), {Sem=[SemEN1, SemEN2]}.
np(SemIP, Num, _,    _)    --> ip(SemIP, Num).

%% Verbalphrasen
vp(Sem, Num, Sex) --> v(_, Num), np(Sem, Num, Sex, nominativ).
vp(_,   Num, _)   --> v(_, Num).

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

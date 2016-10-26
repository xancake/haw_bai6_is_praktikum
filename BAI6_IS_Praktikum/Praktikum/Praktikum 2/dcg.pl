:- consult('../Praktikum 1/stammbaum').
:- consult(readsentence).

solve(Satz) :-
        s(Term, Satz, []),
        
        %%arg(Arity, Term, _), functor(Term, Functor, Arity), arg(1, Term, P1),
        %%write(Functor), write(' '), write(Arity), write(' P1: '), write(P1), nl,

        write(Term), write(' ==> '),
        call(Term),
        write(Term), nl,
        
        %% TODO: Antwortsätze erzeugen
        %% Bisher wird zum Beispiel für vater(harald,abel) [harald,ist] als Antwort erzeugt, richtiger wäre aber [harald,ist,der,vater,von,abel]
        %%
        %% Danach müssen wir noch mit Multiplizität (Plural) umgehen können:
        %%     [wer,sind,die,eltern,von,abel] --> findall(X, elter(X, abel), Z), write(Z)
        %% Der Antwortsatz hierfür müsste ungefähr so lauten:
        %%     [wer,sind,die,eltern,von,abel] --> [harald,und,luise,sind,die,eltern,von,abel] oder [die,elter,von,abel,sind,harald,und,luise]
        
        antw(Term, Antwort, []),
        write('Antwort: '), write(Antwort).

%% Satz
s(Sem)    --> vp(SemVP, Num), np(SemNP, Num), {SemNP=[_,SemVP,_|_], Sem=..SemNP}.
s(Sem)    --> vp(SemVP, Num), np(SemNP, Num), {SemVP=[_,SemNP,_|_], Sem=..SemVP}.
antw(Sem) --> {Sem=..SemVP, SemVP=[_,SemNP,_|_]}, np(SemNP, Num), vp(SemVP, Num).

%% Nominalphrasen
np(SemEN, Num) --> en(SemEN, Num).
np(SemN, Num)  --> art(_SemArt, Num), n(SemN, Num).
np(Sem, Num)   --> art(_SemArt, Num), n(SemN, Num), {append([SemN], SemPP, Sem)}, pp(SemPP, Num).  % Das append muss vor dem pp, damit wir nicht endlos in die Tiefe laufen (weil pp -> np -> pp -> np -> ...)

%% Präpositionalphrasen
pp([_,SemNP], Num) --> p(_SemP, Num), np(SemNP, Num).
%%pp --> p, ?.

%% Verbalphrasen
vp(SemNP, Num) --> v(_SemV, Num), np(SemNP, _Num).
vp(SemIP, Num) --> ip(SemIP, _Num), v(_SemV, Num).

%% Terminal-Symbole
 en(Sem, Num) --> [X], {lex(X,  en, Num, Sem)}. %% Eigenname
art(Sem, Num) --> [X], {lex(X, art, Num, Sem)}. %% Artikel
  n(Sem, Num) --> [X], {lex(X,   n, Num, Sem)}. %% Nomen
  p(Sem, Num) --> [X], {lex(X,   p, Num, Sem)}. %% Präposition
  v(Sem, Num) --> [X], {lex(X,   v, Num, Sem)}. %% Verb
 ip(Sem, Num) --> [X], {lex(X,  ip, Num, Sem)}. %% Interrogativpronomen

%% Lexikon
lex(ist,   v, singular, ist).
lex(der, art, _, artikel).
lex(die, art, _, artikel).
lex(das, art, _, artikel).
lex(von,   p, _, von).
lex(vom,   p, _, von).
lex(wer,  ip, singular, _Wer).
lex(wessen,  ip, singular, _Wer).

lex(X, en, singular, X) :- mann(X).
lex(X, en, singular, X) :- frau(X).

%% Alle Prädikate aus stammbaum mit 2 Parametern hinzufügen (TODO: wie schränkt man das entsprechend ein?)
%%lex(X,  n, singular, X) :- functor(_, X, 2).

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

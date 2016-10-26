:- consult('../Praktikum 1/stammbaum').
:- consult(readsentence).

frage() :-
        write('Frage: '),
        read_sentence(Input),
        
        write(Input), write(' ==> '),
        append(Satz, ['?'], Input),
        write(Satz), nl,
        
        solve(Satz, Antwort),
        write('Antwort: '), write_satz(Antwort), nl, nl,
        frage().
        
write_satz([]) :- write('!').
write_satz([K|Rest]) :-
        write(' '), write(K),
        write_satz(Rest).

solve(Satz, Antwort) :-
        s(Term, Satz, []),
        write(Term), write(' ==> '),
        call(Term),
        write(Term), nl,
        antw(Term, Antwort, []).

%% TODOs:
%% Mit Multiplizität (Plural) umgehen angehen:
%%     [wer,sind,die,eltern,von,abel] --> findall(X, elter(X, abel), Z), write(Z)
%% Der Antwortsatz hierfür müsste ungefähr so lauten:
%%     [wer,sind,die,eltern,von,abel] --> [harald,und,luise,sind,die,eltern,von,abel] oder [die,elter,von,abel,sind,harald,und,luise]

%% Satz
s(Sem)    --> vp(SemVP, Num, Sex), np(SemNP, Num, Sex), {SemNP=[_,SemVP,_|_], Sem=..SemNP}.
s(Sem)    --> vp(SemVP, Num, Sex), np(SemNP, Num, Sex), {SemVP=[_,SemNP,_|_], Sem=..SemVP}.
antw(Sem) --> {Sem=..SemVP, SemVP=[_,SemNP,_|_]}, np(SemNP, Num, Sex), vp(SemVP, Num, Sex).

%% Nominalphrasen
np(SemEN, Num, Sex) --> en(SemEN, Num, Sex).
np(SemN, Num, Sex)  --> art(_SemArt, Num, Sex), n(SemN, Num, Sex).
np(Sem, Num, SexN)  --> art(_SemArt, Num, SexN), n(SemN, Num, SexN), {append([SemN], SemPP, Sem)}, pp(SemPP, Num, _).  % Das append muss vor dem pp, damit wir nicht endlos in die Tiefe laufen (weil pp -> np -> pp -> np -> ...)

%% Präpositionalphrasen
pp([_,SemNP], Num, Sex) --> p(_SemP, Num), np(SemNP, Num, Sex).

%% Verbalphrasen
vp(SemNP, Num, Sex) --> v(_SemV, Num), np(SemNP, _Num, Sex).
vp(SemIP, Num, _)   --> ip(SemIP, _Num), v(_SemV, Num).

%% Terminal-Symbole
 en(Sem, Num, Sex) --> [X], {lex(X,  en, Num, Sex, Sem)}. %% Eigenname
art(Sem, Num, Sex) --> [X], {lex(X, art, Num, Sex, Sem)}. %% Artikel
  n(Sem, Num, Sex) --> [X], {lex(X,   n, Num, Sex, Sem)}. %% Nomen
  p(Sem, Num) --> [X], {lex(X,   p, Num, Sem)}. %% Präposition
  v(Sem, Num) --> [X], {lex(X,   v, Num, Sem)}. %% Verb
 ip(Sem, Num) --> [X], {lex(X,  ip, Num, Sem)}. %% Interrogativpronomen

%% Lexikon
lex(von,     p, _, von).
lex(vom,     p, _, von).
lex(wer,    ip, singular, _Wer).
lex(wessen, ip, singular, _Wer).
lex(ist,     v, singular, ist).

lex(der, art, _, maskulin, artikel).
lex(die, art, _, feminin,  artikel).
lex(das, art, _, _,        artikel).

lex(X, en, singular, maskulin, X) :- mann(X).
lex(X, en, singular, feminin, X) :- frau(X).

lex(verheiratet, n, singular, _, verheiratet).
lex(kind, n, singular, _, kind).
lex(kinder, n, plural, _, kind).
lex(sohn,   n, singular, maskulin, sohn).
lex(tochter, n, singular, feminin, tochter).
lex(elter,  n, singular, _, elter).
lex(eltern, n, plural, _, elter).
lex(vater,  n, singular, maskulin, vater).
lex(mutter, n, singular, feminin, mutter).
lex(geschwister, n, singular, _, geschwister).
lex(bruder, n, singular, maskulin, bruder).
lex(schwester, n, singular, feminin, schwester).
lex(halbgeschwister, n, singular, _, halbgeschwister).
lex(halbbruder, n, singular, maskulin, halbbruder).
lex(halbschwester, n, singular, feminin, halbschwester).
lex(tante, n, singular, feminin, tante).
lex(onkel, n, singular, maskulin, onkel).
lex(neffe, n, singular, maskulin, neffe).
lex(nichte, n, singular, feminin, nichte).
lex(cousin, n, singular, maskulin, cousin).
lex(cousine, n, singular, feminin, cousine).
lex(grosselter, n, singular, _, grosselter).
lex(grosseltern, n, plural, _, grosselter).
lex(oma, n, singular, feminin, oma).
lex(opa, n, singular, maskulin, opa).
lex(grosstante, n, singular, feminin, grosstante).
lex(grossonkel, n, singular, maskulin, grossonkel).

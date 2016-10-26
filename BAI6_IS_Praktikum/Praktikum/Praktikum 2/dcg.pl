:- consult('../Praktikum 1/stammbaum').
:- consult(readsentence).

frage() :-
        write('Frage: '),
        read_sentence(Input),
        
        write(Input), write(' ==> '),
        append(Satz, ['?'], Input),
        write(Satz), nl,
        
        solve(Satz, Antwort),
        write('Antwort: '), write(Antwort), nl, nl,
        frage().

solve(Satz, Antwort) :-
        s(Term, Satz, []),
        write(Term), write(' ==> '),
        call(Term),
        write(Term), nl,
        antw(Term, Antwort, []).

%% TODOs:
%%
%% Feminim und Maskulin unterscheiden (damit wir keine Ausgabe ala "X ist der Oma von Y" bekommen
%%
%% Mit Multiplizität (Plural) umgehen angehen:
%%     [wer,sind,die,eltern,von,abel] --> findall(X, elter(X, abel), Z), write(Z)
%% Der Antwortsatz hierfür müsste ungefähr so lauten:
%%     [wer,sind,die,eltern,von,abel] --> [harald,und,luise,sind,die,eltern,von,abel] oder [die,elter,von,abel,sind,harald,und,luise]

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
lex(oma, n, singular, oma).
lex(opa, n, singular, opa).
lex(grosstante, n, singular, grosstante).
lex(grossonkel, n, singular, grossonkel).

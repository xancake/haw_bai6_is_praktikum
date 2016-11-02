:- consult('../Praktikum 1/stammbaum').
:- consult(readsentence).

% Fragen an Herrn Neitzke:
%
% - Wie können wir geschachtelte Fragen verarbeiten?
%     Beispiel: "ist harald der vater von der mutter von kain" Wie müsste das überführt werden, sodass das verarbeitet werden kann?
%     Variante 1: vater(harald, mutter(_, kain))    // Funktioniert nicht, wäre aber wünschenswert, damit man nur ein Statement callen muss
%                                                   // Alternativ kann man die auch rekursiv einzeln callen (also erst mutter, dann vater), ist aber aufwändiger
%     Variante 2: vater(harald, X), mutter(X, kain) // Würde erfordern, dass jedes Statement einzeln gecalled wird
%
% - Ist es sinnvoll, dem Lexikoneintrag von "wer" in der Semantik eine Variable zuzuweisen, oder sollte man lieber explizit in
%     der Grammatik dafür sorgen, dass eine Variable an die richtige Stelle gesetzt wird. ("np -> art n pp")

% Ein paar gültige Sätze:
% Frage-Satz | Frage als Liste | Antwort
% ist abel der onkel von bernhard?.   | [ist, abel, der, onkel, von, bernhard]  | abel ist der onkel von bernhard
% wer ist der onkel von bernhard?.    | [wer, ist, der, onkel, von, bernhard]   | abel ist der onkel von bernhard
% wer ist die oma von bernd?.         | [wer, ist, die, oma, von, bernd]        | barbel ist die oma von bernd
% wer sind die eltern von anton?.     | [wer, sind, die, elter, von, anton]     | barbel und horst sind die eltern von anton
% sind harald und luise geschwister?. | [sind, harald, und, luise, geschwister] | harald und luise sind geschwister
% ist harald der vater von dem onkel von bernhard?. | [ist, harald, der, vater, von, dem, onkel, von, bernhard] | harald ist der vater von dem onkel von bernhard

%% Mit Multiplizität (Plural) umgehen können:
%%     [wer,sind,die,eltern,von,abel] --> findall(X, elter(X, abel), Z), write(Z)
%% Der Antwortsatz hierfür müsste ungefähr so lauten:
%%     [wer,sind,die,eltern,von,abel] --> [harald,und,luise,sind,die,eltern,von,abel] oder [die,elter,von,abel,sind,harald,und,luise]

frage() :-
        write('Frage: '),
        read_sentence(Input),
        
        write(Input), write(' ==> '),
        append(Satz, ['?'], Input),
        write(Satz), nl,
        
        solve(Satz), nl,
        frage().

write_satz([])       :- write('!').
write_satz([K|Rest]) :- write(' '), write(K), write_satz(Rest).

solve(Satz) :- solve(Satz, Antwort), !, write('Antwort: '), write_satz(Antwort), nl.
solve(_)    :- write('Antwort: Nein'), nl.

solve(Satz, Antwort) :-
        s(Sem, Satz, []),
        write('Semantik: '), write(Sem), nl,

        verarbeite(Sem, Ergebnis),
        write('Ergebnis: '), write(Ergebnis), nl,
        
        %Term =..Sem,
        %write(Term), write(' ==> '),
        %call(Term),
        %write(Term), nl,
        
        antw(Sem, Antwort, []).

verarbeite(EListe, AListe) :- atom(EListe), EListe=AListe.
verarbeite(EListe, AListe) :- var(EListe),  EListe=AListe.
verarbeite(EListe, AListe) :-
        not(var(EListe)),
        EListe = [Funktor|Rest1],
        Rest1  = [Arg1|Rest2],
        Rest2  = [Arg2],
        verarbeite(Arg1, Erg1),
        verarbeite(Arg2, Erg2),
        Funktion =..[Funktor, Erg1, Erg2],
        call(Funktion),
        arg(1, Funktion, AListe).

%% Satz
s(SemNP)    --> v(_, Num), np(SemEN, _, Sex, _), np(SemNP, Num, Sex, nominativ), {SemNP=[_,SemEN,_|_]}.
s(SemVP)    --> np(SemNP, Num, Sex, _), vp(SemVP, Num, Sex), {SemVP=[_,SemNP,_|_]}.
antw(SemVP) --> {SemVP=[_,SemNP,_|_]}, np(SemNP, Num, Sex, _), vp(SemVP, Num, Sex).

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

%% Lexikon
lex(von,    p,  _,         von).
%lex(vom,    p,  _,         von).
lex(wer,    ip, _,        _Wer).
%lex(wessen, ip, _,        _Wer).
lex(ist,    v,  singular,  ist).
lex(sind,   v,  plural,    ist).
lex(und,    k,  plural,    und).

lex(X, en, singular, maskulin, X) :- mann(X).
lex(X, en, singular, feminin,  X) :- frau(X).

lex(der, art, singular, maskulin, nominativ, artikel).
lex(die, art, singular, feminin,  nominativ, artikel).
lex(das, art, singular, neutrum,  nominativ, artikel).
lex(dem, art, singular, maskulin, dativ,     artikel).
lex(der, art, singular, feminin,  dativ,     artikel).
lex(dem, art, singular, neutrum,  dativ,     artikel).

%lex(verheiratet,     n, singular, _,        verheiratet).
lex(kind,            n, singular, neutrum,  _, kind).
lex(kinder,          n, plural,   feminin,  _, kind).
lex(sohn,            n, singular, maskulin, _, sohn).
lex(soehne,          n, plural,   feminin,  _, sohn).
lex(tochter,         n, singular, feminin,  _, tochter).
lex(toechter,        n, plural,   feminin,  _, tochter).
lex(eltern,          n, plural,   feminin,  _, elter).
lex(vater,           n, singular, maskulin, _, vater).
lex(mutter,          n, singular, feminin,  _, mutter).
lex(geschwister,     n, plural,   feminin,  _, geschwister).
lex(bruder,          n, singular, maskulin, _, bruder).
lex(brueder,         n, plural,   feminin,  _, bruder).
lex(schwester,       n, singular, feminin,  _, schwester).
lex(schwestern,      n, plural,   feminin,  _, schwester).
lex(tante,           n, singular, feminin,  _, tante).
lex(tanten,          n, plural,   feminin,  _, tante).
lex(onkel,           n, singular, maskulin, _, onkel).
lex(oenkel,          n, plural,   feminin,  _, onkel).
lex(neffe,           n, singular, maskulin, _, neffe).
lex(neffen,          n, singular, feminin,  _, neffe).
lex(nichte,          n, singular, feminin,  _, nichte).
lex(nichten,         n, plural,   feminin,  _, nichte).
lex(cousin,          n, singular, maskulin, _, cousin).
lex(cousins,         n, plural,   feminin,  _, cousin).
lex(cousine,         n, singular, feminin,  _, cousine).
lex(cousinen,        n, plural,   feminin,  _, cousine).
lex(grosseltern,     n, plural,   feminin,  _, grosselter).
lex(oma,             n, singular, feminin,  _, oma).
lex(omas,            n, plural,   feminin,  _, oma).
lex(grossmutter,     n, singular, feminin,  _, oma).
lex(grossmuetter,    n, plural,   feminin,  _, oma).
lex(opa,             n, singular, maskulin, _, opa).
lex(opas,            n, plural,   feminin,  _, opa).
lex(grossvater,      n, singular, maskulin, _, opa).
lex(grossvaeter,     n, plural,   feminin,  _, opa).
lex(grosstante,      n, singular, feminin,  _, grosstante).
lex(grosstanten,     n, plural,   feminin,  _, grosstante).
lex(grossonkel,      n, singular, maskulin, _, grossonkel).
lex(grossoenkel,     n, plural,   feminin,  _, grossonkel).
lex(halbbruder,      n, singular, maskulin, _, halbbruder).
lex(halbbrueder,     n, plural,   feminin,  _, halbbruder).
lex(halbschwester,   n, singular, feminin,  _, halbschwester).
lex(halbschwestern,  n, plural,   feminin,  _, halbschwester).
lex(halbgeschwister, n, plural,   feminin,  _, halbgeschwister).

:- consult(readsentence).
:- consult(dcg).
:- consult('../Praktikum 1/stammbaum').

% Fragen an Herrn Neitzke:
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

solve(Satz) :- solve(Satz, Antwort), !, write('Antwort: '), write_satz(Antwort), nl.
solve(_)    :- write('Antwort: Nein'), nl.

solve(Satz, Antwort) :-
        frage(Sem, Satz, []),
        write('Semantik: '), write(Sem), nl,
        verarbeite(Sem, Ergebnis),
        write('Ergebnis: '), write(Ergebnis), nl,
        antwort(Sem, Antwort, []).

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

write_satz([])       :- write('!').
write_satz([K|Rest]) :- write(' '), write(K), write_satz(Rest).

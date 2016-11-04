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

main() :-
        write('Frage: '),
        read_sentence(Input),
        
        write(Input), write(' ==> '),
        append(Satz, ['?'], Input),
        write(Satz), nl,
        
        solve(Satz), nl,
        main().

solve(Satz) :- solve(Satz, Antwort), !, write('Antwort: '), write_satz(Antwort), nl.
solve(_)    :- write('Antwort: Nein'), nl.

solve(Satz, Antwort) :-
        frage(Sem, Num, Satz, []),
        write('Semantik: '), write(Sem), nl,
        write('Numerus: '), write(Num), nl,
        verarbeite(Sem, Ergebnis),
        write('Ergebnis: '), write(Ergebnis), nl,
        antwort(Sem, Num, Antwort, []).

verarbeite(EListe, AListe) :-
        not(atom(EListe)), not(var(EListe)),
        length(EListe, 3), %EListe=[E1,_], n(E1, _Num, _, _, _, []), % Bei einer drei-elementigen Liste mit einem Nomen vorne verarbeiten
        EListe = [Funktor|[Arg1|[Arg2]]],
        verarbeite(Arg1, Erg1),
        verarbeite(Arg2, Erg2), !,
        mycall(Funktor, Erg1, Erg2, AListe).
verarbeite(EListe, EListe).

%% Funktor(Arg1, Arg2) callen und das Ergebnis von Arg1 zurückgeben
mycall(Funktor, Arg1, Arg2, Output) :- not(is_list(Arg1)), not(is_list(Arg2)), Funktion =..[Funktor, Arg1, Arg2], call(Funktion), arg(1, Funktion, Output).
%% Wenn wir Listen haben (z.B. [elter, [harald, luise], abel], dann müssen wir elter(harald, abel) und elter(luise, abel) aufrufen
mycall(_, _, [], []).
mycall(_, [], _, []).
mycall(Funktor, Arg1, Arg2, Output) :- not(is_list(Arg1)), is_list(Arg2), Arg2=[K|Rest], mycall(Funktor, Arg1, K, Out1), mycall(Funktor, Arg1, Rest, Out2), append([Out1], Out2, Output).
mycall(Funktor, Arg1, Arg2, Output) :- is_list(Arg1), not(is_list(Arg2)), Arg1=[K|Rest], mycall(Funktor, K, Arg2, Out1), mycall(Funktor, Rest, Arg2, Out2), append([Out1], Out2, Output).

write_satz([])       :- write('!').
write_satz([K|Rest]) :- write(' '), write(K), write_satz(Rest).

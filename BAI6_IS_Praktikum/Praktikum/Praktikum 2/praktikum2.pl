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
        
        write("Input: "), write(Input), write(' ==> '),
        append(Satz, ['?'], Input),
        write(Satz), nl,
        
        solve(Satz), nl,
        main().

solve(Satz) :- solve(Satz, Antwort), !, write('Antwort: '), write_satz(Antwort), nl.
solve(_)    :- nl, write('Antwort: Nein'), nl.

solve(Satz, Antwort) :-
        frage(Sem, Num, Satz, []), !,
        write('Numerus: '), write(Num), nl,
        write('Semantik: '), write(Sem),
        verarbeite(Sem, Num, Ergebnis), !,
        write(' ==> Semantik: '), write(Sem), nl,
        write('Ergebnis: '), write(Ergebnis), nl,
        antwort(Sem, Num, Antwort, []).

verarbeite(Sem, Num, Ergebnis) :-
        not(atom(Sem)), not(var(Sem)),
        length(Sem, 3),
        Sem = [Funktor|[Arg1|[Arg2]]],
        n(Funktor, _, _, _, _, []), % Bei einer drei-elementigen Liste mit einem Nomen vorne verarbeiten
        verarbeite(Arg1, Num, Erg1),
        verarbeite(Arg2, Num, Erg2), !,
        mycall(Funktor, Erg1, Erg2, Num, Ergebnis).
verarbeite(Sem, _, Sem).

%% Funktor(Arg1, Arg2) callen und das Ergebnis von Arg1 zurückgeben
mycall(Funktor, Arg1, Arg2, singular, Output) :- not(is_list(Arg1)), not(is_list(Arg2)), Funktion =..[Funktor, Arg1, Arg2], call(Funktion), arg(1, Funktion, Output).
mycall(Funktor, Arg1, Arg2, plural,   Output) :- not(is_list(Arg1)), not(is_list(Arg2)), Funktion =..[Funktor, Arg1, Arg2], findall(Arg1, call(Funktion), Output), Funktion=..[Funktor, Output, Arg2].
mycall(Funktor, Arg1, Arg2, plural,   Output) :- not(is_list(Arg1)), not(is_list(Arg2)), F1=..[Funktor, Arg1, Arg2], call(F1), F2=..[Funktor, Arg2, Arg1], call(F2), arg(1, F1, X), arg(1, F2, Y), Output=[X, Y].
%% Wenn wir Listen haben (z.B. [elter, [harald, luise], abel], dann müssen wir elter(harald, abel) und elter(luise, abel) aufrufen
mycall(_, _, [], _, []).
mycall(_, [], _, _, []).
mycall(Funktor, Arg1, Arg2, Num, Output) :- not(is_list(Arg1)), is_list(Arg2), Arg2=[K|Rest], mycall(Funktor, Arg1, K, singular, Out1), mycall(Funktor, Arg1, Rest, Num, Out2), append([Out1], Out2, Output).
mycall(Funktor, Arg1, Arg2, Num, Output) :- is_list(Arg1), not(is_list(Arg2)), Arg1=[K|Rest], mycall(Funktor, K, Arg2, singular, Out1), mycall(Funktor, Rest, Arg2, Num, Out2), append([Out1], Out2, Output).

write_satz([])       :- write('!').
write_satz([K|Rest]) :- write(' '), write(K), write_satz(Rest).

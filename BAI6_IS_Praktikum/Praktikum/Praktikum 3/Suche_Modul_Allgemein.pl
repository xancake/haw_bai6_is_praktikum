% Das Programm wird mit solve(depth), solve(breadth) oder solve(informed) aufgerufen.
solve(Strategy):-
  start_description(StartState),
  solve((start,StartState,_),Strategy).
  
% Prädikat search: 
%   1. Argument ist die Liste aller Pfade. Der aktuelle Pfad ist an erster Stelle. 
%   Jeder Pfad ist als Liste von Zuständen repräsentiert, allerdings in falscher 
%   Reihenfolge, d.h. der Startzustand ist an letzter Position.
%   2. Argument ist die Strategie
%   3. Argument ist der Ergebnis-Pfad.
solve(StartNode,Strategy) :-
  start_node(StartNode),
  search([[StartNode]],Strategy,Path),
  reverse(Path,Path_in_correct_order),
  write_solution(Path_in_correct_order).


write_solution(Path):-
  nl,write('SOLUTION:'),nl,
  write_actions(Path).  

write_actions([]).
write_actions([(Action,_,_)|Rest]):-
  write('Action: '),write(Action),nl,
  write_actions(Rest).


% Abbruchbedingung: Wenn ein Zielzustand erreicht ist, wird der aktuelle Pfad an den
% dritten Parameter übertragen.
search([[FirstNode|Predecessors]|_],_,[FirstNode|Predecessors]) :- 
        goal_node(FirstNode),
        nl,write('SUCCESS'),nl,!.
        
% Einstiegspunkt für die iterative Tiefensuche
search(Paths,iterativeDeepening,Solution) :-
        searchIterativeDeepening(Paths, Solution, 1).

% Standard Einstieg für alle anderen Suchalgorithmen
search([[FirstNode|Predecessors]|RestPaths],Strategy,Solution) :-
        expand(FirstNode,Children),                                     % Nachfolge-Zustände berechnen
        generate_new_paths(Children,[FirstNode|Predecessors],NewPaths), % Nachfolge-Zustände einbauen
        insert_new_paths(Strategy,NewPaths,RestPaths,AllPaths),         % Neue Pfade einsortieren
        search(AllPaths,Strategy,Solution).

/**
 * Realisiert die iterative Vertiefung, indem das Bound iterativ inkrementiert wird.
 * Im ersten Block wird die Suche für das aktuelle Bound umgesetzt und im zweiten inkrementiert
 * und wieder aufgerufen, wenn im Ersten kein Zielzustand gefunden wurde.
 */
searchIterativeDeepening(Paths,Solution,Bound) :-
        write_bound(Bound),
        setdepth(Paths),
        searchIterativeDeepeningHelper(Paths,Solution,Bound).
searchIterativeDeepening(Paths,Solution,Bound) :-
        NewBound is Bound+1,
        searchIterativeDeepening(Paths,Solution,NewBound).

/**
 * Realisiert den Zielabgleich und das konkrete Suchen (Durchlauf bis Bound)
 * Erste Block:   Abbruchbedingung, für Zustand ist Zielzustand
 * Zweiter Block: Abbruchbedingung für Knoten deren Tiefe dem Bound entsprechen und dann
 *                wird der Knoten verworfen und der Restpfad des Baumes abgearbeitet
 * Dritter Block: Konkrete Suche (vgl. Standardsuche)
 */
searchIterativeDeepeningHelper([[FirstNode|Predecessors]|_],[FirstNode|Predecessors],_) :-
        goal_node(FirstNode),
        nl, write('SUCCESS'), nl, !.
searchIterativeDeepeningHelper([[(_,_,Bound)|_]|RestPaths],Solution,Bound) :-
        searchIterativeDeepeningHelper(RestPaths,Solution,Bound).
searchIterativeDeepeningHelper([[FirstNode|Predecessors]|RestPaths],Solution,Bound) :-
        FirstNode = (_,_,Depth), Depth < Bound,
        write_depth(Depth, Bound),
        expand(FirstNode,Children),                                       % Nachfolge-Zustände berechnen
        generate_new_paths(Children,[FirstNode|Predecessors],NewPaths),   % Nachfolge-Zustände einbauen
        insert_new_paths(iterativeDeepening,NewPaths,RestPaths,AllPaths), % Neue Pfade einsortieren
        searchIterativeDeepeningHelper(AllPaths,Solution,Bound).


generate_new_paths(Children,Path,NewPaths):-
  maplist(get_state,Path,States),
  generate_new_paths_help(Children,Path,States,NewPaths).

% Abbruchbedingung, wenn alle Kindzustände abgearbeitet sind.
generate_new_paths_help([],_,_,[]).

% Falls der Kindzustand bereits im Pfad vorhanden war, wird der gesamte Pfad verworfen,
% denn er würde nur in einem Zyklus enden. (Dies betrifft nicht die Fortsetzung des 
% Pfades mit Geschwister-Kindern.) Es wird nicht überprüft, ob der Kindzustand in einem
% anderen Pfad vorkommt, denn möglicherweise ist dieser Weg der günstigere.
generate_new_paths_help([FirstChild|RestChildren],Path,States,RestNewPaths):- 
  get_state(FirstChild,State),state_member(State,States),!,
  generate_new_paths_help(RestChildren,Path,States,RestNewPaths).

% Ansonsten, also falls der Kindzustand noch nicht im Pfad vorhanden war, wird er als 
% Nachfolge-Zustand eingebaut.
generate_new_paths_help([FirstChild|RestChildren],Path,States,[[FirstChild|Path]|RestNewPaths]) :-
  generate_new_paths_help(RestChildren,Path,States,RestNewPaths).

 
get_state((_,State,_),State).


%%% Strategie:
write_action([[(Action,_)|_]|_])      .%:- nl,write('Action: '),write(Action),nl.
write_next_state([[_,(_,State)|_]|_]) .%:- nl,write('Go on with: '),write(State),nl.
write_state([[(_,State)|_]|_])        .%:- write('New State: '),write(State),nl.
write_fail(depth,[[(_,State)|_]|_])   .%:- nl,write('FAIL, go on with: '),write(State),nl.
write_fail(_,_)                       .%:- nl,write('FAIL').
write_bound(Bound)                    .%:- write('=== Bound: '), write(Bound), nl.
write_depth(Depth, Bound)             .%:- write('===== Depth: '), write(Depth), write('/'), write(Bound), nl.

% Alle Strategien: Keine neuen Pfade vorhanden
insert_new_paths(Strategy,[],OldPaths,OldPaths):-
  write_fail(Strategy,OldPaths),!.

% Tiefensuche
insert_new_paths(depth,NewPaths,OldPaths,AllPaths):-
  append(NewPaths,OldPaths,AllPaths),
  write_action(NewPaths).

% Breitensuche
insert_new_paths(breadth,NewPaths,OldPaths,AllPaths):-
  append(OldPaths,NewPaths,AllPaths),
  write_next_state(AllPaths),
  write_action(AllPaths).

% Informierte Suche
insert_new_paths(informed,NewPaths,OldPaths,AllPaths):-
  eval_paths(ohnePfadkosten, NewPaths),
  insert_new_paths_informed(NewPaths,OldPaths,AllPaths),
  write_action(AllPaths),
  write_state(AllPaths).

/**
 * A* Suche
 * Sortiert alle Pfade im Suchpfad nach der Heuristik und verwendet dafür auch die Pfadkosten
 */
insert_new_paths(astar,NewPaths,OldPaths,AllPaths):-
  eval_paths(mitPfadkosten, NewPaths),
  insert_new_paths_informed(NewPaths,OldPaths,AllPaths),
  write_action(AllPaths),
  write_state(AllPaths).

/**
 * gierige Bestensuche
 * Sortiert alle Pfade im Suchpfad nach der Heuristik und verwendet dabei keine Pfadkosten
 */
insert_new_paths(bestFirst,NewPaths,OldPaths,AllPaths):-
  eval_paths(ohnePfadkosten, NewPaths),
  insert_new_paths_informed(NewPaths,OldPaths,AllPaths),
  write_action(AllPaths),
  write_state(AllPaths).

/**
 * Optimistisches Bergsteigen
 * Verwerfen der alten Pfade, es wird immer der nach der heuristik am besten
 * bewertete Knoten verfolgt. Dadurch nicht vollständig.
 */
insert_new_paths(optimisticHillClimbing,NewPaths,_OldPaths,AllPaths):-
  eval_paths(ohnePfadkosten, NewPaths),
  insert_new_paths_informed(NewPaths,[],AllPaths),
  write_action(AllPaths),
  write_state(AllPaths).
  
/**
 * Bergsteigen mit Backtracking
 * Hinzufügen der nach der Heuristik sortierten neuen Pfade an den Anfang der Suchpfade.
 */
insert_new_paths(backtrackingHillClimbing,NewPaths,OldPaths,AllPaths):-
  eval_paths(ohnePfadkosten, NewPaths),
  insert_new_paths_informed(NewPaths,[],SortedNewPaths),
  append(SortedNewPaths,OldPaths,AllPaths),
  write_action(AllPaths),
  write_state(AllPaths).

/**
 * Iterative Tiefensuche
 * Iteriert immer eine Ebene tiefer in den Suchbaum hinunter.
 * Die Suche verläuft dann nach dem Prinzip der Tiefensuche.
 * Über setdepth() wird die Tiefe verwaltet.
 */
insert_new_paths(iterativeDeepening,NewPaths,OldPaths,AllPaths):-
  setdepth(NewPaths),
  insert_new_paths(depth,NewPaths,OldPaths,AllPaths),
  write_state(AllPaths).
  
% Nutzt das Value-Feld der Knoten um dort die Tiefe abzulegen, in der sich der Knoten im Suchbaum befindet
setdepth([]).
setdepth([K|R]) :- setdepthHelper(K),setdepth(R).
setdepthHelper([(_,_,Value)|R]) :- length(R, Value).
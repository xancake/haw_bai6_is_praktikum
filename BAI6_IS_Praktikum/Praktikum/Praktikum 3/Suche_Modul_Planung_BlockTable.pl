:- consult('Suche_Modul_Allgemein.pl').
:- consult('Suche_Modul_Informierte_Suche.pl').

% Die Schnittstelle umfasst
%   start_description   ;Beschreibung des Startzustands
%   start_node          ;Test, ob es sich um einen Startknoten handelt
%   goal_node           ;Test, ob es sich um einen Zielknoten handelt
%   state_member        ;Test, ob eine Zustandsbeschreibung in einer Liste 
%                        von Zustandsbeschreibungen enthalten ist
%   expand              ;Berechnung der Kind-Zustandsbeschreibungen
%   eval-path           ;Bewertung eines Pfades

/*
% Einfacher unvollständiger Zielzustand
start_description([
  block(block1),
  block(block2),
  block(block3),
  on(table,block1),
  on(block1,block2),
  on(table,block3),
  clear(block2),
  clear(block3),
  handempty
]).

goal_description([
  block(block1),
  block(block2),
  on(table,block2)
]).
*/

%/*
%Komplexerer Start- und Zielzustand
start_description([
  block(block1),
  block(block2),
  block(block3),
  block(block4),
  block(block5),
  block(block6),
  block(block7),
  block(block8),
  block(block9),
  on(table,block1),
  on(table,block4),
  on(table,block7),
  on(block1,block2),
  on(block2,block3),
  on(block4,block5),
  on(block5,block6),
  on(block7,block8),
  on(block8,block9),
  clear(block6),
  clear(block3),
  clear(block9),
  handempty
]).

goal_description([
  block(block1),
  block(block2),
  block(block3),
  block(block4),
  block(block5),
  block(block6),
  block(block7),
  block(block8),
  block(block9),
  on(table,block3),
  on(table,block6),
  on(table,block1),
  on(table,block5),
  on(block3,block2),
  on(block6,block4),
  clear(block2),
  clear(block5),
  clear(block1),
  clear(block4),
  handempty
]).
%*/

/*
% Standard Start- und Zielzustand (aus Aufgabenstellung)
start_description([
  block(block1),
  block(block2),
  block(block3),
  block(block4),  %mit Block4
  on(table,block2),
  on(table,block3),
  on(block2,block1),
  on(table,block4), %mit Block4
  clear(block1),
  clear(block3),
  clear(block4), %mit Block4
  handempty
]).

goal_description([
  block(block1),
  block(block2),
  block(block3),
  block(block4), %mit Block4
  on(block4,block2), %mit Block4
  on(table,block3),
  on(table,block1),
  on(block1,block4), %mit Block4
%  on(block1,block2), %ohne Block4
  clear(block3),
  clear(block2),
  handempty
]).
*/



start_node((start,_,_)).
goal_node((_,State,_)) :- goal_description(GoalState), subset(GoalState, State).


% Aufgrund der Komplexität der Zustandsbeschreibungen kann state_member nicht auf 
% das Standardprädikat member zurückgeführt werden.
state_member(_,[]) :- !, fail.
state_member(State,[FirstState|_]) :- subtract(State, FirstState, []), !. %State=FirstState, !.
% "Test, ob State bereits durch FirstState beschrieben war. Tipp: Eine Lösungsmöglichkeit besteht in der Verwendung einer Mengenoperation, z.B. subtract"  ,!.

% Es ist sichergestellt, dass die beiden ersten Klauseln nicht zutreffen.
state_member(State,[_|RestStates]) :- state_member(State,RestStates).


eval_path(heuristik, [(_,State,Value)|_]) :- eval_state(State, Value).
eval_path(heuristikMitPfadkosten, [(_,State,Value)|RestPath]) :- eval_state(State, StateValue), length(RestPath, PathLength), Value is StateValue + PathLength.
%/*
% Anzahl überschneidender Zustände
eval_state(State, Value) :-
  goal_description(GoalState),
  intersection(State, GoalState, Intersection), % Schnittmenge berechnen
  length(Intersection, EqualStateCount),
  length(GoalState, GoalStateCount),
  Value is GoalStateCount - EqualStateCount.
%*/
/*
% Anzahl der Blöcke auf allen Blöcken die im Zielzustand frei sein sollen
eval_state(State, Value) :-
  goal_description(GoalState),
  findall(X, member(clear(X), GoalState), ClearBlocks),
  write(ClearBlocks), write(" "), write(State), write(" "),
  sum_blocks_over_blocks(ClearBlocks, State, Value),
  write(Value), nl.
sum_blocks_over_blocks([],    _,     0).
sum_blocks_over_blocks([K|R], State, Count) :- count_blocks_over_block(K, State, Value), sum_blocks_over_blocks(R, State, Sum), Count is Value+Sum.
count_blocks_over_block(Block, State, Count) :- member(holding(Block), State), Count is 0. % Spezialfall, wenn der Block gerade in der Hand ist
count_blocks_over_block(Block, State, Count) :- member(clear(Block), State), Count is 0.
count_blocks_over_block(Block, State, Count) :- member(on(Block, X), State), count_blocks_over_block(X, State, XCount), Count is XCount+1.
*/

%action(Name,           Prüf-Liste,                               Del-Liste,                          Add-Liste)
action(pick_up(X),      [handempty, clear(X), on(table,X)],       [handempty, clear(X), on(table,X)], [holding(X)]).
action(pick_up(X),      [handempty, clear(X), on(Y,X), block(Y)], [handempty, clear(X), on(Y,X)],     [holding(X), clear(Y)]).
action(put_on_table(X), [holding(X)],                             [holding(X)],                       [handempty, clear(X), on(table,X)]).
action(put_on(Y,X),     [holding(X), clear(Y)],                   [holding(X), clear(Y)],             [handempty, clear(X), on(Y,X)]).


% Hilfskonstrukt, weil das PROLOG "subset" nicht die Unifikation von Listenelementen 
% durchführt, wenn Variablen enthalten sind. "member" unifiziert hingegen.
mysubset([],_).
mysubset([H|T],List) :- member(H,List), mysubset(T,List).

  
expand((_,State,_), Result) :- findall((Name,NewState,_), expand_help(State,Name,NewState), Result).

expand_help(State,Name,NewState) :-
        action(Name, Pruef, Del, Add),
        mysubset(Pruef, State),
        subtract(State, Del, Erg),
        append(Erg, Add, NewState).

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




start_node((start,_,_)).
goal_node((_,State,_)) :- goal_description(GoalState), subtract(State, GoalState, []).


% Aufgrund der Komplexit�t der Zustandsbeschreibungen kann state_member nicht auf 
% das Standardpr�dikat member zur�ckgef�hrt werden.
state_member(_,[]) :- !, fail.
state_member(State,[FirstState|_]) :- subtract(State, FirstState, []), !. %State=FirstState, !.
% "Test, ob State bereits durch FirstState beschrieben war. Tipp: Eine L�sungsm�glichkeit besteht in der Verwendung einer Mengenoperation, z.B. subtract"  ,!.

% Es ist sichergestellt, dass die beiden ersten Klauseln nicht zutreffen.
state_member(State,[_|RestStates]) :- state_member(State,RestStates).

eval_path([(_,State,Value)|RestPath]) :-
  eval_state(State,StateValue),
  length(RestPath,PathLength),
  Value is StateValue+PathLength.
eval_state(State, Value) :-
  goal_description(GoalState),
  intersection(State, GoalState, Intersection), % Schnittmenge berechnen
  length(Intersection, EqualStateCount),
  length(GoalState, GoalStateCount),
  Value is GoalStateCount-EqualStateCount.

%action(Name,           Pr�f-Liste,                               Del-Liste,                          Add-Liste)
action(pick_up(X),      [handempty, clear(X), on(table,X)],       [handempty, clear(X), on(table,X)], [holding(X)]).
action(pick_up(X),      [handempty, clear(X), on(Y,X), block(Y)], [handempty, clear(X), on(Y,X)],     [holding(X), clear(Y)]).
action(put_on_table(X), [holding(X)],                             [holding(X)],                       [handempty, clear(X), on(table,X)]).
action(put_on(Y,X),     [holding(X), clear(Y)],                   [holding(X), clear(Y)],             [handempty, clear(X), on(Y,X)]).


% Hilfskonstrukt, weil das PROLOG "subset" nicht die Unifikation von Listenelementen 
% durchf�hrt, wenn Variablen enthalten sind. "member" unifiziert hingegen.
mysubset([],_).
mysubset([H|T],List) :- member(H,List), mysubset(T,List).

  
expand((_,State,_), Result) :- findall((Name,NewState,_), expand_help(State,Name,NewState), Result).

expand_help(State,Name,NewState) :-
        action(Name, Pruef, Del, Add),
        mysubset(Pruef, State),
        subtract(State, Del, Erg),
        append(Erg, Add, NewState).

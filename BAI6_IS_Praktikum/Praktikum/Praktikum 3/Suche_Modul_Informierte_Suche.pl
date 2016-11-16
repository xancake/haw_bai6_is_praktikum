% Informierte Suche

eval_paths(_, []).
eval_paths(heuristikOhnePfadkosten, [FirstPath|RestPaths]) :- eval_path(heuristikOhnePfadkosten, FirstPath), eval_paths(heuristikOhnePfadkosten, RestPaths).
eval_paths(heuristikMitPfadkosten,  [FirstPath|RestPaths]) :- eval_path(heuristikMitPfadkosten,  FirstPath), eval_paths(heuristikMitPfadkosten,  RestPaths).

insert_new_paths_informed([],OldPaths,OldPaths).
insert_new_paths_informed([FirstNewPath|RestNewPaths],OldPaths,AllPaths):-
  insert_path_informed(FirstNewPath,OldPaths,FirstInserted),
  insert_new_paths_informed(RestNewPaths,FirstInserted,AllPaths).

% Wenn der Pfad billiger ist, dann wird er vorn angefügt. (Alte Pfade sind ja sortiert.)
% Wenn er nicht billiger ist, wird er in den Rest einsortiert und der Kopf der Openliste bleibt Kopf der neuen Liste
insert_path_informed(NewPath,[],[NewPath]).
insert_path_informed(NewPath,[FirstPath|RestPaths],[NewPath,FirstPath|RestPaths]) :- cheaper(NewPath, FirstPath), !.
insert_path_informed(NewPath,[FirstPath|RestPaths],[FirstPath|NewRestPaths])      :- insert_path_informed(NewPath, RestPaths, NewRestPaths).

cheaper([(_,_,V1)|_],[(_,_,V2)|_]) :- V1 =< V2.
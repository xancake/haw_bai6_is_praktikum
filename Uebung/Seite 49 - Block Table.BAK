block(block1).
block(block2).
block(block3).
block(block4).

table(table1).

on(block1,block2).
on(block2,table1).
on(block3,block4).
on(block4,table1).


above(X,Y) :- block(X), block(Y), on(X,Y).
above(X,Y) :- block(X), table(Y), on(X,Y).
above(X,Y) :- block(X), block(Z), on(X,Z), above(Z,Y).


%% Beispiel-Abfragen
%% above(block3, block4).
%% above(X, block1).
%% above(X, block2).
%% above(X, table1).
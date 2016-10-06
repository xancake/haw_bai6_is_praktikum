on(block1,block2).
on(block1,block3).
on(block2,table1).
on(block3,table2).
above(X,Y) :- on(X,Y).
above(X,Y) :- on(X,Z), above(Z,Y).
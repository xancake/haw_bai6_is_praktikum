italienisch(pizza).
italienisch(spaghetti).
indisch(dahl).
indisch(curry).
mild(dahl).
scharf(curry).

isst(frank, X) :- italienisch(X).
isst(frank, X) :- indisch(X) , mild(X).
isst(anna,  X) :- indisch(X).
isst(anna, hamburger) :- true.
isst(kurt, X) :- isst(anna, X).
isst(kurt, pizza) :- true.
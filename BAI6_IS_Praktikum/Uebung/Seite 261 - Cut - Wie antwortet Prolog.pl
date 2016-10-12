huhu(b).
huhu(p).
hihi(i).
hihi(j).
hihi(k).
hallo(o).
hallo(p).
hallo(q).
foo(a,a).
foo(X,Y):- huhu(b),hihi(X),!,hallo(Y).
foo(X,c):- hallo(X),!,huhu(X).

%%?- foo(X,Y).
%%?- foo(j,Y).
%%?- foo(p,Y).
%%?- foo(o,Y).
%%?- foo(X,c).
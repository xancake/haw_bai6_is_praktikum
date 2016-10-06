pferd(fury).
hase(bunny).
hund(w).

schneller(P,H):- pferd(P),hund(H).
schneller(w,A):- hase(A).
schneller(X,Z):- schneller(X,Y),schneller(Y,Z).

%% Anfrage
%% schneller(fury,bunny).
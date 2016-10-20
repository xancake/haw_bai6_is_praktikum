:- consult('../Praktikum 1/stammbaum').

:- assert(lex(ist,      v, singular, ist)).
:- assert(lex(sind,     v, plural,   ist)).
:- assert(lex(der,    art,        _, artikel)).
:- assert(lex(die,    art,        _, artikel)).
:- assert(lex(das,    art,        _, artikel)).
:- assert(lex(von,      p,        _, von)).
:- assert(lex(vom,      p,        _, von)).
:- assert(lex(wer,     ip, singular, _Wer)).
:- assert(lex(wessen,  ip, singular, _Wer)).

%% Alle Namen hinzufügen
:- assert(lex(X, en, singular, X) :- mann(X)).
:- assert(lex(X, en, singular, X) :- frau(X)).

%% Alle Prädikate aus stammbaum mit 2 Parametern hinzufügen (TODO: wie schränkt man das entsprechend ein?)
:- assert(lex(X,  n, singular, X) :- functor(_, X, 2)).

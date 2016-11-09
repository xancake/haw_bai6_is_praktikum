:- consult('../Praktikum 1/stammbaum').

%% Lexikon
lex(von,    p,  _,         von).
lex(wer,    ip, _,        _Wer).
lex(ist,    v,  singular,  ist).
lex(sind,   v,  plural,    ist).
lex(und,    k,  plural,    und).

lex(X, en, singular, maskulin, X) :- mann(X).
lex(X, en, singular, feminin,  X) :- frau(X).

lex(der, art, singular, maskulin, nominativ, artikel).
lex(die, art, singular, feminin,  nominativ, artikel).
lex(das, art, singular, neutrum,  nominativ, artikel).
lex(dem, art, singular, maskulin, dativ,     artikel).
lex(der, art, singular, feminin,  dativ,     artikel).
lex(dem, art, singular, neutrum,  dativ,     artikel).
lex(die, art, plural,   feminin,  nominativ, artikel).

%lex(verheiratet,     n, singular, _,        verheiratet).
lex(kind,            n, singular, neutrum,  _, kind).
lex(kinder,          n, plural,   feminin,  _, kind).
lex(sohn,            n, singular, maskulin, _, sohn).
lex(soehne,          n, plural,   feminin,  _, sohn).
lex(tochter,         n, singular, feminin,  _, tochter).
lex(toechter,        n, plural,   feminin,  _, tochter).
lex(eltern,          n, plural,   feminin,  _, elter).
lex(vater,           n, singular, maskulin, _, vater).
lex(mutter,          n, singular, feminin,  _, mutter).
lex(geschwister,     n, plural,   feminin,  _, geschwister).
lex(bruder,          n, singular, maskulin, _, bruder).
lex(brueder,         n, plural,   feminin,  _, bruder).
lex(schwester,       n, singular, feminin,  _, schwester).
lex(schwestern,      n, plural,   feminin,  _, schwester).
lex(tante,           n, singular, feminin,  _, tante).
lex(tanten,          n, plural,   feminin,  _, tante).
lex(onkel,           n, singular, maskulin, _, onkel).
lex(oenkel,          n, plural,   feminin,  _, onkel).
lex(neffe,           n, singular, maskulin, _, neffe).
lex(neffen,          n, singular, feminin,  _, neffe).
lex(nichte,          n, singular, feminin,  _, nichte).
lex(nichten,         n, plural,   feminin,  _, nichte).
lex(cousin,          n, singular, maskulin, _, cousin).
lex(cousins,         n, plural,   feminin,  _, cousin).
lex(cousine,         n, singular, feminin,  _, cousine).
lex(cousinen,        n, plural,   feminin,  _, cousine).
lex(grosseltern,     n, plural,   feminin,  _, grosselter).
lex(oma,             n, singular, feminin,  _, oma).
lex(omas,            n, plural,   feminin,  _, oma).
lex(grossmutter,     n, singular, feminin,  _, oma).
lex(grossmuetter,    n, plural,   feminin,  _, oma).
lex(opa,             n, singular, maskulin, _, opa).
lex(opas,            n, plural,   feminin,  _, opa).
lex(grossvater,      n, singular, maskulin, _, opa).
lex(grossvaeter,     n, plural,   feminin,  _, opa).
lex(grosstante,      n, singular, feminin,  _, grosstante).
lex(grosstanten,     n, plural,   feminin,  _, grosstante).
lex(grossonkel,      n, singular, maskulin, _, grossonkel).
lex(grossoenkel,     n, plural,   feminin,  _, grossonkel).
lex(halbbruder,      n, singular, maskulin, _, halbbruder).
lex(halbbrueder,     n, plural,   feminin,  _, halbbruder).
lex(halbschwester,   n, singular, feminin,  _, halbschwester).
lex(halbschwestern,  n, plural,   feminin,  _, halbschwester).
lex(halbgeschwister, n, plural,   feminin,  _, halbgeschwister).

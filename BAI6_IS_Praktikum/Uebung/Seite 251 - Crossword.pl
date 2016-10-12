word(abalone,a,b,a,l,o,n,e).
word(abandon,a,b,a,n,d,o,n).
word(enhance,e,n,h,a,n,c,e).
word(anagram,a,n,a,g,r,a,m).
word(connect,c,o,n,n,e,c,t).
word(elegant,e,l,e,g,a,n,t).

crosswd(H1,H2,H3,V1,V2,V3):-
        word(H1, _H1Egal1, H1V1, _H1Egal2, H1V2, _H1Egal3, H1V3, _H1Egal4),
        word(H2, _H2Egal1, H2V1, _H2Egal2, H2V2, _H2Egal3, H2V3, _H2Egal4),
        word(H3, _H3Egal1, H3V1, _H3Egal2, H3V2, _H3Egal3, H3V3, _H3Egal4),
        word(V1, _V1Egal1, H1V1, _V1Egal2, H2V1, _V1Egal3, H3V1, _V1Egal4),
        word(V2, _V2Egal1, H1V2, _V2Egal2, H2V2, _V2Egal3, H3V2, _V2Egal4),
        word(V3, _V3Egal1, H1V3, _V3Egal2, H2V3, _V3Egal3, H3V3, _V3Egal4).
        
%% crosswd(H1,H2,H3,V1,V2,V3).
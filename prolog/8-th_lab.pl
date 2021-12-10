# 7 variant
if(A,B,C):-A,B;\+A,C.
d(X, [X|T], T).
d(X, [H|T], [H|NewT]):-d(X,T,NewT).

place_rep(0, _, []).
place_rep(K, L, [H|R]):- K>0, K1 is K - 1, d(H, L, _), place_rep(K1,L,R).

permut([], []).
permut(L, [H|R]):- d(H, L, R1), permut(R1, R).
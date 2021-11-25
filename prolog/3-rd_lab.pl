# 7 variant
if(A,B,C):-A,B;\+A,C.
max(X,Y,Z):- (X>Y, Z is X; X<Y, Z is Y).

max(X,Y,U,Z):-(X>Y, X>U, Z is X; (Y>X, Y>U, Z is Y; U>X, U>Y, Z is U)).

fact_up(N,X):- fact_up(1,N,X1), X is X1.
fact_up(N1, N, X):- if(N1 < N, (fact_up(N1 + 1, N, X1), X is X1*N1), X is N1).


fact_down(1,1).
fact_down(N,X):- C is N-1, fact_down(C, X1), X=X1*N.


fib_up(1,1).
fib_up(2,1).
fib_up(N,X):- N1 is N-1,fib_up(N1,X1),N2 is N-2,fib_up(N2,X2),X is X1+X2.

fib_down(1,1).
fib_down(2,1).
fib_down(N,X):-fib_down(3,N,1,1,X1),X is X1.
fib_down(N1,N,S1,S2,X):-if(N1<N,(S3 is S1+S2,N2 is N1+1,fib_down(N2,N,S3,S1,X1),X is X1),
                       X is S1+S2).

sum_of_digit_up(N,X):- sum_of_digit_up(1, 10, N, X1), X is X1.
sum_of_digit_up(N1, N2, N,X):-if(N1<N, (N3 is N2*10, sum_of_digit_up(N2, N3, N, X1),
                                                            X is X1 + (N mod N2) div N1), X is 0).

sum_of_digit_down(0,0).
sum_of_digit_down(N,X):-N1 is N div 10,sum_of_digit_down(N1,X1),X is X1+N mod 10.


mult_of_digit_down(1,1).
mult_of_digit_down(N,X):- if(N>=10, (N1 is N div 10, P is N mod 10, mult_of_digit_down(N1,X1), X is X1 * P), X is N).

mult_of_digit_up(N,X):- if(N > 10, (mult_of_digit_up(N, 10, P), X is P), X is N * 1).
mult_of_digit_up(N, C, X):-G is N div C, if(G > 0, (D is (N mod C) div (C div 10),C1 is C * 10,
                                                                mult_of_digit_up(N,C1, P), X is D * P), X is X).

count_of_digits_higher3(N,X):-if(N>10,
                                      (A is N mod 10, N1 is N div 10, A1 is A mod 2,
                                                if(A > 3, (count_of_digits_higher3(N1, R), X is R + A1),
                                                          ((count_of_digits_higher3(N1, R)), X is R))
                                                   ),
                                      (A is N mod 2, A =:= 1, X is 1; X is 0)
                                 ).

min(X,Y,Z):-if(X<Y,Z is X,Z is Y).
nod(X,Y,Z):-if(X=Y,Z is X,
                (max(X,Y,X1),min(X,Y,X2),N is X1-X2,nod(N,X2,X4),Z is X4)).

koldel(_,0,0):-!.
koldel(X,L,K):-X mod L=:=0,L1 is L-1,koldel(X,L1,K1),K is K1+1;X mod L=\=0,
    L1 is L-1,koldel(X,L1,K1),K is K1.
kol(X,Y):-koldel(X,X,Y).

simple(X):-kol(X,Y),Y=:=2.

var7(N,X):-sum_of_digit_up(N, S), var7(N, S, 1, X).
var7(N, S, D, X):- if(D =< N div 2,
                        (J is N mod D, D1 is D + 1, if(J =:= 0,
                            (sum_of_digit_up(D, K), if(K < S,
                                    (var7(N, S, D1, L), X is L * D),
                                    (var7(N, S, D1, L), X is L)
                                )),
                            (var7(N, S, D1, L), X is L))),
                        (X is 1)
                    ).
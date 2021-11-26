# 7 variant
if(A,B,C):-A,B;\+A,C.
init():-use_module(library(lists)).

min(X,Y,Z):-if(X<Y,Z is X,Z is Y).
max(X,Y,Z):-if(X>Y,Z is X,Z is Y).


list_read(X):-write("Введите количество элементов"),nl,read(N)
        ,nl, write("Введите элементы"),nl,list_read(N,X1),append(X1,[],X),write(X).
list_read(0,[]):-!.
list_read(N,X):-N1 is N-1,list_read(N1,X1),read(E),append(X1,[E],X).

list_print([H|X]):- write(H), write(" "), list_print(X).


sum_list_down([], 0).
sum_list_down([H|T], S):- sum_list_down([T], R), S is R + H.

sum_list_up([], 0).
sum_list_up(X,S):- last(X,R), delete(X,R,X1), sum_list_up(X1, S1), S is S1 + R.

list_el_numb(X,E,N):-nth0(N,X,E).
list_el_numb1():-list_read(X),nl,nl,write("Введите элемент для проверки")
            ,read(E),
            if(list_el_numb(X,E,N), (write("На позиции:"),write(N))
                                    , write("Не нашёлся")).


list_el_numb2():-list_read(X),nl,nl,write("Введите номер для проверки")
            ,read(N),
            if(list_el_numb(X,E,N), (write("Элемент:"),write(E))
                                    ,write("Нет такой позиции в списке")).

min_list_down([X|[]], X).
min_list_down([H|T], M):-min_list_down(T, M1), min(H, M1, M).

min_list_up([X|[]], X).
min_list_up(X,M):-last(X,K),delete(X,K,X1),min_list_up(X1,M1),min(K,M1,M).

max_list_down([X|[]], X).
max_list_down([H|T],M):-max_list_down(T,M1),max(H,M1,M).

min_list():-list_read(X),min_list_down(X,S),nl,write(S).

exist():-list_read(X),nl,nl,write("Введите элемент для проверки")
            ,read(E),member(E,X).

rev():-list_read(X),reverse(X,L),write(L).

p(Sub,X):-subset(Sub,X).

del(L, I, L):-L = [], !; I < 0, !.
del([_H|T], 0, T):-!.
del([H|T], I, [H|TW]):-N is I - 1,del(T, N, TW).

del_all:-list_read(X),nl,nl,write("Введите элемент для проверки")
            ,read(E),del_all(X,E,L),write(L).
del_all(X,E,L):-if(member(E,X),(delete(X,E,X1),del_all(X1,E,L)), append(X,[],L)).

set_is(X):-is_set(X).

l_s(X):-list_to_set(X,L),nl,write(L).

count(X,E):-count(X,E,N),nl,write(N).
count([],_,0).
count([H|T],E,N):-H =:= E,count(T,E,N1),N is N1+1; H=\=E,count(T,E,N1),N is N1.

len(X,N):-length(X,N).

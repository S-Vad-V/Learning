% размещение по к c повторений
eval([],_).
eval([Head|Tail],Set):-member(Head,Set),eval(Tail,Set).
permutation_with(Set, N,[HEAD|TAIL]):- length(List,5), eval(List,Set),

% перестановки
permutation(List, Permutation):-
  length(List, Length),
  permutation(List, Length, Permutation).

% размещение по к без повторений
permutation(_List, 0, []):-!.
permutation(List, PermutationLength, [Head|PermutationTail]):-
    select(Head, List, ListTail),
    PermutationTailLength is PermutationLength - 1,
    permutation(ListTail, PermutationTailLength, PermutationTail).

% подмножества
subset([], []).
subset([E|Tail], [E|NTail]):-
  subset(Tail, NTail).
subset([_|Tail], NTail):-
  subset(Tail, NTail).

% сочетание по к без повторений
comb(_, 0, []).
comb([X|Tail], N, [X|Comb]) :- N > 0, N1 is N-1, comb(Tail, N1, Comb).
comb([_|Tail], N, Comb) :- N > 0, comb(Tail, N, Comb).

% сочетание по к с повторений
comb(_, 0, []).
comb([X|Tail], N, [X|Comb]) :- N > 0, N1 is N-1, comb(Tail, N1, Comb).
comb([_|Tail], N, Comb) :- N > 0, comb(Tail, N, Comb).

% 2
count([],_,0).
count([Head|Tail],E,N):-Head = E,count(Tail,E,N1),N is N1+1; not(Head=E),count(Tail,E,N1),N is N1.
ex2(X):-
	permutation_with([a,b,c,d,e,f],5,X),
	count(X,a,2).

% 3
ex3:-append([], [a,b,c,d,e,f], Set), length(List,5), eval(List,Set),
    count(List, a, N1),
    list_to_set(List, NewSet),
    length(NewSet, N2),
    (N1=2-> (N2 = 4 -> write(List))).

% 4
ex4:-append([], [a,b,c,d,e,f], Set),length(List,5), eval(List,Set),
    list_to_set(List,S),
    length(S, N),
    (N=4->write(List)).

% 5
ex5([], _, _,Flag):-Flag1 is 1, Flag is Flag1.
ex5([Head|Tail], List, K, Flag):-
    count(List, Head, N),
    (N<3->F1 is 1;F1 is 0),
    (N=2-> (not(K=2) ->F is 1;F is 0);F is F1),
    (F=1->K1 is K+1, ex5(Tail, List, K1, Flag1);Flag1 is 0),
    Flag is Flag1.
ex5:-append([], [a,b,c,d,e,f], Set),length(List,6), eval(List,Set),
    list_to_set(List,S),
    ex5(S, List, 0, Flag),
    (Flag=1->write(List)).

% 6
ex6([], _, Count_of_two,Count_of_three,Flag):-(Count_of_two=1->(Count_of_three=1->Flag1 is 1;Flag1 is 0);Flag1 is 0), Flag is Flag1.
ex6([Head|Tail], List, Count_of_two, Count_of_three, Flag):-
    count(List, Head, N),
    (N=1 -> F1 is 1;F1 is 0),
    (N=2 -> (Count_of_two=0 -> NCount_of_two is Count_of_two+1, F2 is 1;F2 is 0);NCount_of_two is Count_of_two, F2 is F1),
    (N=3 -> (Count_of_three=0 -> NCount_of_three is Count_of_three+1, F3 is 1;F3 is 0);NCount_of_three is Count_of_three,F3 is F2),
    (F3=1->ex6(Tail, List, NCount_of_two, NCount_of_three,Flag1);Flag1 is 0),
    Flag is Flag1.
ex6:-append([], [a,b,c,d,e,f], Set),length(List,7), eval(List,Set),
    list_to_set(List,S),
    ex6(S, List, 0, 0,Flag),
    (Flag=1->write(List)).

% 7
ex7([], _, Count_of_two,Count_of_three,Flag):-(Count_of_two=2->(Count_of_three=1->Flag1 is 1;Flag1 is 0);Flag1 is 0), Flag is Flag1.
ex7([Head|Tail], List, Count_of_two, Count_of_three, Flag):-
    count(List, Head, N),
    (N=1 -> F1 is 1;F1 is 0),
    (N=2 -> (Count_of_two<3 -> NCount_of_two is Count_of_two+1, F2 is 1;F2 is 0);NCount_of_two is Count_of_two, F2 is F1),
    (N=3 -> (Count_of_three=0 -> NCount_of_three is Count_of_three+1, F3 is 1;F3 is 0);NCount_of_three is Count_of_three, F3 is F2),
    (F3=1->ex7(Tail, List, NCount_of_two, NCount_of_three,Flag1);Flag1 is 0),
    Flag is Flag1.
ex7:-append([], [a,b,c,d,e,f], Set),length(List,9), eval(List,Set),
    list_to_set(List,S),
    ex7(S, List, 0, 0,Flag),
    (Flag=1->write(List)).

% 8
ex8:-append([], [a,b,c,d,e,f], Set),length(List,4), eval(List,Set),
  later_a(A), count(List, A, NA), (NA>2-> write(List)).

% 9
ex9:-append([], [a,b,c,d,e,f], Set),length(List,7), eval(List,Set),
  later_a(A), count(List, A, NA), (NA>2-> write(List)).

% 10
ex10:-append([], [a,b,c,d,e,f], Set),length(List,7), eval(List,Set),
  list_to_set(List,S), length(S, NA),(NA=4-> write(List)).

% 11
ex11([], _, Count_of_two,Count_of_more,Flag):-(Count_of_two=4->(Count_of_more=0->Flag1 is 1;Flag1 is 0);Flag1 is 0), Flag is Flag1.
ex11([Head|Tail], List, Count_of_two, Count_of_more, Flag):-
    count(List, Head, N),
    (N=2 -> (Count_of_two < 4 -> NCount_of_two is Count_of_two+1, F2 is 1;F2 is 0);NCount_of_two is Count_of_two, F2 is 1),
    (N > 2 -> NCount_of_more is Count_of_more+1, F3 is 0;NCount_of_more is Count_of_more, F3 is F2),
    (F3=1->ex11(Tail, List, NCount_of_two, NCount_of_more,Flag1);Flag1 is 0),
    Flag is Flag1.
ex11(N):-append([], [a,b,c,d,e,f], Set),length(List,N), eval(List,Set),
    list_to_set(List,S),
    ex11(S, List, 0, 0,Flag),
    (Flag=1->write(List)).




	
	
	
	
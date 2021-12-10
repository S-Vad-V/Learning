# 7 variant
if(A,B,C):-A,B;\+A,C.

in_list([El|_],El).
in_list([_|T],El):-in_list(T,El).
sprava_next(A,B,[C]):-fail.
sprava_next(A,B,[A|[B|Tail]]).
sprava_next(A,B,[_|List]):-sprava_next(A,B,List).
sleva_next(A,B,[C]):-fail.
sleva_next(A,B,[B|[A|Tail]]).
sleva_next(A,B,[_|List]):-sleva_next(A,B,List).
next_to(A,B,List):-sprava_next(A,B,List).
next_to(A,B,List):-sleva_next(A,B,List).
el_no(List,Num,El):-el_no(List,Num,1,El).
el_no([H|_],Num,Num,H):-!.
el_no([_|Tail],Num,Ind,El):-Ind1 is Ind+1,el_no(Tail,Num,Ind1,El).

ex1:- Houses=[_,_,_,_,_],
		in_list(Houses,[red,english,_,_,_]),
		in_list(Houses,[_,spanish,_,dog,_]),
		in_list(Houses,[green,_,coffee,_,_]),
		in_list(Houses,[_,ukrain,tea,_,_]),
		sprava_next([green,_,_,_,_],[white,_,_,_,_],Houses),
		in_list(Houses,[_,_,_,ulitka,old_gold]),
		in_list(Houses,[yellow,_,_,_,kool]),
		el_no(Houses,3,[_,_,milk,_,_]),
		el_no(Houses,1,[_,norway,_,_,_]),
		next_to([_,_,_,_,chester],[_,_,_,fox,_],Houses),
		next_to([_,_,_,_,kool],[_,_,_,horse,_],Houses),
		in_list(Houses,[_,_,orange,_,lucky]),
		in_list(Houses,[_,japan,_,_,parlament]),
		next_to([_,norway,_,_,_],[blue,_,_,_,_],Houses),
		in_list(Houses,[_,WHO1,water,_,_]),
		in_list(Houses,[_,WHO2,_,zebra,_]),
		write(Houses),nl,
		write(WHO1),nl,
                write(WHO2),!.



name(чернов).
name(рыжов).
name(белокуров).
color(брюнет).
color(рыжий).
color(блондин).
is_equals(белокуров,рыжий).
is_equals(X,Y):-name(X),X=белокуров,
    color(Y),Y=рыжий.
is_equals(X,Y):-name(X),X=чернов,
    color(Y),not(Y=брюнет).
is_equals(X,Y):-name(X),X=рыжов,
    color(Y),not(Y=рыжий).
is_equals(X,Y):-name(X),X=белокуров,
    color(Y),not(Y=блондин).
ex2:-is_equals(белокуров,X),
	is_equals(чернов,Y),
	is_equals(рыжов,Z),
        not(X=Y), not(X=Z), not(Y=Z),
	write("чернов - "),write(Y),nl,
	write("рыжов - "),write(Z),nl,
	write("белокуров - "),write(X),!.
	


people(наташа).
people(аня).
people(валя).
dress(синий).
dress(зеленый).
dress(белый).
shoes(синий).
shoes(зеленый).
shoes(белый).
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=аня,Y=Z.
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=валя,not(Y=Z),not(Y=белый),not(Z=белый).
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=наташа,not(Y=Z),Z=зеленый.
ex3:-is_equals_2(аня,Y1,Z1),is_equals_2(валя,Y2,Z2),is_equals_2(наташа,Y3,Z3),
    not(Y1=Y2),not(Y1=Y3),not(Y3=Y2),
    not(Z1=Z2),not(Z1=Z3),not(Z3=Z2),
    write("у ани цвет платья "),write(Y1),write(", а цвет туфель "),write(Z1),nl,
    write("у вали цвет платья "),write(Y2),write(", а цвет туфель "),write(Z2),nl,
    write("у наташи цвет платья "),write(Y3),write(", а цвет туфель "),write(Z3),!.



second_name(борисов).
second_name(иванов).
second_name(семенов).
profession(слесарь).
profession(токарь).
profession(сварщик).
greate_tocar(X):-second_name(X),X=семенов.
have_sister(Y):-second_name(Y),Y=борисов.
ex4:-second_name(SL),
        second_name(T), not(T = SL),
        second_name(SV), not(SV = T), not(SV = SL),
        not(have_sister(SL)),
        not(greate_tocar(SL)),
        not(greate_tocar(T)),
	write("слесарь - это "),write(SL),nl,
	write("токарь - это "),write(T),nl,
	write("сварщик - это "),write(SV),!.



container(бутылка).
container(стакан).
container(кувшин).
container(банка).
liquid(молоко).
liquid(лимонад).
liquid(квас).
liquid(вода).
distribution(X,Y):-container(X),liquid(Y),
	X=бутылка,
	not(Y=вода),not(Y=молоко).
distribution(X,Y):-container(X),liquid(Y),
	X=банка,
	not(Y=вода),not(Y=лимонад),not(Y=молоко).
distribution(X,Y):-container(X),liquid(Y),
	X=кувшин,
	not(Y=лимонад),not(Y=квас).
distribution(X,Y):-container(X),liquid(Y),
	X=стакан,
	not(Y=молоко).
ex5:-distribution(бутылка,Y1),
	distribution(стакан,Y2),
	distribution(банка,Y3),
	distribution(кувшин,Y4),
	not(Y1=Y2),not(Y1=Y3),not(Y1=Y4),not(Y2=Y3),
	not(Y2=Y4),not(Y3=Y4),
	write("в бутылке - "),write(Y1),nl,
	write("в стакане - "),write(Y2),nl,
	write("в банке - "),write(Y3),nl,
	write("в кувшине - "),write(Y4),!.
	
	
	
	
profession_2(танцор).
profession_2(художник).
profession_2(певец).
profession_2(писатель).
dont_know(V,L):-
    L=писатель, !, not(V=художник);
    true.
ex6:-profession_2(V),not(V=певец),
     profession_2(L),not(L=певец),
     profession_2(P),not(P=писатель),not(P=художник),
     profession_2(S),not(S=писатель),
     not(V=писатель),
     not(L=V),not(P=V),not(P=L),
     not(S=V),not(S=L),not(S=P),
     dont_know(V,L),
	 write("воронов - "),write(V),nl,
	 write("павлов - "),write(P),nl,
	 write("левицкий - "),write(L),nl,
	 write("сахаров - "),write(S),!.


friend(майкл).
friend(саймон).
friend(ричард).
sport(баскетбол).
sport(теннис).
sport(крикет).
nation(американец).
nation(израильтянин).
nation(австралиец).
place(1).
place(2).
place(3).
solve(Mans):-
	Mans=[man(X,XN,XS,XM),
	man(Y,YN,YS,YM),
	man(Z,ZN,ZS,ZM)],
	friend(X),friend(Y),friend(Z),
	not(X=Y),not(X=Z),not(Z=Y),
	sport(XS),sport(YS),sport(ZS),
	not(XS=YS),not(XS=ZS),not(ZS=YS),
	nation(XN),nation(YN),nation(ZN),
	not(XN=YN),not(XN=ZN),not(ZN=YN),
	place(XM),place(YM),place(ZM),
	not(XM=YM),not(XM=ZM),not(ZM=YM),

	member(man(майкл, _, баскетбол, MM), Mans),
	member(man(_, американец, _, AM), Mans), MM < AM,
	member(man(саймон, израильтянин, _, SM), Mans),
	member(man(_, _, теннис, TM), Mans), SM < TM,
	member(man(_, _, крикет, 1), Mans).
ex7:-
  solve(X), X = [man(_, _, _, 1), man(_, _, _, 2), man(_, _, _, 3)],
  write(X),!.




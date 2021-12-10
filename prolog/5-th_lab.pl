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



name(������).
name(�����).
name(���������).
color(������).
color(�����).
color(�������).
is_equals(���������,�����).
is_equals(X,Y):-name(X),X=���������,
    color(Y),Y=�����.
is_equals(X,Y):-name(X),X=������,
    color(Y),not(Y=������).
is_equals(X,Y):-name(X),X=�����,
    color(Y),not(Y=�����).
is_equals(X,Y):-name(X),X=���������,
    color(Y),not(Y=�������).
ex2:-is_equals(���������,X),
	is_equals(������,Y),
	is_equals(�����,Z),
        not(X=Y), not(X=Z), not(Y=Z),
	write("������ - "),write(Y),nl,
	write("����� - "),write(Z),nl,
	write("��������� - "),write(X),!.
	


people(������).
people(���).
people(����).
dress(�����).
dress(�������).
dress(�����).
shoes(�����).
shoes(�������).
shoes(�����).
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=���,Y=Z.
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=����,not(Y=Z),not(Y=�����),not(Z=�����).
is_equals_2(X,Y,Z):-people(X),dress(Y),shoes(Z),X=������,not(Y=Z),Z=�������.
ex3:-is_equals_2(���,Y1,Z1),is_equals_2(����,Y2,Z2),is_equals_2(������,Y3,Z3),
    not(Y1=Y2),not(Y1=Y3),not(Y3=Y2),
    not(Z1=Z2),not(Z1=Z3),not(Z3=Z2),
    write("� ��� ���� ������ "),write(Y1),write(", � ���� ������ "),write(Z1),nl,
    write("� ���� ���� ������ "),write(Y2),write(", � ���� ������ "),write(Z2),nl,
    write("� ������ ���� ������ "),write(Y3),write(", � ���� ������ "),write(Z3),!.



second_name(�������).
second_name(������).
second_name(�������).
profession(�������).
profession(������).
profession(�������).
greate_tocar(X):-second_name(X),X=�������.
have_sister(Y):-second_name(Y),Y=�������.
ex4:-second_name(SL),
        second_name(T), not(T = SL),
        second_name(SV), not(SV = T), not(SV = SL),
        not(have_sister(SL)),
        not(greate_tocar(SL)),
        not(greate_tocar(T)),
	write("������� - ��� "),write(SL),nl,
	write("������ - ��� "),write(T),nl,
	write("������� - ��� "),write(SV),!.



container(�������).
container(������).
container(������).
container(�����).
liquid(������).
liquid(�������).
liquid(����).
liquid(����).
distribution(X,Y):-container(X),liquid(Y),
	X=�������,
	not(Y=����),not(Y=������).
distribution(X,Y):-container(X),liquid(Y),
	X=�����,
	not(Y=����),not(Y=�������),not(Y=������).
distribution(X,Y):-container(X),liquid(Y),
	X=������,
	not(Y=�������),not(Y=����).
distribution(X,Y):-container(X),liquid(Y),
	X=������,
	not(Y=������).
ex5:-distribution(�������,Y1),
	distribution(������,Y2),
	distribution(�����,Y3),
	distribution(������,Y4),
	not(Y1=Y2),not(Y1=Y3),not(Y1=Y4),not(Y2=Y3),
	not(Y2=Y4),not(Y3=Y4),
	write("� ������� - "),write(Y1),nl,
	write("� ������� - "),write(Y2),nl,
	write("� ����� - "),write(Y3),nl,
	write("� ������� - "),write(Y4),!.
	
	
	
	
profession_2(������).
profession_2(��������).
profession_2(�����).
profession_2(��������).
dont_know(V,L):-
    L=��������, !, not(V=��������);
    true.
ex6:-profession_2(V),not(V=�����),
     profession_2(L),not(L=�����),
     profession_2(P),not(P=��������),not(P=��������),
     profession_2(S),not(S=��������),
     not(V=��������),
     not(L=V),not(P=V),not(P=L),
     not(S=V),not(S=L),not(S=P),
     dont_know(V,L),
	 write("������� - "),write(V),nl,
	 write("������ - "),write(P),nl,
	 write("�������� - "),write(L),nl,
	 write("������� - "),write(S),!.


friend(�����).
friend(������).
friend(������).
sport(���������).
sport(������).
sport(������).
nation(����������).
nation(������������).
nation(����������).
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

	member(man(�����, _, ���������, MM), Mans),
	member(man(_, ����������, _, AM), Mans), MM < AM,
	member(man(������, ������������, _, SM), Mans),
	member(man(_, _, ������, TM), Mans), SM < TM,
	member(man(_, _, ������, 1), Mans).
ex7:-
  solve(X), X = [man(_, _, _, 1), man(_, _, _, 2), man(_, _, _, 3)],
  write(X),!.




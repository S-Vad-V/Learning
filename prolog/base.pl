% 7.1
parent(sasha, masha).
parent(olya, masha).
parent(olya, pasha).
parent(kolya, dima).
parent(masha, dima).
parent(masha, son).
parent(son, liza).
parent(son, pasha).
parent(dasha, liza). % Добавили мать лизы
% 7.2
child(Y, X):-parent(X,Y).

% Добавили пол
male(sasha).
male(pasha).
male(kolya).
male(dima).
male(son).
female(olya).
female(masha).
female(liza).
female(dasha).

% Добавили ещё придикатов
% 7.3
mother(X, Y):-parent(X, Y), female(X).
mother_print(X):-parent(Y, X), female(Y), write(Y).
% 7.4
father(X, Y):-parent(X,Y), male(X).
father_print(X):-parent(Y, X), male(Y), write(Y). % Выводит отца X

son(X, Y):-child(X, Y), male(X).
douther(X, Y):-child(X, Y), female(X).
% 7.5
brother(X, Y):-parent(Z, X), parent(Z, Y), male(X), dif(X, Y).
brother(X):- parent(X, Z), parent(Y, Z), male(Y), dif(X, Y), write(Y).
% 7.6
sister(X, Y):-parent(Z, X), parent(Z, Y), female(X), dif(X, Y).
sisters(X):-parent(Y, X), child(Z, Y), female(Z), write(Z). % Все сестрый Х
% 7.7
b_s(X, Y):-parent(X, Z), parent(Z, Y).
b_s(X):-parent(Z, X), parent(Z, Y), write(Y).
% 7.8
grand_pa(X, Y):-parent(Z, Y), parent(X, Z), male(X). % Является ли дедушкой
grand_pas(X):-parent(Y, X), parent(Z, Y), male(Z), write(Z). % Всех дедушек
% 7.9
grand_so(X, Y):-child(Z, Y), child(K, Z), male(K).
grand_sons(X):-child(Y, X), child(Z, Y), male(Z), write(Z).
% 7.10
grand_pa_and_son(X, Y):-father(X, Z), son(Y, Z); father(Y, Z), son(X, Z). 
% 7.11
grand_ma_and_son(X, Y):-mother(X, Z), son(Y, Z); mother(Y, Z), son(X, Z).
% 7.12
uncle(X, Y):-parent(Y, Z), brother(X, Z).
uncle_print(X):-parent(X, Z), brother(Y, Z), write(Y).
% 7.13
aunt(X, Y):-parent(Y, Z), sister(X, Z).
aunt_print(X):-parent(X, Z), sister(Y, Z), write(Y).
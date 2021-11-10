high(lebron, 206).
high(curry, 191).
high(irving, 191).
high(durant, 208).
high(antetokounmpo, 211).
high(harden, 196).
high(jokich, 211).
high(davis, 208).
high(mccollum, 190).
high(murray, 191).
high(doncic, 191).
high(onil, 216).
high(lillard, 188).
high(bryant, 198).
high(jordan, 198).

possition(lebron, sf).
possition(curry, pg).
possition(irving, pg).
possition(durant, sf).
possition(jokich, c).
possition(antetokounmpo, pf).
possition(harden, sg).
possition(davis, pf).
possition(lillard, pg).
possition(mccollum, sg).
possition(murray, pg).
possition(jordan, sg).
possition(doncic, pg).
possition(bryant, sg).
possition(onil, c).

club(lebron, lal).
club(curry, gsw).
club(irving, bkn).
club(durant, bkn).
club(jokich, dnw).
club(antetokounmpo, mil).
club(harden, bkn).
club(davis, lal).
club(lillard, por).
club(mccollum, por).
club(murray, dnw).
club(doncic, dal).
club(jordan, nan).
club(bryant, nan).
club(onil, nan).

age(lebron, 36).
age(curry, 33).
age(irving, 29).
age(durant, 32).
age(jokich, 26).
age(antetokounmpo, 26).
age(harden, 32).
age(davis, 28).
age(lillard, 31).
age(mccollum, 30).
age(murray, 24).
age(doncic, 22).
age(jordan, 58).
age(bryant, 41).
age(onil, 49).

draft_year(lebron, 2003).
draft_year(curry, 2009).
draft_year(irving, 2011).
draft_year(durant, 2007).
draft_year(jokich, 2014).
draft_year(antetokounmpo, 2013).
draft_year(harden, 2009).
draft_year(davis, 2012).
draft_year(lillard, 2012).
draft_year(mccollum, 2013).
draft_year(murray, 2016).
draft_year(doncic, 2018).
draft_year(jordan, 1984).
draft_year(bryant, 1996).
draft_year(onil, 1992).

draft_pick(lebron, 1).
draft_pick(curry, 7).
draft_pick(irving, 1).
draft_pick(durant, 2).
draft_pick(jokich, 41).
draft_pick(antetokounmpo, 15).
draft_pick(harden, 3).
draft_pick(davis, 1).
draft_pick(lillard, 6).
draft_pick(mccollum, 10).
draft_pick(murray, 7).
draft_pick(doncic, 3).
draft_pick(jordan, 3).
draft_pick(bryant, 13).
draft_pick(onil, 1).

game_played(lebron, 1310).
game_played(curry, 762).
game_played(irving, 528).
game_played(durant, 849).
game_played(jokich, 453).
game_played(antetokounmpo, 528).
game_played(harden, 841).
game_played(davis, 564).
game_played(lillard, 615).
game_played(mccollum, 528).
game_played(murray, 345).
game_played(doncic, 199).
game_played(jordan, 1072).
game_played(bryant, 1346).
game_played(onil, 1207).

olympic_champ(lebron, 2).
olympic_champ(curry, 0).
olympic_champ(irving, 1).
olympic_champ(durant, 3).
olympic_champ(jokich, 0).
olympic_champ(antetokounmpo, 0).
olympic_champ(harden, 1).
olympic_champ(davis, 1).
olympic_champ(lillard, 1).
olympic_champ(mccollum, 0).
olympic_champ(murray, 0).
olympic_champ(doncic, 0).
olympic_champ(jordan, 2).
olympic_champ(bryant, 2).
olympic_champ(onil, 1).

nba_champ(lebron, 4).
nba_champ(curry, 3).
nba_champ(irving, 1).
nba_champ(durant, 2).
nba_champ(jokich, 0).
nba_champ(antetokounmpo, 1).
nba_champ(harden, 0).
nba_champ(davis, 1).
nba_champ(lillard, 0).
nba_champ(mccollum, 0).
nba_champ(murray, 0).
nba_champ(doncic, 0).
nba_champ(jordan, 6).
nba_champ(bryant, 5).
nba_champ(onil, 4).

mvp(lebron, 2).
mvp(curry, 2).
mvp(irving, 0).
mvp(durant, 0).
mvp(jokich, 1).
mvp(antetokounmpo, 2).
mvp(harden, 1).
mvp(davis, 0).
mvp(lillard, 0).
mvp(mccollum, 0).
mvp(murray, 0).
mvp(doncic, 0).
mvp(jordan, 5).
mvp(bryant, 1).
mvp(onil, 1).

final_mvp(lebron, 4).
final_mvp(curry, 0).
final_mvp(irving, 0).
final_mvp(durant, 2).
final_mvp(jokich, 0).
final_mvp(antetokounmpo, 1).
final_mvp(harden, 0).
final_mvp(davis, 0).
final_mvp(lillard, 0).
final_mvp(mccollum, 0).
final_mvp(murray, 0).
final_mvp(doncic, 0).
final_mvp(jordan, 6).
final_mvp(bryant, 2).
final_mvp(onil, 3).

all_star(lebron, 15).
all_star(curry, 7).
all_star(irving, 5).
all_star(durant, 11).
all_star(jokich, 3).
all_star(antetokounmpo, 4).
all_star(harden, 8).
all_star(davis, 7).
all_star(lillard, 5).
all_star(mccollum, 2).
all_star(murray, 0).
all_star(doncic, 2).
all_star(jordan, 14).
all_star(bryant, 18).
all_star(onil, 15).

question1(X1):- write("Какой рост у вашего любимого баскетболиста?"),nl,
                write("2. ... <= 190"),nl,
                write("1. 190 < ... < 210"),nl,
                write("0. ... >= 210"),nl,
                read(X1).

question2(X2):- write("На какой позиции играет ваш любимый игрок?"),nl,
                write("pg"),nl,
                write("sg"),nl,
                write("sf"),nl,
                write("pf"),nl,
                write("c"),nl,
                read(X2).

question3(X3):- write("В каком клубе играет ваш любимый игрок?"),nl,
                write("lal"),nl,
                write("gsw"),nl,
                write("bkn"),nl,
                write("mil"),nl,
                write("por"),nl,
                write("dnw"),nl,
                write("dal"),nl,
                write("cha"),nl,
                write("nan - not played"),nl,
                read(X3).

question4(X4):- write("Какой возраст вашего любимого игрока?"),nl,
                write("2.  ... >= 35"),nl,
                write("1. 27 < ... < 35"),nl,
                write("0. ... <= 27"),nl,
                read(X4).

question5(X5):- write("Когда был задрафтован ваш любимый игрок?"),nl,
                write("2. ... >= 2013"),nl,
                write("1. 2000 < ... < 2013"),nl,
                write("0. ... <= 2000"),nl,
                write("0. no"),nl,
                read(X5).

question6(X6):- write("Был ли ваш игрок лотерейным пиком? (топ 10)"),nl,
                write("1. да"),nl,
                write("0. нет"),nl,
                read(X6).

question7(X7):- write("Сколько чемпионств NBA завоёвывал ваш любимый игрок?"),nl,
                write("0"),nl,
                write("1"),nl,
                write("2"),nl,
                write("3"),nl,
                write("4"),nl,
                write("5"),nl,
                write("6"),nl,
                read(X7).

high_question_list(L,X):- ((X is 2), findall(R,( high(R,H), H=<190),L));
          ((X is 1), findall(R, (high(R,H), (H>190, H<210)),L));
          ((X is 0), findall(R, (high(R,H), H>=210),L)).

high_question(R,X):- ((X is 2),  high(R,H), H=<190);
          ((X is 1), high(R,H), H>190, H<210);
          ((X is 0), high(R,H), H>=210).


possition_question_list(L,X):- findall(D, possition(R, X), L).

possition_question(L,X):- possition(L, X).

club_question_list(L,X):- findall(R, club(R,X), L).

club_question(L,X):- club(R,X).

age_question_list(L, X):- ((X is 2), findall(R,(age(R,H), H>=35),L));
          ((X is 1), findall(R, (age(R,H), (H>27, H<35)),L));
          ((X is 0), findall(R, (age(R,H), H=<27),L)).
age_question(R, X):- ((X is 2), age(R,H), H>=35);
          ((X is 1), age(R,H), H>27, H<35);
          ((X is 0), age(R,H), H=<27).

draft_year_question(R,X):- ((X is 2), draft_year(R,H));
          ((X is 1), draft_year(R,H), H>2000, H<2013);
          ((X is 0), draft_year(R,H), H=<2000).

draft_pick_question_list(L, X):-((X is 1), findall(R, (draft_pick(R,H),H=<10),L));
          ((X is 0), findall(R, (draft_year(R,H), H>10),L)).

draft_pick_question(R, X):-((X is 1),draft_pick(R,H),H=<10);
          ((X is 0), draft_year(R,H), H>10).

nba_champs_question_list(L,X):- findall(R, nba_champ(R,X), L).

nba_champs_question(R,X):- nba_champ(R,X).

pr:-    question1(X1),question2(X2),question3(X3),question4(X4),
        question5(X5),question6(X6),question7(X7),
        high_question(X,X1),possition_question(X,X2), club_question(X,X3),age_question(X,X4),
        draft_year_question(X,X5),draft_pick_question(X,X6),nba_champs_question(X,X7),
        write(X).
















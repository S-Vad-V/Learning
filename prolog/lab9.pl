if(A,B,C):-A,B;\+A,C.
vertex(0).
vertex(1). 
vertex(2). 
vertex(3). 
vertex(4). 
vertex(5). 
vertex(6).

edge(0, 1). 
edge(1, 4).
edge(2, 3).
edge(4, 5).
edge(2, 5).
edge(4, 6).
edge(0, 6).


transfer([],_,[]).
transfer(Ns,GEs,[GE|TEs]) :- 
   select(GE,GEs,GEs1),        
   incident(GE,X,Y),
   acceptable(X,Y,Ns),
   delete(Ns,X,Ns1),
   delete(Ns1,Y,Ns2),
   transfer(Ns2,GEs1,TEs).

incident(e(X,Y),X,Y).
incident(e(X,Y,_),X,Y).

acceptable(X,Y,Ns) :- memberchk(X,Ns), \+ memberchk(Y,Ns), !.
acceptable(X,Y,Ns) :- memberchk(Y,Ns), \+ memberchk(X,Ns).

is_connected(G) :- 
	s_tree(G,_), !.

connected(A,B) :- edge(A,B);edge(B,A).

s_tree(graph([N|Ns],GraphEdges),graph([N|Ns],TreeEdges)) :- 
   transfer(Ns,GraphEdges,TreeEdgesUnsorted),
   sort(TreeEdgesUnsorted,TreeEdges).
   
vertexList(List) :- 
    findall(X, vertex(X), List).
	
edgesList(List) :- 
    findall(e(X, Y), (edge(X, Y)), List).

ex4(Result):-
        vertexList(V_List),
        edgesList(E_List),
        length(V_List, CountVertex),
        length(E_List, CountEdges),
        NCountEdges is CountEdges + 1,
        if(is_connected(graph(V_List, E_List)),
            (if(CountVertex = NCountEdges,Result is 1, Result is 0)),
        Result is 0).

connected(A,B) :- edge(A,B);edge(B,A).

travel(A,B,P,[B|P]) :- 
    connected(A,B).

travel(A,B,Visited,Path) :-
   connected(A,C),           
   C \== B,
   \+member(C,Visited),
   travel(C,B,[C|Visited],Path). 

path(A,B,Path):-
    travel(A,B,[A],Q), 
    reverse(Q,Path),!.

all_paths(_,[]).
all_paths(X,[H|T]):-
	path(X,H,_),
	all_paths(X, T).
	


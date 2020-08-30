all: queues collinear 8puzzle

queues: queues/Deque.java queues/RandomizedQueue.java queues/Permutation.java
	zip -r queues.zip queues/*.java

collinear: collinear/Point.java collinear/BruteCollinearPoints.java collinear/FastCollinearPoints.java
	zip -r collinear.zip collinear/Point.java collinear/BruteCollinearPoints.java collinear/FastCollinearPoints.java

8puzzle: 8puzzle/Board.java 8puzzle/Solver.java
	zip -r 8puzzle.zip 8puzzle/Board.java 8puzzle/Solver.java

kdtree: kdtree/PointSET.java kdtree/KdTree.java
	zip -r kdtree.zip kdtree/PointSET.java kdtree/KdTree.java

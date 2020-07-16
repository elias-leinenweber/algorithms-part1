all: queues collinear

queues: queues/Deque.java queues/RandomizedQueue.java queues/Permutation.java
	zip -r queues.zip queues/*.java

collinear: collinear/Point.java collinear/BruteCollinearPoints.java collinear/FastCollinearPoints.java
	zip -r collinear.zip collinear/Point.java collinear/BruteCollinearPoints.java collinear/FastCollinearPoints.java

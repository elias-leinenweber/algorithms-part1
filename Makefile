all: queues

queues: queues/Deque.java queues/RandomizedQueue.java queues/Permutation.java
	zip -r queues.zip queues/*.java

import java.util.ArrayList;
import java.util.List;

/*
* AUTHOR: Jacob Scott
* FILE: MaxPQ.java
* ASSIGNMENT: Programming Assignment 10
* COURSE: CSc 210; Fall 2020
* PURPOSE: This class creates a priority queue of ladder objects backed by a binary max heap.
*
* USAGE: 
* Initialize a queue object by calling the constructor. Call the enqueue() method to add elements
* to the queue.
* 
*/

public class MaxPQ {
	private static final int DEFAULT_CAPACITY = 10;

    private Ladder[] queue;
    private int size;
    
    /*
     * Purpose: This method is the constructor and creates a MaxPQ
     * instance.
     * 
     * @return: None.
     */
    public MaxPQ() {
    	queue = new Ladder[DEFAULT_CAPACITY];
    	size = 0;
    	
    	queue[0] = new Ladder(new ArrayList<String>(), 1000000);
    }
    
    /*
     * Purpose: This method creates a ladder object with the passed in name and
     * priority and adds it to the queue. It will then bubble it up if its
     * priority is lower than its parent.
     * 
     * @param: path, The List<String> parameter for the ladder object.
     * 
     * @param: priority, The priority for the ladder object.
     * 
     * @return: None.
     */
    public void enqueue(List<String> path, int priority) {
    	 if (size + 1 >= DEFAULT_CAPACITY) {
             growArray();
         }
    	 
    	 size++;
         queue[size] = new Ladder(path, priority);
         int curr = size;

         if (queue[curr].priority == queue[(curr) / 2].priority) {
             swapParent(curr);
         }
         while (queue[curr].priority > queue[(curr) / 2].priority
                 && (curr) / 2 != 0) {
             curr = swapParent(curr);
         } 
    }

    /*
     * Purpose: This method removes the first ladder in the queue and replaces
     * it with the one at the end. It then bubbles it down the queue if its
     * priority is higher than its children.
     * 
     * @return: The List<String> of the first ladder object in the queue.
     */
    public List<String> dequeue() {
        List<String> path = queue[1].path;
        queue[1] = queue[size];
        queue[size] = null;
        size--;
        int curr = 1;
    	
        while (queue[curr * 2] != null || queue[(curr * 2) + 1] != null) {
        	if (queue[curr].priority > queue[curr * 2].priority
                    && queue[curr * 2 + 1] == null) {
                return path;
            }

            else if (queue[curr].priority < queue[curr * 2].priority
                    && queue[curr * 2 + 1] == null) {
                swapLeftChild(curr);
                return path;
            }

            else if (queue[curr].priority > queue[curr * 2].priority
                    && queue[curr].priority > queue[(curr * 2) + 1].priority) {
                return path;

            } else if (queue[curr].priority < queue[curr * 2].priority
                    || queue[curr].priority < queue[(curr * 2) + 1].priority) {
                if (queue[curr * 2].priority < queue[(curr * 2) + 1].priority) {
                    curr = swapRightChild(curr);
                } else {
                    curr = swapLeftChild(curr);
                }
            }
        }
    	
    	return path;
    }
    
    /*
     * Purpose: This helper method creates a new array of a larger capacity when
     * the max is reached. It copies every value over.
     * 
     * @return: None.
     */
    private void growArray() {
        Ladder[] newArray = new Ladder[2 * size];
        for (int i = 0; i <= size; i++) {
            newArray[i] = queue[i];
        }
        queue = newArray;
    }
    
    /*
     * Purpose: This method checks if the queue is empty
     * 
     * @return: A boolean telling if the queue is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /*
     * Purpose: This helper method swaps a child with its parent in the queue
     * and updates the index.
     * 
     * @param: curr, The index which is being compared to its parent.
     * 
     * @return: The updated current index.
     */
    private int swapParent(int curr) {
        Ladder l = queue[(curr) / 2];
        queue[(curr) / 2] = queue[curr];
        queue[curr] = l;
        return curr / 2;
    }
    
    /*
     * Purpose: This helper method swaps a parent with its left child in the
     * queue and updates the index.
     * 
     * @param: curr, The index which is being compared to its child.
     * 
     * @return: The updated current index.
     */
    private int swapLeftChild(int curr) {
        Ladder l = queue[curr];
        queue[curr] = queue[curr * 2];
        queue[curr * 2] = l;
        return curr * 2;
    }
    
    /*
     * Purpose: This helper method swaps a parent with its right child in the
     * queue and updates the index.
     * 
     * @param: curr, The index which is being compared to its child.
     * 
     * @return: The updated current index.
     */
    private int swapRightChild(int curr) {
        Ladder l = queue[curr];
        queue[curr] = queue[curr * 2 + 1];
        queue[curr * 2 + 1] = l;
        return (curr * 2) + 1;
    }
    
    /*
     * Purpose: This method returns a string representation of the queue.
     * 
     * @return: A string representation of the queue.
     */
    @Override
    public String toString() {
        String s = "{";
        for (int i = 1; i <= size; i++) {
            if (i != size) {
                s = s + queue[i].toString() + ", ";
            } else {
                s = s + queue[i].toString();
            }
        }
        return s + "}";
    }

    /*
     * Purpose: This private class creates the ladder objects which the priority queue holds
     */
    private class Ladder {

        public List<String> path;
        public int priority;

        /*
         * Purpose: This method is the constructor and creates a ladder instance.
         * 
         * @param: path, The List<String> parameter for the ladder object.
         * 
         * @param: priority, The priority for the ladder object.
         * 
         * @return: None.
         */
        public Ladder(List<String> path, int priority) {
            this.path = path;
            this.priority = priority;
        }

        /*
         * Purpose: This method returns a string representation of the queue.
         * 
         * @return: A string representation of the queue.
         */
        @Override
    	public String toString() {
    		return path + " (" + priority + ")";
    	}

    }
}




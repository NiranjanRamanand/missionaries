/*
Author:Niranjan Ramanand
Prof. Devin Balkcom gave a stub etching out class format
*/
package cannibals;

import java.util.ArrayList;
import java.util.Arrays;


public class CannibalProblem extends UUSearchProblem {

	private int goalm, goalc, goalb;
	private int totalMissionaries, totalCannibals; 

	public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
		// I (djb) wrote the constructor; nothing for you to do here.

		startNode = new CannibalNode(sm, sc, 1, 0);
		goalm = gm;
		goalc = gc;
		goalb = gb;
		totalMissionaries = sm;
		totalCannibals = sc;
		
		//Test for successors in below comments
		
		//System.out.println();
		//((CannibalNode)startNode).testSuccessors();
		//System.out.println();
	}
	

	private class CannibalNode implements UUSearchNode {

		private final static int BOAT_SIZE = 2; //Feel free to change this
	
		// how many missionaries, cannibals, and boats
		// are on the starting shore
		private int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for search, but useful information for debugging, and for comparing paths
		private int depth;  

		public CannibalNode(int m, int c, int b, int d) {
			state = new int[3];
			this.state[0] = m;
			this.state[1] = c;
			this.state[2] = b;
			
			depth = d;

		}

		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> list = new ArrayList<>();
			
			if(state[2] == 1){//boat on one side
				for(int c = 0; c <= (BOAT_SIZE < state[1] ? BOAT_SIZE : state[1]); c++)//# cannibals equal to the smaller of these
					for(int m = 0; m <= ((state[0] > (BOAT_SIZE - c) ? (BOAT_SIZE - c) : state[0])); m++){
						if((m + c) != 0 && (m >= c || m == 0) && isSafeState(state[0] - m, state[1] - c))//Don't want more cannibals than missionaries on boat
							list.add(new CannibalNode(state[0] - m, state[1]  - c, 0, depth));
					}
			} else {//boat on the other side
				for(int c = 0; c <= (BOAT_SIZE < (totalCannibals - state[1]) ? BOAT_SIZE : (totalCannibals - state[1])); c++)
					for(int m = 0;
							m <= ((totalMissionaries - state[0]) > (BOAT_SIZE - c) ? (BOAT_SIZE - c) : (totalMissionaries - state[0]));
							m++){
						if((m + c) != 0 && (m >= c || m == 0) && isSafeState(state[0] + m, state[1] + c))
							list.add(new CannibalNode(state[0] + m, state[1]  + c, 1, depth));
					}
			}
			return list;
		}
		
		private boolean isSafeState(int m, int c){ //Are both sides of the short in acceptable configurations?
			if(m > 0 && m < c) return false;
			if ((totalMissionaries - m) > 0 && (totalMissionaries - m) < (totalCannibals - c)) return false;
			return true;
			
		}
		
		@Override
		public boolean goalTest() {
			return state[0] == goalm && state[1] == goalc && state[2] == goalb;
		}

		public void testSuccessors(){
			System.out.println("Successors of " + this + ": " + getSuccessors());
			System.out.println("==============");
			for(UUSearchNode n : getSuccessors()){
				System.out.println("Successors of " + n + ": " + n.getSuccessors());
			}
		}

		// an equality test is required so that visited lists in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((CannibalNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1] * 10 + state[2];
		}

		@Override
		public String toString() {
			String s = state[0] + "m, " + state[1] + "c, and " + state[2] + "b.";
			return s;
		}

		
       // You might need this method when you start writing 
        //(and debugging) UUSearchProblem.
        
		@Override
		public int getDepth() {
			return depth;
		}
		

	}
	

}
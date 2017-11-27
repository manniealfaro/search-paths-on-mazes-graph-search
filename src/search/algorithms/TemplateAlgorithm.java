package search.algorithms;


import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import search.*;

/** 
 * Extends the abstract class SearchAlgorithm and implements a template algorithm.
 */
public abstract class TemplateAlgorithm extends SearchAlgorithm{
	
	protected HashSet<State> explored;

	protected ArrayList<Node> succesors;

	protected int limit = Integer.MAX_VALUE;

	Collection<Node> frontier;

	public TemplateAlgorithm() {
		explored = new HashSet<State>();
		succesors = new ArrayList<Node>();
		actionSequence = new ArrayList<Action>();
	}


    /** 
     * Implements a template search algorithm.
     */
	@Override
	public void search() {
		// Inits performance variables
		totalCost = 0;
		expandedNodes = 0;
		searchTime = System.currentTimeMillis();
				
		// Auxiliary variables.
		Node chosen = null;

		frontier = createFrontier();

		frontier.add(new Node(problem.initialState()));

        // Main cycle
        do {

			if(chosen != null) {

				if(!(explored.contains(chosen.getState()))) {

					if (chosen.getDepth() < limit) {
						succesors = getSuccessors(chosen);

						for (Node node : succesors) {
							insert(node, frontier);
						}

						explored.add(chosen.getState());
					}

				}

			}

			if(frontier.size() == 0)
				return;

			chosen = extract(frontier);

		}while(!problem.testGoal(chosen.getState()));

		totalCost = chosen.getCost();

        // Calculates the search time.
		searchTime = System.currentTimeMillis()-searchTime;

		recoverActions(chosen);
        // As the list of actions has been obtained upwards, reverses it.
    	Collections.reverse(actionSequence);
    }

    public abstract Collection<Node> createFrontier();

	public abstract Node extract(Collection<Node> frontier);

	public abstract void insert(Node node, Collection<Node> frontier);

	public void recoverActions(Node node) {
		while(!(node.getState().equals(problem.initialState()))){
			actionSequence.add(node.getAction());

			node = node.getParent();
		}
	}


}

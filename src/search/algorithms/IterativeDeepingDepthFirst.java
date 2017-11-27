package search.algorithms;

import search.Action;
import search.Node;
import search.State;

import java.util.*;

/**
 * Created by manuel on 11/10/2017.
 */
public class IterativeDeepingDepthFirst extends TemplateAlgorithm {

    public IterativeDeepingDepthFirst() {
        super();

        this.limit = 0;
    }

    @Override
    public void search() {

        // Auxiliary variables.
        Node chosen;
        totalCost = 0;
        expandedNodes = 0;
        searchTime = System.currentTimeMillis();

        do {

            this.limit++;

            // Inits performance variables
            explored = new HashSet<State>();

            // Auxiliary variables.
            chosen = null;

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
                    break;

                chosen = extract(frontier);

            } while(!problem.testGoal(chosen.getState()));

        } while(!problem.testGoal(chosen.getState()));

        totalCost = chosen.getCost();

        // Calculates the search time.
        searchTime = System.currentTimeMillis()-searchTime;

        recoverActions(chosen);
        // As the list of actions has been obtained upwards, reverses it.
        Collections.reverse(actionSequence);
    }

    @Override
    public Collection<Node> createFrontier() {
        return  new Stack<Node>();
    }

    @Override
    public Node extract(Collection<Node> frontier) {
        return ((Stack<Node>) frontier).pop();
    }

    @Override
    public void insert(Node node, Collection<Node> frontier) {
        ((Stack<Node>) frontier).push(node);
    }
}

package search.algorithms;

import search.Node;

import java.util.Collection;
import java.util.PriorityQueue;

/**
 * Created by manuel on 11/10/2017.
 */
public class ASearch extends TemplateAlgorithm {

    @Override
    public Collection<Node> createFrontier() {
        return new PriorityQueue<Node>(100, Node.BY_EVALUATION);
    }

    @Override
    public Node extract(Collection<Node> frontier) {
        return ((PriorityQueue<Node>) frontier).poll();
    }

    @Override
    public void insert(Node node, Collection<Node> frontier) {
        ((PriorityQueue<Node>) frontier).add(node);
    }
}

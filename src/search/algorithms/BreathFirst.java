package search.algorithms;

import search.Node;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by manuel on 11/10/2017.
 */
public class BreathFirst extends TemplateAlgorithm {

    @Override
    public Collection<Node> createFrontier() {
        return  new LinkedList<Node>();
    }

    @Override
    public Node extract(Collection<Node> frontier) {
        return ((LinkedList<Node>) frontier).remove();
    }

    @Override
    public void insert(Node node, Collection<Node> frontier) {
        ((LinkedList<Node>) frontier).add(node);
    }
}

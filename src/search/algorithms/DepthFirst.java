package search.algorithms;

import search.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by manuel on 11/10/2017.
 */
public class DepthFirst extends TemplateAlgorithm {

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

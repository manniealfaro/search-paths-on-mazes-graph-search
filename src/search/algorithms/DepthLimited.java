package search.algorithms;

import search.Node;

import java.util.Collection;
import java.util.Stack;

/**
 * Created by manuel on 11/10/2017.
 */
public class DepthLimited extends TemplateAlgorithm {

    public DepthLimited() {
        super();

        limit = 100;
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

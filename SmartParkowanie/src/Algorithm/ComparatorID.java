package Algorithm;

import java.util.Comparator;

/**
 * @author Tomek
 */
public class ComparatorID implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        return  n1.getId()-n2.getId();
    }
}

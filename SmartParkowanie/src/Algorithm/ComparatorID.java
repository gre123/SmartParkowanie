package Algorithm;

import java.util.Comparator;

/**
 * @author Tomek
 */
public class ComparatorID implements Comparator {

    public int compare(Node n1, Node n2) {
        return n2.getId()- n1.getId();
    }

    @Override
    public int compare(Object o1, Object o2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


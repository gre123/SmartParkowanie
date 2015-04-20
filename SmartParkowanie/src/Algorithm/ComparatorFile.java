package Algorithm;

import java.util.Comparator;

/**
 * @author Tomek
 */
public class ComparatorFile  implements Comparator<ToSort>{
    @Override
    public int compare(ToSort n1, ToSort n2) {
        return  n1.getId()-n2.getId();
    }
}

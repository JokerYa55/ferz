package app;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author vasil
 */
@Getter
@Setter
public class Node extends NodeBase {

    private final Node parent;
    private final boolean flag;
    private final List<Node> chailds = new ArrayList<>();
    private List<Node> ferzList = new ArrayList<>();

    public Node(int i, int j, boolean flag, Node parent) {
        super(i, j);
        this.flag = flag;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" + "i=" + getI() + ", j=" + getJ() + ", flag=" + flag + '}';
    }

}

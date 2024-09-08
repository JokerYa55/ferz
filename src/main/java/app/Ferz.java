package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author vasil
 */
public class Ferz {

    private final static int SIZE = 8;

    private static int col;
    private static long colAll;
    private static final Map<String, List<Node>> resultMap = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            var root = new Node(i, 0, true, null);
            root.getFerzList().add(root);
            buildNode(root);
        }

        resultMap.entrySet()
                .stream()
                .forEach(t -> {
                    System.out.println("\n");
                    ChessUtil.printBoard(ChessUtil.getBoard(t.getValue(), SIZE));
                    System.out.println(t.getKey());
                });
        System.out.println("\nsize = " + resultMap.entrySet().size() + "\ncol = " + col + "\ncoll_all = " + colAll);
    }

    private static String genKey(List<Node> ferzList) {
        return ferzList.stream()
                .map(t1 -> t1.getI() + ":" + t1.getJ())
                .sorted()
                .collect(Collectors.joining(","));
    }

    // Ищем решения в виде дерева решений
    private static void buildNode(final Node node) {
        /**
         * Формируем текущее состояние доски. Берем перечень уже установленных
         * фигур и отмечаем все клетки которые под боем
         */
        int[][] board;
        int ki;
        int kj;

        ki = node.getI();
        kj = node.getJ();
        board = ChessUtil.getBoard(node.getFerzList(), SIZE);

        // Получаем все доступные элементы и делаем их нодами и запускаем процесс от них
        for (int k = 0; k < SIZE; k++) {
            for (int l = 0; l < SIZE; l++) {
                if ((board[k][l] == 0) && k != ki && k != kj) {
                    colAll++;
                    var newNode = new Node(k, l, true, node);
                    var newFerzList = new ArrayList<Node>();
                    newFerzList.addAll(node.getFerzList());
                    newFerzList.add(newNode);
                    newNode.setFerzList(newFerzList);

                    if (newNode.getFerzList().size() == SIZE) {
                        col++;
                        resultMap.putIfAbsent(genKey(newFerzList), newFerzList);
                    }

                    boolean flag = true;

                    if (newNode.getFerzList().size() == (SIZE - 3)) {
                        // Вычисляем key
                        var key = genKey(newFerzList);

                        flag = resultMap.entrySet()
                                .stream()
                                .filter(t -> t.getKey().contains(key))
                                .findAny()
                                .isEmpty();
//                        if (!flag) {
//                            System.out.println("flag = " + flag + " key " + key);
//                        }
                    }
                    if (flag) {
                        buildNode(newNode);
                    }

                }
            }
        }
    }

}

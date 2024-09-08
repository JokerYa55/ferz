package app;

import static app.AppConst.FERZ;
import static app.AppConst.NOT_EMPTY;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;

/**
 *
 * @author vasil
 */
@UtilityClass
public class ChessUtil {

//    public static void printPath(Node node) {
//        String res = "";
//        Node current = node;
//        while (true) {
//            res = res + " -> (" + current.getI() + "," + current.getJ() + ")";
//            if (current.getParent() != null) {
//                current = current.getParent();
//            } else {
//                break;
//            }
//        }
//        System.out.println(res);
//    }
    // Формируем досу с отмеченными клетками под боем
    public static int[][] getBoard(List<Node> ferzList, final int boardSize) {
        var boardLocal = new int[boardSize][boardSize];
        if (ferzList != null) {
            for (Node ferzList1 : ferzList) {
                var x = ferzList1.getI();
                var y = ferzList1.getJ();
                // отмечаем вертикали
                for (int k = 0; k < boardSize; k++) {
                    boardLocal[k][y] = NOT_EMPTY;
                }
                // отмечаем горизонтали
                for (int k = 0; k < boardSize; k++) {
                    boardLocal[x][k] = NOT_EMPTY;
                }
                // отмечаем диагонали
                var px = x;
                var py = y;
                while (px < boardSize - 1 && py < boardSize - 1) {
                    px++;
                    py++;
                    boardLocal[px][py] = 2;
                }

                px = x;
                py = y;

                while (px > 0 && py > 0) {
                    px--;
                    py--;
                    boardLocal[px][py] = 2;
                }

                px = x;
                py = y;

                while (px > 0 && py < boardSize - 1) {
                    px--;
                    py++;
                    boardLocal[px][py] = 2;
                }

                px = x;
                py = y;

                while (px < boardSize - 1 && py > 0) {
                    px++;
                    py--;
                    boardLocal[px][py] = 2;
                }

                boardLocal[x][y] = FERZ;
            }
        }

        return boardLocal;
    }

    // Отображение доски
    public static void printBoard(final int[][] board) {
        // Рисуем шапку
        var gHeader = "      " + IntStream.range(97, 97 + board.length)
                .boxed()
                .map(t -> String.valueOf((char) t.byteValue()))
                .collect(Collectors.joining(" "));

        var gline = IntStream.range(1, gHeader.length() - 1)
                .mapToObj(t -> "═")
                .collect(Collectors.joining(""));

        System.out.println(gHeader);
        System.out.println("    " + "╔" + gline + "╗");
        // рисуем доску
        for (int i = 0; i < board.length; i++) {
            int[] array = board[i];
            var str = (i + 1) + "   ║" + IntStream.range(0, array.length)
                    .mapToObj(t -> {
                        if (array[t] == 1) {
                            return " ■";
                        } else if (array[t] == 2) {
                            return " x";
                        } else {
                            return " 0";
                        }
                    })
                    .collect(Collectors.joining());
            System.out.println(str + " ║");
        }
        System.out.println("    " + "╚" + gline + "╝");
    }
}

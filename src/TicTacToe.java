import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static int countForWin;
    private static int x;
    private static int y;
   // private static int[][] weigthArray;
    //init field
    private static void initField() {
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    // printField
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    // humanTurn
    private static void humanTurn() {
       do {
            System.out.print("Введите координаты хода X и Y через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    // aiTurn
    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    // checkWin
    private static boolean checkWin(char c) {
        for (int i = 0; i < fieldSizeY - countForWin + 1; i++) {
            for (int j = 0; j < fieldSizeX - countForWin + 1; j++) {
                if (checkDiagonal(c, i, j) || checkLines(c, i, j)) return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonal(char c, int shiftX, int shiftY) {
        boolean diagonal1;
        boolean diagonal2;
        diagonal1 = true;
        diagonal2 = true;
        for (int i = 0; i < countForWin; i++) {
            diagonal1 = (field[i + shiftX][i + shiftY] == c) & diagonal1;
            diagonal2 = (field[countForWin - i - 1 + shiftX][i + shiftY] == c) & diagonal2;
        }

        if (diagonal1 || diagonal2) return true;

        return false;
    }

    private static boolean checkLines(char c, int shiftX, int shiftY) {
        boolean colTrue;
        boolean rowTrue;
        for (int i = shiftX; i < countForWin + shiftX; i++) {
            colTrue = true;
            rowTrue = true;
            for (int j = shiftY ; j < countForWin + shiftY; j++) {
                colTrue = (field[i][j] == c) & colTrue;
                rowTrue = (field[j][i] == c) & rowTrue;
            }

            if (colTrue || rowTrue) return true;
        }

        return false;
    }

    //checkDraw
    private static boolean checkDraw() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isCellEmpty(i, j)) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Введите размеры поля через пробел");
        fieldSizeX = SCANNER.nextInt();
        fieldSizeY = SCANNER.nextInt();
        System.out.println("Введите количество символов для победы");
        countForWin = SCANNER.nextInt();
        while (true) {
            initField();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameChecks(DOT_HUMAN, "Human win")) break;
                aiTurn();
                printField();
                if (gameChecks(DOT_AI, "Computer win")) break;
            }
            System.out.println("Play again?");
            if (!SCANNER.next().equals("Y"))
                break;
        }
    }

    private static boolean gameChecks(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }
}

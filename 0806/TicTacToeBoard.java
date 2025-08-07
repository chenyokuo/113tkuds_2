import java.util.Scanner;

public class TicTacToeBoard {
    private static final int SIZE = 3;
    private static final char EMPTY = ' ';
    private char[][] board;
    private char currentPlayer;

    public TicTacToeBoard() {
        board = new char[SIZE][SIZE];
        currentPlayer = 'X';
        initializeBoard();
    }

    // 初始化棋盤
    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = EMPTY;
    }

    // 顯示棋盤
    public void printBoard() {
        System.out.println("  0 1 2");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("  -+-+-");
        }
    }

    // 放置棋子（含檢查）
    public boolean placeMark(int row, int col) {
        if (row < 0 || col < 0 || row >= SIZE || col >= SIZE || board[row][col] != EMPTY)
            return false;
        board[row][col] = currentPlayer;
        return true;
    }

    // 切換玩家
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // 檢查是否勝利
    public boolean checkWin() {
        // 檢查行與列
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer)
                return true;
            if (board[0][i] == currentPlayer &&
                board[1][i] == currentPlayer &&
                board[2][i] == currentPlayer)
                return true;
        }
        // 檢查對角線
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer)
            return true;
        if (board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer)
            return true;

        return false;
    }

    // 判斷平手
    public boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == EMPTY)
                    return false;
        return true;
    }

    // 執行主程式
    public static void main(String[] args) {
        TicTacToeBoard game = new TicTacToeBoard();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 井字遊戲開始 ===");
        game.printBoard();

        while (true) {
            System.out.print("玩家 " + game.currentPlayer + " 請輸入 row 和 col (以空格分隔)：");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!game.placeMark(row, col)) {
                System.out.println("無效的位置，請重試！");
                continue;
            }

            game.printBoard();

            if (game.checkWin()) {
                System.out.println("🎉 玩家 " + game.currentPlayer + " 獲勝！");
                break;
            } else if (game.isBoardFull()) {
                System.out.println("平手！");
                break;
            }

            game.switchPlayer();
        }

        scanner.close();
    }
}

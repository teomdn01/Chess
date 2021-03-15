package Chess;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard(InitBoard.INIT_BOARD);
        board.printBoard();
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Start row: ");
            int startRow = Integer.parseInt(scanner.nextLine());
            System.out.println("Start col: ");
            int startCol = Integer.parseInt(scanner.nextLine());
            System.out.println("End row: ");
            int endRow = Integer.parseInt(scanner.nextLine());
            System.out.println("End col: ");
            int endCol = Integer.parseInt(scanner.nextLine());
            board.movePiece(startRow - 1, startCol - 1, endRow - 1, endCol - 1);
        }
    }
}

package Chess;

public class ChessBoard {

    private final Piece[][] board = new Piece[8][8];

    private boolean turnWhite = true;
    private boolean turnBlack = false;

    public ChessBoard(Piece[][] matrix)
    {
        for(int i=0; i<8; i++)
        {
            System.arraycopy(matrix[i], 0, this.board[i], 0, 8);
        }
    }

    public void printBoard()
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
        System.out.println("      A    B    C    D    E    F    G    H   ");
        for(int i=0; i<8; i++)
        {
            System.out.print((i + 1) + "\t");
            for(int j=0; j<8; j++)
            {
                if(this.board[i][j] != null)
                {
                    System.out.print( " " +this.board[i][j]+" ");
                }
                else
                {
                    System.out.print("  ~  ");
                }
            }
            System.out.print( "  " +(i + 1) + "\t");
            System.out.println();
        }
        System.out.println("      A    B    C    D    E    F    G    H   ");
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol)
    {
        if(isMoveLegal(startRow, startCol, endRow, endCol, true))
        {
            en_passant_check();
            isMoveLegal(startRow, startCol, endRow, endCol, true);
            this.board[endRow][endCol] = this.board[startRow][startCol];
            this.board[startRow][startCol] = null;
            switchTurn();
            printBoard();
            System.out.println(whosTurn() + " turn!");
        }
        else
        {
            printBoard();
            System.out.println("Not a valid move!");
            System.out.println("Still " + whosTurn() + "'s turn!");
        }
    }

    public boolean isMoveLegal(int startRow, int startCol, int endRow, int endCol, boolean rightTurn)
    {
        Piece piece = board[startRow][startCol];

        if(piece == null)
        {
            return false;
        }
        else if((piece.color.equals("White") && turnBlack) || (piece.color.equals("Black") && turnWhite))
        {
            if(rightTurn)
            {
                System.out.println("Not your turn!");
            }

            return false;
        }
        else
        {
            switch(piece)
            {
                case B_P:
                case W_P:
                    return pawnMove(startRow, startCol, endRow, endCol, rightTurn);

                case B_B:
                case W_B:
                    return bishopMove(startRow, startCol, endRow, endCol);

                case B_N:
                case W_N:
                    return knightMove(startRow, startCol, endRow, endCol);

                case B_R:
                case W_R:
                    return rookMove(startRow, startCol, endRow, endCol);

                case B_Q:
                case W_Q:
                    return queenMove(startRow, startCol, endRow, endCol);

                case B_K:
                case W_K:
                    return kingMove(startRow, startCol, endRow, endCol);

                default:
                    return false;
            }
        }
    }

    public boolean pawnMove(int startRow, int startCol, int endRow, int endCol, boolean rightTurn)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];
        Piece en_passant;
        Piece en_passant_enemy;
        int pawnStartRow;
        int moveDir;

        if(piece.color.equals("White"))
        {
            pawnStartRow = 6;
            moveDir = -1;
            en_passant = Piece.W_E;
            en_passant_enemy = Piece.B_E;
        }
        else
        {
            pawnStartRow = 1;
            moveDir = 1;
            en_passant = Piece.B_E;
            en_passant_enemy = Piece.W_E;
        }

        if(endTile == null)
        {
            if(endRow == startRow+moveDir && startCol == endCol)
            {
                return true;
            }
            else if((board[startRow+moveDir][startCol] == null) && startRow == pawnStartRow && endRow == startRow+(moveDir*2) && startCol == endCol)
            {
                if(rightTurn)
                {
                    board[startRow][startCol] = en_passant;
                }
                return true;
            }
            else if(board[endRow-moveDir][endCol] == en_passant_enemy)
            {
                if(board[endRow-moveDir][endCol] == en_passant_enemy && ((endCol == startCol+1)||(endCol == startCol-1)) && endRow == startRow+moveDir)
                {
                    if(rightTurn)
                    {
                        board[endRow-moveDir][endCol] = null;
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            {
                return false;
            }
        }
        else if(!endTile.color.equals(piece.color))
        {
            return (endCol == startCol + 1) || (endCol == startCol - 1) && endRow == startRow + moveDir;
        }
        else
        {
            return false;
        }
    }

    public boolean bishopMove(int startRow, int startCol, int endRow, int endCol)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];

        if(endTile == null  || !endTile.color.equals(piece.color))
        {
            if(Math.abs(endCol-startCol) == Math.abs(endRow-startRow))
            {
                int colIndexPar;
                if(endCol>startCol)
                {
                    colIndexPar = 1;
                }
                else
                {
                    colIndexPar = -1;
                }

                int rowIndexPar;
                if(endRow>startRow)
                {
                    rowIndexPar = 1;
                }
                else
                {
                    rowIndexPar = -1;
                }

                for(int i=1; i<Math.abs(endCol-startCol); i++)
                {
                    if(board[startRow+i*rowIndexPar][startCol+i*colIndexPar] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean knightMove(int startRow, int startCol, int endRow, int endCol)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];

        int horizAbs = Math.abs(endRow-startRow);
        int vertAbs = Math.abs(endCol-startCol);

        if(endTile == null  || !endTile.color.equals(piece.color))
        {
            return horizAbs <= 2 && Math.abs(horizAbs - vertAbs) == 1;
        }
        else
        {
            return false;
        }
    }

    public boolean rookMove(int startRow, int startCol, int endRow, int endCol)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];

        if(endTile == null  || !endTile.color.equals(piece.color))
        {
            int colIndexPar;
            if(endCol>startCol)
            {
                colIndexPar = 1;
            }
            else
            {
                colIndexPar = -1;
            }

            int rowIndexPar;
            if(endRow>startRow)
            {
                rowIndexPar = 1;
            }
            else
            {
                rowIndexPar = -1;
            }

            if(endCol == startCol)
            {
                for(int i=1; i<Math.abs(endRow-startRow); i++)
                {
                    if(board[startRow+i*rowIndexPar][startCol] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else if(endRow == startRow)
            {
                for(int i=1; i<Math.abs(endCol-startCol); i++)
                {
                    if(board[startRow][startCol+i*colIndexPar] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean queenMove(int startRow, int startCol, int endRow, int endCol)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];

        if(endTile == null  || !endTile.color.equals(piece.color))
        {
            int colIndexPar;
            if(endCol>startCol)
            {
                colIndexPar = 1;
            }
            else
            {
                colIndexPar = -1;
            }

            int rowIndexPar;
            if(endRow>startRow)
            {
                rowIndexPar = 1;
            }
            else
            {
                rowIndexPar = -1;
            }

            if(Math.abs(endCol-startCol) == Math.abs(endRow-startRow))
            {
                for(int i=1; i<Math.abs(endCol-startCol); i++)
                {
                    if(board[startRow+i*rowIndexPar][startCol+i*colIndexPar] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            if(endCol == startCol)
            {
                for(int i=1; i<Math.abs(endRow-startRow); i++)
                {
                    if(board[startRow+i*rowIndexPar][startCol] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else if(endRow == startRow)
            {
                for(int i=1; i<Math.abs(endCol-startCol); i++)
                {
                    if(board[startRow][startCol+i*colIndexPar] != null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean kingMove(int startRow, int startCol, int endRow, int endCol)
    {
        Piece piece = board[startRow][startCol];
        Piece endTile = board[endRow][endCol];

        if(endTile == null  || !endTile.color.equals(piece.color))
        {
            return Math.abs(endCol - startCol) <= 1 && Math.abs(endRow - startRow) <= 1;
        }
        else
        {
            return false;
        }
    }

    public void switchTurn()
    {
        turnWhite = !turnWhite;
        turnBlack = !turnBlack;
    }

    public String whosTurn()
    {
        if(turnWhite)
        {
            return "White";
        }
        else
        {
            return "Black";
        }
    }

    private void en_passant_check()
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if(board[i][j] == Piece.W_E)
                {
                    board[i][j] = Piece.W_P;
                }
                else if(board[i][j] == Piece.B_E)
                {
                    board[i][j] = Piece.B_P;
                }
            }
        }
    }
}
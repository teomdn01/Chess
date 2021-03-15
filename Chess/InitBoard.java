package Chess;

public interface InitBoard {

    static final Piece[][] INIT_BOARD = {
            {Piece.B_R, Piece.B_N, Piece.B_B, Piece.B_Q, Piece.B_K, Piece.B_B, Piece.B_N, Piece.B_R},
            {Piece.B_P, Piece.B_P, Piece.B_P, Piece.B_P, Piece.B_P, Piece.B_P, Piece.B_P, Piece.B_P},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {Piece.W_P, Piece.W_P, Piece.W_P, Piece.W_P, Piece.W_P, Piece.W_P, Piece.W_P, Piece.W_P},
            {Piece.W_R, Piece.W_N, Piece.W_B, Piece.W_Q, Piece.W_K, Piece.W_B, Piece.W_N, Piece.W_R},
    };
}
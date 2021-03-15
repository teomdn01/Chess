package Chess;

public enum Piece {

    W_P("White"), W_E("White"), W_N("White"), W_B("White"), W_R("White"), W_Q("White"), W_K("White"),
    B_P("Black"), B_E("Black"), B_N("Black"), B_B("Black"), B_R("Black"), B_Q("Black"), B_K("Black");

    public final String color;

    Piece(String color)
    {
        this.color = color;
    }
}
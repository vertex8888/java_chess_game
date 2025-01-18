package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asset {
    static BufferedImage chessBoardImg;
    static BufferedImage[][] pieceImgs = new BufferedImage[Piece.COLOR_COUNT][Piece.TYPE_COUNT];

    public static Font karminaFont32;

    public static void loadAll() {
        chessBoardImg = Utils.FileIO.loadImage("res/pieces_cartoon/board.png");

        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_KING]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-king-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_QUEEN]  = Utils.FileIO.loadImage("res/pieces_cartoon/chess-queen-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_ROOK]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-rook-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_BISHOP] = Utils.FileIO.loadImage("res/pieces_cartoon/chess-bishop-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_KNIGHT] = Utils.FileIO.loadImage("res/pieces_cartoon/chess-knight-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_PAWN]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-pawn-white.png");

        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_KING]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-king-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_QUEEN]  = Utils.FileIO.loadImage("res/pieces_cartoon/chess-queen-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_ROOK]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-rook-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_BISHOP] = Utils.FileIO.loadImage("res/pieces_cartoon/chess-bishop-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_KNIGHT] = Utils.FileIO.loadImage("res/pieces_cartoon/chess-knight-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_PAWN]   = Utils.FileIO.loadImage("res/pieces_cartoon/chess-pawn-black.png");

        karminaFont32 = Utils.FileIO.loadFont("res/fonts/karmina.ttf", 32.0f);
    }
}

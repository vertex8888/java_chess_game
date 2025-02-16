package game;

import java.awt.*;
import java.awt.image.BufferedImage;

import game.Utils.FileIO;

public class Asset {
    static BufferedImage chessBoardImg;
    static BufferedImage[][] pieceImgs = new BufferedImage[Piece.COLOR_COUNT][Piece.TYPE_COUNT];

    public static Font karminaFont24;
    public static Font karminaFont32;
    public static Font karminaFont64;

    public static void loadAll() {
        chessBoardImg = FileIO.loadImage("res/pieces_cartoon/board.png");

        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_KING]   = FileIO.loadImage("res/pieces_cartoon/chess-king-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_QUEEN]  = FileIO.loadImage("res/pieces_cartoon/chess-queen-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_ROOK]   = FileIO.loadImage("res/pieces_cartoon/chess-rook-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_BISHOP] = FileIO.loadImage("res/pieces_cartoon/chess-bishop-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_KNIGHT] = FileIO.loadImage("res/pieces_cartoon/chess-knight-white.png");
        pieceImgs[Piece.COLOR_WHITE][Piece.TYPE_PAWN]   = FileIO.loadImage("res/pieces_cartoon/chess-pawn-white.png");

        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_KING]   = FileIO.loadImage("res/pieces_cartoon/chess-king-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_QUEEN]  = FileIO.loadImage("res/pieces_cartoon/chess-queen-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_ROOK]   = FileIO.loadImage("res/pieces_cartoon/chess-rook-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_BISHOP] = FileIO.loadImage("res/pieces_cartoon/chess-bishop-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_KNIGHT] = FileIO.loadImage("res/pieces_cartoon/chess-knight-black.png");
        pieceImgs[Piece.COLOR_BLACK][Piece.TYPE_PAWN]   = FileIO.loadImage("res/pieces_cartoon/chess-pawn-black.png");

        karminaFont24 = FileIO.loadFont("res/fonts/karmina.ttf", 24.0f);
        karminaFont32 = FileIO.loadFont("res/fonts/karmina.ttf", 32.0f);
        karminaFont64 = FileIO.loadFont("res/fonts/karmina.ttf", 64.0f);
    }
}

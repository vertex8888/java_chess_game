package game;

public class Game {
    public static final int SCREEN_WIDTH  = 620;
    public static final int SCREEN_HEIGHT = 768;
    public static final double TARGET_FPS = 60.0;

    public static final int BOARD_DIM       = 600;
    public static final int BOARD_X         = SCREEN_WIDTH/2 - BOARD_DIM/2;
    public static final int BOARD_Y         = SCREEN_HEIGHT/2 - BOARD_DIM/2;
    public static final int BOARD_TILE_SIZE = BOARD_DIM/8;

    static Piece[][] board;

    static Player whitePlayer, blackPlayer;
    static int currTurn;

    public static Piece[][] createBoard() {
        Piece WHITE_KING   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_KING);
        Piece WHITE_QUEEN  = new Piece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN);
        Piece WHITE_ROOK   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_ROOK);
        Piece WHITE_BISHOP = new Piece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP);
        Piece WHITE_KNIGHT = new Piece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT);
        Piece WHITE_PAWN   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_PAWN);

        Piece BLACK_KING   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_KING);
        Piece BLACK_QUEEN  = new Piece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN);
        Piece BLACK_ROOK   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_ROOK);
        Piece BLACK_BISHOP = new Piece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP);
        Piece BLACK_KNIGHT = new Piece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT);
        Piece BLACK_PAWN   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_PAWN);

        Piece[][] boardResult = {
                {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
                {BLACK_PAWN, BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN,  BLACK_PAWN, BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN},
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {WHITE_PAWN, WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN,  WHITE_PAWN, WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN},
                {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}
        };

        return boardResult;
    }

    static void initBoard() {
        board = createBoard();

        whitePlayer = new Player(board, Piece.COLOR_WHITE);
        blackPlayer = new Player(board, Piece.COLOR_BLACK);
        currTurn    = Piece.COLOR_WHITE;
    }

    static void init() {
        initBoard();
    }

    static void doMove(PlayerMove move) {
        board[move.toY][move.toX] = board[move.fromY][move.fromX];
        board[move.fromY][move.fromX] = null;
    }


    static void update() {
        Player currPlayer = whitePlayer;
        if(currTurn == Piece.COLOR_BLACK) currPlayer = blackPlayer;

        PlayerMove move = currPlayer.update();
        if(move != null) {
            doMove(move);

            whitePlayer.updateBoard(board);
            blackPlayer.updateBoard(board);

            if(currTurn == Piece.COLOR_WHITE) currTurn = Piece.COLOR_BLACK;
            else                              currTurn = Piece.COLOR_WHITE;
        }
    }

    static void render() {
        Player currPlayer = whitePlayer;
        if(currTurn == Piece.COLOR_BLACK) currPlayer = blackPlayer;

        currPlayer.render();

        Gui.render();

        Renderer.frameFlip();
    }
}

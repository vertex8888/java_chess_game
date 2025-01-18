package game;

public class Piece {
        public static final int COLOR_BLACK = 0;
        public static final int COLOR_WHITE = 1;
        public static final int COLOR_COUNT = 2;

        public static final int TYPE_KING   = 0;
        public static final int TYPE_QUEEN  = 1;
        public static final int TYPE_ROOK   = 2;
        public static final int TYPE_BISHOP = 3;
        public static final int TYPE_KNIGHT = 4;
        public static final int TYPE_PAWN   = 5;
        public static final int TYPE_COUNT  = 6;

        public int color;
        public int type;

        public Piece(int color, int type) {
            this.color = color;
            this.type = type;
        }
}

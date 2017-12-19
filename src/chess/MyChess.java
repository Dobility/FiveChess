package chess;

import java.awt.*;

/**
 * Created by Dobility on 2017/12/10.
 */
class MyChess extends Canvas {

    private ChessBoard chess = ChessBoard.getInstance();
    private final int N = chess.N + 2;
    public final int square = 40;
    private final int stx = square;
    private final int sty = square;
    private final int length = (N-1)*square;
    public Graphics graphics;

    public void drawPiece(int color, int x, int y, Graphics g) {
        if (color == chess.BLACK) {
            // 画黑子
            g.setColor(new Color(0, 0, 0));
            g.fillArc(stx + x * square - 19, sty + y * square - 19, 38, 38, 0, 360);
        } else if (color == chess.WHITE) {
            // 画白子
            g.setColor(new Color(255, 255, 255));
            g.fillArc(stx + x * square - 19, sty + y * square - 19, 38, 38, 0, 360);
        }
    }

    public void paint(Graphics g) {
        // 棋盘背景色
        g.setColor(new Color(0, 0, 0));
        g.fillRect(stx-8, sty-8, stx+(N-2)*square+15, sty+(N-2)*square+15);

        // 每个格子填充颜色
        g.setColor(new Color(214, 172, 132));
        g.fillRect(stx-4, sty-4, stx+(N-2)*square+7, sty+(N-2)*square+7);

        // 4个点
        g.setColor(new Color(0, 0, 0));
        g.fillArc(stx+8*square-6, sty+8*square-6, 12, 12, 0, 360);
        g.fillArc(stx+4*square-6, sty+4*square-6, 12, 12, 0, 360);
        g.fillArc(stx+4*square-6, sty+12*square-6, 12, 12, 0, 360);
        g.fillArc(stx+12*square-6, sty+4*square-6, 12, 12, 0, 360);
        g.fillArc(stx+12*square-6, sty+12*square-6, 12, 12, 0, 360);

        // 棋盘线
        for(int i = 0; i < N; i++) {
            g.drawLine(stx+i*square, sty, stx+i*square, sty+length);
            g.drawLine(stx, sty+i*square, stx+length, sty+i*square);
        }

        for(int i=1; i<=chess.N; i++) {
            for(int j=1; j<=chess.N; j++) {
                drawPiece(chess.board[i][j], i, j, g);
            }
        }
        graphics = g;
    }
}
package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Dobility on 2017/12/10.
 */
public class UI {
    private ChessBoard chess = ChessBoard.getInstance();
    private int userColor = chess.WHITE;
    private int robotColor = chess.BLACK;
    private Frame frame = new Frame("人机五子棋交互");
    private MyChess drawArea = new MyChess();
    private Robot robot = Robot.getRobot();

    public void init() {

        Panel p = new Panel();
        //机器执先
        chess.makeMove(chess.N / 2 + 1, chess.N / 2 + 1, chess.BLACK);

        drawArea.setPreferredSize(new Dimension(720, 720));

        drawArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int i = (x - 60) / 40 + 1;
                int j = (y - 60) / 40 + 1;
                System.out.println(i + "---" + j);
                if (chess.isEmpty(i, j)) {

                    chess.makeMove(i, j, userColor);
                    drawArea.repaint();
                    int rel = chess.isEnd(i, j, userColor);
                    if (rel != 0) {
                        System.out.println("玩家胜利");
                        JOptionPane.showMessageDialog(null, "玩家胜利", "Game Over", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int rob[] = robot.getNext(robotColor);
                    chess.makeMove(rob[0], rob[1], robotColor);
                    drawArea.repaint();
                    rel = chess.isEnd(rob[0], rob[1], robotColor);
                    if (rel != 0) {
                        System.out.println("机器胜利");
                        JOptionPane.showMessageDialog(null, "机器胜利", "Game Over", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.add(drawArea);
        frame.setSize(900, 720);
        frame.setVisible(true);
    }
}

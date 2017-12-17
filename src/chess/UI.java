package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dobility on 2017/12/10.
 */
public class UI {
    private ChessBoard chess = ChessBoard.getInstance();
    private int userColor = chess.WHITE;
    private int robotColor = chess.BLACK;
    private JFrame frame = new JFrame("人机五子棋交互");
    private MyChess drawArea = new MyChess();
    private Robot robot = Robot.getRobot();

    public void init() {
        Panel p = new Panel();

        //选择开始顺序
        Object[] possibilities = {"机器", "玩家"};
        int n = JOptionPane.showOptionDialog(
                null,
                "选择优先下子对象",
                "开始顺序",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                possibilities,
                possibilities[0]
        );

        if (n == 0) {
            //机器执先
            chess.makeMove(chess.N / 2 + 1, chess.N / 2 + 1, chess.BLACK);
        }

        drawArea.setPreferredSize(new Dimension(720, 720));

        drawArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int i = (x - 60) / drawArea.square + 1;
                int j = (y - 60) / drawArea.square + 1;
                System.out.println(i + "---" + j);
                if (chess.isEmpty(i, j)) {
                    // 显示玩家下的棋子
                    chess.makeMove(i, j, userColor);
                    drawArea.repaint();
                    int rel = chess.isEnd(i, j, userColor);
                    int winner = chess.EMPTY;
                    if (rel != 0) {
                        restart("玩家胜利");
                        return;
                    }
                    // 计算机器要下的位置并显示棋子
                    int rob[] = robot.getNext(robotColor);
                    chess.makeMove(rob[0], rob[1], robotColor);
                    drawArea.repaint();
                    rel = chess.isEnd(rob[0], rob[1], robotColor);
                    if (rel != 0) {
                        restart("机器胜利");
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
        frame.setSize(740, 760);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void restart(String alert) {
        System.out.println(alert);
        Object[] possibilities = {"再来一次", "退出游戏"};
        int n = JOptionPane.showOptionDialog(
                null,
                "机器胜利",
                "Game Over",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                possibilities,
                possibilities[0]
        );
        if (n == 1) {
            System.exit(0);
        } else {
            chess.clear();
            drawArea.repaint();
            Object[] possibilities1 = {"机器", "玩家"};
            n = JOptionPane.showOptionDialog(
                    null,
                    "选择优先下子对象",
                    "开始顺序",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    possibilities1,
                    possibilities1[0]
            );
            if (n == 0) {
                //机器执先
                chess.makeMove(chess.N / 2 + 1, chess.N / 2 + 1, chess.BLACK);
                drawArea.repaint();
            }
        }
    }

}

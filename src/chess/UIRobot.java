package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Created by Dobility on 2017/12/10.
 */
public class UIRobot {
    private ChessBoard chess = ChessBoard.getInstance();
    private int userColor = chess.WHITE;
    private int robotColor = chess.BLACK;
    private MyChess drawArea = new MyChess();
    private boolean showUI = false;
    private boolean showStep = false;

    private Robot r1 = Robot.getRobot();
    private Robot r2 = Robot.getRobot();

    private int firstX = chess.N / 2 + 1;
    private int firstY = chess.N / 2 + 1;
    private Random random = new Random();
//    private int firstX = random.nextInt(chess.N - 1) + 1;
//    private int firstY = random.nextInt(chess.N - 1) + 1;

    // r1先走
//    private Robot firstRobot = r1;
//    private Robot secondRobot = r2;
//    private int firstColor = robotColor;
//    private int secondColor = userColor;
//    private String firstName = "r1";
//    private String secondName = "r2";
    // r2先走
    private Robot firstRobot = r2;
    private Robot secondRobot = r1;
    private int firstColor = userColor;
    private int secondColor = robotColor;
    private String firstName = "r2";
    private String secondName = "r1";

    private int firstWin = 0;
    private int secondWin = 0;

    private int times = 1001;

    public void init() {
        Panel p = new Panel();
        System.out.println(firstName + "先行：(" + firstX + ", " + firstY + ")");
        chess.makeMove(firstX, firstY, firstColor);
        Console_showStep(firstName, firstX, firstY);
        UI_showFrame();
        play();
    }

    public void UI_showFrame() {
        if (showUI) {
            JFrame frame = new JFrame("机器五子棋对弈");
            drawArea.setPreferredSize(new Dimension(720, 720));
            frame.add(drawArea);
            frame.setSize(740, 760);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    public void UI_repaint() {
        if (showUI) {
            drawArea.repaint();
        }
    }

    public void Console_showStep(String name, int x, int y) {
        if (showStep) {
            System.out.println(name + ": (" + x + ", " + y + ")");
        }
    }

    public void play() {
        while (true) {
            // 第二个下的机器
            int rob[] = secondRobot.getNext(secondColor);
            chess.makeMove(rob[0], rob[1], secondColor);
            Console_showStep(secondName, rob[0], rob[1]);
            UI_repaint();
            int rel = chess.isEnd(rob[0], rob[1], secondColor);
            if (rel != 0) {
                secondWin++;
                restart(secondName + "胜利");
                return;
            }

            // 第一个下的机器
            rob = firstRobot.getNext(firstColor);
            chess.makeMove(rob[0], rob[1], firstColor);
            Console_showStep(firstName, rob[0], rob[1]);
            UI_repaint();
            rel = chess.isEnd(rob[0], rob[1], firstColor);
            if (rel != 0) {
                firstWin++;
                restart(firstName + "胜利");
                return;
            }

            // 判断是否平局
            if (chess.isDead()) {
                restart("平局");
                return;
            }
        }
    }

    public void restart(String alert) {
        System.out.println(alert + ", " + firstName + ":" + secondName + " = " + firstWin + ":" + secondWin + "\n");
        int n = 1;
        if (showUI) {
            Object[] possibilities = {"再来一次", "退出游戏"};
            n = JOptionPane.showOptionDialog(
                    null,
                    alert,
                    "Game Over",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    possibilities,
                    possibilities[0]
            );
        } else {
            n = times--;
        }
        if (n == 1) {
            System.exit(0);
        } else {
            chess.clear();
            UI_repaint();
//            firstX = random.nextInt(chess.N - 1) + 1;
//            firstY = random.nextInt(chess.N - 1) + 1;
            chess.makeMove(firstX, firstY, firstColor);
            System.out.println(firstName + "先行：(" + firstX + ", " + firstY + ")");
            Console_showStep(firstName, firstX, firstY);
            UI_repaint();
            play();
        }
    }

}

package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Dobility on 2017/12/10.
 */
public class UIRobot {
    private ChessBoard chess = ChessBoard.getInstance();
    private MyChess drawArea = new MyChess();

    private boolean showUI = false;         // 是否显示窗口
    private boolean showStep = false;       // 是否在console显示步骤
    private boolean writeFile = false;      // 是否写入文件
    private boolean autoAlter = true;       // 先行者是否来回切换
    private boolean randomFirst = false;    // 第一步是否任意位置

    private Robot r1 = Robot.getRobot();
    private Robot r2 = Robot.getRobot();

    private Random random = new Random();
    private int firstX = random.nextInt(chess.N - 1) + 1;
    private int firstY = random.nextInt(chess.N - 1) + 1;

    // r1先走
    private int first = 1;
    private int second = first % 2 + 1;
    private Robot firstRobot = r1;
    private Robot secondRobot = r2;

    private int r1Win = 0;
    private int r2Win = 0;

    private FileWriter fw;
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    private int times = 100;                // 循环次数
    private int center = chess.N / 2 + 1;

    // 初始化
    public void init() {
        Panel p = new Panel();
        UI_showFrame();
        start();
    }

    // 显示窗口
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

    // 重绘棋盘棋子
    public void UI_repaint() {
        if (showUI) {
            drawArea.repaint();
        }
    }

    // 在console显示步骤
    public void Console_showStep(String name, int x, int y) {
        if (showStep) {
            System.out.println(name + ": (" + x + ", " + y + ")");
        }
        writeChessFile(name + "\t" + x + "\t" + y);
    }

    // 创建棋谱文件
    public void createChessFile() {
        if (writeFile) {
            try {
                fw = new FileWriter("chess/chess" + df.format(new Date()) + ".txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 将下的每一步写入文件
    public void writeChessFile(String s) {
        if (writeFile) {
            try {
                fw.write(s + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 下棋过程
    public void play() {
        while (true) {
            // 第二个下的机器
            int rob[] = secondRobot.getNext(chess.WHITE);
            chess.makeMove(rob[0], rob[1], chess.WHITE);
            Console_showStep("r" + second, rob[0], rob[1]);
            UI_repaint();
            int rel = chess.isEnd(rob[0], rob[1], chess.WHITE);
            if (rel != 0) {
                if (first == 1) {
                    r2Win++;
                } else {
                    r1Win++;
                }
                restart("r" + second + "胜利");
                return;
            }

            // 第一个下的机器
            rob = firstRobot.getNext(chess.BLACK);
            chess.makeMove(rob[0], rob[1], chess.BLACK);
            Console_showStep("r" + first, rob[0], rob[1]);
            UI_repaint();
            rel = chess.isEnd(rob[0], rob[1], chess.BLACK);
            if (rel != 0) {
                if (first == 1) {
                    r1Win++;
                } else {
                    r2Win++;
                }
                restart("r" + first + "胜利");
                return;
            }

            // 判断是否平局
            if (chess.isDead()) {
                restart("平局");
                return;
            }
        }
    }

    // 开始游戏
    public void start() {
        if (randomFirst) {
            firstX = random.nextInt(chess.N - 1) + 1;
            firstY = random.nextInt(chess.N - 1) + 1;
        } else {
            firstX = center;
            firstY = center;
        }
        chess.makeMove(firstX, firstY, chess.BLACK);
        System.out.println("r" + first + "先行：(" + firstX + ", " + firstY + ")");
        createChessFile();
        writeChessFile("r" + first);
        Console_showStep("r" + first, firstX, firstY);
        UI_repaint();
        play();
    }

    // 重新开始游戏
    public void restart(String alert) {
        System.out.println(alert + ", r1:r2 = " + r1Win +  ":" + r2Win + "\n");
        writeChessFile(alert);
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
            try {
                if (writeFile) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (autoAlter) {
                first = first % 2 + 1;
                second = first % 2 + 1;
                if (first == 1) {
                    firstRobot = r1;
                    secondRobot = r2;
                } else {
                    firstRobot = r2;
                    secondRobot = r1;
                }
            }
            start();
        }
    }

}

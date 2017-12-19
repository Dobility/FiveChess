package chess;

/**
 * Created by Dobility on 2017/12/10.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("游戏开始");

//        UI ui = new UI();
        UIRobot ui = new UIRobot();
        ui.init();
    }
}
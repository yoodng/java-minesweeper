/*
 * Created by JFormDesigner on Sat Jun 13 14:49:40 CST 2020
 */

package jav;

import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static jav.ImageType.*;


/**
 * @author sdf
 */
public class Board extends JPanel {
    public static void main(String[] args) {
        Board a = new Board();
    }

    private int level;


    public Board() {
        appPath = System.getProperty("user.dir");
        imgs = new ImageIcon[16];
        for (int i = 0; i < 16; i++) {
            String p = appPath + "/images/" + String.valueOf(i + 1) + ".png";
            ImageIcon icon = new ImageIcon(p);
            Image img;
            if (i == 9 || i == 12 || i == 13 || i == 14) {
                img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_FAST);
            } else {
                img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_FAST);
            }
            icon.setImage(img);
            imgs[i] = icon;
        }
        initComponents();
        //setSize(400, 300);
        // setLocation(10, 10);
        frame1.setVisible(true);
        frame1.setLocation(450, 150);
        // easyGame();
        easyGame();

    }

    public void easyGame() {
        level = 1;
        createMineField(9, 9, 10, level);
    }

    public void middleGame() {
        level = 2;
        createMineField(16, 16, 40, level);
    }

    public void hardGame() {
        level = 3;
        createMineField(16, 30, 99, level);
    }

    public void createMineField(int row, int col, int mC, int level) {
        label4.setText("开始计时");
        label5.setText("0-步");
        remove = 0;
        mineC = mC;
        r = row;
        c = col;
        i = 0;
        j = 0;
        gameData = new GameData(level);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                i++;
                if (i >= 60) {
                    j++;
                    i -= 60;
                }
                label4.setText(" " + j + "分/" + i + "秒");
            }
        };

        button1.setIcon(imgs[CALM.ordinal()]);
        buttons.clear();
        game = new Game(row, col, mC);
        game.setClickCount(level);
        GridLayout layout = new GridLayout(row, col);
        panel5.setLayout(layout);
        int count = row * col;
        int btnWidth = 41;
        int btnHeight = 41;
        Font f = new Font(null, Font.BOLD, 10);
        for (int i = 0; i < count; i++) {
            int seed = i;
            JButton btn = new JButton();
            buttons.add(btn);
//            if (game.getMineArea().isMine(i / col, i % col)) {
//                btn.setIcon(imgs[BOOM.ordinal()]);
//            }
            btn.setFont(f);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mineBtnMouseClicked(e, seed);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (btn.isEnabled()) {
                            button1MousePressed(e);
                        }
                    }
                }
            });
            btn.setPreferredSize(new Dimension(btnWidth, btnHeight));
            panel5.add(btn);
        }
        // frame1.setSize(btnWidth * col + 1, row * btnHeight + panel4.getHeight() + btnHeight + 11);
        frame1.setSize(btnWidth * col, row * btnHeight + panel4.getHeight() + btnHeight + 10 + menuBar1.getHeight());
        frame1.validate();
        //  panel5.validate();

        timer.schedule(timerTask, 1000, 1000);
    }


    private void mineBtnMouseClicked(MouseEvent e, int seed) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (buttons.get(seed).isEnabled()) {
                gameData.addStepNumber();

                label5.setText(gameData.getStepNumber() + "-步");
                mineButtonClicked(seed);
            }
        } else {
            mineButtonRightC(seed);
        }
    }

    // TODO: 2020/10/15  
    private void mineButtonRightC(int index) {
        // TODO add your code here
        JButton btn = buttons.get(index);
        if (btn.getIcon() == null) {
            btn.setIcon(imgs[FLAG.ordinal()]);
            btn.setDisabledIcon(imgs[FLAG.ordinal()]);
            btn.setEnabled(false);
        } else if (imgs[FLAG.ordinal()].equals(btn.getIcon())) {
            btn.setIcon(imgs[QUESTION.ordinal()]);
            btn.setDisabledIcon(imgs[QUESTION.ordinal()]);
            btn.setEnabled(false);
        } else if (imgs[QUESTION.ordinal()].equals(btn.getIcon())) {
            btn.setEnabled(true);
            btn.setIcon(null);
        }
    }

    private void mineButtonClicked(int index) {
        // TODO add your code here

        JButton btn = buttons.get(index);
        if (!btn.isEnabled()) {
            return;
        }

        btn.setEnabled(false);

        int r = index / game.getMineArea().getCol();
        int c = index % game.getMineArea().getCol();

        if (game.getMineArea().isMine(r, c)) {
            // btn.setBackground(new Color(255, 0, 0));
            int type = REDBOOM.ordinal();
            setButtonIcon(btn, type);
            button1.setIcon(imgs[SAD.ordinal()]);
            gameOver();
        } else {
            button1.setIcon(imgs[CALM.ordinal()]);
            int cnt = game.getMineArea().getNearMineCount(r, c);
            if (cnt != 0) {
                setButtonIcon(btn, cnt - 1);
            } else {
                //top left
                int tempRow = r - 1;
                int tempCol = c - 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }

                //top
                tempRow = r - 1;
                tempCol = c;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }

                //top right
                tempRow = r - 1;
                tempCol = c + 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }


                // right
                tempRow = r;
                tempCol = c + 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }
                //bottom right
                tempRow = r + 1;
                tempCol = c + 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }
                //bottom
                tempRow = r + 1;
                tempCol = c;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }
                //bottom left
                tempRow = r + 1;
                tempCol = c - 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }
                // left
                tempRow = r;
                tempCol = c - 1;
                if (game.getMineArea().isValid(tempRow, tempCol)) {
                    mineButtonClicked(tempRow * game.getMineArea().getCol() + tempCol);
                }

            }

        }

        /*
         * ImageIcon icon1 = new ImageIcon(curDir + "/img/folder.png");
         * Image img = icon1.getImage();
         * Image newimg = img.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
         * ImageIcon icon = new ImageIcon(newimg);
         * JButton button = new JButton(icon);
         * */

        for (JButton button : buttons) {
            if (!button.isEnabled()) {
                if (button.getDisabledIcon() == null) {
                    remove++;
                } else if (!button.getDisabledIcon().equals(imgs[FLAG.ordinal()]) && !button.getDisabledIcon().equals(imgs[QUESTION.ordinal()])) {
                    remove++;

                }
            }
        }
        if (remove == (this.r * this.c - mineC)) {
            gameVictory();
        }
        remove = 0;
    }

    private void gameVictory() {
        gameOver();
        gameData.setTime(j * 100 + i);
        gameData.saveGameData();
        button1.setIcon(imgs[SMILE.ordinal()]);
        JOptionPane.showMessageDialog(null, "游戏胜利,恭喜！");

    }

    private void setButtonIcon(JButton btn, int index) {

        if (index < 0) {
            btn.setEnabled(false);
            return;
        }
        btn.setIcon(imgs[index]);
        btn.setDisabledIcon(imgs[index]);
        btn.setEnabled(false);
    }

    private void gameOver() {
        int row = game.getMineArea().getRow();
        int col = game.getMineArea().getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!buttons.get(i * col + j).isEnabled()) {
                    continue;
                } else {

                    if (game.getMineArea().isMine(i, j)) {
                        setButtonIcon(buttons.get(i * col + j), BOOM.ordinal());
                    } else {
                        int cnt = game.getMineArea().getNearMineCount(i, j);
                        setButtonIcon(buttons.get(i * col + j), cnt - 1);
                    }


                }
            }
        }
        timerTask.cancel();
//        timer.cancel();
    }

    private void button1MousePressed(MouseEvent e) {
        button1.setIcon(imgs[SURPRISED.ordinal()]);
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
        timerTask.cancel();
        panel5.removeAll();
        panel5.setLayout(null);
        if (level == 1) {
            easyGame();
        }
        if (level == 2) {
            middleGame();
        }
        if (level == 3) {
            hardGame();
        }
    }

    private void menuItem1MouseClicked(MouseEvent e) {
        // TODO add your code here
        panel5.removeAll();
        panel5.setLayout(null);
        easyGame();
    }

    //hha
    private void menuItem1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        panel5.removeAll();
        panel5.setLayout(null);
        easyGame();
    }


    private void middleActionPerformed(ActionEvent e) {
        // TODO add your code here
        panel5.removeAll();
        panel5.setLayout(null);
        middleGame();
    }

    private void hardActionPerformed(ActionEvent e) {
        // TODO add your code here
        panel5.removeAll();
        panel5.setLayout(null);
        hardGame();
    }

    private void customActionPerformed(ActionEvent e) {
        // TODO add your code here

    }

    //hhhhhh
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        frame1 = new JFrame();
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu2 = new JMenu();
        menuItem5 = new JMenuItem();
        panel4 = new JPanel();
        label4 = new JLabel();
        hSpacer2 = new JPanel(null);
        button1 = new JButton();
        hSpacer4 = new JPanel(null);
        label5 = new JLabel();
        panel5 = new JPanel();
        buttons = new ArrayList<>();

        timer = new Timer();
        //======== frame1 ========
        {
            frame1.setTitle("\u6fc0\u60c5\u626b\u96f7");
            frame1.setResizable(false);
            frame1.setFont(new Font(Font.DIALOG, Font.PLAIN, 5));
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new VerticalLayout());

            //======== menuBar1 ========
            {
                menuBar1.setBackground(new Color(238, 238, 238));

                //======== menu1 ========
                {
                    menu1.setText("\u96be\u5ea6");

                    //---- menuItem1 ----
                    menuItem1.setText("\u521d\u7ea7");
                    menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                    menu1.add(menuItem1);

                    //---- menuItem2 ----
                    menuItem2.setText("\u4e2d\u7ea7");
                    menuItem2.addActionListener(e -> middleActionPerformed(e));
                    menu1.add(menuItem2);

                    //---- menuItem3 ----
                    menuItem3.setText("\u9ad8\u7ea7");
                    menuItem3.addActionListener(e -> hardActionPerformed(e));
                    menu1.add(menuItem3);

                    //---- menuItem4 ----
                    menuItem4.setText("\u81ea\u5b9a\u4e49");
                    menuItem4.addActionListener(e -> customActionPerformed(e));
                    menu1.add(menuItem4);
                }
                menuBar1.add(menu1);

                //======== menu2 ========
                {
                    menu2.setText("选项");

                    //---- menuItem5 ----
                    menuItem5.setText("\u67e5\u770b\u6218\u7ee9");
                    menuItem5.addActionListener(e -> historyActionPerformed(e));
                    menu2.add(menuItem5);
                }
                menuBar1.add(menu2);
            }
            frame1.setJMenuBar(menuBar1);

            //======== panel4 ========
            {
                panel4.setPreferredSize(new Dimension(150, 60));
                panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));

                //---- label4 ----

                label4.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
                label4.setPreferredSize(new Dimension(100, 30));
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                panel4.add(label4);

                //---- hSpacer2 ----
                hSpacer2.setPreferredSize(new Dimension(100, 30));
                hSpacer2.setMinimumSize(new Dimension(20, 12));
                panel4.add(hSpacer2);

                //---- button1 ----
                button1.setText("");
//                button1.setMinimumSize(new Dimension(40, 40));
//                button1.setMaximumSize(new Dimension(40, 40));
                button1.setBackground(Color.black);
                button1.setPreferredSize(new Dimension(45, 45));
                button1.addMouseListener(new MouseAdapter() {
                                             @Override
                                             public void mouseClicked(MouseEvent e) {
                                                 button1MouseClicked(e);
                                             }
                                         }
                );
                panel4.add(button1);

                //---- hSpacer4 ----
                hSpacer4.setPreferredSize(new Dimension(100, 30));
                hSpacer4.setMinimumSize(new Dimension(20, 12));
                panel4.add(hSpacer4);

                //---- label5 ----

                label5.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
                label5.setPreferredSize(new Dimension(60, 30));
                label5.setHorizontalAlignment(SwingConstants.CENTER);
                panel4.add(label5);
            }
            frame1ContentPane.add(panel4);

            //======== panel5 ========
            {
                panel5.setLayout(new GridLayout());
            }
            frame1ContentPane.add(panel5);
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void historyActionPerformed(ActionEvent e) {
        List<String> strings = GameData.readGameData();
        ShowGameData showGameData = new ShowGameData(strings.size(), 2, strings);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame frame1;
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenu menu2;
    private JMenuItem menuItem5;
    private JPanel panel4;
    private JLabel label4;
    private JPanel hSpacer2;
    private JButton button1;
    private JPanel hSpacer4;
    private JLabel label5;
    private JPanel panel5;
    private Game game;
    private String appPath;
    private ImageIcon[] imgs;
    private ArrayList<JButton> buttons;
    private int remove;
    private int mineC;
    private int r;
    private int c;
    private GameData gameData;
    private Date date;
    private Timer timer;
    private TimerTask timerTask;
    private int i;
    private int j;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

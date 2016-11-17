package org.xm.game.retrosnake;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by xuming on 2016/6/18.
 */
public class SnakeView implements Observer {
    SnakeControl control = null;
    SnakeModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    JLabel labelScore;

    public static final int canvasWidth = 200;
    public static final int canvasHeight = 300;

    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;

    public SnakeView(SnakeModel model, SnakeControl control) {
        this.model = model;
        this.control = control;

        mainFrame = new JFrame("GreedSnake");

        Container cp = mainFrame.getContentPane();

        // 创建顶部的分数显示
        labelScore = new JLabel("Score:");
        cp.add(labelScore, BorderLayout.NORTH);

        // 创建中间的游戏显示区域
        paintCanvas = new Canvas();
        paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
        paintCanvas.addKeyListener(control);
        cp.add(paintCanvas, BorderLayout.CENTER);

        // 创建底下的帮助栏
        JPanel panelButtom = new JPanel();
        panelButtom.setLayout(new BorderLayout());
        JLabel labelHelp;
        labelHelp = new JLabel("PageUp, PageDown for speed;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel("ENTER or R or S for start;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.CENTER);
        labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.SOUTH);
        cp.add(panelButtom, BorderLayout.SOUTH);

        mainFrame.addKeyListener(control);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    void repaint() {
        Graphics g = paintCanvas.getGraphics();

        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // draw the snake
        g.setColor(Color.BLACK);
        LinkedList na = model.nodeArray;
        Iterator it = na.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        // draw the food
        g.setColor(Color.RED);
        Node n = model.food;
        drawNode(g, n);

        updateScore();
    }

    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth,
                n.y * nodeHeight,
                nodeWidth - 1,
                nodeHeight - 1);
    }

    public void updateScore() {
        String s = "Score: " + model.score;
        labelScore.setText(s);
    }

    public void update(Observable o, Object arg) {
        repaint();
    }
}

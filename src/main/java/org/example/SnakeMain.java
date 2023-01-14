package org.example;

import org.example.objects.Apple;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeMain extends JPanel implements ActionListener {
    public static JFrame jFrame;

    Snake snake = new Snake(5, 6, 5, 5);
    Apple apple = new Apple(Math.abs((int) Math.random() * SnakeMain.WIDTH - 1),
            Math.abs((int) Math.random() * SnakeMain.HEIGHT - 1));
    public static int speed = 8;

    Timer timer = new Timer(1000 / speed, this);
    public static final int SCALE = 32;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static boolean col = true;

    public SnakeMain() {
        timer.start();
        col=true;
        addKeyListener(new KeyBoard());
        setFocusable(true);
    }

    public static void main(String[] args) {

        jFrame = new JFrame("Snake");
        jFrame.setSize(WIDTH * SCALE + 15, HEIGHT * SCALE + 37);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.add(new SnakeMain());
        jFrame.setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
        for (int x = 0; x < WIDTH * SCALE; x += SCALE) {
            g.setColor(Color.black);
            g.drawLine(x, 0, x, WIDTH * SCALE);

        }
        for (int y = 0; y < WIDTH * SCALE; y += SCALE) {
            g.setColor(Color.black);
            g.drawLine(0, y, HEIGHT * SCALE, y);
        }

        g.setColor(Color.RED);
        g.fillOval(apple.posX * SCALE + 4, apple.posY * SCALE + 4, SCALE - 8, SCALE - 8);

        for (int i = 0; i < snake.length; i++) {
            if (col == true) {
                g.setColor(Color.GREEN);
            } else if (col == false) {
                g.setColor(Color.RED);
            }
            g.fillRect(snake.snakeX[i] * SCALE + 2, snake.snakeY[i] * SCALE + 2, SCALE - 3, SCALE - 3);

            g.setColor(Color.magenta);
            g.fillRect(snake.snakeX[0] * SCALE + 2, snake.snakeY[0] * SCALE + 2, SCALE - 3, SCALE - 3);
        }
    }

    public void actionPerformed(ActionEvent e) {
        snake.move();
        if (snake.snakeX[0] == apple.posX && snake.snakeY[0] == apple.posY) {
            apple.setRandomPosition();
            snake.length++;
        }
        for (int i = 1; i < snake.length; i++) {
            if (snake.snakeX[1] == apple.posX && snake.snakeY[1] == apple.posY) {
                apple.setRandomPosition();
            }
            if (snake.snakeX[0] == snake.snakeX[i] && snake.snakeY[0] == snake.snakeY[i]) {

                timer.stop();
                col = false;
                int opt = JOptionPane.showConfirmDialog(null, "You lost.\n You want to start over? ", "", JOptionPane.YES_NO_OPTION);
                {
                    if (opt == 0) {
                        jFrame.setVisible(false);
                        snake.length = 2;
                        snake.direction = 0;
                        apple.setRandomPosition();
                        jFrame.setVisible(true);
                        timer.start();
                        col=true;
                    }else {
                        System.exit(0);
                    }
                }
            }
            repaint();
        }
    }

    public class KeyBoard extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_UP && snake.direction != 2) {
                snake.direction = 0;
            }
            if (keyCode == KeyEvent.VK_DOWN && snake.direction != 0) {
                snake.direction = 2;
            }
            if (keyCode == KeyEvent.VK_LEFT && snake.direction != 1) {
                snake.direction = 3;
            }
            if (keyCode == KeyEvent.VK_RIGHT && snake.direction != 3) {
                snake.direction = 1;
            }
        }
    }

}

package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    Random r = new Random();
    int SpeedX = r.nextInt(3) + 2;
    int SpeedY = r.nextInt(5) + 3;
    
    int BricksX = 3;
    int BricksY = 7;

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 1;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -SpeedX;
    private int ballYdir = -SpeedY;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(BricksX, BricksY);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Creates a Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Creates Blocks
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //Scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        //Paddle
        g.setColor(Color.red);
        g.fillRect(playerX, 550, 100, 8);

        //Ball
        g.setColor(Color.blue);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if (totalBricks<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Win: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Space for next Level", 150, 350);
        }

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                MoveRight();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                MoveLeft();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                Random r = new Random();
                SpeedX = r.nextInt(3) + 2;
                SpeedY = r.nextInt(5) + 3;
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -SpeedX;
                ballYdir = -SpeedY;
                playerX = 310;
                score = 0;
                BricksX = 3;
                BricksY = 7;
                totalBricks = BricksX*BricksY;
                map = new MapGenerator(BricksX, BricksY);

                repaint();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!play) {
                Random r = new Random();
                SpeedX = r.nextInt(3) + 2;
                SpeedY = r.nextInt(5) + 3;
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -SpeedX;
                ballYdir = -SpeedY;
                playerX = 310;
                BricksX ++;
                BricksY ++;
                totalBricks = BricksX*BricksY;
                map = new MapGenerator(BricksX, BricksY);

                repaint();
            }
        }
    }

    public void MoveRight() {
        play = true;
        playerX += 20;
    }

    public void MoveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}

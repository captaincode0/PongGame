package core;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by captaincode on 26/03/17.
 */
public class BallPanel extends JPanel implements Runnable {
    private int[] ballChords;
    private int[] player1Chords;
    private int[] player2Chords;
    private Thread gameThread;

    private final int MOVE_OFFSET = 5;
    private final int MOVE_LEFT = MOVE_OFFSET;
    private final int MOVE_RIGHT = -1 * MOVE_OFFSET;
    private final int MOVE_UP = MOVE_OFFSET;
    private final int MOVE_DOWN = -1 * MOVE_OFFSET;

    private int scorePlayer1;
    private int scorePlayer2;
    private int windowHeight;
    private int windowWidth;

    private boolean isPlayer1Up;
    private boolean isPlayer1Down;
    private boolean isPlayer2Up;
    private boolean isPlayer2Down;
    private boolean isGameOver;
    private boolean theGameKeeps;

    public BallPanel() {
        //set element chords
        this.ballChords = new int[]{10, 100};
        this.player1Chords = new int[]{10, 100};
        this.player2Chords = new int[]{230, 100};

        this.gameThread = new Thread(this);
        this.gameThread.start();
        this.theGameKeeps = true;
    }

    @Override
    public void paintComponent(Graphics gc) {
        this.setOpaque(false);
        super.paintComponent(gc);

        //draw the ball
        gc.setColor(Color.black);
        gc.fillOval(this.ballChords[0], this.ballChords[1], 8, 8);

        //draw the panels
        gc.fillRect(this.player1Chords[0], this.player1Chords[1], 10, 25);
        gc.fillRect(this.player2Chords[0], this.player2Chords[1], 10, 25);

        //draw player scores
        gc.drawString("Player 1: " + this.scorePlayer1, 25, 10);
        gc.drawString("Player 2: " + this.scorePlayer2, 150, 10);

        if (this.isGameOver)
            gc.drawString("Game Over", 100, 125);
    }

    public void drawBall(int nx, int ny) {
        this.ballChords[0] = nx;
        this.ballChords[1] = ny;
        this.windowWidth = this.getWidth();
        this.windowHeight = this.getHeight();
        this.repaint();
    }

    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_W:
                this.isPlayer1Up = true;
                break;
            case KeyEvent.VK_S:
                this.isPlayer1Down = true;
                break;
            case KeyEvent.VK_UP:
                this.isPlayer2Up = true;
                break;
            case KeyEvent.VK_DOWN:
                this.isPlayer2Down = true;
        }
    }

    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_W:
                this.isPlayer1Up = false;
                break;
            case KeyEvent.VK_S:
                this.isPlayer1Down = false;
                break;
            case KeyEvent.VK_UP:
                this.isPlayer2Up = false;
                break;
            case KeyEvent.VK_DOWN:
                this.isPlayer2Down = false;
        }
    }

    public void movePlayer1() {
        if (this.isPlayer1Up & (this.player1Chords[1] >= 0))
            this.player1Chords[1] += MOVE_DOWN;
        if (this.isPlayer1Down & (this.player1Chords[1] <= (this.getHeight() - 25)))
            this.player1Chords[1] += MOVE_UP;
        repaint();
    }

    public void movePlayer2() {
        if (this.isPlayer2Up & (this.player2Chords[1] >= 0))
            this.player2Chords[1] += MOVE_DOWN;
        if (this.isPlayer2Down & (this.player2Chords[1] <= (this.getHeight())))
            this.player2Chords[1] += MOVE_UP;

        repaint();
    }

    @Override
    public void run() {
        boolean leftToRight = false;
        boolean upToDown = false;

        while (true) {
            if (theGameKeeps) {
                if (leftToRight) {
                    ballChords[0] += MOVE_RIGHT;

                    //to the right
                    if (ballChords[0] >= (windowHeight - 8))
                        leftToRight = false;
                } else {
                    ballChords[0] += MOVE_LEFT;

                    if (ballChords[0] <= 0)
                        leftToRight = true;
                }

                if (upToDown) {
                    ballChords[1] += MOVE_UP;

                    if (ballChords[1] >= (windowHeight - 8))
                        upToDown = false;
                } else {
                    ballChords[1] += MOVE_DOWN;

                    if (ballChords[1] <= 0)
                        upToDown = true;
                }

                drawBall(ballChords[0], ballChords[1]);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                //move player 1
                movePlayer1();

                //move player 2
                movePlayer2();

                if (ballChords[0] >= (windowWidth - 8))
                    ++scorePlayer1;

                if (ballChords[0] == 0)
                    ++scorePlayer2;

                if (scorePlayer1 == 6 || scorePlayer2 == 6) {
                    isGameOver = true;
                    theGameKeeps = false;
                }

                if(ballChords[0] == (player1Chords[0]+10) & ballChords[1] >= player1Chords[1] & ballChords[1] <= (player1Chords[1]+25))
                    leftToRight = true;

                if(ballChords[0] == (player2Chords[0]-5) & ballChords[1] >= player2Chords[1] & ballChords[1] <= (player2Chords[1]+25))
                    leftToRight = false;
            }
        }
    }
}
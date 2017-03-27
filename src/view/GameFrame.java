package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import core.BallPanel;

public class GameFrame extends JFrame{
    private JPanel gameContentPane;
    private BallPanel ballPanel;

    public GameFrame(){
        super();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt){
                formKeyPressed(evt);
            }

            @Override
            public void keyReleased(KeyEvent evt){
                formKeyRealeased(evt);
            }
        });

        this.ballPanel = new BallPanel();

        initialize();
    }

    private BallPanel getBallPanel(){
        return this.ballPanel;
    }

    private JPanel getGameContentPane(){
        if(this.gameContentPane == null){
            this.gameContentPane = new JPanel();
            this.gameContentPane.setLayout(new BorderLayout());
            this.gameContentPane.add(this.ballPanel);
        }
        return this.gameContentPane;
    }

    private void formKeyPressed(KeyEvent evt){
        this.ballPanel.keyPressed(evt);
    }

    private void formKeyRealeased(KeyEvent evt){
        this.ballPanel.keyReleased(evt);
    }

    private void initialize(){
        this.setResizable(false);
        this.setBounds(new Rectangle(312, 184, 250, 250));
        this.setMaximumSize(new Dimension(250, 250));
        this.setMinimumSize(new Dimension(250, 250));
        this.setContentPane(this.getGameContentPane());
        this.setTitle("Captaincode Pong");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame pongGame = new GameFrame();
                pongGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pongGame.setVisible(true);
            }
        });
    }
}

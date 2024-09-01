package com.ovi.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TicTacToe {

    int b_Width = 300;
    int b_Height = 350;

    JFrame frame = new JFrame("Tic Tac Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel gamePanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String player1;
    String player2;
    String currentPlayer;

    boolean gameover = false;
    int turns = 0;

    TicTacToe() {
        getPlayerNames(); 

        currentPlayer = player1; 

        frame.setVisible(true);
        frame.setSize(b_Width, b_Height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(new Color(255, 69, 0)); 
        textLabel.setForeground(Color.WHITE); 
        textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(currentPlayer + "'s turn..");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setBackground(new Color(0, 191, 255)); 
        frame.add(gamePanel);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                JButton box = new JButton();
                board[i][j] = box;
                gamePanel.add(box);
                box.setBackground(new Color(255, 255, 102)); 
                box.setForeground(new Color(75, 0, 130)); 
                box.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                box.setBorderPainted(true); 
                box.setFocusPainted(false); 
                box.setFocusable(false);

                box.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameover){
                            return;
                        }
                        JButton box = (JButton) e.getSource();
                        if(box.getText().equals("")){
                            box.setText(currentPlayer.equals(player1) ? "X" : "O");
                            turns++;
                            checkWinner();
                            if(!gameover){
                                currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
                                textLabel.setText(currentPlayer + "'s turn..");

                            }
                        }
                    }
                });
            }
        }
    }

    void getPlayerNames() {
        while (true) { 
            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            JTextField player1Field = new JTextField();
            JTextField player2Field = new JTextField();
            inputPanel.add(new JLabel("Player 1:"));
            inputPanel.add(player1Field);
            inputPanel.add(new JLabel("Player 2:"));
            inputPanel.add(player2Field);

            int option = JOptionPane.showOptionDialog(
                frame, 
                inputPanel, 
                "Enter players name", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                new String[]{"Enter"},
                "Enter"
            );

            if (option == 0) { 
                player1 = player1Field.getText().trim();
                player2 = player2Field.getText().trim();
                
                
                if (!player1.isEmpty() && !player2.isEmpty()) {
                    break;
                } else {
                    int retry = JOptionPane.showConfirmDialog(
                        frame, 
                        "Names cannot be empty. Do you want to try again?", 
                        "Invalid Input", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.ERROR_MESSAGE
                    );
                    if (retry != JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(
                            frame, 
                            "Game will exit.", 
                            "Exit", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        System.exit(0);
                    }
                }
            } else {
                int confirm = JOptionPane.showConfirmDialog(
                    frame, 
                    "Do you want to exit the game?", 
                    "Exit Confirmation", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    void checkWinner(){
        for (int i = 0; i < 3; i++) {
            if ("".equals(board[i][0].getText())) continue;

            if (board[i][0].getText().equals(board[i][1].getText()) &&
                board[i][1].getText().equals(board[i][2].getText())) {
                for (int k = 0; k < 3; k++) {
                    setWinner(board[i][k]);
                }
                gameover = true;
                return;
            }
        }
        for (int j = 0; j < 3; j++) {
            if ("".equals(board[0][j].getText())) continue;
            
            if (board[0][j].getText().equals(board[1][j].getText()) &&
                    board[1][j].getText().equals(board[2][j].getText())) {
                for (int l = 0; l < 3; l++) {
                    setWinner(board[l][j]);
                }
                gameover = true;
                return;
            }
        }
        if (board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText()) &&
            !"".equals(board[0][0].getText())) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameover = true;
            return;
        }
        if (board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText()) &&
            !"".equals(board[0][2].getText())) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameover = true;
            return;
        }
        if (turns == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    setTie(board[i][j]);
                }
            }
            gameover = true;
            showRestartDialog();
        }
    }

    void setWinner(JButton box) {
        box.setForeground(Color.WHITE);
        box.setBackground(new Color(50, 205, 50));
        textLabel.setText(currentPlayer + " won the game!");
    }

    void setTie(JButton box) {
        box.setForeground(Color.WHITE);
        box.setBackground(new Color(255, 69, 0));
        textLabel.setText("Tie!");
    }
    
    void showRestartDialog() {
        int response = JOptionPane.showConfirmDialog(
            frame, 
            "Do you want to restart the game?", 
            "Restart Game",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        if (response == JOptionPane.YES_OPTION) {
            frame.dispose();
            new TicTacToe();
        } else {
            frame.dispose();
        }
    }
}

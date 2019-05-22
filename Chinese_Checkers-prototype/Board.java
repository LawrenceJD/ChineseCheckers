
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

import java.net.URL;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Board extends JPanel {

        private int BOARD_SIZE = 620;
        private JLabel gb [] = new JLabel[121]; /*Represents all board spaces, and 
                                                contains all pieces as icons.
        
        When syncing the "gb" array with the server's array, a "for each" loop and
        "setIcon" could be used to set each of the "gb" array's JLabel contents as
        the contents of the corresponding JLabels in the array given by the server.*/
        
        private JLabel modeLabels [] = new JLabel[4];
        private JLabel gameStatus1;
        private JLabel gameStatus2;
        private JButton endTurn;

        private JButton modes [] = new JButton[9];
        private SpringLayout boardLayout;
        
        private ArrayList<Integer> turnOrder;
        private HashMap<Integer, Integer> player;
        
        private int currPlayer; //keeps track of who is the current player
        private int gameMode = -1; // 0 - 8
        
        private int gMode = -1;
        
        /*Can add a single variable stating what color this client is, assigned by
        the server/host, and disable the ability to move pieces for colors other
        than that color, and when it is not that color's turn.
        
        Could restrict server from allowing more connections than the first client
        that connects until that client picks a "gamemode", then allows however many more
        clients are required for that "gamemode" to connect.
        
        The server will only listen for input sent from the color whose turn it currently is.
        The client will tell the server when its turn has ended, and the server will update
        the other clients accordingly.*/
        
        private boolean gameStarted; //is true upon a game mode being selected
        private boolean gameOver;

        private ImageIcon bkg;

        private ImageIcon o_grn, o_blk, o_wht, o_yel, o_blu, o_red, o_blank;
        private ImageIcon s_grn, s_blk, s_wht, s_yel, s_blu, s_red;
        private ImageIcon h_grn, h_blk, h_wht, h_yel, h_blu, h_red;
        private ImageIcon d_grn, d_blk, d_wht, d_yel, d_blu, d_red;

        private ImageIcon mode_0, mode_1, mode_2, mode_3, mode_4,
                          mode_5, mode_6, mode_7, mode_8, mode_9;

        private ImageIcon boardBKG;

        private BoardMouseHandler boardMouseHandler = new BoardMouseHandler();
        private OptionMouseHandler optionMouseHandler
                                         = new OptionMouseHandler();

        private int winner; //Holds the color of the winning player upon a player winning
        
        //////////////////////////////////////////////////////////////////////
        // Returns the value of currPlayer
        public int getCurrPlayer() {
            return currPlayer;
        }
        
        //////////////////////////////////////////////////////////////////////
        // Returns the value of gMode
        public boolean getMode() {
            //System.out.println(gMode);
            return optionMouseHandler.optGetMode();
        }
        
        //////////////////////////////////////////////////////////////////////
        // Sets the value of gMode
        public void setMode(int mode) {
            gMode = mode;
            //System.out.println(mode);
            //System.out.println(gMode);
        }
        
        //////////////////////////////////////////////////////////////////////
        // Returns the value of winner
        public int getWinner() {
            return winner;
        }
        
        //////////////////////////////////////////////////////////////////////
        // Initialize all the ImageIcon objects with resources
        public void initImageIcons() {

            o_grn = new ImageIcon(getClass().getResource("images/o_green_34.png"));
            o_blk = new ImageIcon(getClass().getResource("images/o_black_34.png"));
            o_wht = new ImageIcon(getClass().getResource("images/o_white_34.png"));
            o_yel = new ImageIcon(getClass().getResource("images/o_yellow_34.png"));
            o_blu = new ImageIcon(getClass().getResource("images/o_blue_34.png"));
            o_red = new ImageIcon(getClass().getResource("images/o_red_34.png"));
            o_blank = new ImageIcon(getClass().getResource("images/o_blank_34.png"));

            s_grn = new ImageIcon(getClass().getResource("images/s_green_34.png"));
            s_blk = new ImageIcon(getClass().getResource("images/s_black_34.png"));
            s_wht = new ImageIcon(getClass().getResource("images/s_white_34.png"));
            s_yel = new ImageIcon(getClass().getResource("images/s_yellow_34.png"));
            s_blu = new ImageIcon(getClass().getResource("images/s_blue_34.png"));
            s_red = new ImageIcon(getClass().getResource("images/s_red_34.png"));

            h_grn = new ImageIcon(getClass().getResource("images/h_green_34.png"));
            h_blk = new ImageIcon(getClass().getResource("images/h_black_34.png"));
            h_wht = new ImageIcon(getClass().getResource("images/h_white_34.png"));
            h_yel = new ImageIcon(getClass().getResource("images/h_yellow_34.png"));
            h_blu = new ImageIcon(getClass().getResource("images/h_blue_34.png"));
            h_red = new ImageIcon(getClass().getResource("images/h_red_34.png"));

            d_grn = new ImageIcon(getClass().getResource("images/d_green_34.png"));
            d_blk = new ImageIcon(getClass().getResource("images/d_black_34.png"));
            d_wht = new ImageIcon(getClass().getResource("images/d_white_34.png"));
            d_yel = new ImageIcon(getClass().getResource("images/d_yellow_34.png"));
            d_blu = new ImageIcon(getClass().getResource("images/d_blue_34.png"));
            d_red = new ImageIcon(getClass().getResource("images/d_red_34.png"));

            mode_0 = new ImageIcon(getClass().getResource("images/mode_0.jpg"));
            mode_1 = new ImageIcon(getClass().getResource("images/mode_1.jpg"));
            mode_2 = new ImageIcon(getClass().getResource("images/mode_2.jpg"));
            mode_3 = new ImageIcon(getClass().getResource("images/mode_3.jpg"));
            mode_4 = new ImageIcon(getClass().getResource("images/mode_4.jpg"));
            mode_5 = new ImageIcon(getClass().getResource("images/mode_5.jpg"));
            mode_6 = new ImageIcon(getClass().getResource("images/mode_6.jpg"));
            mode_7 = new ImageIcon(getClass().getResource("images/mode_7.jpg"));
            mode_8 = new ImageIcon(getClass().getResource("images/mode_8.jpg"));
            
            boardBKG = new ImageIcon(getClass().getResource("images/board_620.jpg"));
            
        } // end of initImageIcons
        
        //////////////////////////////////////////////////////////////////////
        // Based on the gameMode integer passed in it builds 
        public void setTurnOrder(int mode1) {
            
            
            
            switch(mode1) {

                /////////////////////
                // 2 Players
                case 0:
                    currPlayer = 0;

                    player.put(0,3);
                    player.put(3,0);

                    turnOrder.add(0);
                    turnOrder.add(3);

                    break;
                case 1:
                    currPlayer = 1;

                    player.put(1,4);
                    player.put(4,1);

                    turnOrder.add(1);
                    turnOrder.add(4);

                    break;              
                case 2:
                    currPlayer = 2;

                    player.put(2,5);
                    player.put(5,2);

                    turnOrder.add(2);
                    turnOrder.add(5);

                    break;

                /////////////////////
                // 3 Players
                case 3:
                    currPlayer = 0;

                    player.put(0,2);
                    player.put(2,4);
                    player.put(4,0);

                    turnOrder.add(0);
                    turnOrder.add(2);
                    turnOrder.add(4);

                    break;

                case 4:
                    currPlayer = 1;

                    player.put(1,3);
                    player.put(3,5);
                    player.put(5,1);

                    turnOrder.add(1);
                    turnOrder.add(3);
                    turnOrder.add(5);

                    break;      

                /////////////////////
                // 4 Players
                case 5:
                    currPlayer = 0;

                    player.put(0,3);
                    player.put(1,4);
                    player.put(3,0);
                    player.put(4,1);

                    turnOrder.add(0);
                    turnOrder.add(1);
                    turnOrder.add(3);
                    turnOrder.add(4);

                    break;

                case 6:
                    currPlayer = 1;

                    player.put(1,4);
                    player.put(2,5);
                    player.put(4,1);
                    player.put(5,2); 
                    
                    turnOrder.add(1);
                    turnOrder.add(2);
                    turnOrder.add(4);
                    turnOrder.add(5);

                    break;

                case 7:
                    currPlayer = 0;

                    player.put(0,3);
                    player.put(2,5);
                    player.put(3,0);
                    player.put(5,2);  

                    turnOrder.add(0);
                    turnOrder.add(2);
                    turnOrder.add(3);
                    turnOrder.add(5);

                    break; 

                /////////////////////
                // 6 Players
                case 8:
                    currPlayer = 0;
                    player.put(0,3);
                    player.put(1,4);
                    player.put(2,5);
                    player.put(3,0);
                    player.put(4,1);
                    player.put(5,2);   

                    turnOrder.add(0);
                    turnOrder.add(1);
                    turnOrder.add(2);
                    turnOrder.add(3);
                    turnOrder.add(4);
                    turnOrder.add(5);

                    break;  

                default:
                    break;
            }
            
            setMode(mode1);
            //System.out.println(mode);
            System.out.println(gMode);
            updatePlayer(currPlayer); //Added to fix the problem of it saying Green for the first
                                      //turn regardless of whose turn it actually is.
        } // end of setTurnOrder

        //////////////////////////////////////////////////////////////////////
        // Initialize major game control variables
        public void initGame() {
            gameOver = false;
            gameStarted = false;
            winner = -1;

            turnOrder = new ArrayList<Integer>();
            player = new HashMap<Integer, Integer>();
            
            bkg = boardBKG;
            
            buildTurnInfo();
            buildOptions();
            buildBoard();

        } // end of initGame
        
        //////////////////////////////////////////////////////////////////////
        // Board constructor 
        public Board() {
            initImageIcons();

            boardLayout = new SpringLayout();
            setLayout(boardLayout);
            initGame();

        } // end of Board constructor

        //////////////////////////////////////////////////////////////////////
        // Shows the game options that intially show up at start of game
        public void showOptions() {
            endTurn.setVisible(false);
            gameStatus1.setVisible(false);
            gameStatus2.setVisible(false);

            modeLabels[0].setVisible(true);
            modeLabels[1].setVisible(true);
            modeLabels[2].setVisible(true);
            modeLabels[3].setVisible(true);

            modes[0].setVisible(true);
            modes[1].setVisible(true);
            modes[2].setVisible(true);
            modes[3].setVisible(true);
            modes[4].setVisible(true);
            modes[5].setVisible(true);
            modes[6].setVisible(true);
            modes[7].setVisible(true);          
            modes[8].setVisible(true);
        } // end of showOptions

        //////////////////////////////////////////////////////////////////////
        // Hides the game options that intially show up at start of game
        public void hideOptions() {
            endTurn.setVisible(true);
            gameStatus1.setVisible(true);
            gameStatus2.setVisible(true);

            modeLabels[0].setVisible(false);
            modeLabels[1].setVisible(false);
            modeLabels[2].setVisible(false);
            modeLabels[3].setVisible(false);

            modes[0].setVisible(false);
            modes[1].setVisible(false);
            modes[2].setVisible(false);
            modes[3].setVisible(false);
            modes[4].setVisible(false);
            modes[5].setVisible(false);
            modes[6].setVisible(false);
            modes[7].setVisible(false);         
            modes[8].setVisible(false);
            
        } // end of hideOptions


        public void updatePlayer(int i) {
            switch(i) {
                case 0: 
                    gameStatus1.setText("Green's"); 
                    gameStatus1.setForeground(Color.GREEN);
                    break;
                case 1: 
                    gameStatus1.setText("White's"); 
                    gameStatus1.setForeground(Color.WHITE);
                    break;
                case 2: 
                    gameStatus1.setText("Blue's"); 
                    gameStatus1.setForeground(Color.BLUE);
                    break;
                case 3: 
                    gameStatus1.setText("Red's");
                    gameStatus1.setForeground(Color.RED);
                    break;
                case 4: 
                    gameStatus1.setText("Yellow's");
                    gameStatus1.setForeground(Color.YELLOW); 
                    break;
                case 5: 
                    gameStatus1.setText("Black's"); 
                    gameStatus1.setForeground(Color.BLACK);
                    break;
                default: break;
            }
        }
        //////////////////////////////////////////////////////////////////////
        // Builds the turn information elements to be displayed
        public void buildTurnInfo() {
            
            gameStatus1 = new JLabel();
            gameStatus2 = new JLabel();
            
            gameStatus1.setFont(new Font("Tahoma", Font.BOLD, 30));
            gameStatus2.setFont(new Font("Tahoma", Font.BOLD, 30));

            updatePlayer(currPlayer);
                
            gameStatus2.setText("Turn"); 

            add(gameStatus1);
            add(gameStatus2);

            boardLayout.putConstraint(SpringLayout.NORTH, gameStatus1, 
                10, SpringLayout.NORTH, this);
            boardLayout.putConstraint(SpringLayout.WEST, gameStatus1, 
                10, SpringLayout.WEST, this);

            boardLayout.putConstraint(SpringLayout.NORTH, gameStatus2, 
                0, SpringLayout.SOUTH, gameStatus1);
            boardLayout.putConstraint(SpringLayout.WEST, gameStatus2, 
                0, SpringLayout.WEST, gameStatus1);

            endTurn = new JButton("End Turn");
            
            endTurn.setPreferredSize(new Dimension(135, 35));
            endTurn.setFocusPainted(false);
            endTurn.setMargin(new Insets(0,0,0,0));
            endTurn.setFont(new Font("Tahoma", Font.BOLD, 25));
            endTurn.addMouseListener(boardMouseHandler);
            endTurn.setBackground(new Color(59, 89, 182));
            endTurn.setForeground(Color.WHITE);

            boardLayout.putConstraint(SpringLayout.SOUTH, endTurn, 
                -10, SpringLayout.SOUTH, this);
            boardLayout.putConstraint(SpringLayout.EAST, endTurn, 
                -10, SpringLayout.EAST, this);
            
            add(endTurn);

            endTurn.setVisible(false);
            gameStatus1.setVisible(false);
            gameStatus2.setVisible(false);

        } // end of buildTurnInfo

        //////////////////////////////////////////////////////////////////////
        // Builds the option objects for gameMode selection
        public void buildOptions() {

            for (int i = 0; i < 9; i++) {
                modes[i] = new JButton();
                modes[i].setPreferredSize(new Dimension(25, 25));
                modes[i].setFocusPainted(false);
                modes[i].setMargin(new Insets(0,0,0,0));
                modes[i].setFont(new Font("Tahoma", Font.BOLD, 12));
                modes[i].addMouseListener(optionMouseHandler);
                modes[i].setBackground(new Color(59, 89, 182));
                modes[i].setForeground(Color.WHITE);
                add(modes[i]);
            }

            for (int i = 0; i < 4; i++) {
                modeLabels[i] = new JLabel();
                modeLabels[i].setFont(new Font("Tahoma", Font.BOLD, 14));
                add(modeLabels[i]);
            }

            //////////////////////////////////////////////////////////////////

            modeLabels[0].setText("2 PLAYERS");

            boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[0], 
                15, SpringLayout.NORTH, this);
            boardLayout.putConstraint(SpringLayout.WEST, modeLabels[0], 
                15, SpringLayout.WEST, this);
            
            modes[0].setText("1");
            modes[1].setText("2");
            modes[2].setText("3");

            boardLayout.putConstraint(SpringLayout.NORTH, modes[0],
             5, SpringLayout.SOUTH, modeLabels[0]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[0],
             0, SpringLayout.WEST, modeLabels[0]);

            boardLayout.putConstraint(SpringLayout.NORTH, modes[1],
             0, SpringLayout.NORTH, modes[0]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[1],
             5, SpringLayout.EAST, modes[0]);

            boardLayout.putConstraint(SpringLayout.NORTH, modes[2],
             0, SpringLayout.NORTH, modes[1]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[2],
             5, SpringLayout.EAST, modes[1]);
            
            //////////////////////////////////////////////////////////////////

            modeLabels[1].setText("3 PLAYERS");

            boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[1],
             -62, SpringLayout.SOUTH, this);
            boardLayout.putConstraint(SpringLayout.WEST, modeLabels[1],
             15, SpringLayout.WEST, this);

            modes[3].setText("1");
            modes[4].setText("2");

            boardLayout.putConstraint(SpringLayout.NORTH, modes[3],
             5, SpringLayout.SOUTH, modeLabels[1]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[3],
             0, SpringLayout.WEST, modeLabels[1]);

            boardLayout.putConstraint(SpringLayout.NORTH, modes[4],
             0, SpringLayout.NORTH, modes[3]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[4],
             5, SpringLayout.EAST, modes[3]);

            //////////////////////////////////////////////////////////////////
            
            modeLabels[2].setText("4 PLAYERS");

            boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[2],
             15, SpringLayout.NORTH, this);
            boardLayout.putConstraint(SpringLayout.EAST, modeLabels[2],
             -15, SpringLayout.EAST, this);

            modes[5].setText("1");
            modes[6].setText("2");
            modes[7].setText("3");
            
            boardLayout.putConstraint(SpringLayout.NORTH, modes[5],
             5, SpringLayout.SOUTH, modeLabels[2]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[5],
             0, SpringLayout.WEST, modeLabels[2]);

            boardLayout.putConstraint(SpringLayout.NORTH, modes[6],
             0, SpringLayout.NORTH, modes[5]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[6],
             5, SpringLayout.EAST, modes[5]);

            boardLayout.putConstraint(SpringLayout.NORTH, modes[7],
             0, SpringLayout.NORTH, modes[6]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[7],
             5, SpringLayout.EAST, modes[6]);
            
            //////////////////////////////////////////////////////////////////

            modeLabels[3].setText("6 PLAYERS");

            boardLayout.putConstraint(SpringLayout.NORTH, modeLabels[3],
             -62, SpringLayout.SOUTH, this);
            boardLayout.putConstraint(SpringLayout.EAST, modeLabels[3],
             -15, SpringLayout.EAST, this);

            modes[8].setText("1");
            
            boardLayout.putConstraint(SpringLayout.NORTH, modes[8],
             5, SpringLayout.SOUTH, modeLabels[3]);
            boardLayout.putConstraint(SpringLayout.WEST, modes[8],
             0, SpringLayout.WEST, modeLabels[3]);
        } // end of buildOptions

        //////////////////////////////////////////////////////////////////////
        // builds the springlayout for the board
        public void buildBoard() {

            // Initialize game board aka gb
            for (int i = 0; i < gb.length; i++) {
                gb[i] = new JLabel(pieceType(i));
                gb[i].addMouseListener(boardMouseHandler);
                add(gb[i]);
            }

            int SIZE = 34;
            int BELOW = 0;
            int RIGHT = 5;
            int OFFSET = 20;

            boardLayout.putConstraint(SpringLayout.NORTH, gb[0],
             20, SpringLayout.WEST, this);
            boardLayout.putConstraint(SpringLayout.WEST, gb[0],
             295, SpringLayout.WEST, this);
            
            boardLayout.putConstraint(SpringLayout.NORTH, gb[1],
             BELOW, SpringLayout.SOUTH, gb[0]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[2],
             BELOW, SpringLayout.SOUTH, gb[0]);

            boardLayout.putConstraint(SpringLayout.WEST, gb[1],
             -OFFSET, SpringLayout.WEST, gb[0]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[2],
             RIGHT, SpringLayout.EAST, gb[1]);

            boardLayout.putConstraint(SpringLayout.NORTH, gb[3],
             BELOW, SpringLayout.SOUTH, gb[1]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[4],
             BELOW, SpringLayout.SOUTH, gb[1]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[5],
             BELOW, SpringLayout.SOUTH, gb[1]);

            boardLayout.putConstraint(SpringLayout.WEST, gb[3],
             -OFFSET, SpringLayout.WEST, gb[1]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[4],
             RIGHT, SpringLayout.EAST, gb[3]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[5],
             RIGHT, SpringLayout.EAST, gb[4]);
            
            boardLayout.putConstraint(SpringLayout.NORTH, gb[6],
             BELOW, SpringLayout.SOUTH, gb[3]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[7],
             BELOW, SpringLayout.SOUTH, gb[3]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[8],
             BELOW, SpringLayout.SOUTH, gb[3]);
            boardLayout.putConstraint(SpringLayout.NORTH, gb[9],
             BELOW, SpringLayout.SOUTH, gb[3]);

            boardLayout.putConstraint(SpringLayout.WEST, gb[6],
             -OFFSET, SpringLayout.WEST, gb[3]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[7],
             RIGHT, SpringLayout.EAST, gb[6]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[8],
             RIGHT, SpringLayout.EAST, gb[7]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[9],
             RIGHT, SpringLayout.EAST, gb[8]);

            for (int i = 10; i < 23; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[6]);    
            }

            boardLayout.putConstraint(SpringLayout.EAST, gb[10],
             -RIGHT, SpringLayout.WEST, gb[11]);
            boardLayout.putConstraint(SpringLayout.EAST, gb[11],
             -RIGHT, SpringLayout.WEST, gb[12]);
            boardLayout.putConstraint(SpringLayout.EAST, gb[12],
             -RIGHT, SpringLayout.WEST, gb[13]);
            boardLayout.putConstraint(SpringLayout.EAST, gb[13],
             -RIGHT, SpringLayout.WEST, gb[14]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[14],
             -OFFSET, SpringLayout.WEST, gb[6]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[15],
             RIGHT, SpringLayout.EAST, gb[14]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[16],
             RIGHT, SpringLayout.EAST, gb[15]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[17],
             RIGHT, SpringLayout.EAST, gb[16]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[18],
             RIGHT, SpringLayout.EAST, gb[17]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[19],
             RIGHT, SpringLayout.EAST, gb[18]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[20],
             RIGHT, SpringLayout.EAST, gb[19]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[21],
             RIGHT, SpringLayout.EAST, gb[20]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[22],
             RIGHT, SpringLayout.EAST, gb[21]);

            boardLayout.putConstraint(SpringLayout.NORTH, gb[23],
             BELOW, SpringLayout.SOUTH, gb[10]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[23],
             OFFSET, SpringLayout.WEST, gb[10]);

            for (int i = 24; i < 35; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[10]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }
            
            boardLayout.putConstraint(SpringLayout.NORTH, gb[35],
             BELOW, SpringLayout.SOUTH, gb[23]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[35],
             OFFSET, SpringLayout.WEST, gb[23]);

            for (int i = 36; i < 46; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[23]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }
            
            boardLayout.putConstraint(SpringLayout.NORTH, gb[46],
             BELOW, SpringLayout.SOUTH, gb[35]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[46],
             OFFSET, SpringLayout.WEST, gb[35]);

            for (int i = 47; i < 56; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[35]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[56],
             BELOW, SpringLayout.SOUTH, gb[46]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[56],
             OFFSET, SpringLayout.WEST, gb[46]);

            for (int i = 57; i < 65; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[46]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[65],
             BELOW, SpringLayout.SOUTH, gb[56]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[65],
             -OFFSET, SpringLayout.WEST, gb[56]);

            for (int i = 66; i < 75; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[56]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[75],
             BELOW, SpringLayout.SOUTH, gb[65]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[75],
             -OFFSET, SpringLayout.WEST, gb[65]);

            for (int i = 76; i < 86; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[65]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[86],
             BELOW, SpringLayout.SOUTH, gb[75]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[86],
             -OFFSET, SpringLayout.WEST, gb[75]);

            for (int i = 87; i < 98; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[75]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[98],
             BELOW, SpringLayout.SOUTH, gb[86]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[98],
             -OFFSET, SpringLayout.WEST, gb[86]);

            for (int i = 99; i < 111; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[86]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[111],
             BELOW, SpringLayout.SOUTH, gb[102]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[111],
             OFFSET, SpringLayout.WEST, gb[102]);

            for (int i = 112; i < 115; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[102]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[115],
             BELOW, SpringLayout.SOUTH, gb[111]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[115],
             OFFSET, SpringLayout.WEST, gb[111]);

            for (int i = 116; i < 118; i++) {
                boardLayout.putConstraint(SpringLayout.NORTH, gb[i],
                 BELOW, SpringLayout.SOUTH, gb[111]);    
                boardLayout.putConstraint(SpringLayout.WEST, gb[i],
                 RIGHT, SpringLayout.EAST, gb[i-1]);
            }

            boardLayout.putConstraint(SpringLayout.NORTH, gb[118],
             BELOW, SpringLayout.SOUTH, gb[115]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[118],
             OFFSET, SpringLayout.WEST, gb[115]);

            boardLayout.putConstraint(SpringLayout.NORTH, gb[119],
             BELOW, SpringLayout.SOUTH, gb[115]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[119],
             RIGHT, SpringLayout.EAST, gb[118]);

            boardLayout.putConstraint(SpringLayout.NORTH, gb[120],
             BELOW, SpringLayout.SOUTH, gb[118]);
            boardLayout.putConstraint(SpringLayout.WEST, gb[120],
             OFFSET, SpringLayout.WEST, gb[118]);
        } // end of buildBoard
        
        //////////////////////////////////////////////////////////////////////
        // sets the icons of the JLabels that match JLabel 'p'
        public void showMoves(JLabel p) {
            for (int i = 0; i < gb.length; i++) {
                if((inRange(p, gb[i]) || canJump(p, gb[i])) && 
                    p != gb[i] && !isMarble(gb[i])) {
                    gb[i].setIcon(getImageIcon(getColorInt(p), 'h'));
                }
            }
        } // end of showMoves

        //////////////////////////////////////////////////////////////////////
        // Hides all the highlighted options available to JLabel p
        public void hideMoves(JLabel p) {
            // NEED TO ADD HIDE JUMPS TOO
            for (int i = 0; i < gb.length; i++) {
                if(isHighlight(gb[i])) {
                    gb[i].setIcon(o_blank);
                }
            }
        } // end of hideMoves

        //////////////////////////////////////////////////////////////////////
        // Returns true only if JLabel p is blank
        public boolean isBlank(JLabel p) { 
            return 6 == getColorInt(p); 
        } // end of isBlank

        //////////////////////////////////////////////////////////////////////
        // Returns true only if JLabel p is a marble
        public boolean isMarble(JLabel p) { 
            return 0 <= getColorInt(p) && 5 >= getColorInt(p); 
        } // end of isMarble

        //////////////////////////////////////////////////////////////////////
        // Returns true only if JLabel p is a highlighted space
        public boolean isHighlight(JLabel p) {
            if(p.getIcon() == h_grn) return true;
            if(p.getIcon() == h_wht) return true;
            if(p.getIcon() == h_blu) return true;
            if(p.getIcon() == h_red) return true;
            if(p.getIcon() == h_yel) return true;
            if(p.getIcon() == h_blk) return true;
            return false;
        } // end of isHighlight
        
        //////////////////////////////////////////////////////////////////////
        // Returns true only if JLabel p is a dotted space
        public boolean isDot(JLabel p) {
            if(p.getIcon() == d_grn) return true;
            if(p.getIcon() == d_wht) return true;
            if(p.getIcon() == d_blu) return true;
            if(p.getIcon() == d_red) return true;
            if(p.getIcon() == d_yel) return true;
            if(p.getIcon() == d_blk) return true;
            return false;
        } // end of isDot

        //////////////////////////////////////////////////////////////////////
        // returns true if e is 1 move distance from s
        public boolean inRange(JLabel s, JLabel e) {
            return (Math.abs(s.getX() - e.getX()) 
                  + Math.abs(s.getY() - e.getY())) < 55;
        } // end of inRange

        //////////////////////////////////////////////////////////////////////
        // returns true if currPlayer matches JLabel
        public boolean isTurn(JLabel s) {
            if (getColorInt(s) == currPlayer)
                return true;
            return false;
        } // end of isTurn

        //////////////////////////////////////////////////////////////////////
        // Returns true only if the component passed in is both
        // a JLabel and also a marble
        public boolean compIsMarble(Component c) {
            JLabel tmp;

            try { 
                tmp = ((JLabel) c); 
            } catch (ClassCastException e) { 
                return false; 
            }

            if (isMarble(tmp))
                return true;

            return false;           
        } // end of compIsMarble

        //////////////////////////////////////////////////////////////////////
        // returns true if all pieces of a are in the original spots of b
        public boolean isGameOver(int a, int b){
            int count = 0;

            for (int i = 0; i < gb.length; i++) {
                if (pieceType(i) == getImageIcon(b,'o'))
                    if (gb[i].getIcon() == getImageIcon(a,'o'))
                        count++;
            }

            if (count == 10) 
                return true;
            return false;               
        } // end of isGameOver

        //////////////////////////////////////////////////////////////////////
        // returns true if e is 1 move distance from s
        public boolean canJump(JLabel s, JLabel e) {

            // Calculate the distance between s and e
            int total = Math.abs(s.getX() - e.getX()) 
                      + Math.abs(s.getY() - e.getY());

            int x = s.getX() - e.getX();
            int y = s.getY() - e.getY();

            // Determine the direction of jump
            //////////////////////////////////////////////////////////////////
            // direction can be 1, 2, 3, 4, 5 or 6
            int direction = 0;

            if (x > 0 && y > 0) direction = 1;          // + +
            else if (x < 0 && y > 0) direction = 2;     // - +
            else if (x < 0 && y < 0) direction = 3;     // - -
            else if (x > 0 && y < 0) direction = 4;     // + -
            else if (x > 0 && y == 0) direction = 5;    // <--
            else if (x < 0 && y == 0) direction = 6;    // -->
            
            // The code is confusing here because the pixels are not 
            // perfectly aligned as well as I would have liked but 
            // I was able to work around the issues in positioning
            if(total == 78 || total == 106 || total == 107 || total == 108) {
                switch (direction) {
                    case 1: // + +
                        if(compIsMarble(getComponentAt(s.getX() - 20,
                         s.getY() - 34))
                        || compIsMarble(getComponentAt(s.getX() - 19,
                         s.getY() - 34)))
                            if (isBlank(e))
                                return true;
                        break;
                    case 2: // - +
                        if(compIsMarble(getComponentAt(s.getX() + 20, 
                            s.getY() - 34))
                        || compIsMarble(getComponentAt(s.getX() + 19, 
                            s.getY() - 34)))
                            if (isBlank(e))
                                return true;
                        break;
                    case 3: // - -
                        if(compIsMarble(getComponentAt(s.getX() + 20, 
                            s.getY() + 34))
                        || compIsMarble(getComponentAt(s.getX() + 19, 
                            s.getY() + 34)))
                            if (isBlank(e))
                                return true;
                        break;
                    case 4: // + -
                        if(compIsMarble(getComponentAt(s.getX() - 20, 
                            s.getY() + 34))
                        || compIsMarble(getComponentAt(s.getX() - 19, 
                            s.getY() + 34)))
                            if (isBlank(e))
                                return true;
                        break;
                    case 5: // <--
                        if(compIsMarble(getComponentAt(s.getX() - 39, 
                            s.getY())))
                            if (isBlank(e))
                                return true;
                        break;
                    case 6: // -->
                        if(compIsMarble(getComponentAt(s.getX() + 39, 
                            s.getY())))
                            if (isBlank(e))
                                return true;
                        break;
                    default:
                        return false;
                }
            }
            
            return false;
        } // end of canJump

        //////////////////////////////////////////////////////////////////////
        // returns true p has another jump move avaible
        public boolean canJumpAgain(JLabel p) {
            for (int i = 0; i < gb.length; i++)
                if(canJump(p, gb[i]))
                    return true;
            return false;
        } // end of canJumpAgain

        //////////////////////////////////////////////////////////////////////
        // highlights only the jumpable spaces for JLabel p
        public void showJumps(JLabel p) {
            for (int i = 0; i < gb.length; i++) {
                if(canJump(p, gb[i]) && p != gb[i] && !isMarble(gb[i]))
                    gb[i].setIcon(getImageIcon(getColorInt(p), 'h'));
            }
        } // end of showJumps

        //////////////////////////////////////////////////////////////////////
        // returns an int to match p based on its original or select image
        public int getColorInt(JLabel p) {
            if (p.getIcon() == o_grn || p.getIcon() == s_grn) return 0;
            if (p.getIcon() == o_wht || p.getIcon() == s_wht) return 1;
            if (p.getIcon() == o_blu || p.getIcon() == s_blu) return 2;
            if (p.getIcon() == o_red || p.getIcon() == s_red) return 3;
            if (p.getIcon() == o_yel || p.getIcon() == s_yel) return 4;
            if (p.getIcon() == o_blk || p.getIcon() == s_blk) return 5;
            if (p.getIcon() == o_blank) return 6;
            
            return 7;
        } // end of getColorInt

        //////////////////////////////////////////////////////////////////////
        // returns the correct icon for the given index of the game board  
        public ImageIcon pieceType(int index) {

            int grn_arr [] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            int blk_arr [] = {10, 11, 12, 13, 23, 24, 25, 35, 36, 46};
            int wht_arr [] = {19, 20, 21, 22, 32, 33, 34, 44, 45, 55};
            int yel_arr [] = {65, 75, 76, 86, 87, 88, 98, 99, 100, 101};
            int blu_arr [] = {74, 84, 85, 95, 96, 97, 107, 108, 109, 110};
            int red_arr [] = {111, 112, 113, 114, 115, 116, 117, 118, 119, 120};

            for (int i = 0; i < 10; i++) {
                if(grn_arr[i] == index) 
                    return o_grn;
                else if (blk_arr[i] == index)
                    return o_blk;
                else if (wht_arr[i] == index)
                    return o_wht;
                else if (yel_arr[i] == index)
                    return o_yel;
                else if (blu_arr[i] == index)
                    return o_blu;
                else if (red_arr[i] == index)
                    return o_red;
            }

            return o_blank;
        } // end of pieceType
        
        //////////////////////////////////////////////////////////////////////
        // returns ImageIcon type, color or mode based on passed i and c
        public ImageIcon getImageIcon(int i, char c) {
            
            ///////////////////////////////////////
            // Highlight
            if (c == 'h') {
                if (0 == i) return h_grn;
                else if (1 == i) return h_wht;
                else if (2 == i) return h_blu;
                else if (3 == i) return h_red;
                else if (4 == i) return h_yel;
                else if (5 == i) return h_blk;
            }

            ///////////////////////////////////////
            // Dot
            if (c == 'd') {
                if (0 == i) return d_grn;
                else if (1 == i) return d_wht;
                else if (2 == i) return d_blu;
                else if (3 == i) return d_red;
                else if (4 == i) return d_yel;
                else if (5 == i) return d_blk;
            }

            ///////////////////////////////////////
            // Select
            if (c == 's') {
                if (0 == i) return s_grn;
                else if (1 == i) return s_wht;
                else if (2 == i) return s_blu;
                else if (3 == i) return s_red;
                else if (4 == i) return s_yel;
                else if (5 == i) return s_blk;
            }

            ///////////////////////////////////////
            // Original
            if (c == 'o') {
                if (0 == i) return o_grn;
                else if (1 == i) return o_wht;
                else if (2 == i) return o_blu;
                else if (3 == i) return o_red;
                else if (4 == i) return o_yel;
                else if (5 == i) return o_blk;
                else if (6 == i) return o_blank;
            }

            ///////////////////////////////////////
            // Mode
            if (c == 'm') {
                if (0 == i) return mode_0;
                else if (1 == i) return mode_1;
                else if (2 == i) return mode_2;
                else if (3 == i) return mode_3;
                else if (4 == i) return mode_4;
                else if (5 == i) return mode_5;
                else if (6 == i) return mode_6;
                else if (7 == i) return mode_7;
                else if (8 == i) return mode_8;
            }

            ///////////////////////////////////////

            return null;
        } // end of getImageIcon

        //////////////////////////////////////////////////////////////////////
        // returns the original image for a passed in JLabel if
        // p is a selected marble
        public ImageIcon getMarble(JLabel p) {
            if (p.getIcon() == s_grn) return o_grn;
            if (p.getIcon() == s_wht) return o_wht;
            if (p.getIcon() == s_blu) return o_blu;
            if (p.getIcon() == s_red) return o_red;
            if (p.getIcon() == s_yel) return o_yel;
            if (p.getIcon() == s_blk) return o_blk;
            return null;
        } // end of getMarble

        //////////////////////////////////////////////////////////////////////
        // performs variable changes based on gameMode and currPlayer
        // also checks if the game is over based on last move
        public void turnOver() {

            int currIndex = 0;

            for (int i = 0; i < turnOrder.size(); i++)
                if (turnOrder.get(i) == currPlayer)
                    currIndex = i;

            // if the number of players exceeds 3 then the way
            // or is determined changes 
            if (gameMode > 4)
                if (currIndex == turnOrder.size()-1)
                    currPlayer = turnOrder.get(0);
                else 
                    currPlayer = turnOrder.get(currIndex+1);
            else
                currPlayer = player.get(currPlayer);

            updatePlayer(currPlayer);

            if(isGameOver(currPlayer, player.get(currPlayer))) {
                winner = currPlayer;
                initGame();
                showOptions();
                System.out.printf("Player %d Wins!!!\n", winner);
                gameOver = true;
            }
            
        } // end of turnOver

        //////////////////////////////////////////////////////////////////////
        // paints the background image on the Board JPanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Paints the background
            g.drawImage(bkg.getImage(), 0, 0, null);
        }

        //////////////////////////////////////////////////////////////////////
        // sets the size of the JPanel to match the size of board 
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(BOARD_SIZE, BOARD_SIZE);
        }

        //////////////////////////////////////////////////////////////////////
        // Mouse listener for board elements 
        class BoardMouseHandler implements MouseListener {

            private JLabel start;
            private JLabel end;
            private JLabel jumping;
            private JLabel firstMoved;

            private boolean hasJumped = false;

            // MOUSE PRESSED ON PIECE TO MOVE
            public void mousePressed( MouseEvent event ) {
                if (event.getSource() != endTurn) {
                    start = (JLabel) event.getSource();
                    
                    if (!gameOver && gameStarted && isTurn(start) 
                        && !isBlank(start) 
                        && isMarble((JLabel) event.getSource())
                        && firstMoved == null) {
                    
                        start.setIcon(getImageIcon(getColorInt(start), 's'));
                        showMoves(start);
                    }
                    
                    if (!gameOver && gameStarted && isTurn(start) 
                        && !isBlank(start) 
                        && isMarble((JLabel) event.getSource())
                        && firstMoved == start) {

                        start.setIcon(getImageIcon(getColorInt(start), 's'));
                        showJumps(start);
                    }
                }

                if (event.getSource() == endTurn) {
                    hasJumped = false;
                    jumping = null;
                    firstMoved = null;
                    turnOver(); 
                }

            } 

            // ENDING MOUSE RELEASE 
            public void mouseReleased( MouseEvent event ) {
                boolean moveMade = false;

                try{
                    //MOVES 
                    if (isDot(end)) {
                        end.setIcon(getMarble(start));
                        moveMade = true;
                        if (!inRange(start,end)) {
                            hasJumped = true;
                        }
                        firstMoved = end;
                    }
                } catch (NullPointerException e) { moveMade = false; }

                hideMoves(start);               

                // MOVE if labels are not the same, 
                // is blankspace and move is in range
                if (!moveMade) { // IF NO MOVE MADE RESET START TO ORIGINAL
                    start.setIcon(getImageIcon(getColorInt(start), 'o'));
                } else { // ELSE SET START TO BLANK
                    // if jump still available 
                    if (canJumpAgain(end) && hasJumped) {
                        hasJumped = true;

                        start.setIcon(o_blank); // blank image

                        start = end;
                        jumping = start;
                        end = null;

                    } else {
                        hasJumped = false;
                        jumping = null;
                        firstMoved = null;
                        start.setIcon(o_blank); // blank image
                        turnOver();
                    }
                }
            }

            // SETS THE END POINT 
            public void mouseEntered( MouseEvent event ) {
                if (event.getSource() != endTurn) {
                    end = (JLabel) event.getSource();

                    if (isHighlight((JLabel) event.getSource())) {
                        end = (JLabel) event.getSource();
                        end.setIcon(getImageIcon(currPlayer, 'd'));
                    }
                }       
            } 

            // handle event when mouse exits area
            public void mouseExited( MouseEvent event ) {
                if (event.getSource() != endTurn) {
                    JLabel tmp = (JLabel) event.getSource();
                    if (isDot(tmp))
                        end.setIcon(getImageIcon(currPlayer, 'h'));
                }
            } 
            
            public void mouseClicked( MouseEvent event ) { }  

        } // end of BoardMouseHandler class


        //////////////////////////////////////////////////////////////////////
        // Mouse listener for game option buttons
        public class OptionMouseHandler implements MouseListener {

            public void mousePressed(MouseEvent e) {
                if (e.getSource() == modes[0]) {
                    gameMode = 0;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[1]) {
                    gameMode = 1;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[2]) {
                    gameMode = 2;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[3]) {
                    gameMode = 3;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[4]) {
                    gameMode = 4;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[5]) {
                    gameMode = 5;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[6]) {
                    gameMode = 6;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[7]) {
                    gameMode = 7;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }   
                if (e.getSource() == modes[8]) {
                    gameMode = 8;
                    setTurnOrder(gameMode);
                    gameStarted = true;
                    hideOptions();
                }
            }
            
            public boolean optGetMode() {
                return endTurn.isVisible();
            }

            public void mouseReleased(MouseEvent e) { }

            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == modes[0]) bkg = getImageIcon(0,'m');
                if (e.getSource() == modes[1]) bkg = getImageIcon(1,'m');
                if (e.getSource() == modes[2]) bkg = getImageIcon(2,'m');
                if (e.getSource() == modes[3]) bkg = getImageIcon(3,'m');
                if (e.getSource() == modes[4]) bkg = getImageIcon(4,'m');
                if (e.getSource() == modes[5]) bkg = getImageIcon(5,'m');
                if (e.getSource() == modes[6]) bkg = getImageIcon(6,'m');
                if (e.getSource() == modes[7]) bkg = getImageIcon(7,'m');
                if (e.getSource() == modes[8]) bkg = getImageIcon(8,'m');
                    
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                bkg = boardBKG;
                repaint();
            }

            public void mouseClicked(MouseEvent e) {

            }
        } // end of OptionMouseHandler class
    } // end of Board class
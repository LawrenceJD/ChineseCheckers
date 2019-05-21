
//////////////////////////////////////////////////////////////////////////////
// Author : John Cyr
// Date : 12/3/15
// Class : COP3253 Fall 2015
// Assigment X - Chinese Checkers 
//////////////////////////////////////////////////////////////////////////////

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
import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.JLayer;
import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.io.*;
import java.net.*;
import java.util.Scanner;
//////////////////////////////////////////////////////////////////////////////
// ChineseCheckers class
public class ChineseCheckers extends JFrame {

    //////////////////////////////////////////////////////////////////////////
    // main
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        ChineseCheckers game1 = new ChineseCheckers(hostName, portNumber);

    } // end of main

    //////////////////////////////////////////////////////////////////////////
    // ChineseCheckers constructor
    public ChineseCheckers(String hostN, int portNum) {

        try (
        //Step #2: Client initiates a connection request to the server's IP and port#.
        Socket kkSocket = new Socket(hostN, portNum);
        ObjectOutputStream out = new ObjectOutputStream(kkSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(kkSocket.getInputStream());
        Scanner scan = new Scanner(System.in);
        ) {
            boolean isTurn = true;
            Board gameBoard = new Board();
            int playerColor = 0;

            LayerUI<JComponent> layerUI = new MyLayerUISubclass();
            JLayer<JComponent> jlayer = new JLayer<>(gameBoard, layerUI);

            EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Chinese Checkers");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLayout(new BorderLayout());

                        frame.add(jlayer);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        frame.setResizable(false);

                        layerUI.uninstallUI(jlayer);
                    }
                });
            //Step #5: The client recieves a message from the server.
            try {
                gameBoard = (Board) in.readObject();
                playerColor = gameBoard.getCurrPlayer();

                while(true){
                    if (gameBoard.getCurrPlayer() != playerColor){
                        isTurn = false;
                        layerUI.uninstallUI(jlayer);

                        out.writeObject(gameBoard);
                        out.reset();

                        while (!isTurn){
                            gameBoard = (Board) in.readObject();
                        }

                        layerUI.installUI(jlayer);
                    }

                    if (gameBoard.getWinner() > -1)
                        break;
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    } // end of ChineseCheckers constructor

    static class MyLayerUISubclass extends LayerUI<JComponent>{

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(
                AWTEvent.MOUSE_EVENT_MASK |
                AWTEvent.KEY_EVENT_MASK 
            );
        }

        @Override
        public void uninstallUI(JComponent c) {
            JLayer jlayer = (JLayer)c;
            jlayer.setLayerEventMask(0);
            super.uninstallUI(c);
        }

        @Override
        protected void processMouseEvent(MouseEvent e, JLayer l) {
            e.consume();
        }

        @Override
        protected void processKeyEvent(KeyEvent e,
        JLayer<? extends JComponent> l) {
            e.consume();
        }
    }
} // end of ChineseCheckers class


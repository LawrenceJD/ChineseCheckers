
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

public class CCServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();

        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
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
                        JFrame frame = new JFrame("Chinese Checkers 1");
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
            try {
                
                int currentPlayer;
                Board inBoard;
                //gameBoard.setMode(5);
                System.out.println(gameBoard.getMode());
                while(true){
                    System.out.println(gameBoard.getMode());
                    if (gameBoard.getMode()){
                        System.out.println(gameBoard.getMode());
                        
                        currentPlayer = gameBoard.getCurrPlayer();
                        playerColor = currentPlayer;
                        System.out.println("test");
                        break;
                    }
                }

                while(true){
                    currentPlayer = gameBoard.getCurrPlayer();
                    if (currentPlayer != playerColor){
                        isTurn = false;
                        layerUI.installUI(jlayer);

                        out.writeObject(gameBoard);
                        out.reset();

                        while (true){
                            gameBoard = (Board) in.readObject();
                            if (gameBoard.getCurrPlayer() == playerColor)
                                break;
                        }
                        //gameBoard = inBoard;

                        layerUI.uninstallUI(jlayer);
                    }

                    if (gameBoard.getWinner() > -1)
                        break;
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

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
}
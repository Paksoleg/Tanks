package com.theByteGuru.display;
import com.theByteGuru.IO.Input;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {
    private  static boolean created = false;
    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;


    public static void create(int width, int height, String title, int _clearColor, int numBuffers){
        if(created)
            return;

        window = new JFrame(title);
        //add functions for close window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//For close window
        content = new Canvas();


        Dimension size = new Dimension(width,height);
        content.setPreferredSize(size);

        window.setResizable(false);//You cant change size of window
        window.getContentPane().add(content);//return only inside of window
        window.pack();//change window for our content
        window.setLocationRelativeTo(null);//window will created in center
        window.setVisible(true);//window will be created for user

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();//our image whis pixels
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//Сглаживание
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);
        bufferStrategy= content.getBufferStrategy();

        created = true;
    }
    public static void clear(){
        Arrays.fill(bufferData, clearColor);
    }

    public static void swapBuffers(){
        Graphics g = bufferStrategy.getDrawGraphics();//add graphics from canvas
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }
    public static Graphics2D getGraphics(){
        return (Graphics2D) bufferGraphics;
    }

    public static void destroy(){
        if (!created)
            return;
        window.dispose();
    }
    public static void setTitle(String title){
        window.setTitle(title);
    }
    public static void  addInputListener(Input inputListener){
        window.add(inputListener);
    }
}

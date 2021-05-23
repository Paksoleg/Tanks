package com.theByteGuru.main;

import com.theByteGuru.display.Display;
import com.theByteGuru.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Game tanks = new Game();
        tanks.start();



    }
}

//123456789abcdef (byte 255 0xff  ff-max byte, 00- min byte)
//int  0xffffffff - max integer, where two ff-its red, blue, green
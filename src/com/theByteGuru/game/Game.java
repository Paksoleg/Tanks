package com.theByteGuru.game;

import com.theByteGuru.display.Display;
import com.theByteGuru.utils.Time;

import java.awt.*;

public class Game implements Runnable{
    //int width, int height, String title, int _clearColor, int numBuffers
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;//наш апдейт, влияет на загрузку памяти
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;//
    public static final long IDLE_TIME = 1;//для перерыва между созданием других процессов, когда мы создали новый процесс 1 мили-секунда

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
        //temp
    float x = 350;
    float y = 250;
    float delta = 0;
    float radius = 50;

    //temp end
    public Game(){
        running = false;
        Display.create(WIDTH,HEIGHT,TITLE,CLEAR_COLOR,NUM_BUFFERS);
        graphics = Display.getGraphics();
    }
    //for start one process
    public synchronized void start(){
        if (running)
            return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();//запустит run
    }

    public synchronized void stop(){
        if (!running)
            return;
        running = false;
        try {
            gameThread.join();//ждет , пока наш процесс закончит свою работу
        }
        catch (InterruptedException e){
            e.printStackTrace();//Распечатать, где произошел сбой
        }
        cleanup();//функ очистки
    }
    //Считать всю физику,позиции и тд
    private void update(){
        delta +=0.02f;
    }
    //посчитали всю физику , все местонахождение всех танков
    private void render(){
        Display.clear();
        graphics.setColor(Color.white);
        graphics.fillOval((int)(x+(Math.sin(delta)*200)),(int)(y),(int)(radius*2),(int)(radius*2));
        Display.swapBuffers();
    }
    //Функция , которая будет вызывать рендер и апдейт, когда это нужно
    public void run(){

        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        float delta = 0;

        long lastTime = Time.get();
        while (running){
            long now = Time.get();//текущее время
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false; //не перерисовывать наш экран,если нет апдейта
            delta +=( elapsedTime/UPDATE_INTERVAL );//кол-во времени, которое прошло после итерации на кол-во должного времени для апдейта

            while (delta>1){    //Если прошло достаточно времени для апдейта, то нужно сделать новый апдейт
                update();
                upd ++;
                delta--;
                if(render){
                    updl++;
                } else {
                    render = true;
                }

            }
            if (render){
                render();
                fps ++;
            }else {
                try {
                    Thread.sleep(IDLE_TIME);//даем программе как бы отдохнуть 1 сек(может сказаться на fps
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= Time.SECOND){
                Display.setTitle(TITLE + " || fps: " + fps + " || upd: " + upd + " || updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }
        }
    }

    private void cleanup(){
        Display.destroy();//закрываем процессы при закрытии игры
    }
    ///Процесс, который будет бежать параллельно программе
}

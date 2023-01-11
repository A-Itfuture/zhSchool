package com.gg.zhschool.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**绘制验证码图片
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 12:05
 */
public class CreateVerifiCodeImage {
    private static int WIDTH = 90;
    private static int HEIGHT = 35;
    private static int FONT_SIZE = 20;//字体大小
    private static char[] verifiCode;//验证码
    private static BufferedImage verifiCodeImage;//验证码图片

    /**
     * 获取验证码图片
     * @return
     */
    public static BufferedImage getVerifiCodeImage(){
        verifiCodeImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);//create a image
        Graphics graphics = verifiCodeImage.getGraphics();

        verifiCode = generateCheckCode();
        drawBackground(graphics);
        drawRands(graphics,verifiCode);

        graphics.dispose();

        return verifiCodeImage;
    }

    /**
     * 获取验证码
     * @return
     */
    public static char[] getVerifiCode(){
        return verifiCode;
    }


    /**
     * 随机生成验证码
     * @return
     */
    public static char[] generateCheckCode(){
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz"+"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[4];
        for (int i=0;i<4;i++){
            int rand = (int)(Math.random()*(10+26*2));
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }


    /**
     * 绘制验证码
     * @param g
     * @param rands
     */
    public static void drawRands(Graphics g,char[] rands){
        g.setFont(new Font("Console",Font.BOLD,FONT_SIZE));
        for (int i=0;i<rands.length;i++){
            g.setColor(getRandomColor());
            g.drawString(""+rands[i],i*FONT_SIZE+10,25);
        }
    }

    /**
     * 绘制验证码图片背景
     * @param g
     */
    public static void drawBackground(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //绘制验证码干扰点
        for (int i=0;i<200;i++){
            int x = (int)(Math.random()*WIDTH);
            int y = (int)(Math.random()*HEIGHT);
            g.setColor(getRandomColor());
            g.drawOval(x,y,1,1);
        }
    }


    /**
     * 获取随机颜色
     * @return
     */
    private static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(220),random.nextInt(220),random.nextInt(220));
    }
}

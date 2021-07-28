package fr.olleroy.ImageModifier.components;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Bitmap class stores pixels and basic information about the image.
 * Bitmap looks like a BufferedImage but is smaller in memory.
 * BufferedImage could be more efficient to open files faster -> could upgrade to it in an other version.
 * @author olleory alias ollprogram.
 * @version 1.0
 */
public class Bitmap {
    private final Pixel [] [] map;//pixel array
    private final int width;
    private final int height;

    /**
     * Construct a bitmap with the the specified size.
     * @param width Width of the bitmap image.
     * @param height Height of the bitmap image.
     */
    public Bitmap(int width, int height){
        this.height = height;
        this.width = width;
        this.map = new Pixel[height][width];
        for(int i = 0; i< map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Pixel();
            }
        }
    }

    /**
     * Get a pixel.
     * @param x X axis location.
     * @param y Y axis location.
     * @return The pixel at the specified location in the bitmap. Black pixel it's a 0x0 bitmap.
     */
    public Pixel getPixel(int x, int y){
        if(map.length == 0 || map[0].length == 0) return new Pixel();
        return map[y][x];
    }

    /**
     * Get the RGB color of a specified pixel.
     * @param x X axis location.
     * @param y Y axis location.
     * @return The RGB color of the pixel with his specified location in the bitmap.
     */
    public int getRGB(int x, int y){
        return getPixel(x, y).getRGB();
    }

    /**
     * Get the ARGB color of a specified pixel.
     * @param x X axis location.
     * @param y Y axis location.
     * @return The ARGB color of the pixel with his specified location in the bitmap.
     */
    public int getARGB(int x, int y){
        return getPixel(x, y).getARGB();
    }

    /*Pixel modifiers*/

    /**
     * Modify the color of a pixel in the bitmap.
     * @param x X axis location.
     * @param y Y axis location.
     * @param pixel Pixel for replacement.
     */
    public void setPixel(int x, int y, Pixel pixel){ map[y][x] = pixel; }
    /**
     * Modify the color of a pixel in the bitmap.
     * @param x X axis location.
     * @param y Y axis location.
     * @param color An integer in RGB or ARGB format.
     */
    public void setPixel(int x, int y, int color){ map[y][x].setColor(color);}
    /**
     * Modify the color of a pixel in the bitmap.
     * @param x X axis location.
     * @param y Y axis location.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public void setPixel(int x, int y, int r, int g, int b){ map[y][x].setColor(r,g,b);}
    /**
     * Modify the color of a pixel in the bitmap.
     * @param x X axis location.
     * @param y Y axis location.
     * @param a Alpha.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public void setPixel(int x, int y, int a, int r, int g, int b){ map[y][x].setColor(a, r, g, b);}
    /**
     * Modify the color of a pixel in the bitmap.
     * @param x X axis location.
     * @param y Y axis location.
     * @param color The color to set.
     */
    public void setPixel(int x, int y, Color color){map[y][x].setColor(color);}

    @Override
    public String toString() {
        return "Bitmap{" +
                "map=" + Arrays.toString(map) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * Get the width of the bitmap.
     * @return The width of the bitmap.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the bitmap.
     * @return The height of the bitmap.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Convert the bitmap to a buffered image.
     * @return The bufferedImage built with the bitmap.
     */
    public BufferedImage toImage(){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                img.setRGB(x, y, this.getPixel(x, y).getARGB());
            }
        }
        return img;
    }

    /**
     * Get the size of the bitmap image.
     * @param alpha <code>true</code> for ARGB size, else RGB size.
     * @return The size in bytes.
     */
    public int getSize(boolean alpha){
        if (alpha) return height * width * 4;
        else return height * width * 3;
    }
}

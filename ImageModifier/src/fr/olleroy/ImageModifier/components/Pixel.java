package fr.olleroy.ImageModifier.components;

import java.awt.Color;
import java.util.Objects;
/**
 * An RGB or ARGB Pixel (24 bits or 32 bits).
 * @author olleroy alias ollprogram
 * @version 1.2.0
 */
public class Pixel {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 255;

    /**
     * Construct a black pixel.
     */
    public Pixel(){
    }

    /**
     * Construct a pixel with the specified color.
     * @param color the Color.
     */
    public Pixel(Color color){
        setColor(color);
    }

    /**
     *Construct a pixel with the specified argb color (with alpha).
     * @param color The color in classic ARGB format.
     */
    public Pixel(int color){
        setColor(color);
    }

    /**
     *Construct a pixel with the specified rgb color without alpha value (default 255).
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public Pixel(int r, int g, int b){
        setColor(r, g, b);
    }

    /**
     * Construct a pixel with the specified ARGB color.
     * @param a Alpha.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public Pixel(int a,int r, int g, int b){
        setColor(ARGBtoInt(a, r ,g, b));
    }

    /**
     * Parse the ARGB values.
     * @param a Alpha.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     * @return An array of 4 elements <code>{alpha, red, green, blue}</code>. All elements are from 0 to 255.
     */
    public static int[] parseARGB(int a, int r, int g, int b){
        int[] res = new int[4];
        if(! (r < 0)){ res[1] = r; }
        if (! (g < 0)){ res[2] = g; }
        if(! (b < 0)){ res[3] = b; }
        if(! (a < 0)){ res[0] = a; }
        if(r > 255){ res[1] = 255; }
        if(g > 255){ res[2] = 255; }
        if(b > 255){ res[3] = 255; }
        if(a > 255){ res[0] = 255; }
        return res;
    }

    /**
     * Convert ARGB to integer.
     * @param a Alpha.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     * @return An integer of the ARGB color in the classic ARGB format.
     */
    public static int ARGBtoInt(int a, int r, int g, int b){
        int[] RGB = parseARGB(a, r, g, b);
        return (RGB[0] << 24) | (RGB[1] << 16) | (RGB[2] << 8) | RGB[3];
        /*0xRRGGBBAA*/
    }

    /**
     * Convert RGB to integer.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     * @return The integer of the color in RGB format.
     */
    public static int RGBtoInt(int r, int g, int b){
        return ARGBtoInt(0, r, g, b);//0x00RRGGBB
    }

    /**
     * Set the RGB color of the Pixel (Alpha = 255 by default).
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public void setColor(int r, int g, int b){
        setColor(ARGBtoInt(this.alpha,r,g,b));
    }

    /**
     * Set the ARGB color of the Pixel.
     * @param a Alpha.
     * @param r Red color.
     * @param g Green color.
     * @param b Blue color.
     */
    public void setColor(int a,int r, int g, int b){
        setColor(ARGBtoInt(a, r, g, b));
    }

    /**
     * Set the color of the Pixel.
     * @param color A color from the awt library.
     */
    public void setColor(Color color){
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getAlpha();
    }

    /**
     * Construct a pixel with the specified color.
     * @param color An ARGB color in classic RGB format.
     */
    public void setColor(int color){
        alpha = color >>> 24; //0x000000AA
        red = (color >>> 16) & 0xFF; //0x000000RR
        green = (color >>> 8) & 0xFF; //0x000000GG
        blue = color & 0xFF; //0x000000BB
    }

    /*Setters and getters*/

    /**
     * Get red color.
     * @return The red color of the pixel.
     */
    public int getRed(){
        return this.red;
    }

    /**
     * Set red color.
     * @param red The red color of the pixel to set.
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Get green color.
     * @return The green color of the pixel.
     */
    public int getGreen(){
        return this.green;
    }

    /**
     * Set green color.
     * @param green The green color of the pixel to set.
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Get blue color.
     * @return The blue color of the pixel.
     */
    public int getBlue(){
        return this.blue;
    }

    /**
     * Set blue color.
     * @param blue The blue color of the pixel to set
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Set alpha.
     * @param alpha The alpha of the pixel
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    /**
     * Get alpha.
     * @return The alpha value of the pixel.
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Get the ARGB color.
     * @return Integer of the ARGB color.
     */
    public  int getARGB(){
        return ARGBtoInt(alpha, red, green, blue);
    }

    /**
     * Get the RGB color.
     * @return Integer of the RGB color.
     */
    public int getRGB(){
        return RGBtoInt(red, green, blue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pixel)) return false;
        Pixel pixel = (Pixel) o;
        return red == pixel.red && green == pixel.green && blue == pixel.blue && alpha == pixel.alpha;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, blue, green, alpha);
    }
}

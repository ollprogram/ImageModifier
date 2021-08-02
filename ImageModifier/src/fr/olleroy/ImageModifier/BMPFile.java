package fr.olleroy.ImageModifier;


import fr.olleroy.ImageModifier.components.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represent a bmp file.
 * @author olleroy alias ollprogram.
 * @version 1.2.0
 */
public class BMPFile {
    private Bitmap bitmap; //Bitmap is smaller than BufferedImage

    /**
     * Construct a BMPFile from a file or a URL.
     * @param imagePathnameOrURL The pathname or the URL of the image.
     * @throws IOException If it can't access or read the File.
     */
    public BMPFile(String imagePathnameOrURL) throws IOException {
        BufferedImage img;
        String absolPathname = "";
        System.out.println("Loading an image...");
        try {
            URL url = new URL(imagePathnameOrURL);
            img = loadImageFromURL(url);
        }
        catch(MalformedURLException e){
            img = loadImageFromFile(imagePathnameOrURL);
            absolPathname = System.getProperty("user.dir")+"/";
        }
        if(img == null) img = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
        generateBitmapFromImage(img);
        System.out.println("Image from <"+absolPathname+imagePathnameOrURL+"> successfully loaded");
    }

    /**
     * Construct a BMPFile with a new Bitmap built with the specified Bitmap
     * @param bitmap The bitmap for the bmp file.
     */
    public BMPFile(Bitmap bitmap){
        this.bitmap = new Bitmap(bitmap.getWidth(), bitmap.getHeight());
        for(int y = 0; y < bitmap.getHeight(); y++){
            for(int x = 0; x < bitmap.getWidth(); x++){
                this.bitmap.setPixel(x, y, bitmap.getARGB(x, y));
            }
        }
    }

    /**
     * Generate the bitmap of the file from a BufferedImage.
     * @param img The BufferedImage.
     */
    private void generateBitmapFromImage(BufferedImage img){
        this.bitmap = new Bitmap(img.getWidth(), img.getHeight());
        for(int y = 0; y < bitmap.getHeight(); y++){
            for(int x = 0; x < bitmap.getWidth(); x++){
                bitmap.setPixel(x, y, img.getRGB(x, y));
            }
        }
    }

    /**
     * Load an image from a file.
     * @param pathname The pathname to the file.
     * @return The image from the file.
     * @throws IOException If it can't access or read the file.
     */
    private BufferedImage loadImageFromFile(String pathname) throws IOException{
        File file = new File(pathname);
        return ImageIO.read(file);
    }

    /**
     * Load an image from a URL.
     * @param URL An image URL.
     * @return The BufferedImage (image) from the URL.
     */
    private BufferedImage loadImageFromURL(URL URL){
        try{
            return ImageIO.read(URL);
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the Image.
     * @return The image from the BMPFile.
     */
    public BufferedImage getImage() {
        return this.bitmap.toImage();
    }

    /**
     * Write the image file as a bmp file with the Windows DIB <em>BITMAPINFOHEADER</em> to the specified pathname.
     * The image can be 24bpp or 32bpp (bit per pixel).
     * 24bpp is recommended for large scaled images.
     * @param pathname The pathname. Where you want to save.
     * @param alpha <code>true</code> for 32 bpp (ARGB), else 24bpp (RGB).
     * @throws IOException Tf an I/O error occurs.
     */
    public void save(String pathname, boolean alpha) throws IOException {
        System.out.println("Saving an image...");
        DataOutputStream out = new DataOutputStream( new FileOutputStream(pathname));
        /*File Header*/
        out.write(0x42); out.write(0x4D);//file type "BM"
        writeEndian32(out, bitmap.getSize(alpha)+54);//bmp file size
        writeEndian32(out, 0); //app spec x2
        writeEndian32(out, 54);//offset where the bitmap data is
        /*DIB HEADER*/
        writeEndian32(out, 40);//DIB size
        writeEndian32(out, bitmap.getWidth());//width of the image in pixels
        writeEndian32(out, bitmap.getHeight());//height if the image in pixels
        out.write(0x01); out.write(0x00);//1 plane
        if(alpha) out.write(0x20);//color type (ARGB) 32bits
        else out.write(0x18);//color type (RGB) 24bits
        out.write(0);// end of color type
        writeEndian32(out, 0);// no pixel compression used
        writeEndian32(out, 0);//compressed size
        writeEndian32(out, 2835); writeEndian32(out, 2835);//resolution
        writeEndian32(out, 0);//color indexes
        writeEndian32(out, 0);//important color index
        /*Bitmap Array*/
        if(alpha) writeBitmapArray32(out);
        else writeBitmapArray24(out);
        out.close();
        System.out.println("Image successfully saved in <"+System.getProperty("user.dir")+"/"+pathname+">");
    }

    /**
     * Write the bitmap array in 32bpp.
     * @param out The output stream.
     * @throws IOException If an I/O error occurs.
     */
    private void writeBitmapArray32(DataOutputStream out) throws IOException {
        for(int y = bitmap.getHeight() -1; y >= 0; y--){
            for(int x = 0; x < bitmap.getWidth(); x++){
                writeEndian32(out, bitmap.getARGB(x, y));
            }
        }
    }

    /**
     * Write the bitmap array in 24bpp.
     * @param out The output stream.
     * @throws IOException If an I/O error occurs.
     */
    private void writeBitmapArray24(DataOutputStream out) throws IOException {
        int appendNumber = (bitmap.getWidth() * 3 ) % 4;
        for(int y = bitmap.getHeight() -1; y >= 0; y--){
            for(int x = 0; x < bitmap.getWidth(); x++){
                writeEndian24(out, bitmap.getRGB(x, y));
            }
            for(int i = 0; i < appendNumber; i++){
                out.write(0);//adding bytes to preserve a multiple of 4bytes per lines in the array
            }
        }
    }

    /**
     * Write 32 bits in Little Endian format.
     * @param out The output stream.
     * @param number The binary number to write.
     * @throws IOException If an I/O error occurs.
     */
    private static void writeEndian32(DataOutputStream out, int number) throws IOException {
        out.write(number & 0xFF);
        out.write((number >>> 8) & 0xFF);
        out.write((number >>> 16) & 0xFF);
        out.write( (number >>> 24) & 0xFF);
    }

    /**
     * Write 24 bits in Little Endian format.
     * @param out The output stream.
     * @param number The binary number to write.
     * @throws IOException If an I/O error occurs.
     */
    private static void writeEndian24(DataOutputStream out, int number) throws IOException {
        out.write(number & 0xFF);
        out.write((number >>> 8) & 0xFF);
        out.write( (number >>> 16) & 0xFF);
    }

    /**
     * Get the bitmap.
     * @return the bitmap from the BMPFile.
     */
    public Bitmap getBitmap(){
        return bitmap;
    }

}

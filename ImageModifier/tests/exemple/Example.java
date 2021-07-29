package exemple;

import fr.olleroy.ImageModifier.BMPFile;
import fr.olleroy.ImageModifier.components.Bitmap;
import fr.olleroy.ImageModifier.components.Pixel;

import java.awt.Color;
import java.io.IOException;

class Example {
    public static void main(String[] args) throws IOException {
        imageCreationTest();
        duplicateFilesTest();
        fromURLTest();//Be sure to have an internet connection
        monochrome();
    }

    private static void imageCreationTest() throws IOException {
        Bitmap bmp = new Bitmap(2, 2);
        bmp.setPixel(0,0, new Pixel(122, 0, 255, 255));
        bmp.setPixel(0, 1, new Pixel(255, 255, 255, 0));
        bmp.setPixel(1,0, new Pixel(122, 255, 255, 255));
        bmp.setPixel(1, 1, new Pixel(255, 255,0,0));
        BMPFile file = new BMPFile(bmp);
        file.save("there.bmp", true);
    }

    private static void duplicateFilesTest() throws IOException {
        BMPFile file = new BMPFile("there.bmp");//loading from a pathname
        BMPFile f2 = new BMPFile(file.getBitmap());//loading from a Bitmap (duplicating the file)
        f2.getBitmap().setPixel(0,0, Color.MAGENTA);
        file.save("saved1.bmp", false);
        f2.save("saved2.bmp", false);
    }

    private static void fromURLTest() throws IOException {
        BMPFile f = new BMPFile("https://p4.wallpaperbetter.com/wallpaper"
                +"/368/353/216/animal-cute-meerkat-wallpaper-preview.jpg");
        f.save("fromUrl.bmp", false);
    }

    private static void monochrome() throws IOException {
        BMPFile f = new BMPFile("fromUrl.bmp");
        Bitmap bmp = f.getBitmap();
        for(int y = 0; y < bmp.getHeight(); y++){
            for(int x = 0; x < bmp.getWidth(); x++){
                Pixel p = bmp.getPixel(x, y);
                int mid = (p.getBlue() + p.getRed() + p.getGreen())/3;
                bmp.setPixel(x, y, 255, mid, mid, mid);
            }
        }
        f.save("monochrome.bmp", false);
    }
}

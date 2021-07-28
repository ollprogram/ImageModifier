import fr.olleroy.ImageModifier.components.Pixel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Pixel_test{

   @Test
   public void ARGBtoInt(){
      assertEquals(0x00000000, Pixel.ARGBtoInt(0, 0, 0, 0));
      assertEquals(0xFFFFFFFF, Pixel.ARGBtoInt(255, 255, 255, 255));
      assertEquals(0x07ABCDEF, Pixel.ARGBtoInt(07, 171, 205, 239));
      assertEquals(0x00FF00FF, Pixel.ARGBtoInt(-222, 555, -1, 400));
   }

   @Test
   public void IntToRGB(){
      Pixel pixel = new Pixel(0xFFFFFFFF);
      assertEquals(255, pixel.getRed());
      assertEquals(255, pixel.getGreen());
      assertEquals(255, pixel.getBlue());
      assertEquals( 255, pixel.getAlpha());
      pixel.setColor(0x07ABCDEF);
      assertEquals(171, pixel.getRed());
      assertEquals( 205, pixel.getGreen());
      assertEquals( 239, pixel.getBlue());
      assertEquals( 07, pixel.getAlpha());
      assertEquals(0x07ABCDEF, pixel.getARGB());
      pixel.setColor(0);
      assertEquals( 0, pixel.getAlpha());
      assertEquals( 0, pixel.getRed());
      assertEquals( 0, pixel.getAlpha());
      assertEquals( 0, pixel.getBlue());
   }

}

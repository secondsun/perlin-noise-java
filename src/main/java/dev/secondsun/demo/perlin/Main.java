package dev.secondsun.demo.perlin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {


  static final Color blue = Color.blue;
  static final Color cyan = Color.cyan;
  static final Color green = Color.green;
  static final Color black = Color.black;
  static final Color white =Color.white;
  static final Color gray = Color.lightGray;
  static final Color[] colors = {blue, cyan, green, black, white, gray};


  private static final Executor EXECUTOR = Executors.newFixedThreadPool(16);
  private static float z = 0.1f;
  public static void main(String... args) {
    JFrame app = new JFrame("Perlin Noise");
    app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    app.setSize(640 ,480);
    var pp = new PerlinPane();
    app.setContentPane(pp);
    app.setVisible(true);
    pp.setSize(640,480);
    EXECUTOR.execute(()->{
      while(true){
        doPerlin(pp);
      }
    });

  }

  private static void doPerlin(PerlinPane pp) {
    final var width = pp.getWidth();
    final var height = pp.getHeight();

    var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    IntStream.range(0,width).parallel().forEach(
        x -> {
          IntStream.range(0,height).forEach( y -> {
            var perlin = Perlin.perlin(x/100f,y/100f,z/10f);
            var color = colors[(int)(perlin *5.9f)];
            if (color == Color.green) {
              perlin *= (perlin +  Perlin.perlin(x/10f,y/10f,perlin));
              color = colors[(int)(perlin *5.9f)];
              image.setRGB(x, y, color.getRGB());
            } else {
              image.setRGB(x, y, color.getRGB());
            }
          });
        }
    );

    z += 0.1f;
    pp.updateImage(image);
  }

}

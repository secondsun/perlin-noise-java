package dev.secondsun.demo.perlin;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PerlinPane extends Container {

  private BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);


  public void updateImage(BufferedImage image) {
    this.image = image;
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

//    final var image = new BufferedImage(640,480, BufferedImage.TYPE_INT_ARGB);
//    for (int x = 0; x < image.getWidth(); x++) {
//      for (int y = 0; y < image.getHeight(); y++) {
//        var noise = Math.min(255,Math.abs((int)(255*noise(x,y,1.0d))));
//        var color = new Color(noise,noise,noise).getRGB();
//        image.setRGB(x,y, color);
//      }
//    }

    g.drawImage(image,0,0, getWidth(), getHeight(), null);

  }

  double noise(double x, double y, double time) {
    record seed(double x, double y){}

    var x0 = 8*((int)(x/8d));
    var y0 = 8*((int)(y/8d));

    var x1 = x0+8;
    var y1 = y0+8;

    var noise00 = new Random( new seed(x0,y0).hashCode() ).nextFloat();
    var noise10 = new Random( new seed(x1,y0).hashCode() ).nextFloat();
    var noise11 = new Random( new seed(x1,y1).hashCode() ).nextFloat();
    var noise01 = new Random( new seed(x0,y1).hashCode() ).nextFloat();

    var xy0lerp = lerp(noise00,noise10,(x-x0)/8d);
    var xy1lerp = lerp(noise01,noise11,(x-x0)/8d);
    var weightedNoise = lerp(xy1lerp,xy0lerp, (y-y0)/8d);

    return weightedNoise;
  }

  private double lerp(double v0, double v1, double weight) {
    return v0+(v1-v0)*weight;
  }

}

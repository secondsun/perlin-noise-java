package dev.secondsun.demo.perlin;

public final class Perlin {
  private static final int[] p = {
      30, 35, 180, 97, 57, 196, 93, 6, 169, 37, 118, 54, 132, 53, 236, 240, 179, 147, 109, 7, 85,
      64, 200, 12, 66, 79, 107, 158, 181, 186, 174, 36, 45, 198, 231, 9, 49, 115, 187, 211, 32, 26,
      140, 176, 106, 0, 222, 173, 249, 48, 125, 51, 33, 23, 161, 144, 225, 95, 189, 50, 252, 193,
      142, 199, 254, 116, 239, 98, 17, 190, 151, 39, 10, 76, 205, 58, 63, 136, 165, 150, 84, 182,
      90, 21, 171, 168, 47, 133, 256, 103, 126, 138, 131, 42, 160, 19, 31, 245, 175, 104, 28, 91,
      92, 195, 34, 185, 210, 156, 243, 146, 65, 219, 221, 55, 145, 130, 96, 203, 11, 177, 233, 102,
      241, 8, 228, 194, 227, 110, 251, 238, 52, 124, 230, 4, 163, 206, 2, 75, 149, 112, 155, 159,
      229, 153, 137, 74, 234, 141, 5, 18, 3, 14, 68, 246, 16, 100, 41, 250, 70, 80, 119, 73, 43,
      226, 22, 105, 117, 139, 191, 202, 122, 1, 220, 237, 223, 152, 215, 244, 60, 253, 113, 204,
      248, 69, 13, 62, 59, 86, 71, 128, 25, 38, 89, 20, 44, 78, 154, 135, 24, 121, 188, 218, 40, 15,
      217, 208, 108, 46, 178, 192, 166, 212, 201, 247, 170, 164, 207, 148, 27, 99, 81, 94, 77, 209,
      101, 197, 216, 167, 214, 255, 61, 87, 120, 224, 129, 111, 183, 83, 82, 162, 172, 143, 123,
      232, 134, 88, 56, 213, 235, 114, 184, 72, 29, 127, 67, 157, 30, 35, 180, 97, 57, 196, 93, 6,
      169, 37, 118, 54, 132, 53, 236, 240, 179, 147, 109, 7, 85, 64, 200, 12, 66, 79, 107, 158, 181,
      186, 174, 36, 45, 198, 231, 9, 49, 115, 187, 211, 32, 26, 140, 176, 106, 0, 222, 173, 249, 48,
      125, 51, 33, 23, 161, 144, 225, 95, 189, 50, 252, 193, 142, 199, 254, 116, 239, 98, 17, 190,
      151, 39, 10, 76, 205, 58, 63, 136, 165, 150, 84, 182, 90, 21, 171, 168, 47, 133, 256, 103,
      126, 138, 131, 42, 160, 19, 31, 245, 175, 104, 28, 91, 92, 195, 34, 185, 210, 156, 243, 146,
      65, 219, 221, 55, 145, 130, 96, 203, 11, 177, 233, 102, 241, 8, 228, 194, 227, 110, 251, 238,
      52, 124, 230, 4, 163, 206, 2, 75, 149, 112, 155, 159, 229, 153, 137, 74, 234, 141, 5, 18, 3,
      14, 68, 246, 16, 100, 41, 250, 70, 80, 119, 73, 43, 226, 22, 105, 117, 139, 191, 202, 122, 1,
      220, 237, 223, 152, 215, 244, 60, 253, 113, 204, 248, 69, 13, 62, 59, 86, 71, 128, 25, 38, 89,
      20, 44, 78, 154, 135, 24, 121, 188, 218, 40, 15, 217, 208, 108, 46, 178, 192, 166, 212, 201,
      247, 170, 164, 207, 148, 27, 99, 81, 94, 77, 209, 101, 197, 216, 167, 214, 255, 61, 87, 120,
      224, 129, 111, 183, 83, 82, 162, 172, 143, 123, 232, 134, 88, 56, 213, 235, 114, 184, 72, 29,
      127, 67, 157
  };

  private static final int repeat = p.length - 1;


  public static double perlin(double x, double y, double z) {
    int xi = (int) x;
    int yi = (int) y;
    int zi = (int) z;


    double xf = x - xi;
    double yf = y - yi;
    double zf = z - zi;

    double u = fade(xf);
    double v = fade(yf);
    double w = fade(zf);

    //clamps!
    xi &= 0x7F;
    yi &= 0x7F;
    zi &= 0x7F;


    int aaa, aba, aab, abb, baa, bba, bab, bbb;
    aaa = p[p[p[xi] + yi] + zi];
    aba = p[p[p[xi] + inc(yi)] + zi];
    aab = p[p[p[xi] + yi] + inc(zi)];
    abb = p[p[p[xi] + inc(yi)] + inc(zi)];
    baa = p[p[p[inc(xi)] + yi] + zi];
    bba = p[p[p[inc(xi)] + inc(yi)] + zi];
    bab = p[p[p[inc(xi)] + yi] + inc(zi)];
    bbb = p[p[p[inc(xi)] + inc(yi)] + inc(zi)];

    double x1, x2, y1, y2;
    x1 = lerp(grad(aaa, xf, yf, zf),
        // The gradient function calculates the dot product between a pseudorandom
        grad(baa, xf - 1, yf, zf),
        // gradient vector and the vector from the input coordinate to the 8
        u);                    // surrounding points in its unit cube.
    x2 = lerp(grad(aba, xf, yf - 1, zf),
        // This is all then lerped together as a sort of weighted average based on the faded (u,v,w)
        grad(bba, xf - 1, yf - 1, zf),        // values we made earlier.
        u);
    y1 = lerp(x1, x2, v);

    x1 = lerp(grad(aab, xf, yf, zf - 1),
        grad(bab, xf - 1, yf, zf - 1),
        u);
    x2 = lerp(grad(abb, xf, yf - 1, zf - 1),
        grad(bbb, xf - 1, yf - 1, zf - 1),
        u);
    y2 = lerp(x1, x2, v);

    return (lerp(y1, y2, w) + 1) / 2;
  }

  public static int inc(int num) {
    num++;

    return num;
  }

  public static double lerp(double a, double b, double x) {
    return a + x * (b - a);
  }

  public static double grad(int hash, double x, double y, double z) {
    int h = hash    & 15;
    double u = h < 8  ? x : y;
    double v;


    if (h < 4)
    {
      v = y;
    } else if (h == 12 || h == 14 )
    {
      v = x;
    } else
    {
      v = z;
    }

    return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v
        : -v);
  }

  public static double fade(double t) {
    return t * t * t * (t * (t * 6 - 15) + 10);      // 6t^5 - 15t^4 + 10t^3
  }



}

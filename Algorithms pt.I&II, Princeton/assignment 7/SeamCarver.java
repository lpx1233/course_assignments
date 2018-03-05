import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.lang.Math;
import java.lang.Double;

public class SeamCarver {
  private Color[][] pic = null;
  private int width = 0;
  private int height = 0;

  public SeamCarver(Picture picture) {
    // create a seam carver object based on the given picture
    if(picture == null) throw new java.lang.IllegalArgumentException();
    this.width = picture.width();
    this.height = picture.height();
    this.pic = new Color[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.pic[i][j] = picture.get(i, j);
      }
    }
  }

  public Picture picture() {
    // current picture
    Picture pic = new Picture(this.width, this.height);
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        pic.set(i, j, this.pic[i][j]);
      }
    }
    return pic;
  }

  public int width() {
    // width of current picture
    return this.width;
  }

  public int height() {
    // height of current picture
    return this.height;
  }

  public double energy(int x, int y) {
    // energy of pixel at column x and row y
    if(x < 0 || x >= this.width || y < 0 || y >= this.height)
      throw new java.lang.IllegalArgumentException();
    if(x == 0 || x == (this.width - 1) || y == 0 || y == (this.height - 1))
      return 1000;
    Color left = this.pic[x - 1][y];
    Color right = this.pic[x + 1][y];
    Color up = this.pic[x][y - 1];
    Color down = this.pic[x][y + 1];
    double deltaX2 = Math.pow(left.getRed() - right.getRed(), 2)
                  + Math.pow(left.getGreen() - right.getGreen(), 2)
                  + Math.pow(left.getBlue() - right.getBlue(), 2);
    double deltaY2 = Math.pow(up.getRed() - down.getRed(), 2)
                  + Math.pow(up.getGreen() - down.getGreen(), 2)
                  + Math.pow(up.getBlue() - down.getBlue(), 2);
    return Math.sqrt(deltaX2 + deltaY2);
  }

  public int[] findVerticalSeam() {
    // sequence of indices for horizontal seam
    int[][] edgeTo = new int[this.width][this.height];
    double[][] distTo = new double[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        distTo[i][j] = Double.POSITIVE_INFINITY;
        if (j == 0) {
          distTo[i][j] = this.energy(i, j);
        }
      }
    }
    for (int j = 0; j < this.height - 1; j++) {
      for (int i = 0; i < this.width; i++) {
        if (i - 1 >= 0 && distTo[i-1][j+1] > distTo[i][j] + this.energy(i-1, j+1)) {
          distTo[i-1][j+1] = distTo[i][j] + this.energy(i-1, j+1);
          edgeTo[i-1][j+1] = i;
        }
        if (distTo[i][j+1] > distTo[i][j] + this.energy(i, j+1)) {
          distTo[i][j+1] = distTo[i][j] + this.energy(i, j+1);
          edgeTo[i][j+1] = i;
        }
        if (i + 1 < this.width && distTo[i+1][j+1] > distTo[i][j] + this.energy(i+1, j+1)) {
          distTo[i+1][j+1] = distTo[i][j] + this.energy(i+1, j+1);
          edgeTo[i+1][j+1] = i;
        }
      }
    }
    int minLast = 0;
    double minDist = Double.POSITIVE_INFINITY;
    for (int i = 0; i < this.width; i++) {
      if (minDist > distTo[i][this.height-1]) {
        minDist = distTo[i][this.height-1];
        minLast = i;
      }
    }
    int[] result = new int[this.height];
    for (int j = this.height - 1; j >= 0; j--) {
      result[j] = minLast;
      minLast = edgeTo[minLast][j];
    }
    return result;
  }

  public int[] findHorizontalSeam() {
    // sequence of indices for vertical seam
    int[][] edgeTo = new int[this.height][this.width];
    double[][] distTo = new double[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        distTo[i][j] = Double.POSITIVE_INFINITY;
        if (j == 0) {
          distTo[i][j] = this.energy(j, i);
        }
      }
    }
    for (int j = 0; j < this.width - 1; j++) {
      for (int i = 0; i < this.height; i++) {
        if (i - 1 >= 0 && distTo[i-1][j+1] > distTo[i][j] + this.energy(j+1, i-1)) {
          distTo[i-1][j+1] = distTo[i][j] + this.energy(j+1, i-1);
          edgeTo[i-1][j+1] = i;
        }
        if (distTo[i][j+1] > distTo[i][j] + this.energy(j+1, i)) {
          distTo[i][j+1] = distTo[i][j] + this.energy(j+1, i);
          edgeTo[i][j+1] = i;
        }
        if (i + 1 < this.height && distTo[i+1][j+1] > distTo[i][j] + this.energy(j+1, i+1)) {
          distTo[i+1][j+1] = distTo[i][j] + this.energy(j+1, i+1);
          edgeTo[i+1][j+1] = i;
        }
      }
    }
    int minLast = 0;
    double minDist = Double.POSITIVE_INFINITY;
    for (int i = 0; i < this.height; i++) {
      if (minDist > distTo[i][this.width-1]) {
        minDist = distTo[i][this.width-1];
        minLast = i;
      }
    }
    int[] result = new int[this.width];
    for (int j = this.width - 1; j >= 0; j--) {
      result[j] = minLast;
      minLast = edgeTo[minLast][j];
    }
    return result;
  }

  public void removeVerticalSeam(int[] seam) {
    // remove vertical seam from current picture
    if(seam == null || seam.length != this.height)
      throw new java.lang.IllegalArgumentException();
    for (int i = 0; i < this.height; i++) {
      if (seam[i] < 0 || seam[i] >= this.width)
        throw new java.lang.IllegalArgumentException();
      if (i >= 1 && Math.abs(seam[i] - seam[i-1]) >= 2)
        throw new java.lang.IllegalArgumentException();
    }
    Color[][] picAfter = new Color[this.width - 1][this.height];
    for (int i = 0; i < this.width - 1; i++) {
      for (int j = 0; j < this.height; j++) {
        if (i < seam[j]) {
          picAfter[i][j] = pic[i][j];
        } else {
          picAfter[i][j] = pic[i+1][j];
        }
      }
    }
    this.pic = picAfter;
    this.width = this.width - 1;
  }

  public void removeHorizontalSeam(int[] seam) {
    // remove horizontal seam from current picture
    if(seam == null || seam.length != this.width)
      throw new java.lang.IllegalArgumentException();
    for (int i = 0; i < this.width; i++) {
      if(seam[i] < 0 || seam[i] >= this.height)
        throw new java.lang.IllegalArgumentException();
      if (i >= 1 && Math.abs(seam[i] - seam[i-1]) >= 2)
        throw new java.lang.IllegalArgumentException();
    }
    Color[][] picAfter = new Color[this.width][this.height - 1];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height - 1; j++) {
        if (j < seam[i]) {
          picAfter[i][j] = pic[i][j];
        } else {
          picAfter[i][j] = pic[i][j+1];
        }
      }
    }
    this.pic = picAfter;
    this.height = this.height - 1;
  }
}

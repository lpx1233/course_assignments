import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//TODO remove
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Percolation {
    private boolean[] status = null;
    private WeightedQuickUnionUF wQUF = null;
    private int widthGrid = 0;
    private int numOpen = 0;
    
    public Percolation(int n){ // create n-by-n grid, with all sites blocked
        if(n <= 0){
            throw new java.lang.IllegalArgumentException();
        } 
        status = new boolean[n*n + 2];
        for(int i = 0; i < n*n; i++){
            status[i] = false;
        }
        widthGrid = n;
        wQUF = new WeightedQuickUnionUF(n*n+2);
    }
    
    private int pointToIndex(int row, int col){
        int index = (row-1) * widthGrid + (col-1);
//        StdOut.printf("row = %d, col = %d, index = %d\n", row, col, index);
        return index;
    }
    
    public void open(int row, int col){    // open site (row, col) if it is not open already
        if((row <= 0) || (row > widthGrid) || (col <= 0) || (col > widthGrid)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if(!isOpen(row, col)){
            status[pointToIndex(row, col)] = true;
            if((row - 1) > 0){
                if(isOpen(row-1, col)){
                    wQUF.union(pointToIndex(row-1, col), pointToIndex(row, col));
                }
            }else if((row - 1) <= 0){
                wQUF.union(widthGrid*widthGrid, pointToIndex(row, col));
            }
            
            if((row + 1) <= widthGrid){
                if(isOpen(row+1, col)){
                    wQUF.union(pointToIndex(row+1, col), pointToIndex(row, col));
                }
            }else if((row + 1) > widthGrid){
                wQUF.union(widthGrid*widthGrid + 1, pointToIndex(row, col));
            }
            
            if((col-1) > 0){
                if(isOpen(row, col-1)){
                    wQUF.union(pointToIndex(row, col-1), pointToIndex(row, col));
                }
            }
            
            if((col+1) <= widthGrid){
                if(isOpen(row, col+1)){
                    wQUF.union(pointToIndex(row, col+1), pointToIndex(row, col));
                }
            }
            numOpen = numOpen + 1;
        }
    }
    
    public boolean isOpen(int row, int col){  // is site (row, col) open?
        if((row <= 0) || (row > widthGrid) || (col <= 0) || (col > widthGrid)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        return status[pointToIndex(row, col)];
    }
    
    public boolean isFull(int row, int col){  // is site (row, col) full?
        if((row <= 0) || (row > widthGrid) || (col <= 0) || (col > widthGrid)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        return wQUF.connected(pointToIndex(row, col), widthGrid*widthGrid);
    }
    
    public int numberOfOpenSites(){       // number of open sites
        return numOpen;
    }
    
    public boolean percolates(){              // does the system percolate?
        return wQUF.connected(widthGrid*widthGrid+1, widthGrid*widthGrid);
    }
    
    public static void main(String[] args) throws Exception{   // test client (optional)
        //TODO remove this
        File file = new File("input3.txt");
        if(file.isFile() && file.exists()){ 
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            lineTxt = bufferedReader.readLine();
            int n = Integer.parseInt(lineTxt);
            Percolation perc = new Percolation(n);
            while((lineTxt = bufferedReader.readLine()) != null){
                String[] param = lineTxt.split(" +");
                perc.open(Integer.parseInt(param[1]), Integer.parseInt(param[2]));
                StdOut.printf("isFull(3, 1) is %s\n", new Boolean(perc.isFull(3, 1)).toString());
            }
            read.close();
        }
    }
}
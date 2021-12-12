import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;import java.util.Scanner;


public class Main {
    static BufferedImage InputImage,OutputImage;
    static int [][] kernelMatrix = new int[3][3];
    static int [][] Gx = {{-1, 0, 1}, {-2, 0, 2 } , {-1, 0, 1}}; //Directional Derivative in the X direction
    static int [][] Gy = {{1,2,1}, {0,0,0}, {-1,-2,-1}}; //Directional Derivative in the Y direction

    public static void main(String[] args) {

        try{
            String filename;
            Scanner Keyboard = new Scanner(System.in);
            System.out.println("Enter the directory of the image file you wish to perform sobel edge detection on:" +
                    "\nExample of directory C:\\Users\\David\\Videos\\Grand Theft Auto V\\Grand Theft Auto V Super-Resolution 2021.11.16 - 13.10.05.07.png");
            filename = Keyboard.nextLine();

            InputImage = ImageIO.read(new File(filename));
            OutputImage = new BufferedImage(InputImage.getWidth(), InputImage.getHeight(),BufferedImage.TYPE_INT_RGB);
            for(int i = 1; i < InputImage.getWidth()-1; i++ ){
                for (int j = 1; j < InputImage.getHeight()-1; j++){

                    kernelMatrix[0][0] = new Color(InputImage.getRGB(i-1, j-1)).getRed();
                    kernelMatrix[0][1] = new Color(InputImage.getRGB(i-1, j)).getRed();
                    kernelMatrix[0][2] = new Color(InputImage.getRGB(i-1, j +1)).getRed();
                    kernelMatrix[1][0] = new Color(InputImage.getRGB(i,j-1)).getRed();
                    kernelMatrix[1][1] = new Color(InputImage.getRGB(i,j)).getRed();
                    kernelMatrix[1][2] = new Color(InputImage.getRGB(i,j+1)).getRed();
                    kernelMatrix[2][0] = new Color(InputImage.getRGB(i+1,j-1)).getRed();
                    kernelMatrix[2][1] = new Color(InputImage.getRGB(i+1,j)).getRed();
                    kernelMatrix[2][2] = new Color(InputImage.getRGB(i+1,j+1)).getRed();

                    //Take in the red value of the pixel in the image and places it into the pixel matrix

                    int edge = (int) Convolution(kernelMatrix,Gx,Gy);

                    //Takes in the pixelMatrix and performs convolution on it using Gx and Gy kernels

                    OutputImage.setRGB(i,j,(edge << 16 | edge << 8 | edge));
                    //Manipulate edge RGB values by shifting bits








                }






            }
            File outputFile = new File("edgedetectedimage.png");
            ImageIO.write(OutputImage,"jpg",outputFile);




        }
        catch (IOException ex){

            System.out.print("Invalid or Corrupted File");
        }



    }



    public static double Convolution(int[][] pixelMatrix, int[][] Gx, int[][] Gy){

        int DirectionalX = (pixelMatrix[0][0] * Gx[0][0]) +(pixelMatrix[0][1] * Gx[0][1])+ (pixelMatrix[0][2] * Gx[0][2]) +
                (pixelMatrix[1][0] * Gx[1][0]) + (pixelMatrix[1][1] * Gx[1][1]) +(pixelMatrix[1][2] * Gx[1][2]) +
                (pixelMatrix[2][0] * Gx[2][0]) + (pixelMatrix[2][1] * Gx[2][1])+ (pixelMatrix[2][2] * Gx[2][2]);
        //Calculates the DirectionalX using the kernel for sobel operator in X direction
        int DirectionalY = (pixelMatrix[0][0]* Gy[0][0]) + (pixelMatrix[0][1] * Gy[0][1]) + (pixelMatrix[0][2] * Gy[0][2]) +
                (pixelMatrix[1][0] * Gy[1][0]) + (pixelMatrix[1][1] * Gy[1][1]) + (pixelMatrix[1][2] * Gy[1][2]) +
                (pixelMatrix[2][0]* Gy[2][0]) + (pixelMatrix[2][1]* Gy[2][1]) +(pixelMatrix[2][2]* Gy[2][2]);
        //Calculates the DirectionalY using the kernel for sobel operator in Y direction

        return Math.sqrt(Math.pow(DirectionalX,2)+ Math.pow(DirectionalY,2)); //Normalizes the results so we do not obtain negative numbers as there are no negative rgb values





    }
}

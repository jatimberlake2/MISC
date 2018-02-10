/*	Justin Anthony Timberlake
 *  CS 360
 *  Project 1
 *  Matrix Multiplication by means of Strassen's Method
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class project1 {

	public static void main(String[] args) {
		int[][] A = new int[0][0];
		int[][] B = new int[0][0];
		int[][] C = new int[0][0];
		int n = 0;
		int adds = 0;
		int mults = 0;
		//initialize primary variables and all two-dimensional matrices
		if (args.length > 0){	//checking for whether the user wants randomly generated matrices or file read-in
			if (args[0].equals("-n")) {
				try {
					n = Integer.parseInt(args[1]);
					A = createMatrices(n);
					B = createMatrices(n);
				} catch (NumberFormatException e) {
					System.err.println("Your argument \"" + args[1] + "\" should actually be an integer, sorry. :/");	//user gives incrorrect input for integer
					System.exit(1);	//quit so the program doesn't crash
					}
			}
				else {
					System.out.println("The only acceptable first arguments are \"-n\". If you want to read from a file, then don't put any command line arguments in. :/");
					//Only accept "-n" for the first argument
					System.exit(1);	//quit so the program doesn't crash
				}
		}
		else		//read-in from datafile
		{
			try {
				Scanner derp = new Scanner(new File("datafile"));	//create Scanner object
				String firstLine = derp.nextLine();	//read the first line where n=#
				String[] fLine = firstLine.split("");	//split into characters and store in a new array
				n = Integer.parseInt(fLine[2]);		//parse integer given for dimensions
				A = new int[n][n];					//resize all matrices accordingly
				B = new int[n][n];
				C = new int[n][n];
				readInMatrices(A, n, derp);	
				readInMatrices(B, n, derp);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		//general outputs made to look all nice and fancy
		strassenMatrix sMatrix = new strassenMatrix(C, adds, mults);	//create the object in which output matrix C will be stored
		System.out.println("N=" + n);
		System.out.println();
		System.out.println("Input Matrix A");
		printMatrices(A);
		System.out.println();
		System.out.println("Input Matrix B");
		printMatrices(B);
		System.out.println();
		System.out.println("Output Matrix C");
		C = multiplyStrassen(A, B, n, sMatrix).get_matrix();	//store two-dimensional matrix from strassenMatrix object in two-dimensional integer array C
		printMatrices(C);
		System.out.println();
		System.out.println("Number of Multiplications: " + sMatrix.get_mults());  //prints the number of multiplications
		System.out.println();
		System.out.println("Number of Additions: " + sMatrix.get_adds());	//prints the number of additions, excluding the subtractions
	}
	
	public static int[][] createMatrices(int size){	//randomly generate matrix based on original size n from command-line input
		int[][] matrix = new int[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				matrix[i][j] = (int)(Math.random()*10);
			}
		}
		return matrix;
	}
	
	public static int[][]readInMatrices(int[][] matrix, int size, Scanner derp){		//parse the datafile for creation of an individual two-dimensional matrix
		for (int i = 0; i < size; i++){
			String nextLine = derp.nextLine();
			String[] nLine = nextLine.split(",");
			for (int j = 0; j < size; j++){
				matrix[i][j] = Integer.parseInt(nLine[j]);
				}
			}
		return matrix;
	}
	
	public static void printMatrices(int[][] matrix){	//pretty much what you'd expect this method to do
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[i].length; j++){
				if (matrix[i][j] > 9)
					System.out.print(" ");
					if (matrix[i][j] < 100){
						System.out.print(" ");
					}
				else if (matrix[i][j] < 10)
					System.out.print("  ");
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
			}
	}
	
    public static int[][] add(int[][] A, int[][] B, int size, strassenMatrix sMatrix) {		//method in which all basic or matrix additions are carried out
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = A[i][j] + B[i][j];
                sMatrix.increment_adds();		//sent strassenMatrix object for the purpose of incrementing the number of additions at this point w/o global variable
            }
        }

        return matrix;
    }

    public static int[][] subtract(int[][] A, int[][] B, int size) {		//method in which all basic or matrix subtractions are carried out
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = A[i][j] - B[i][j];
            }
        }
        return matrix;
    }
	
	public static strassenMatrix multiplyStrassen(int[][] A, int[][] B, int size, strassenMatrix sMatrix){
		if (size <= 1){ //base case	
			int[][] baseMatrix = new int[1][1];
	        baseMatrix[0][0] += A[0][0] * B[0][0]; //literally multiplying one number by a single other number when size = 1 (as basic as it gets)
            sMatrix.increment_mults(); //Every time a basic multiplication is performed within Strassen's method, increment the number of multiplications
	        sMatrix.set_matrix(baseMatrix);
	        return sMatrix;
	    }  else {
            int Nhalf = size / 2;

            //GET IT? It's because "n" gets cut "in half"! Hahahaha... I'm sorry. Please don't deduct points for puns.
            int[][] a11 = new int[Nhalf][Nhalf];
            int[][] a12 = new int[Nhalf][Nhalf];
            int[][] a21 = new int[Nhalf][Nhalf];
            int[][] a22 = new int[Nhalf][Nhalf];
            
            int[][] b11 = new int[Nhalf][Nhalf];
            int[][] b12 = new int[Nhalf][Nhalf];
            int[][] b21 = new int[Nhalf][Nhalf];
            int[][] b22 = new int[Nhalf][Nhalf];
            //These are all the new sub-matrices for A and B
            for (int i = 0; i < Nhalf; i++) {
                for (int j = 0; j < Nhalf; j++) {
                    a11[i][j] = A[i][j]; // upper left
                    a12[i][j] = A[i][j + Nhalf]; // upper right
                    a21[i][j] = A[i + Nhalf][j]; // lower left
                    a22[i][j] = A[i + Nhalf][j + Nhalf]; // lower right

                    b11[i][j] = B[i][j]; // upper left
                    b12[i][j] = B[i][j + Nhalf]; // upper right
                    b21[i][j] = B[i + Nhalf][j]; // lower left
                    b22[i][j] = B[i + Nhalf][j + Nhalf]; // lower right
                }
            }
            // M1 to M7 (taken directly from Wikipedia)
            int[][] M1 = multiplyStrassen(add(a11, a22, Nhalf, sMatrix), add(b11, b22, Nhalf, sMatrix), Nhalf, sMatrix).get_matrix();
            // M1 = (a11+a22) * (b11+b22)
            int[][] M2 = multiplyStrassen(add(a21, a22, Nhalf, sMatrix), b11, Nhalf, sMatrix).get_matrix();
            // M2 = (a21+a22) * (b11)
            int[][] M3 = multiplyStrassen(a11, subtract(b12, b22, Nhalf), Nhalf, sMatrix).get_matrix();
            // M3 = (a11) * (b12 - b22)
            int[][] M4 = multiplyStrassen(a22, subtract(b21, b11, Nhalf), Nhalf, sMatrix).get_matrix();
            // M4 = (a22) * (b21 - b11)
            int[][] M5 = multiplyStrassen(add(a11, a12, Nhalf, sMatrix), b22, Nhalf, sMatrix).get_matrix();
            // M5 = (a11+a12) * (b22)
            int[][] M6 = multiplyStrassen(subtract(a21, a11, Nhalf), add(b11, b12, Nhalf, sMatrix), Nhalf, sMatrix).get_matrix();
            // M6 = (a21-a11) * (b11+b12)
            int[][] M7 = multiplyStrassen(subtract(a12, a22, Nhalf), add(b21, b22, Nhalf, sMatrix), Nhalf, sMatrix).get_matrix();
            // M7 = (a12-a22) * (b21+b22)

            // new C values based on M1 through M7 (taken directly from Wikipedia)
            int[][] c12 = add(M3, M5, Nhalf, sMatrix);
            // c12 = M3 + M5
            int[][] c21 = add(M2, M4, Nhalf, sMatrix);
            // c21 = M2 + M4
            int[][] c11 = subtract(add(add(M1, M4, Nhalf, sMatrix), M7, Nhalf, sMatrix), M5, Nhalf);
            // c11 = M1 + M4 - M5 + M7
            int[][] c22 = subtract(add(add(M1, M3, Nhalf, sMatrix), M6, Nhalf, sMatrix), M2, Nhalf);
            // c22 = M1 + M3 - M2 + M6
            int[][] output = new int[size][size];
            // Calculating output matrix C
            for (int i = 0; i < Nhalf; i++) {
                for (int j = 0; j < Nhalf; j++) {
                    output[i][j] = c11[i][j];
                    output[i][j + Nhalf] = c12[i][j];
                    output[i + Nhalf][j] = c21[i][j];
                    output[i + Nhalf][j + Nhalf] = c22[i][j];
                	}
            	}
            sMatrix.set_matrix(output);
            return sMatrix;	//return object of type strassenMatrix due to needing the number of additions and multiplications along with the matrix itself
        	}
		}
	}

class strassenMatrix {
	//	This was originally in another file, but I don't know how to write makefiles or .bat files,
	//		and since the CS department has never required me to make one yet, I figured I'd rather be
	//		safe than sorry since I had literally no idea what I was doing...
	private int adds, mults;	//integer counts of additions and multiplications
	private int[][] matrix;	//what will become the output matrix C eventually
	strassenMatrix(){		//base-case initialization constructor
		adds = 0;
		mults = 0;
		matrix = new int[0][0];
	}
	
	strassenMatrix(int[][] C, int add, int mult){		//constructor with given variables
		matrix = C;
		adds = add;
		mults = mult;
	}
	// "getter" methods
	int[][] get_matrix(){	
		return matrix;
	}
	
	
	int get_adds(){
		return adds;
	}
	
	int get_mults(){
		return mults;
	}
	// "mutator" methods
	void increment_adds(){
		adds++;
	}
	
	void increment_mults(){
		mults++;
	}
	// "setter" method for the output matrix C
	void set_matrix(int[][] C){
		matrix = C;
	}

}


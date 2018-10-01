import tubes.matrix.Matrix;
import tubes.matrix.MatrixInterpolasi;
import tubes.matrix.MatrixParametrik;
import tubes.consolecapturer.ConsoleOutputCapturer;
import tubes.fileexternal.External;

import java.util.*;

public class main {

    public static void main(String[] args) {
        ConsoleOutputCapturer capture=new ConsoleOutputCapturer();
        External external=new External();
        Scanner input = new Scanner(System.in);
        char inputLuar, inputDalam;
        int baris, kolom;
        String consoleView;
        do {
            System.out.println("Menu Utama : ");
            System.out.printf(
                    "1. Sistem Persamaan Linier\n" +
                    "2. Interpolasi Polinom\n" +
                    "3. Keluar\n" +
                    "4. Test File\n" +
                    "5. GUI\n");
            inputLuar = input.next().charAt(0);
            if (inputLuar == '1') {
                System.out.println("Pilihan : ");
                System.out.printf("1. Metode Gauss\n2. Metode Gauss Jordan\n");
                inputDalam = input.next().charAt(0);
                external.cekSimpan();
                if(external.simpanStatus()){
                    external.namaExternal();
                }
                System.out.print("Banyak persamaan : ");
                baris = input.nextInt();
                System.out.print("Banyak variable : ");
                kolom = input.nextInt();
                System.out.println("Masukkan persamaan : ");
                MatrixParametrik M = new MatrixParametrik(baris, kolom + 1);
                M.bacaMatrix();
                capture.start();
                System.out.println("Matrix persamaan :");
                M.printMatrix();
                if (inputDalam == '1') {
                    M.gauss();
                    System.out.println("Matrix hasil Gauss :");
                    M.printMatrix();
                    M.genStatus();
                    M.solveParametrikGauss();

                } else if (inputDalam == '2') {
                    M.gaussJordan();
                    System.out.println("Matrix hasil Gauss Jordan :");
                    M.printMatrix();
                    M.genStatus();
                    M.solveParametrikGaussJordan();
                }
                System.out.println("Solusi Persamaan Linier : ");
                M.printHasilParametrik(false, null);
                consoleView=capture.stop();
                if(external.simpanStatus()){
                    external.saveExternal(consoleView);
                }
            } else if (inputLuar == '2') {
                external.cekSimpan();
                if(external.simpanStatus()){
                    external.namaExternal();
                }
                System.out.print("Masukkan banyak titik : ");
                baris = input.nextInt();
                double[][] matrixTitik = new double[baris][2];
                System.out.println("Masukkan titik-titik : ");
                for (int i = 0; i < baris; i++) {
                    for (int j = 0; j < 2; j++) {
                        matrixTitik[i][j] = input.nextDouble();
                    }
                }
                MatrixInterpolasi M = new MatrixInterpolasi(baris, baris + 1);
                M.titikMatrix(matrixTitik);
                external.saveExternal("Matrix titik :",M);
                M.gaussJordan();
                if (M.titikValid()) {
                    capture.start();
                    M.printSolusiInterpolasi();
                    consoleView=capture.stop();
                    System.out.print("Masukkan nilai x yang akan diinterpolasi : ");
                    double x = input.nextDouble();
                    capture.start();
                    M.printHasilInterpolasi(x);
                    consoleView+=capture.stop();
                } else {
                    capture.start();
                    System.out.println("Tidak dapat dilakukan interpolasi, ada titik yang sama");
                    consoleView=capture.stop();
                }
                if(external.simpanStatus()){
                    external.saveExternal(consoleView);
                }

            } else if (inputLuar == '4') {
                Matrix m = new Matrix(2, 3);
                m.bacaMatrixFile("temp.txt");
                m.printMatrix();
            } else if(inputLuar == '5') {
                GUI gui = new GUI(500,500);
            }

        } while (inputLuar != '3');

    }
}

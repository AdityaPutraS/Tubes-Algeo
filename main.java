import java.util.*;
public class main {

    public static void main(String[] args)
    {
        Scanner input=new Scanner(System.in);
        char inputLuar,inputDalam;
        int n;
        /*System.out.println("Menu Utama : ");
        System.out.printf("1. Sistem Persamaan Linier\n2. Interpolasi Polinom\n3. Keluar\n");
        inputLuar=input.next().charAt(0);
        if(inputLuar=='1'){
            System.out.println("Pilihan : ");
            System.out.printf("1. Metode Gauss\n2. Metode Gauss Jordan\n3. Kembali\n");
            inputDalam=input.next().charAt(0);
            System.out.print("Banyak persamaan : ");
            System.out.println("Masukkan persamaan : ");
            Matrix M=new Matrix(n,n+1);
            M.bacaMatrix();
            if (inputDalam=='1'){
                M.gauss();
                M.printMatrix();
            }else if(inputDalam=='2'){
                M.gaussJordan();
                M.printMatrix();
            }
        }else if(inputLuar=='2'){

        }else if(inputLuar=='3'){
            System.exit(0);
        }*/
        do{
            System.out.println("Menu Utama : ");
            System.out.printf("1. Sistem Persamaan Linier\n2. Interpolasi Polinom\n3. Keluar\n");
            inputLuar=input.next().charAt(0);
            if(inputLuar=='1'){
                System.out.println("Pilihan : ");
                System.out.printf("1. Metode Gauss\n2. Metode Gauss Jordan\n3. Kembali\n");
                inputDalam=input.next().charAt(0);
                System.out.print("Banyak persamaan : ");
                n=input.nextInt();
                System.out.println("Masukkan persamaan : ");
                Matrix M=new Matrix(n,n+1);
                M.bacaMatrix();
                if (inputDalam=='1'){
                    M.gauss();
                    M.printMatrix();
                }else if(inputDalam=='2'){
                    M.gaussJordan();
                    M.printMatrix();
                }
            }else if(inputLuar=='2'){
                System.out.print("Masukkan banyak titik : ");
                n=input.nextInt();
                float[][] matrixTitik=new float[n][2];
                System.out.println("Masukkan titik-titik : ");
                for(int i=0;i<n;i++){
                    for(int j=0;j<2;j++){
                        matrixTitik[i][j]=input.nextFloat();
                    }
                }
                MatrixInterpolasi M=new MatrixInterpolasi(n,n+1);
                M.titikMatrix(matrixTitik);
                M.gaussJordan();
                if(M.titikValid()){
                    M.printSolusiInterpolasi();
                    System.out.print("Masukkan nilai x yang akan diinterpolasi : ");
                    float x=input.nextFloat();
                    M.printHasilInterpolasi(x);
                }else{
                    System.out.println("Tidak dapat dilakukan interpolasi, ada titik yang sama");
                }


            }


        }while(inputLuar!=3);
    }

}

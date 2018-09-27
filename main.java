public class main {

    public static void main(String[] args)
    {
        Matrix M = new Matrix(3,4);
        M.bacaMatrix();
        M.printMatrix();
        System.out.printf("\n");
        Matrix koefHasil = new Matrix(3,1);
        koefHasil.bacaMatrix();
        koefHasil.printMatrix();
        System.out.printf("\n");
        M.solveParametrikGauss(koefHasil);
        M.printHasilParametrik();
        System.out.printf("\n");
    }

}

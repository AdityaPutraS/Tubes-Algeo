import java.util.Scanner;

public class Matrix {
    private int nBrs, nKol;
    private float[][] data;
    private float determinan;

    public Matrix(int baris, int kolom) {
        this.nBrs = baris;
        this.nKol = kolom;
        data = new float[baris][kolom];
    }

    public void bacaMatrix() {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < this.nBrs; i++) {
            for (int j = 0; j < this.nKol; j++) {
                this.data[i][j] = scan.nextFloat();
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < this.nBrs; i++) {
            for (int j = 0; j < this.nKol; j++) {
                System.out.printf("%.2f ",this.data[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void tukarBaris(int r1, int r2) {
        if (r1 < 0 || r1 >= nBrs) {
            System.out.println("r1 tidak valid");
        } else if (r2 < 0 || r2 >= nBrs) {
            System.out.println("r2 tidak valid");
        } else {
            for (int i = 0; i < this.nKol; i++) {
                float temp = this.data[r1][i];
                this.data[r1][i] = this.data[r2][i];
                this.data[r2][i] = temp;
            }
        }
    }

    public void kaliBaris(int r, float a) {
        //Membuat r = r * a
        //Valdasi
        if (r < 0 || r >= nBrs) {
            System.out.println("r tidak valid");
        } else {
            for (int i = 0; i < this.nKol; i++) {
                this.data[r][i] *= a;
            }
        }
    }

    public void plusBaris(int r1, float a, int r2) {
        //Membuat r1 = r1 + a * r2
        if (r1 < 0 || r1 >= nBrs) {
            System.out.println("r1 tidak valid");
        } else if (r2 < 0 || r2 >= nBrs) {
            System.out.println("r2 tidak valid");
        } else {
            for (int i = 0; i < this.nKol; i++) {
                this.data[r1][i] += (a * this.data[r2][i]);
            }
        }
    }

    public boolean isRowZero(int r) {
        //Return true jika baris r semuanya 0
        int i=0;
        while((this.data[r][i]==0)&&i<this.nKol-1){
            i++;
        }
        if (this.data[r][i]==0){
            return true;
        }else{
            return false;
        }
    }

    public int getLeadCoef(int r)
    {
        //return index leading point, jika tidak ketemu return nkol
        boolean found=false;
        int i=0;
        while((i<this.nKol)&&!found){
            if(this.data[r][i]!=0){
                found=true;
            }else{
                i++;
            }
        }
        if (found){
            return i;
        }else{
            return this.nKol;
        }
    }

    public void sortMatrix(){
        int i,j;
        if(this.nBrs>1) {
            for (i = 0; i < this.nBrs - 1; i++) {
                int brsMax=i;
                for (j = i + 1; j < this.nBrs; j++) {
                    int cidxLeadCoef = this.getLeadCoef(j);
                    if(cidxLeadCoef<this.getLeadCoef(brsMax)){
                        brsMax=j;
                    }
                }
                this.tukarBaris(i,brsMax);
            }
        }
    }

    public void gauss() {
        //Melakukan algoritma gauss pada matrix ini
        this.sortMatrix();
        for (int i = 0; i < nBrs - 1; i++) {
            //cek apakah baris 0
            if (!this.isRowZero(i)) {
                //Cari leading coef nya
                int idxLeadCoef = this.getLeadCoef(i);
                float leadCoef = this.data[i][idxLeadCoef];
                //manipulasi semua baris di bawahnya
                for (int j = i + 1; j < nBrs; j++) {
                    if (!this.isRowZero(j)) {
                        float pengali = -1 * this.data[j][idxLeadCoef] / leadCoef;
                        this.plusBaris(j, pengali, i);
                        //Bagi baris ini  dengan lead coef baris ini juga
                        int idxLeadBarisJ = this.getLeadCoef(j);
                        float leadCoefJ = this.data[j][idxLeadBarisJ];
                        this.kaliBaris(j,1/leadCoefJ);
                    }
                }
            }
        }
    }

    public void gaussJordan(){
        this.gauss();
        int i,j;
        for (i=nBrs-1;i>0;i--){
            if(!this.isRowZero(i)) {
                int idxLeadCoef = this.getLeadCoef(i);
                float leadCoef = this.data[i][idxLeadCoef];
                for (j = i - 1; j >= 0; j--) {
                    if(!this.isRowZero(j)) {
                        float pengali = -1 * this.data[j][idxLeadCoef];
                        this.plusBaris(j, pengali, i);
                    }
                }
            }
        }

    }
}

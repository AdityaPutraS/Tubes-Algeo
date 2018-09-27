import java.util.Scanner;

import tubes.error.*;

public class Matrix {
    public float[][] data;
    private float[][] hasilParametrik;
    private int[] status;
    private int nBrs, nKol;


    public Matrix(int baris, int kolom) {
        this.nBrs = baris;
        this.nKol = kolom;
        data = new float[baris][kolom];
    }

    public int getnBrs() {
        return this.nBrs;
    }

    public int getnKol() {
        return this.nKol;
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
                System.out.printf("%.2f ", this.data[i][j]);
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
        int i = 0;
        while ((this.data[r][i] == 0) && i < this.nKol - 1) {
            i++;
        }
        if (this.data[r][i] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getLeadCoef(int r) {
        //return index leading point, jika tidak ketemu return nkol
        boolean found = false;
        int i = 0;
        while ((i < this.nKol) && !found) {
            if (this.data[r][i] != 0) {
                found = true;
            } else {
                i++;
            }
        }
        if (found) {
            return i;
        } else {
            return this.nKol;
        }
    }

    public void sortMatrix() {
        //Selection sort, dari kecil ke besar
        int i, j;
        if (this.nBrs > 1) {
            for (i = 0; i < this.nBrs - 1; i++) {
                int brsMax = i;
                for (j = i + 1; j < this.nBrs; j++) {
                    int cidxLeadCoef = this.getLeadCoef(j);
                    if (cidxLeadCoef < this.getLeadCoef(brsMax)) {
                        brsMax = j;
                    }
                }
                this.tukarBaris(i, brsMax);
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
                        this.kaliBaris(j, 1 / leadCoefJ);
                    }
                }
            }
        }
    }

    public void solveParametrikGauss(Matrix koefHasil) {
        //I.S : Matrix awal sudah di gauss
        int banyakVariable = this.nKol;
        //cek ukuran koefHasil
        if (koefHasil.getnBrs() == this.nBrs && koefHasil.getnKol() == 1) {
            this.hasilParametrik = new float[banyakVariable][banyakVariable + 1];
            this.status = new int[banyakVariable];
            /*
            Bentuk matrix hasil adalah seperti berikut
            x0  =   0*x0 + b*x1 + c*x2 + ... + koefHasil0
            x1  =   d*x0 + 0*x1 + f*x2 + ... + koefHasil1
            x2  =   g*x0 + h*x1 + 0*x2 + ... + koefHasil2

            Untuk variable bebas, nilai matrix pada baris tersebut adalah
            x5 = 0*x0 + 0*x1 + 0*x2 + 0*x3 + 0*x4 + 1*x5 + 0*x6 + ... + 0   (Contoh)   status = 0

            Untuk variable terikat, nilai matrix pada baris tersebut adalah
            x5 = 1*x0 + 2*x1 + 0*x2 + 0*x3 + 0*x4 + 0*x5 + 3*x6 + ... + 30  (Contoh)    status = 1

            Untuk variable tentu, nilai matrix pada baris tersebut adalah
            x5 = 0*x0 + 0*x1 + 0*x2 + 0*x3 + 0*x4 + 1*x5 + 0*x6 + ... + 20  (Contoh)    status = 2

            Jika tidak ada solusi maka akan melempar exception NoSolution
             */
            //Asumsi matrix sudah dilakukan operasi gauss dan sekarang sedang dalam bentuk row echelon form
            //Dari atas, cari status variable, sekaligus ngecek apakah ada baris yang bermasalah (pers 0 semua, tapi hasil != 0)
            for (int i = 0; i < this.nBrs; i++) {
                if (this.isRowZero(i)) {
                    //Kosong semua, ada 2 kemungkinan. koefHasil[i] == 0 -> ga guna, koefHasil[i] != 0 -> tidak ada solusi
                    if (koefHasil.data[i][0] != 0) {
                        throw new NoSolution("Tidak ada solusi untuk persamaan ini");
                    }
                } else {
                    //Tidak 0 semua, cari leading coefficientnya lalu cek belakangnya, jika 0 semua ->  variable tentu, jika ada != 0 -> variable terikat
                    int idxLead = this.getLeadCoef(i);
                    boolean tentu = true;
                    for (int j = idxLead + 1; j < this.nKol; j++) {
                        if (this.data[i][j] != 0) {
                            tentu = false;
                            break;
                        }
                    }
                    if (tentu) {
                        this.status[idxLead] = 2;
                    } else {
                        this.status[idxLead] = 1;
                    }
                }
            }

            //Iter dari bawah, lakukan algoritma berikut
            /*
                Algoritma :
                    Untuk setiap baris, cari leading koef nya, lalu untuk setiap kolom disamping lead koef, sebut saja kolom k
                    lakukan :
                        jika status[k] == 0 maka ->
                            hasil[leadKoef][k] += -data[i][k]
                        jika status[k] == 1 maka ->
                            hasil[leadKoef] += -data[i][k]*hasil[k]
                        jika status[k] == 2 maka ->
                            hasil[leadKoef][banyakVariable] += hasil[k][banyakVariable]
             */
            for (int i = this.nBrs - 1; i >= 0; i--) {
                int idxLead = this.getLeadCoef(i);
                for (int k = idxLead + 1; k < this.nKol; k++) {
                    if (this.status[k] == 0) { //bebas
                        this.hasilParametrik[idxLead][k] += -1 * this.data[i][k];
                    } else if (this.status[k] == 1) { //terikat
                        for (int j = 0; j < this.nKol; j++) {
                            this.hasilParametrik[idxLead][j] += (-1*this.data[i][k] * this.hasilParametrik[k][j] );
                        }
                        this.hasilParametrik[idxLead][banyakVariable] += (-1*this.data[i][k] * this.hasilParametrik[k][banyakVariable]);
                    } else if (this.status[k] == 2) { //tentu
                        this.hasilParametrik[idxLead][banyakVariable] += -1 * this.hasilParametrik[k][banyakVariable];
                    }
                }
                this.hasilParametrik[idxLead][banyakVariable] += koefHasil.data[i][0];
            }
            
        } else {
            //ukuran koefHasil tidak sesuai
            throw new MismatchedSize("Ukuran matrix tidak sesuai");
        }
    }

    public void printHasilParametrik()
    {
        for (int i = 0; i < this.nKol ; i++) {
            //Cek tipe variablenya
            if(status[i] == 0){
                //bebas
                System.out.printf("x%d = bebas\n",i);
            }else if(status[i] == 1){
                //terikat
                System.out.printf("x%d = ",i);
                boolean pertama = true;
                for (int j = 0; j < this.nKol; j++) {
                    float nilai = this.hasilParametrik[i][j];
                    if(nilai != 0){
                        if(nilai > 0){
                            //positif
                            if(pertama) {
                                System.out.printf("(%.2f * x%d)", nilai, j);
                                pertama = false;
                            }else{
                                System.out.printf(" + (%.2f * x%d)", nilai, j);
                            }
                        }else{
                            //negatif
                            if(pertama) {
                                System.out.printf("(-%.2f * x%d)", -1 * nilai, j);
                                pertama = false;
                            }else{
                                System.out.printf(" - (-%.2f * x%d)", -1 * nilai, j);
                            }
                        }
                    }
                }
                float koefHasil = this.hasilParametrik[i][this.nKol];
                if(koefHasil != 0){
                    if(koefHasil > 0){
                        //positif
                        if(pertama) {
                            System.out.printf("%.2f", koefHasil);
                            pertama = false;
                        }else{
                            System.out.printf(" + %.2f", koefHasil);
                        }
                    }else{
                        //negatif
                        if(pertama) {
                            System.out.printf("-%.2f", -1 * koefHasil);
                            pertama = false;
                        }else{
                            System.out.printf(" - %.2f", -1 * koefHasil);
                        }
                    }
                }
                System.out.printf("\n");
            }else if(status[i] == 2){
                //tentu
                System.out.printf("x%d = %.2f\n",i,this.hasilParametrik[i][this.nKol]);
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

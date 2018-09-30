package tubes.matrix;

import tubes.error.NoSolution;

public class MatrixParametrik extends Matrix {
    //Variable
    private double[][] hasilParametrik;
    /*
            Bentuk array hasilParametrik adalah seperti berikut
            x0  =   0*x0 + b*x1 + c*x2 + ... + x
            x1  =   d*x0 + 0*x1 + f*x2 + ... + y
            x2  =   g*x0 + h*x1 + 0*x2 + ... + z

            Untuk variable bebas, nilai matrix pada baris tersebut adalah
            x5 = 0*x0 + 0*x1 + 0*x2 + 0*x3 + 0*x4 + 0*x5 + 0*x6 + ... + 0   (Contoh) && status = 0

            Untuk variable terikat, nilai matrix pada baris tersebut adalah
            x5 = 1*x0 + 2*x1 + 0*x2 + 0*x3 + 0*x4 + 0*x5 + 3*x6 + ... + 30  (Contoh) && status = 1

            Untuk variable tentu, nilai matrix pada baris tersebut adalah
            x5 = 0*x0 + 0*x1 + 0*x2 + 0*x3 + 0*x4 + 0*x5 + 0*x6 + ... + 20  (Contoh) && status = 2

            Yang membedakan variable bebas dengan tentu adalah nilai statusnya
    */

    private int[] status;
    private int banyakVariable = this.nKol - 1;

    //Konstruktor
    public MatrixParametrik(int baris, int kolom) {
        super(baris, kolom);
        this.hasilParametrik = new double[banyakVariable][banyakVariable + 1];
        this.status = new int[banyakVariable];
    }

    public void genStatus() {
        for (int i = this.nBrs-1; i >= 0; i--) {
            if (!this.isRowZero(i)) {
                /*
                    Asumsi matrix sudah di gauss / gauss-jordan
                    Cek apakah lead coefnya ada di paling belakang, jika iya maka ada baris berbentuk
                        0 0 0 0 0...0 0 0 | a   dengan a != 0, maka itu tidak ada solusi
                    Jika lead coef tidak di paling belakang, cek lagi status angka di belakang lead coef
                        Jika 0 semua maka variable tentu
                        Jika tidak, maka variable terikat / parametrik

                    Pada awalnya semua variable diasumsikan bebas, makanya nilai awal array status adalah 0 semua

                    0 = bebas
                    1 = terikat
                    2 = tentu

                 */
                int idxLead = this.getLeadCoef(i);
                //Cek letak lead coef
                if (idxLead == this.nKol - 1) {
                    //Tidak ada solusi, lempar NoSolution Exception
                    throw new NoSolution("Tidak ada solusi");
                } else {
                    //Aman
                    boolean tentu = true;
                    //Cek semua variable di belakang lead coef
                    for (int j = idxLead + 1; j < banyakVariable; j++) {
                        //Syarat tentu adalah, semua variable dikanannya koefnya 0 atau tentu semua
                        //Maka dia tidak tentu jika ada salah satu yang tidak 0 dan tidak tentu
                        if (this.data[i][j]!= 0 && this.status[j]!=2) {
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
        }
    }

    //Metode Gauss
    public void solveParametrikGauss() {
        //I.S : tubes.matrix.Matrix awal sudah di gauss dan status sudah di generate
        /*
            Asumsi matrix sudah dilakukan operasi gauss dan sekarang sedang dalam bentuk row echelon form
            Iter dari bawah, lakukan algoritma berikut

            Algoritma :
                Untuk setiap baris, cari leading koef nya, lalu untuk setiap kolom disamping lead koef, sebut saja kolom k
                lakukan :
                    jika status[k] == 0 maka ->
                        hasil[leadKoef][k] += -data[i][k]
                    jika status[k] == 1 maka ->
                        hasil[leadKoef] += -data[i][k]*hasil[k]
                    jika status[k] == 2 maka ->
                        hasil[leadKoef][banyakVariable] += -hasil[k][banyakVariable]
         */
        for (int i = this.nBrs - 1; i >= 0; i--) {
            if(!isRowZero(i)) {
                int idxLead = this.getLeadCoef(i);
                for (int k = idxLead + 1; k < this.banyakVariable; k++) {
                    if (this.status[k] == 0) { //bebas
                        this.hasilParametrik[idxLead][k] += -1 * this.data[i][k];
                    } else if (this.status[k] == 1) { //terikat
                        for (int j = 0; j < this.banyakVariable; j++) {
                            this.hasilParametrik[idxLead][j] += (-1 * this.data[i][k] * this.hasilParametrik[k][j]);
                        }
                        this.hasilParametrik[idxLead][banyakVariable] += (-1 * this.data[i][k] * this.hasilParametrik[k][banyakVariable]);
                    } else if (this.status[k] == 2) { //tentu
                        this.hasilParametrik[idxLead][banyakVariable] += (-1 * this.data[i][k] * this.hasilParametrik[k][banyakVariable]);
                    }
                }
                this.hasilParametrik[idxLead][banyakVariable] += this.data[i][this.nKol - 1];
            }
        }
    }

    //Metode Gauss-Jordan
    public void solveParametrikGaussJordan() {
        //I.S : matrix sudah di gauss-jordan dan status sudah di generate
        /*
            Asumsi matrix sudah dilakukan operasi gauss-jordan dan sekarang dalam bentuk reduced row echelon form
            Iter dari bawah, lakukan algoritma berikut

            Algoritma:
                Untuk setiap baris, cari leading koef nya, cek statusnya
                Jika :
                    status == 0 maka -> tidak mungkin. Karena gauss-jordan, jika dia adalah leading coefficient, dia
                        pasti antara variable tentu atau terikat.
                    status == 1 maka ->
                        loop semua kolom disamping leadKoef, sebut kolom k, maka
                            hasil[leadKoef][k] += -data[i][k], semua variable kolom ini pastilah bebas (karena gauss jordan)
                    status == 2 maka ->
                        hasil[leadKoef][banyakVariable] = data[i][banyakVariable]
         */
        for (int i = this.nBrs - 1; i >= 0; i--) {
            if(!isRowZero(i)) {
                int idxLead = this.getLeadCoef(i);
                if (status[idxLead] == 1) {
                    for (int k = idxLead + 1; k < banyakVariable; k++) {
                        this.hasilParametrik[idxLead][k] += (-1 * this.data[i][k]);
                    }
                } else if (this.status[idxLead] == 2) {
                    this.hasilParametrik[idxLead][banyakVariable] = this.data[i][this.nKol - 1];
                }
                this.hasilParametrik[idxLead][banyakVariable] += this.data[i][this.nKol - 1];
            }
        }

    }


    public void printHasilParametrik() {
        //Dipanggil setelah solveParametrikGauss() atau solveParametrikGaussJordan()
        for (int i = 0; i < this.banyakVariable; i++) {
            //Cek tipe variablenya
            if (status[i] == 0) {
                //bebas
                System.out.printf("x%d = bebas", i);
                System.out.println();
            } else if (status[i] == 1) {
                //terikat
                System.out.printf("x%d = ", i);
                boolean pertama = true;
                //Print ax^n + bx^(n-1) + cx^(n-2) ... + dx
                for (int j = 0; j < this.banyakVariable; j++) {
                    double nilai = this.hasilParametrik[i][j];
                    if (nilai != 0) {
                        if (nilai > 0) {
                            //positif
                            if (pertama) {
                                System.out.printf("(%.2f * x%d)", nilai, j);
                                pertama = false;
                            } else {
                                System.out.printf(" + (%.2f * x%d)", nilai, j);
                            }
                        } else {
                            //negatif
                            if (pertama) {
                                System.out.printf("(-%.2f * x%d)", -1 * nilai, j);
                                pertama = false;
                            } else {
                                System.out.printf(" - (-%.2f * x%d)", -1 * nilai, j);
                            }
                        }
                    }
                }
                //Print koefisien fungsi
                double koefHasil = this.hasilParametrik[i][banyakVariable];
                if (koefHasil != 0) {
                    if (koefHasil > 0) {
                        //positif
                        if (pertama) {
                            System.out.printf("%.2f", koefHasil);
                            pertama = false;
                        } else {
                            System.out.printf(" + %.2f", koefHasil);
                        }
                    } else {
                        //negatif
                        if (pertama) {
                            System.out.printf("-%.2f", -1 * koefHasil);
                            pertama = false;
                        } else {
                            System.out.printf(" - %.2f", -1 * koefHasil);
                        }
                    }
                }
                System.out.println();
            } else if (status[i] == 2) {
                //tentu
                System.out.printf("x%d = %.2f", i, this.hasilParametrik[i][banyakVariable]);
                System.out.println();
            }
        }
    }

}

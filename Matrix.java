import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

import tubes.error.*;

import java.lang.Math.*;

public class Matrix {
    public float[][] data;
    protected int nBrs, nKol;


    public Matrix(int baris, int kolom) {
        this.nBrs = baris;
        this.nKol = kolom;
        data = new float[baris][kolom];
    }

    public Matrix(float[][] dat, int baris, int kolom) {
        this.data = dat;
        this.nBrs = baris;
        this.nKol = kolom;
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

    public void bacaMatrixFile(String namaFile) {
        File fileExternal;
        int baris = -1, kolom = -1;
        ArrayList<ArrayList<Float>> temp = new ArrayList<ArrayList<Float>>();
        try {
            fileExternal = new File(namaFile);
            Scanner scanBaris = new Scanner(fileExternal);
            while (scanBaris.hasNextLine()) {
                baris += 1;
                temp.add(new ArrayList<Float>());
                String s = scanBaris.nextLine();
                Scanner scanFloat = new Scanner(s);
                while (scanFloat.hasNextFloat()) {
                    Float f = scanFloat.nextFloat();
                    temp.get(baris).add(f);
                }
            }
            kolom = temp.get(0).size();
            baris += 1; //harus di +1 karena baris tadi digunakan untuk mengindex, bukan untuk menghitung banyak baris
            //Buat matrixnya, jika baris & kolom != -1
            if (baris != -1 && kolom != -1) {
                this.data = new float[temp.size()][temp.get(0).size()];
                for (int i = 0; i < baris; i++) {
                    for (int j = 0; j < kolom; j++) {
                        this.data[i][j] = temp.get(i).get(j);
                    }
                }
                this.nBrs = baris;
                this.nKol = kolom;
            } else {
                System.out.println("File kosong");
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }

    }

    public void printMatrix() {
        for (int i = 0; i < this.nBrs; i++) {
            for (int j = 0; j < this.nKol; j++) {
                System.out.printf("%f ", this.data[i][j]);
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
        while (this.isZero((this.data[r][i])) && i < this.nKol - 1) {
            i++;
        }
        if (this.isZero((this.data[r][i]))) {
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
            if (!this.isZero(this.data[r][i])) {
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

    public boolean isZero(float x){
    	return Math.abs(x)<0.0000001;
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
                    }else if(cidxLeadCoef==this.getLeadCoef(brsMax)){
                    	if(this.data[j][cidxLeadCoef]>this.data[brsMax][this.getLeadCoef(brsMax)])
                    	{
                    		brsMax = j;
                    	}
                    }
                }
                this.tukarBaris(i, brsMax);
            }
        }
    }

    public void gauss() {
        //Melakukan algoritma gauss pada matrix ini
        int i, j;
        this.sortMatrix();
        for (i = 0; i < nBrs - 1; i++) {
            //cek apakah baris 0
            if (!this.isRowZero(i)) {
                //Cari leading coef nya
                int idxLeadCoef = this.getLeadCoef(i);
                float leadCoef = this.data[i][idxLeadCoef];
                //manipulasi semua baris di bawahnya
                for (j = i + 1; j < nBrs; j++) {
                    if (!this.isRowZero(j)) {
                        float pengali = -1 * this.data[j][idxLeadCoef] / leadCoef;
                        this.plusBaris(j, pengali, i);
                    }
                }
            }
        }

        //pembagian dilakukan terpisah untuk menghindari sebaris 0 semua setelah proses manipulasi
        for (i = 0; i < this.nBrs; i++) {
            if (!this.isRowZero(i)) {
                int idxLeadBaris = this.getLeadCoef(i);
                float leadCoef = this.data[i][idxLeadBaris];
                this.kaliBaris(i, 1 / leadCoef);
            }
        }
    }

    public void gaussJordan() {
        this.gauss();
        int i, j;
        for (i = nBrs - 1; i > 0; i--) {
            if (!this.isRowZero(i)) {
                int idxLeadCoef = this.getLeadCoef(i);
                //float leadCoef = this.data[i][idxLeadCoef];
                for (j = i - 1; j >= 0; j--) {
                    if (!this.isRowZero(j)) {
                        float pengali = -1 * this.data[j][idxLeadCoef];
                        this.plusBaris(j, pengali, i);
                    }
                }
            }
        }

    }


}

package tubes.matrix;

import javax.swing.*;
import java.awt.*;

public class MatrixInterpolasi extends Matrix {

    public MatrixInterpolasi(int baris, int kolom) {
        super(baris,kolom);
    }

    public MatrixInterpolasi(JTable tabel, int baris, int kolom)
    {
        //Konstruktor khusus untuk gui
        super(baris,kolom);
        double[][] matTitik = new double[baris][2];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < 2; j++) {
                matTitik[i][j] = Double.valueOf(tabel.getModel().getValueAt(i,j).toString());
            }
        }
        this.titikMatrix(matTitik);
    }

    public static double fastPangkat(double base, int pangkat)
    {
        double kuadrat = base * base, hasil;
        if(pangkat % 2 == 1)
        {
            hasil = base;
            //Pangkat ganjil
            for (int i = 0; i <pangkat/2 ; i++) {
                hasil = hasil * kuadrat;
            }
        }else{
            //Pangkat genap
            hasil = 1;
            //Pangkat ganjil
            for (int i = 0; i <pangkat/2 ; i++) {
                hasil = hasil * kuadrat;
            }
        }
        return hasil;
    }

    public void titikMatrix(double[][] matrixTitik){
        //Membuat matrix dari titik yang diinput
        int i,j;
        for (i=0;i<this.nBrs;i++){
            for(j=0;j<this.nKol-1;j++){
                //this.data[i][j]=(double)Math.pow((double)matrixTitik[i][0],(double)this.nKol-j-2);
                this.data[i][j] = fastPangkat(matrixTitik[i][0],this.nKol-j-2);
            }
            this.data[i][j]=matrixTitik[i][1];
        }
    }

    public boolean titikValid(){
        //Mengecek apakah titik valid dengan mengecek apakah ada 2 titik yang sama
        int i,j;
        Matrix temp=new Matrix(this.getnBrs(),this.getnBrs());
        for(i=0;i<this.getnBrs();i++){
            for(j=0;j<this.getnKol()-1;j++){
                temp.data[i][j]=this.data[i][j];
            }
        }
        i=0;
        boolean valid=true;
        while(i<getnBrs()&&valid){
            if(temp.isRowZero(i)) {
                valid = false;
            }else{
                i++;
            }
        }
        return valid;
    }


    //Gauss Jordan
    public String printSolusiInterpolasi(){
        //Asumsi tubes.matrix.Matrix sudah di Gauss Jordan
        String hasil = "";
        hasil += String.format("Solusi :\nf(x)=");
        int i;
        for(i=0;i<this.getnBrs()-1;i++){
            if(this.data[i][this.getnKol()-1]!=0) {
                int pangkat=this.getnBrs()-1-i;
                if (i > 0){
                    if(this.data[i][this.getnKol()-1]>0) {
                        if(this.data[i][this.getnKol()-1]!=1){
                            if(pangkat!=1){
                                hasil += String.format("+%.2fx^%d",this.data[i][this.getnKol() - 1],pangkat);
                            }else{
                                hasil += String.format("+%.2fx",this.data[i][this.getnKol() - 1]);
                            }
                        }else{
                            if(pangkat!=1){
                                hasil += String.format("+x^%d",pangkat);
                            }else{
                                hasil += String.format("+x");
                            }
                        }
                    }else {
                        if (this.data[i][this.getnKol() - 1] != -1) {
                            if(pangkat!=1){
                                hasil += String.format("%.2fx^%d",this.data[i][this.getnKol() - 1],pangkat);
                            }else{
                                hasil += String.format("%.2fx",this.data[i][this.getnKol() - 1]);
                            }
                        } else {
                            if(pangkat!=1){
                                hasil += String.format("-x^%d",pangkat);
                            }else{
                                hasil += String.format("-x");
                            }
                        }
                    }
                }else{
                    if(this.data[i][this.getnKol()-1]!=1&&this.data[i][this.getnKol()-1]!=-1){
                        if(pangkat!=1){
                            hasil += String.format("%.2fx^%d",this.data[i][this.getnKol() - 1],pangkat);
                        }else{
                            hasil += String.format("%.2fx",this.data[i][this.getnKol() - 1]);
                        }
                    }else if(this.data[i][this.getnKol()-1]==1){
                        if(pangkat!=1){
                            hasil += String.format("x^%d",pangkat);
                        }else{
                            hasil += String.format("x");
                        }
                    }else{
                        if(pangkat==-1){
                            hasil += String.format("-x^%d",pangkat);
                        }else{
                            hasil += String.format("-x");
                        }
                    }
                }
            }
        }
        if(this.data[i][this.getnKol()-1]!=0){
            if(this.data[i][this.getnKol()-1]>0) {
                hasil += String.format("+%.2f",this.data[i][this.getnKol() - 1]);
            }else {
                hasil += String.format("%.2f",this.data[i][this.getnKol() - 1]);
            }
        }
        hasil += "\n";
        return hasil;
    }

    public void printHasilInterpolasi(double x,boolean isGui, JLabel label){
        double sum=0;
        for(int i=0;i<this.getnBrs();i++){
            sum+=this.data[i][this.getnKol()-1]*(double)Math.pow((double)x,(double)(this.getnBrs()-1-i));
        }
        String hasil = String.format("f(%.2f)=%f", x, sum);
        if(isGui){
            label.setText(hasil);
        }else {
            System.out.println(hasil);
        }
    }


}

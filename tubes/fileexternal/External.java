package tubes.fileexternal;

import tubes.matrix.Matrix;
import java.io.*;
import java.util.Scanner;
import java.io.File;

public class External {
    private String fileName;
    private Boolean simpan;

    public Boolean simpanStatus(){
        return this.simpan;
    }

    public void cekSimpan(){
        Scanner input=new Scanner(System.in);
        boolean stop=false;
        System.out.println("Apakah anda ingin menyimpan hasilnya?\n<ya/tidak> : ");
        do {
            String s=input.nextLine();
            if(s.equals("ya")||s.equals("tidak")){
                if(s.equals("ya")) {
                    simpan = true;
                } else{
                    simpan=false;
                }
                stop=true;
            }else{
                System.out.println("Input anda salah. Ulangi.");
            }
        }while(!stop);
    }

    public void namaExternal(){
        Scanner input=new Scanner(System.in);
        System.out.print("Masukkan nama file : ");
        fileName="data_save/"+input.nextLine()+".txt";
    }

    public void saveExternal(String judul,Matrix M){
        if(simpan) {
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(judul);
                int i, j;
                for (i = 0; i < M.getnBrs(); i++) {
                    for (j = 0; j < M.getnKol() - 1; j++) {
                        pw.printf("%.7f\t", M.data[i][j]);
                    }
                    pw.printf("%.7f", M.data[i][j]);
                    pw.println();
                }
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveExternal(String s){
        if(simpan) {
            try {
                File file = new File(fileName);
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                if (!file.exists()) {
                    file.createNewFile();
                }
                pw.println(s);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

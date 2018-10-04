package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExternalGUI {
    private File f;


    public ExternalGUI(String namaFile) {
        this.f = new File(namaFile);
    }

    public ExternalGUI(File fil) {
        this.f = fil;
    }

    public void save(String s) {
        try {
            if(!this.f.exists()){
                this.f.createNewFile();
            }
            FileWriter fw = new FileWriter(this.f);
            fw.write(s);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public double[][] readMatrix() {
        double[][] tempData =  new double[0][0];
        int baris = -1, kolom = -1;
        ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
        try {
            Scanner scanBaris = new Scanner(this.f);
            while (scanBaris.hasNextLine()) {
                baris += 1;
                temp.add(new ArrayList<Double>());
                String s = scanBaris.nextLine();
                Scanner scanDouble = new Scanner(s);
                while (scanDouble.hasNextDouble()) {
                    Double f = scanDouble.nextDouble();
                    temp.get(baris).add(f);
                }
            }
            kolom = temp.get(0).size();
            baris += 1; //harus di +1 karena baris tadi digunakan untuk mengindex, bukan untuk menghitung banyak baris
            //Buat matrixnya, jika baris & kolom != -1
            if (baris != -1 && kolom != -1) {
                tempData = new double[temp.size()][temp.get(0).size()];
                for (int i = 0; i < baris; i++) {
                    for (int j = 0; j < kolom; j++) {
                        tempData[i][j] = temp.get(i).get(j);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return tempData;
    }
}

package src;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.ParseException;

public class GUI {
    private final int minBaris = 2, minKolom = 2;
    private final int maxBaris = 20, maxKolom = 20;
    private JFrame f;
    private JTabbedPane tabPane;
    //String
    String strSPL = "", strInter = "";
    //Matrix
    private MatrixParametrik matSPL;
    private MatrixInterpolasi matInter;
    //File Chooser
    private JFileChooser fcBuka = new JFileChooser();
    private JFileChooser fcSimpan = new JFileChooser();
    //Button Group
    private ButtonGroup groupSPL;
    //RadioButton SPL
    private JRadioButton gauss, gaussJordan;
    //Panel
    private JPanel FileExternal;
    private JPanel SPL, Interpolasi;
    private JPanel panelMatrixSPL = new JPanel(new BorderLayout());
    private JPanel panelMatrixInter = new JPanel(new BorderLayout());
    private JPanel panelHasilSPL, panelHasilInter, panelHasilPoly;
    //Label
    private JLabel labelPoly;
    //Text Field
    private JTextField xPoly;
    //Spinner
    private JSpinner baris, kolom, titik;
    //Tabel
    private JTable tabelMatrixSPL, tabelMatrixInter;
    private int barisSPL, kolomSPL;
    private int barisInter, kolomInter;

    //Konstruktor
    public GUI(int lebar, int tinggi) {
        this.f = new JFrame();
        this.f.setLayout(new BorderLayout());
        //this.f.setSize(lebar, tinggi);
        initTabPane();
        this.f.pack();
        this.f.setVisible(true);
        this.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        GUI g = new GUI(500, 500);
    }

    private void initTabPane() {
        this.tabPane = new JTabbedPane();
        TabFileExternal();
        TabPersamaanLinier();
        TabInterpolasi();
        this.f.add(tabPane, BorderLayout.CENTER);
    }

    private void TabFileExternal() {
        this.FileExternal = new JPanel();
        JButton open = new JButton("Buka File");
        JButton save = new JButton("Simpan File");
        open.addActionListener(e -> this.openData());
        save.addActionListener(e -> this.saveData());
        this.FileExternal.add(open);
        this.FileExternal.add(save);
        this.f.add(FileExternal,BorderLayout.NORTH);

    }

    private void openData(){
        int retVal = fcBuka.showOpenDialog(this.f);
        //Cek apakah sudah valid dan user jadi memilih file tempat diload
        if(retVal == JFileChooser.APPROVE_OPTION){
            File fOpen = fcBuka.getSelectedFile();
            //Baca data dari file
            ExternalGUI e = new ExternalGUI(fOpen);
            double[][] tempData = e.readMatrix();
            int tempBaris = tempData.length, tempKolom = tempData[0].length;
            //Cek apakah file kosong / tidak
            if(tempBaris==0 && tempKolom==0){
                //Kosong
                JOptionPane.showMessageDialog(null, "File kosong");
            }else {
                //Valid
                //Cek apakah panel interpolasi / spl yang dibuka
                int nomorTab = this.tabPane.indexOfComponent(this.tabPane.getSelectedComponent());
                //0 untuk SPL, 1 untuk Interpolasi
                if (nomorTab == 0) {
                    //SPL
                    //Tampilkan di tabel
                    String[] variable = new String[tempKolom];
                    for (int i = 0; i < tempKolom - 1; i++) {
                        variable[i] = "x" + i;
                    }
                    variable[tempKolom - 1] = "Hasil";
                    //Ubah tempData jadi array of array of object
                    Object[][] objData = new Object[tempBaris][tempKolom];
                    for (int i = 0; i < tempBaris; i++) {
                        for (int j = 0; j < tempKolom; j++) {
                            objData[i][j] = tempData[i][j];
                        }
                    }
                    this.tabelMatrixSPL = new JTable(objData, variable);
                    this.baris.setValue(tempBaris);
                    this.barisSPL = tempBaris;
                    this.kolom.setValue(tempKolom);
                    this.kolomSPL = tempKolom;
                    //Tampilkan
                    //Atur ukuran
                    for (int i = 0; i < this.kolomSPL; i++) {
                        this.tabelMatrixSPL.getColumnModel().getColumn(i).setPreferredWidth(50);
                    }
                    this.panelMatrixSPL.removeAll();
                    this.panelMatrixSPL.add(this.tabelMatrixSPL.getTableHeader(), BorderLayout.NORTH);
                    this.panelMatrixSPL.add(this.tabelMatrixSPL, BorderLayout.CENTER);
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridx = 0;
                    c.gridy = 7;
                    c.fill = GridBagConstraints.NONE;
                    this.SPL.add(this.panelMatrixSPL, c);
                    this.panelHasilSPL.removeAll();
                    JOptionPane.showMessageDialog(null,"Load file berhasil");
                } else {
                    //Interpolasi, harus di cek apakah kolom == 2
                    if(tempKolom == 2) {
                        //Tampilkan di tabel
                        this.barisInter = tempBaris;
                        this.kolomInter = 2;
                        String[] variable = {"x", "y"};
                        Object[][] data = new Object[this.barisInter][this.kolomInter];
                        for (int i = 0; i < this.barisInter; i++) {
                            for (int j = 0; j < this.kolomInter; j++) {
                                data[i][j] = tempData[i][j];
                            }
                        }
                        this.tabelMatrixInter = new JTable(data, variable);
                        //Atur ukuran
                        for (int i = 0; i < this.kolomInter; i++) {
                            this.tabelMatrixInter.getColumnModel().getColumn(i).setPreferredWidth(50);
                        }
                        this.panelMatrixInter.removeAll();
                        this.panelMatrixInter.add(this.tabelMatrixInter.getTableHeader(), BorderLayout.NORTH);
                        this.panelMatrixInter.add(this.tabelMatrixInter, BorderLayout.CENTER);
                        GridBagConstraints c = new GridBagConstraints();
                        c.gridx = 0;
                        c.gridy = 3;
                        c.fill = GridBagConstraints.NONE;
                        this.Interpolasi.add(this.panelMatrixInter, c);
                        this.panelHasilInter.removeAll();
                        JOptionPane.showMessageDialog(null,"Load file berhasil");
                    }else{
                        JOptionPane.showMessageDialog(null,"Format file salah");
                    }
                }
            }
            this.f.pack();
        }
    }
    private void saveData(){
        int retVal = fcSimpan.showOpenDialog(this.f);
        //Cek apakah sudah valid dan user jadi memilih file tempat disimpan
        if(retVal == JFileChooser.APPROVE_OPTION){
            //Cek apakah panel interpolasi / spl yang dibuka
            File fSave = fcSimpan.getSelectedFile();
            ExternalGUI e = new ExternalGUI(fSave);
            int nomorTab = this.tabPane.indexOfComponent(this.tabPane.getSelectedComponent());
            //0 untuk SPL, 1 untuk Interpolasi
            if(nomorTab == 0){
                //SPL
                e.save(this.strSPL);
            }else{
                //Interpolasi
                e.save(this.strInter);
            }
            JOptionPane.showMessageDialog(null,"Save file berhasil");
        }
    }

    private void initTabel(GridBagConstraints c, boolean isSPL) {
        if (isSPL) {
            //Ambil nilai dari spinner
            try {
                this.baris.commitEdit();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Nilai baris tidak valid");
            }
            this.barisSPL = (Integer) this.baris.getValue();
            try {
                this.kolom.commitEdit();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Nilai kolom tidak valid");
            }
            this.kolomSPL = (Integer) this.kolom.getValue();
            String[] variable = new String[this.kolomSPL];
            for (int i = 0; i < this.kolomSPL - 1; i++) {
                variable[i] = "x" + i;
            }
            variable[this.kolomSPL - 1] = "Hasil";
            Object[][] data = new Object[this.barisSPL][this.kolomSPL];
            for (int i = 0; i < this.barisSPL; i++) {
                for (int j = 0; j < this.kolomSPL; j++) {
                    data[i][j] = new Double(0);
                }
            }
            this.tabelMatrixSPL = new JTable(data, variable);
            //Atur ukuran
            for (int i = 0; i < this.kolomSPL; i++) {
                this.tabelMatrixSPL.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
            this.panelMatrixSPL.removeAll();
            this.panelMatrixSPL.add(this.tabelMatrixSPL.getTableHeader(), BorderLayout.NORTH);
            this.panelMatrixSPL.add(this.tabelMatrixSPL, BorderLayout.CENTER);
            c.gridx = 0;
            c.gridy = 7;
            c.fill = GridBagConstraints.NONE;
            this.SPL.add(this.panelMatrixSPL, c);
            this.panelHasilSPL.removeAll();
        } else {
            //Ambil nilai dari spinner
            try {
                this.titik.commitEdit();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Nilai baris tidak valid");
            }
            this.barisInter = (Integer) this.titik.getValue();
            this.kolomInter = 2;
            String[] variable = {"x", "y"};
            Object[][] data = new Object[this.barisInter][this.kolomInter];
            for (int i = 0; i < this.barisInter; i++) {
                for (int j = 0; j < this.kolomInter; j++) {
                    data[i][j] = new Double(0);
                }
            }
            this.tabelMatrixInter = new JTable(data, variable);
            //Atur ukuran
            for (int i = 0; i < this.kolomInter; i++) {
                this.tabelMatrixInter.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
            this.panelMatrixInter.removeAll();
            this.panelMatrixInter.add(this.tabelMatrixInter.getTableHeader(), BorderLayout.NORTH);
            this.panelMatrixInter.add(this.tabelMatrixInter, BorderLayout.CENTER);
            c.gridx = 0;
            c.gridy = 3;
            c.fill = GridBagConstraints.NONE;
            this.Interpolasi.add(this.panelMatrixInter, c);
            this.panelHasilInter.removeAll();
        }

        this.f.pack();
    }

    private void hitungSolusi(GridBagConstraints c, boolean isSPL) {
        if (isSPL) {
            //SPL
            //Pilih metode
            if (this.gauss.isSelected()) {
                //Solusi Gauss
                this.matSPL = new MatrixParametrik(this.tabelMatrixSPL, this.barisSPL, this.kolomSPL);
                this.matSPL.gauss();
                try {
                    this.matSPL.genStatus();
                    this.matSPL.solveParametrikGauss();
                    this.strSPL = this.matSPL.printHasilParametrik();
                    panelHasilSPL.removeAll();
                    panelHasilSPL.add(new JLabel("Hasil Parametrik : "),BorderLayout.NORTH);
                    panelHasilSPL.add(new JTextArea(this.strSPL),BorderLayout.CENTER);
                    c.gridx = 0;
                    c.gridy = 9;
                    this.SPL.add(panelHasilSPL, c);
                } catch (NoSolution e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (this.gaussJordan.isSelected()) {
                //Solusi Gauss Jordan
                this.matSPL = new MatrixParametrik(this.tabelMatrixSPL, this.barisSPL, this.kolomSPL);
                this.matSPL.gaussJordan();
                try {
                    this.matSPL.genStatus();
                    this.matSPL.solveParametrikGaussJordan();
                    this.strSPL = this.matSPL.printHasilParametrik();
                    panelHasilSPL.removeAll();
                    panelHasilSPL.add(new JLabel("Hasil Parametrik : "),BorderLayout.NORTH);
                    panelHasilSPL.add(new JTextArea(this.strSPL),BorderLayout.CENTER);
                    c.gridx = 0;
                    c.gridy = 9;
                    this.SPL.add(panelHasilSPL, c);
                } catch (NoSolution e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih metode terlebih dahulu");
            }
        } else {
            //Interpolasi
            matInter = new MatrixInterpolasi(this.tabelMatrixInter, this.barisInter, this.barisInter + 1);
            matInter.gaussJordan();
            //Cek apakah valid
            if (matInter.titikValid()) {
                //Valid, output ke textbox
                this.strInter = matInter.printSolusiInterpolasi();
                this.panelHasilInter.removeAll();
                this.panelHasilInter.add(new JLabel("Polynomial : "), BorderLayout.NORTH);
                this.panelHasilInter.add(new JTextArea(this.strInter), BorderLayout.CENTER);
                this.panelHasilPoly = new JPanel(new BorderLayout());
                this.panelHasilPoly.add(new JLabel("x = "), BorderLayout.WEST);
                xPoly = new JTextField();
                labelPoly = new JLabel("f(x) = ");
                xPoly.addActionListener(e -> matInter.printHasilInterpolasi(Double.valueOf(xPoly.getText()),
                        true,
                        labelPoly));
                this.panelHasilPoly.add(labelPoly, BorderLayout.SOUTH);
                this.panelHasilPoly.add(xPoly, BorderLayout.CENTER);
                this.panelHasilInter.add(this.panelHasilPoly, BorderLayout.SOUTH);
                c.gridx = 0;
                c.gridy = 5;
                this.Interpolasi.add(panelHasilInter, c);
            } else {
                //Tidak Valid
                JOptionPane.showMessageDialog(null, "Titik tidak valid");
            }
        }
        this.f.pack();
    }

    private void TabPersamaanLinier() {
        this.SPL = new JPanel();
        this.SPL.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.tabPane.addTab("Persamaan Linier", null, this.SPL, "Sistem Persamaan Linier");
        //Panel Metode
        JPanel metode = new JPanel(new FlowLayout());
        JLabel labelMetode = new JLabel("Metode : ");
        metode.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        metode.add(labelMetode);
        this.gauss = new JRadioButton("Gauss");
        this.gaussJordan = new JRadioButton("Gauss-Jordan");
        this.groupSPL = new ButtonGroup();
        this.groupSPL.add(this.gauss);
        this.groupSPL.add(this.gaussJordan);
        metode.add(this.gauss);
        metode.add(this.gaussJordan);
        c.gridx = 0;
        c.gridy = 0;
        this.SPL.add(metode, c);
        //Isi Separator
        JSeparator pemisah1 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah1.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        this.SPL.add(pemisah1, c);
        //Panel banyak baris
        JPanel panelBaris = new JPanel(new GridLayout(1, 4));
        JLabel nBaris = new JLabel("Banyak Baris");
        SpinnerNumberModel modelBaris = new SpinnerNumberModel(this.minBaris, this.minBaris, this.maxBaris, 1);
        this.baris = new JSpinner(modelBaris);
        panelBaris.add(nBaris);
        panelBaris.add(this.baris);
        c.gridx = 0;
        c.gridy = 3;
        this.SPL.add(panelBaris, c);
        //Panel banyak kolom
        JPanel panelKolom = new JPanel(new GridLayout(1, 4));
        JLabel nKolom = new JLabel("Banyak Kolom");
        SpinnerNumberModel modelKolom = new SpinnerNumberModel(this.minKolom, this.minKolom, this.maxKolom, 1);
        this.kolom = new JSpinner(modelKolom);
        panelKolom.add(nKolom);
        panelKolom.add(this.kolom);
        c.gridx = 0;
        c.gridy = 4;
        this.SPL.add(panelKolom, c);
        //Tombol buat matrix
        JButton buatMatrix = new JButton("Buat Matrix");
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 1;
        this.SPL.add(buatMatrix, c);
        //Isi Separator
        JSeparator pemisah2 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah2.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        this.SPL.add(pemisah2, c);
        //Matrix
        buatMatrix.addActionListener(e -> this.initTabel(c, true));
        //Tombol untuk hitung hasil spl
        JButton hasilSPL = new JButton("Hitung Solusi");
        c.gridx = 0;
        c.gridy = 8;
        this.SPL.add(hasilSPL, c);
        //Hasil SPL
        this.panelHasilSPL = new JPanel(new BorderLayout());
        hasilSPL.addActionListener(e -> this.hitungSolusi(c, true));
    }

    private void TabInterpolasi() {
        this.Interpolasi = new JPanel();
        this.Interpolasi.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.tabPane.addTab("Interpolasi", null, this.Interpolasi, "Interpolasi Polynomial");
        //Panel banyak titik
        JPanel panelTitik = new JPanel(new GridLayout(1, 4));
        JLabel nTitik = new JLabel("Banyak Titik");
        SpinnerNumberModel modelTitik = new SpinnerNumberModel(this.minBaris, this.minBaris, this.maxBaris, 1);
        this.titik = new JSpinner(modelTitik);
        panelTitik.add(nTitik);
        panelTitik.add(this.titik);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.Interpolasi.add(panelTitik, c);
        //Tombol buat matrix
        JButton buatMatrix = new JButton("Buat Matrix");
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        this.Interpolasi.add(buatMatrix, c);
        //Isi Separator
        JSeparator pemisah2 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah2.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        this.Interpolasi.add(pemisah2, c);
        //Matrix
        buatMatrix.addActionListener(e -> this.initTabel(c, false));
        //Tombol untuk hitung hasil interpolasi
        JButton hasilInter = new JButton("Interpolasi Polynomial");
        c.gridx = 0;
        c.gridy = 4;
        this.Interpolasi.add(hasilInter, c);
        //Hasil SPL
        this.panelHasilInter = new JPanel(new BorderLayout());
        hasilInter.addActionListener(e -> this.hitungSolusi(c, false));
    }

}

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class GUI {
    private final int minBaris = 2, minKolom = 2;
    private final int maxBaris = 20, maxKolom = 20;
    private JFrame f;
    private JTabbedPane tabPane;
    //Panel
    private JPanel SPL, Interpolasi;
    private JPanel panelMatrixSPL = new JPanel(new BorderLayout());
    private JPanel panelMatrixInter = new JPanel(new BorderLayout());
    private JPanel panelHasilSPL, panelHasilInter;
    //Spineer
    private JSpinner baris, kolom, titik;
    //Tabel
    private JTable tabelMatrixSPL, tabelMatrixInter;

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
        TabPersamaanLinier();
        TabInterpolasi();
        this.f.add(tabPane, BorderLayout.NORTH);
    }

    private void initTabel(GridBagConstraints c, boolean isSPL) {
        int bar, kol;
        if (isSPL) {
            //Ambil nilai dari spinner
            try {
                this.baris.commitEdit();
            } catch (ParseException e) {
                System.out.println("Nilai baris tidak valid");
            }
            bar = (Integer) this.baris.getValue();
            try {
                this.kolom.commitEdit();
            } catch (ParseException e) {
                System.out.println("Nilai kolom tidak valid");
            }
            kol = (Integer) this.kolom.getValue();
            String[] variable = new String[kol];
            if (isSPL || kol > 2) {
                for (int i = 0; i < kol - 1; i++) {
                    variable[i] = "x" + i;
                }
                variable[kol - 1] = "Hasil";
            } else {
                variable[0] = "x";
                variable[1] = "y";
            }
            Object[][] data = new Object[bar][kol];
            for (int i = 0; i < bar; i++) {
                for (int j = 0; j < kol; j++) {
                    data[i][j] = new Integer(0);
                }
            }
            this.tabelMatrixSPL = new JTable(data, variable);
            //Atur ukuran
            for (int i = 0; i < kol; i++) {
                this.tabelMatrixSPL.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
            this.panelMatrixSPL.removeAll();
            this.panelMatrixSPL.add(this.tabelMatrixSPL.getTableHeader(), BorderLayout.NORTH);
            this.panelMatrixSPL.add(this.tabelMatrixSPL, BorderLayout.CENTER);
            c.gridx = 0;
            c.gridy = 7;
            c.fill = GridBagConstraints.NONE;
            this.SPL.add(this.panelMatrixSPL, c);
        } else {
            //Ambil nilai dari spinner
            try {
                this.titik.commitEdit();
            } catch (ParseException e) {
                System.out.println("Nilai baris tidak valid");
            }
            bar = (Integer) this.titik.getValue();
            kol = 2;
            String[] variable = {"x", "y"};
            Object[][] data = new Object[bar][kol];
            for (int i = 0; i < bar; i++) {
                for (int j = 0; j < kol; j++) {
                    data[i][j] = new Integer(0);
                }
            }
            this.tabelMatrixInter = new JTable(data, variable);
            //Atur ukuran
            for (int i = 0; i < kol; i++) {
                this.tabelMatrixInter.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
            this.panelMatrixInter.removeAll();
            this.panelMatrixInter.add(this.tabelMatrixInter.getTableHeader(), BorderLayout.NORTH);
            this.panelMatrixInter.add(this.tabelMatrixInter, BorderLayout.CENTER);
            c.gridx = 0;
            c.gridy = 6;
            c.fill = GridBagConstraints.NONE;
            this.Interpolasi.add(this.panelMatrixInter, c);
        }

        this.f.pack();
    }

    private void hitungSolusi(GridBagConstraints c, boolean isSPL) {

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
        JRadioButton gauss = new JRadioButton("Gauss");
        JRadioButton gaussJordan = new JRadioButton("Gauss-Jordan");
        ButtonGroup group = new ButtonGroup();
        group.add(gauss);
        group.add(gaussJordan);
        metode.add(gauss);
        metode.add(gaussJordan);
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
        //Panel Metode
        JPanel metode = new JPanel(new FlowLayout());
        JLabel labelMetode = new JLabel("Metode : ");
        metode.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        metode.add(labelMetode);
        JRadioButton gauss = new JRadioButton("Gauss");
        JRadioButton gaussJordan = new JRadioButton("Gauss-Jordan");
        ButtonGroup group = new ButtonGroup();
        group.add(gauss);
        group.add(gaussJordan);
        metode.add(gauss);
        metode.add(gaussJordan);
        c.gridx = 0;
        c.gridy = 0;
        this.Interpolasi.add(metode, c);
        //Isi Separator
        JSeparator pemisah1 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah1.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        this.Interpolasi.add(pemisah1, c);
        //Panel banyak titik
        JPanel panelTitik = new JPanel(new GridLayout(1, 4));
        JLabel nTitik = new JLabel("Banyak Titik");
        SpinnerNumberModel modelTitik = new SpinnerNumberModel(this.minBaris, this.minBaris, this.maxBaris, 1);
        this.titik = new JSpinner(modelTitik);
        panelTitik.add(nTitik);
        panelTitik.add(this.titik);
        c.gridx = 0;
        c.gridy = 3;
        this.Interpolasi.add(panelTitik, c);
        //Tombol buat matrix
        JButton buatMatrix = new JButton("Buat Matrix");
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 1;
        this.Interpolasi.add(buatMatrix, c);
        //Isi Separator
        JSeparator pemisah2 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah2.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        this.Interpolasi.add(pemisah2, c);
        //Matrix
        buatMatrix.addActionListener(e -> this.initTabel(c, false));
        //Tombol untuk hitung hasil interpolasi
        JButton hasilInter = new JButton("Interpolasi Polynomial");
        c.gridx = 0;
        c.gridy = 8;
        this.Interpolasi.add(hasilInter, c);
        //Hasil SPL
        this.panelHasilInter = new JPanel(new BorderLayout());
        hasilInter.addActionListener(e -> this.hitungSolusi(c, false));
    }

}

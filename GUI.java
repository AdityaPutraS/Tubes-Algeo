import com.sun.xml.internal.ws.encoding.ImageDataContentHandler;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private final int minBaris = 2, minKolom = 2;
    private final int maxBaris = 20, maxKolom = 20;
    private JFrame f;
    private JTabbedPane tabPane;

    //Konstruktor
    public GUI(int lebar, int tinggi) {
        this.f = new JFrame();
        this.f.setLayout(new BorderLayout());
        this.f.setSize(lebar, tinggi);
        initTabPane();
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
        TabGauss();
        TabGaussJordan();
        this.f.add(tabPane, BorderLayout.NORTH);
    }

    private void TabPersamaanLinier() {
        JPanel SPL = new JPanel();
        SPL.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.tabPane.addTab("Persamaan Linier", null, SPL, "Sistem Persamaan Linier");
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
        SPL.add(metode, c);
        //Isi Separator
        JSeparator pemisah1 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah1.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        SPL.add(pemisah1, c);
        //Panel banyak baris
        JPanel panelBaris = new JPanel(new GridLayout(1, 4));
        JLabel nBaris = new JLabel("Banyak Baris");
        SpinnerNumberModel modelBaris = new SpinnerNumberModel(this.minBaris, this.minBaris, this.maxBaris, 1);
        JSpinner baris = new JSpinner(modelBaris);
        panelBaris.add(nBaris);
        panelBaris.add(baris);
        c.gridx = 0;
        c.gridy = 3;
        SPL.add(panelBaris, c);
        //Panel banyak kolom
        JPanel panelKolom = new JPanel(new GridLayout(1, 4));
        JLabel nKolom = new JLabel("Banyak Kolom");
        SpinnerNumberModel modelKolom = new SpinnerNumberModel(this.minKolom, this.minKolom, this.maxKolom, 1);
        JSpinner kolom = new JSpinner(modelKolom);
        panelKolom.add(nKolom);
        panelKolom.add(kolom);
        c.gridx = 0;
        c.gridy = 4;
        SPL.add(panelKolom, c);
        //Tombol buat matrix
        JButton buatMatrix = new JButton("Buat Matrix");
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 1;
        SPL.add(buatMatrix,c);
        //Isi Separator
        JSeparator pemisah2 = new JSeparator(SwingConstants.HORIZONTAL);
        pemisah2.setPreferredSize(new Dimension(4, 1));
        c.gridx = 0;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 4;
        SPL.add(pemisah2, c);
        //Matrix
        JTable tabelMatrix = new JTable();
    }

    private void TabInterpolasi() {
        JPanel Interpolasi = new JPanel();
        this.tabPane.addTab("Interpolasi", null, Interpolasi, "Interpolasi Polynomial");
    }

    private void TabGauss() {
        JPanel Gauss = new JPanel();
        this.tabPane.addTab("Gauss", null, Gauss, "Metode Gauss");
    }

    private void TabGaussJordan() {
        JPanel GaussJordan = new JPanel();
        this.tabPane.addTab("Gauss-Jordan", null, GaussJordan, "Metode Gauss-Jordan");
    }
}

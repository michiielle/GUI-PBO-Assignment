import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private JTextField inputTugas;
    private JComboBox<String> pilihMatkul;
    private JButton tombolTambah;
    private JButton tombolHapus;
    private DefaultListModel<String> modelDaftar;
    private JList<String> daftarTugas;
    private JLabel labelStatus;

    // Palet warna tema bersih & berwarna
    private final Color WARNA_BG = new Color(244, 246, 250);
    private final Color WARNA_PUTIH = Color.WHITE;
    private final Color WARNA_TEKS = new Color(30, 41, 59);
    private final Color WARNA_BIRU = new Color(37, 99, 235);
    private final Color WARNA_MERAH = new Color(220, 38, 38);
    private final Color WARNA_HIJAU = new Color(22, 163, 74);
    private final Color WARNA_BORDER = new Color(203, 213, 225);

    private final Font FONT_NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    private final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 13);

    public Main() {
        setTitle("Catatan Tugas Kuliah");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel(new BorderLayout(8, 8));
        rootPanel.setBackground(WARNA_BG);
        rootPanel.setBorder(new EmptyBorder(12, 14, 12, 14));
        setContentPane(rootPanel);

        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        panelAtas.setBackground(WARNA_BG);

        JPanel panelTengah = new JPanel(new BorderLayout(4, 6));
        panelTengah.setBackground(WARNA_BG);

        JPanel panelBawah = new JPanel(new GridLayout(2, 1, 4, 6));
        panelBawah.setBackground(WARNA_BG);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        panelTombol.setBackground(WARNA_BG);

        JLabel labelTugas = new JLabel("Tugas:");
        labelTugas.setFont(FONT_BOLD);
        labelTugas.setForeground(WARNA_TEKS);

        inputTugas = new JTextField(14);
        inputTugas.setFont(FONT_NORMAL);
        inputTugas.setBackground(WARNA_PUTIH);
        inputTugas.setForeground(WARNA_TEKS);
        inputTugas.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(WARNA_BORDER, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        String[] matkul = {"Alpro", "Algodat", "Jarkom", "IMK"};
        pilihMatkul = new JComboBox<>(matkul);
        pilihMatkul.setFont(FONT_NORMAL);
        pilihMatkul.setBackground(WARNA_PUTIH);
        pilihMatkul.setForeground(WARNA_TEKS);

        tombolTambah = new JButton("Tambah");
        tombolTambah.setFont(FONT_BOLD);
        tombolTambah.setBackground(WARNA_BIRU);
        tombolTambah.setForeground(WARNA_PUTIH);
        tombolTambah.setFocusPainted(false);
        tombolTambah.setBorder(new EmptyBorder(6, 14, 6, 14));

        tombolHapus = new JButton("Hapus");
        tombolHapus.setFont(FONT_BOLD);
        tombolHapus.setBackground(WARNA_MERAH);
        tombolHapus.setForeground(WARNA_PUTIH);
        tombolHapus.setFocusPainted(false);
        tombolHapus.setBorder(new EmptyBorder(6, 16, 6, 16));

        JLabel labelDaftar = new JLabel("Daftar Tugas: (Klik 2x item untuk menandai selesai)");
        labelDaftar.setFont(FONT_BOLD);
        labelDaftar.setForeground(WARNA_TEKS);

        modelDaftar = new DefaultListModel<>();
        daftarTugas = new JList<>(modelDaftar);
        daftarTugas.setFont(FONT_NORMAL);
        daftarTugas.setBackground(WARNA_PUTIH);
        daftarTugas.setForeground(WARNA_TEKS);
        daftarTugas.setSelectionBackground(new Color(219, 234, 254));
        daftarTugas.setSelectionForeground(new Color(30, 58, 138));
        daftarTugas.setFixedCellHeight(26);

        JScrollPane scrollDaftar = new JScrollPane(daftarTugas);
        scrollDaftar.setBorder(new LineBorder(WARNA_BORDER, 1, true));

        labelStatus = new JLabel("Status: Siap digunakan", SwingConstants.CENTER);
        labelStatus.setFont(FONT_BOLD);
        labelStatus.setForeground(WARNA_TEKS);

        panelAtas.add(labelTugas);
        panelAtas.add(inputTugas);
        panelAtas.add(pilihMatkul);
        panelAtas.add(tombolTambah);

        panelTengah.add(labelDaftar, BorderLayout.NORTH);
        panelTengah.add(scrollDaftar, BorderLayout.CENTER);

        panelTombol.add(tombolHapus);
        panelBawah.add(panelTombol);
        panelBawah.add(labelStatus);

        rootPanel.add(panelAtas, BorderLayout.NORTH);
        rootPanel.add(panelTengah, BorderLayout.CENTER);
        rootPanel.add(panelBawah, BorderLayout.SOUTH);

        tombolTambah.addActionListener(this);
        tombolHapus.addActionListener(this);

        inputTugas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tambahTugas();
                }
            }
        });

        daftarTugas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int idx = daftarTugas.locationToIndex(e.getPoint());
                    if (idx >= 0) {
                        String item = modelDaftar.getElementAt(idx);
                        if (!item.startsWith("[Selesai] ")) {
                            modelDaftar.set(idx, "[Selesai] " + item);
                            labelStatus.setForeground(WARNA_HIJAU);
                            labelStatus.setText("Status: Tugas ditandai selesai");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tombolTambah) {
            tambahTugas();
        } else if (e.getSource() == tombolHapus) {
            int idx = daftarTugas.getSelectedIndex();
            if (idx != -1) {
                modelDaftar.remove(idx);
                labelStatus.setForeground(WARNA_MERAH);
                labelStatus.setText("Status: Tugas dihapus");
            } else {
                labelStatus.setForeground(WARNA_MERAH);
                labelStatus.setText("Status: Pilih tugas dulu");
            }
        }
    }

    private void tambahTugas() {
        String judul = inputTugas.getText().trim();
        String mk = (String) pilihMatkul.getSelectedItem();
        if (!judul.isEmpty()) {
            modelDaftar.addElement("[" + mk + "] " + judul);
            inputTugas.setText("");
            labelStatus.setForeground(WARNA_BIRU);
            labelStatus.setText("Status: Tugas ditambahkan");
        } else {
            labelStatus.setForeground(WARNA_MERAH);
            labelStatus.setText("Status: Isi nama tugas dulu");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
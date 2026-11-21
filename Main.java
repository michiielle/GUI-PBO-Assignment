import javax.swing.*;
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

    public Main() {
        setTitle("GUITask - Catatan Tugas Kuliah");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(6, 6));

        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        JPanel panelTengah = new JPanel(new BorderLayout(4, 4));

        JPanel panelBawah = new JPanel(new GridLayout(2, 1, 4, 4));
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));

        JLabel labelTugas = new JLabel("Tugas:");
        inputTugas = new JTextField(12);

        String[] matkul = {"Alpro", "Algodat", "Jarkom", "IMK"};
        pilihMatkul = new JComboBox<>(matkul);

        tombolTambah = new JButton("Tambah");
        tombolHapus = new JButton("Hapus");

        modelDaftar = new DefaultListModel<>();
        daftarTugas = new JList<>(modelDaftar);
        JScrollPane scrollDaftar = new JScrollPane(daftarTugas);

        labelStatus = new JLabel("Status: Siap digunakan", SwingConstants.CENTER);

        panelAtas.add(labelTugas);
        panelAtas.add(inputTugas);
        panelAtas.add(pilihMatkul);
        panelAtas.add(tombolTambah);

        panelTengah.add(new JLabel("Daftar Tugas:"), BorderLayout.NORTH);
        panelTengah.add(scrollDaftar, BorderLayout.CENTER);

        panelTombol.add(tombolHapus);
        panelBawah.add(panelTombol);
        panelBawah.add(labelStatus);

        add(panelAtas, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

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
                labelStatus.setText("Status: Tugas dihapus");
            } else {
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
            labelStatus.setText("Status: Tugas ditambahkan");
        } else {
            labelStatus.setText("Status: Isi nama tugas dulu");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
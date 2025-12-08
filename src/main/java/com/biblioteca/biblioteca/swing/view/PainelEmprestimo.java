package com.biblioteca.biblioteca.swing.view;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.dto.EmprestimoRequestDTO;

import javax.swing.*;
import java.awt.*;

public class PainelEmprestimo extends JPanel {

    private JTextField txtIsbn, txtUsuarioId, txtBibliotecarioId;
    private JTextArea txtResumo;
    private final BibliotecaClient client;

    public PainelEmprestimo(BibliotecaClient client) {
        this.client = client;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        form.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        form.add(txtIsbn);

        form.add(new JLabel("ID Usuário:"));
        txtUsuarioId = new JTextField();
        form.add(txtUsuarioId);

        form.add(new JLabel("ID Bibliotecário:"));
        txtBibliotecarioId = new JTextField();
        form.add(txtBibliotecarioId);

        JButton btn = new JButton("Emprestar");
        form.add(new JLabel(""));
        form.add(btn);

        add(form, BorderLayout.NORTH);

        txtResumo = new JTextArea();
        txtResumo.setEditable(false);
        add(new JScrollPane(txtResumo), BorderLayout.CENTER);

        btn.addActionListener(e -> realizarEmprestimo());
    }

    private void realizarEmprestimo() {
        try {
            EmprestimoRequestDTO req = new EmprestimoRequestDTO();
            req.setIsbn(txtIsbn.getText());
            req.setUsuarioId(Long.parseLong(txtUsuarioId.getText()));
            req.setBibliotecarioId(Long.parseLong(txtBibliotecarioId.getText()));

            EmprestimoDTO dto = client.criarEmprestimo(req);

            txtResumo.setText("EMPRÉSTIMO REALIZADO!\nLivro: " + dto.getTitulo());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}

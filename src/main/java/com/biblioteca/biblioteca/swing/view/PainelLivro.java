package com.biblioteca.biblioteca.swing.view;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.dto.LivroDTO;
import com.biblioteca.biblioteca.model.Livro;

import javax.swing.*;
import java.awt.*;

public class PainelLivro extends JPanel {

    private JTextField txtIsbn;
    private JTextArea txtLog;
    private final BibliotecaClient client;

    public PainelLivro(BibliotecaClient client) {
        this.client = client;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField(15);
        top.add(txtIsbn);

        JButton btn = new JButton("Buscar");
        top.add(btn);

        add(top, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        add(new JScrollPane(txtLog), BorderLayout.CENTER);

        btn.addActionListener(e -> acaoBuscarLivro());
    }

    private void acaoBuscarLivro() {
        String isbn = txtIsbn.getText().trim();
        txtLog.setText("Consultando: " + isbn + "...\n");

        new Thread(() -> {
            try {
                LivroDTO livro = client.buscarLivro(isbn);

                SwingUtilities.invokeLater(() -> {
                    txtLog.append("Livro encontrado!\n");
                    txtLog.append("Título: " + livro.getTitulo());
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                        txtLog.append("Erro: " + ex.getMessage())
                );
            }
        }).start();
    }
}

package com.biblioteca.biblioteca.swing.view;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.dto.BibliotecarioRequestDTO;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JDialog {

    private final BibliotecaClient client;

    public TelaCadastro(Frame parent, BibliotecaClient client) {
        super(parent, "Cadastrar", true);
        this.client = client;

        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNome = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JPasswordField txtSenha = new JPasswordField(15);
        JTextField txtMatricula = new JTextField(15);
        JButton btnSalvar = new JButton("Criar Conta");

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Nome:"), gbc);
        gbc.gridy = 1; add(txtNome, gbc);
        gbc.gridy = 2; add(new JLabel("Email:"), gbc);
        gbc.gridy = 3; add(txtEmail, gbc);
        gbc.gridy = 4; add(new JLabel("Senha:"), gbc);
        gbc.gridy = 5; add(txtSenha, gbc);
        gbc.gridy = 6; add(new JLabel("Matrícula:"), gbc);
        gbc.gridy = 7; add(txtMatricula, gbc);
        gbc.gridy = 9; add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            try {
                BibliotecarioRequestDTO req = new BibliotecarioRequestDTO();
                req.setNome(txtNome.getText());
                req.setEmail(txtEmail.getText());
                req.setSenha(new String(txtSenha.getPassword()));
                req.setMatricula(txtMatricula.getText());

                client.cadastrarBibliotecario(req);

                JOptionPane.showMessageDialog(this, "Cadastro realizado via API");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro API: " + ex.getMessage());
            }
        });
    }
}
package com.biblioteca.biblioteca.swing.view;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.dto.UsuarioDTO;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private final BibliotecaClient client;

    public TelaLogin(BibliotecaClient client) {
        this.client = client;

        setTitle("Biblioteca");
        setSize(320, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtEmail = new JTextField(15);
        JPasswordField txtSenha = new JPasswordField(15);
        JButton btnEntrar = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar-se");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Login", SwingConstants.CENTER), gbc);

        gbc.gridy = 1; add(new JLabel("Email:"), gbc);
        gbc.gridy = 2; add(txtEmail, gbc);
        gbc.gridy = 3; add(new JLabel("Senha:"), gbc);
        gbc.gridy = 4; add(txtSenha, gbc);
        gbc.gridy = 5; add(btnEntrar, gbc);
        gbc.gridy = 6; add(btnCadastrar, gbc);
        btnEntrar.addActionListener(e -> {
            try {
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());


                UsuarioDTO usuarioLogado = client.realizarLogin(email, senha);

                if (usuarioLogado != null) {

                    TelaBiblioteca telaPrincipal = new TelaBiblioteca(client);
                    telaPrincipal.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não encontrado na API.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro de Conexão: " + ex.getMessage());
            }
        });

        btnCadastrar.addActionListener(e -> {
            TelaCadastro cadastro = new TelaCadastro(this, client);
            cadastro.setVisible(true);
        });
    }
}
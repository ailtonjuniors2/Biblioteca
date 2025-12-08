package com.biblioteca.biblioteca.swing;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.swing.view.TelaLogin;

public class MainSwing {

    public static void main(String[] args) {

        BibliotecaClient client = new BibliotecaClient("http://localhost:8080");

        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin(client);
            tela.setVisible(true);
        });
    }
}

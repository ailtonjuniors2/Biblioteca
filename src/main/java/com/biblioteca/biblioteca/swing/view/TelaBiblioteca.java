package com.biblioteca.biblioteca.swing.view;

import com.biblioteca.biblioteca.client.BibliotecaClient;
import com.biblioteca.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.biblioteca.dto.LivroDTO;
import com.biblioteca.biblioteca.model.Livro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaBiblioteca extends JFrame {

    private final BibliotecaClient client;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel lblResultadoLivro;
    private LivroDTO livroEncontrado;
    private JProgressBar barraCarregando;

    public TelaBiblioteca(BibliotecaClient client) {
        this.client = client;

        setTitle("Biblioteca");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(PainelBusca(), "BUSCA");
        mainPanel.add(PainelEmprestimo(), "EMPRESTIMO");

        add(mainPanel);

        cardLayout.show(mainPanel, "BUSCA");
    }

    private JPanel PainelBusca() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Pesquisar no Acervo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txtPesquisa = new JTextField(20);
        txtPesquisa.setToolTipText("Digite o Título ou ISBN");

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBackground(new Color(60, 100, 180));
        btnPesquisar.setForeground(Color.WHITE);

        barraCarregando = new JProgressBar();
        barraCarregando.setIndeterminate(true);
        barraCarregando.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Digite Título ou ISBN:"), gbc);

        gbc.gridy = 2;
        panel.add(txtPesquisa, gbc);

        gbc.gridy = 3;
        panel.add(btnPesquisar, gbc);

        gbc.gridy = 4;
        panel.add(barraCarregando, gbc);

        btnPesquisar.addActionListener(e -> {
            String termo = txtPesquisa.getText().trim();
            if(!termo.isEmpty()){
                barraCarregando.setVisible(true);
                iniciarBusca(termo);
            }
        });

        return panel;
    }

    private JPanel PainelEmprestimo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblResultadoLivro = new JLabel("", SwingConstants.CENTER);
        lblResultadoLivro.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lblResultadoLivro, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton btnConfirmar = new JButton("Confirmar Empréstimo");
        btnConfirmar.setBackground(new Color(34, 139, 34));
        btnConfirmar.setForeground(Color.WHITE);

        JButton btnVoltar = new JButton("Voltar");

        btnPanel.add(btnVoltar);
        btnPanel.add(btnConfirmar);
        panel.add(btnPanel, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            cardLayout.show(mainPanel, "BUSCA");
            livroEncontrado = null;
        });

        btnConfirmar.addActionListener(e -> pedirIdEFinalizar());

        return panel;
    }

    private void iniciarBusca(String termo) {
        new Thread(() -> {
            try {
                List<LivroDTO> livrosDaApi = client.listarLivros();

                LivroDTO l = livrosDaApi.stream()
                        .filter(x -> x.getIsbn().equalsIgnoreCase(termo) ||
                                x.getTitulo().toLowerCase().contains(termo.toLowerCase()))
                        .findFirst()
                        .orElse(null);

                if (l == null && termo.replace("-", "").matches("\\d{10}|\\d{13}")) {
                    try {
                        l = client.buscarLivro(termo.replace("-", ""));
                    } catch (Exception ignored) {}
                }

                LivroDTO finalL = l;

                SwingUtilities.invokeLater(() -> {
                    barraCarregando.setVisible(false);

                    if (finalL != null) {
                        TelaEmprestimo(finalL);
                    } else {
                        JOptionPane.showMessageDialog(this, "Livro não encontrado na API.");
                    }
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    barraCarregando.setVisible(false);
                    JOptionPane.showMessageDialog(this, "Erro ao conectar com a API: " + e.getMessage());
                });
            }
        }).start();
    }


    private void Tela(Livro l) {
    }

    private void TelaEmprestimo(LivroDTO livro) {
        this.livroEncontrado = livro;

        String status = livro.isDisponivel()
                ? "<b style='color:green'>DISPONÍVEL</b>"
                : "<b style='color:red'>INDISPONÍVEL</b>";

        lblResultadoLivro.setText("<html><center>" +
                "<h2>" + livro.getTitulo() + "</h2>" +
                "Autor: " + livro.getAutor() + "<br/>" +
                "ISBN: " + livro.getIsbn() + "<br/><br/>" +
                "Status: " + status +
                "</center></html>");

        cardLayout.show(mainPanel, "EMPRESTIMO");
    }

    private void pedirIdEFinalizar() {
        if (livroEncontrado == null || !livroEncontrado.isDisponivel()) {
            JOptionPane.showMessageDialog(this, "Livro indisponível.");
            return;
        }

        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Usuário:");
        if (idStr == null || idStr.isEmpty()) return;

        try {
            EmprestimoRequestDTO req = new EmprestimoRequestDTO();
            req.setIsbn(livroEncontrado.getIsbn());
            req.setUsuarioId(Long.parseLong(idStr));
            req.setBibliotecarioId(1L);

            client.criarEmprestimo(req);

            JOptionPane.showMessageDialog(this, "Empréstimo realizado.");

            livroEncontrado.setDisponivel(false);
            cardLayout.show(mainPanel, "BUSCA");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na API: " + ex.getMessage());
        }
    }
}
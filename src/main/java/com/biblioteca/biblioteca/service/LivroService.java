package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.exception.ApiExternalException;
import com.biblioteca.biblioteca.exception.IssnInvalidoException;
import com.biblioteca.biblioteca.exception.LivroNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LivroService {

    private final LivroRepository livroRepository;
    private final RestTemplate restTemplate;

    // URL da API CrossRef usada pra validar ISSN
    private static final String CROSSREF_URL = "https://api.crossref.org/journals/{issn}";

    public LivroService(LivroRepository livroRepository, RestTemplate restTemplate) {
        this.livroRepository = livroRepository;
        this.restTemplate = restTemplate;
    }

    // Criar livro após validar ISSN na API externa
    public Livro criarLivro(Livro livro) {
        String issn = livro.getIssn();
        if (livroRepository.existsById(issn)) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com esse ISSN: " + issn);
        }
        validarIssnExterno(issn);
        return livroRepository.save(livro);
    }

    // Atualizar livro existente
    public Livro atualizarLivro(String issn, Livro atualizado) {
        Livro existente = livroRepository.findById(issn)
                .orElseThrow(() -> new LivroNotFoundException(issn));
        atualizado.setIssn(issn);
        validarIssnExterno(issn);
        existente.setTitulo(atualizado.getTitulo());
        existente.setAutor(atualizado.getAutor());
        existente.setDisponivel(atualizado.isDisponivel());
        return livroRepository.save(existente);
    }

    // Deletar livro
    public void deletarLivro(String issn) {
        if (!livroRepository.existsById(issn)) throw new LivroNotFoundException(issn);
        livroRepository.deleteById(issn);
    }

    // Buscar livro por ISSN
    public Livro buscarPorIssn(String issn) {
        return livroRepository.findById(issn)
                .orElseThrow(() -> new LivroNotFoundException(issn));
    }

    // Listar todos
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // Buscar por título ou autor
    public List<Livro> buscarPorTituloOuAutor(Optional<String> titulo, Optional<String> autor) {
        if (titulo.isPresent() && autor.isPresent()) {
            return livroRepository.findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCase(
                    titulo.get(), autor.get());
        } else if (titulo.isPresent()) {
            return livroRepository.findByTituloContainingIgnoreCase(titulo.get());
        } else if (autor.isPresent()) {
            return livroRepository.findByAutorContainingIgnoreCase(autor.get());
        } else {
            return listarTodos();
        }
    }

    // Validação de ISSN com API externa (CrossRef)
    private void validarIssnExterno(String issn) {
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(CROSSREF_URL, String.class, issn);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                throw new IssnInvalidoException(issn);
            }
        } catch (HttpClientErrorException.NotFound nf) {
            throw new IssnInvalidoException(issn);
        } catch (HttpClientErrorException e) {
            throw new IssnInvalidoException(issn);
        } catch (Exception ex) {
            throw new ApiExternalException("Erro ao validar ISSN na API externa", ex);
        }
    }
}

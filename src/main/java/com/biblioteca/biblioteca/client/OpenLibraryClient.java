package com.biblioteca.biblioteca.client;

import com.biblioteca.biblioteca.dto.openlibrary.OpenLibraryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OpenLibraryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public OpenLibraryResponse buscarLivroPorISBN(String isbn) {
        String url = "https://openlibrary.org/isbn/" + isbn + ".json";

        try {
            return restTemplate.getForObject(url, OpenLibraryResponse.class);
        } catch (Exception e) {
            return null; // ISBN não encontrado ou API offline
        }
    }
}

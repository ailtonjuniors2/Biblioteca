package com.biblioteca.biblioteca.dto.openlibrary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenLibraryResponse {

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("by_statement")
    private String autor;
}

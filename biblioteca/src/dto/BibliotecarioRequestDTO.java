package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BibliotecarioRequestDTO extends UsuarioRequestDTO{
    @NotBlank(message = "A matrícula é obrigatória")
    private String matricula;
}

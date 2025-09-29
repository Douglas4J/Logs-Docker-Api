package logs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PessoaDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID da Pessoa é gerado automaticamente")
    private Long idPessoa;

    @NotBlank(message = "O nome da pessoa é obrigatório.")
    private String nomePessoa;

    @NotNull(message = "A idade da pessoa é obrigatória.")
    @Min(value = 0, message = "A idade não pode ser negativa.")
    @Max(value = 120, message = "A idade não pode ser maior que 120 anos.")
    private Integer idadePessoa;

    @NotBlank(message = "O CPF da pessoa é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
    private String cpfPessoa;

    @NotBlank(message = "A cidade da pessoa é obrigatória.")
    private String cidadePessoa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(example = "15-09-2025")
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve estar no passado.")
    private LocalDate nascimentoPessoa;

    @NotNull(message = "É obrigatório informar se a pessoa está viva ou não.")
    private Boolean vivaPessoa;

}

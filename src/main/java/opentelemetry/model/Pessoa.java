package opentelemetry.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pes_id", updatable = false, nullable = false)
    private Long idPessoa;

    @Column(name = "pes_nome", nullable = false)
    private String nomePessoa;

    @Column(name = "pes_idade", nullable = false)
    private Integer idadePessoa;

    @Column(name = "pes_cpf", nullable = false, length = 11, unique = true)
    private String cpfPessoa;

    @Column(name = "pes_cidade", nullable = false)
    private String cidadePessoa;

    @Column(name = "pes_nascimento", nullable = false)
    private LocalDate nascimentoPessoa;

    @Column(name = "pes_viva", nullable = false)
    private Boolean vivaPessoa;

}

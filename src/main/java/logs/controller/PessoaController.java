package logs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logs.dto.PessoaDTO;
import logs.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pessoa", description = "Controller para testar API")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Registrar uma nova pessoa.")
    @PostMapping
    public ResponseEntity<PessoaDTO> registrarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO novaPessoa = pessoaService.registrarPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @Operation(summary = "Listar todas as pessoas.")
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @Operation(summary = "Deletar registro de uma pessoa por id.")
    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> deletarPessoaId(@PathVariable("idPessoa") Long idPessoa) {
        pessoaService.deletarPessoaId(idPessoa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(
            @PathVariable Long idPessoa,
            @RequestBody PessoaDTO pessoaDTO) {

        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoaId(idPessoa, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

}

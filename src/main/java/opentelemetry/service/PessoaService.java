package opentelemetry.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import opentelemetry.dto.PessoaDTO;
import opentelemetry.mapper.PessoaMapper;
import opentelemetry.model.Pessoa;
import opentelemetry.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PessoaService {

    @Autowired
    private Validator validator;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public PessoaDTO registrarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = PessoaMapper.pessoaParaEntidade(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return PessoaMapper.pessoaParaDTO(pessoa);
    }

    public List<PessoaDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(PessoaMapper::pessoaParaDTO)
                .toList();
    }

    @Transactional
    public void deletarPessoaId(Long idPessoa) {
        Pessoa pessoaExistente = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa com ID " + idPessoa + " não encontrada."));

        pessoaRepository.delete(pessoaExistente);
    }

    @Transactional
    public PessoaDTO atualizarPessoaId(Long idPessoa, PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa com ID " + idPessoa + " não encontrada."));

        Set<ConstraintViolation<PessoaDTO>> violacoes = validator.validate(pessoaDTO);
        if (violacoes.isEmpty()) {
            pessoaExistente.setNomePessoa(pessoaDTO.getNomePessoa());
            pessoaExistente.setIdadePessoa(pessoaDTO.getIdadePessoa());
            pessoaExistente.setCpfPessoa(pessoaDTO.getCpfPessoa());
            pessoaExistente.setCidadePessoa(pessoaDTO.getCidadePessoa());
            pessoaExistente.setNascimentoPessoa(pessoaDTO.getNascimentoPessoa());
            pessoaExistente.setVivaPessoa(pessoaDTO.getVivaPessoa());

            Pessoa pessoaAtualizada = pessoaRepository.save(pessoaExistente);
            return PessoaMapper.pessoaParaDTO(pessoaAtualizada);

        } else {
            throw new ConstraintViolationException(violacoes);
        }
    }

}

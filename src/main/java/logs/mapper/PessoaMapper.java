package logs.mapper;

import logs.dto.PessoaDTO;
import logs.model.Pessoa;

public class PessoaMapper {

    public static PessoaDTO pessoaParaDTO(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        return PessoaDTO.builder()
                .idPessoa(pessoa.getIdPessoa())
                .nomePessoa(pessoa.getNomePessoa())
                .idadePessoa(pessoa.getIdadePessoa())
                .cpfPessoa(pessoa.getCpfPessoa())
                .cidadePessoa(pessoa.getCidadePessoa())
                .nascimentoPessoa(pessoa.getNascimentoPessoa())
                .vivaPessoa(pessoa.getVivaPessoa())
                .build();

    }

    public static Pessoa pessoaParaEntidade(PessoaDTO pessoaDTO) {
        if (pessoaDTO == null) {
            return null;
        }

        return Pessoa.builder()
                .nomePessoa(pessoaDTO.getNomePessoa())
                .idadePessoa(pessoaDTO.getIdadePessoa())
                .cpfPessoa(pessoaDTO.getCpfPessoa())
                .cidadePessoa(pessoaDTO.getCidadePessoa())
                .nascimentoPessoa(pessoaDTO.getNascimentoPessoa())
                .vivaPessoa(pessoaDTO.getVivaPessoa())
                .build();
    }

}

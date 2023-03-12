package minha.api.MinhaAPI.domain.medico;

import jakarta.validation.constraints.NotNull;
import minha.api.MinhaAPI.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}

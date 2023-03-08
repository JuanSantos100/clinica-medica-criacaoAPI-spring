package minha.api.MinhaAPI.medico;

import jakarta.validation.constraints.NotNull;
import minha.api.MinhaAPI.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}

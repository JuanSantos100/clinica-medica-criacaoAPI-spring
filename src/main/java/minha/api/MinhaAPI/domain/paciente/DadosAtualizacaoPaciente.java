package minha.api.MinhaAPI.domain.paciente;

import jakarta.validation.constraints.NotNull;
import minha.api.MinhaAPI.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco

) {
}

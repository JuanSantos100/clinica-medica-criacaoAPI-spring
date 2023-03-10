package minha.api.MinhaAPI.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import minha.api.MinhaAPI.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco

) {
}

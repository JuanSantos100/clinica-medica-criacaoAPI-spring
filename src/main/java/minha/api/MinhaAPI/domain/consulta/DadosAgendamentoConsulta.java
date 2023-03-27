package minha.api.MinhaAPI.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import minha.api.MinhaAPI.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,

        @NotNull
        @Future //Datas somente para o futuro
        LocalDateTime data,

        Especialidade especialidade

) {
}

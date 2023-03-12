package minha.api.MinhaAPI.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, Especialidade especialidade, String crm) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getEspecialidade(), medico.getCrm());
    }
}

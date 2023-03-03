package minha.api.MinhaAPI.medico;

public record DadosListagemMedico(String nome, String email, Especialidade especialidade, String crm) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getEspecialidade(), medico.getCrm());
    }
}

package minha.api.MinhaAPI.domain.consulta;

import minha.api.MinhaAPI.domain.ValidacaoException;
import minha.api.MinhaAPI.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import minha.api.MinhaAPI.domain.medico.Medico;
import minha.api.MinhaAPI.domain.medico.MedicoRepository;
import minha.api.MinhaAPI.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Classe de serviço, executa validações, regras de negócio
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;


    /*
        Injeção de uma lista com a Interface:

        1 - Procura todas as classe que implementam a interface
        2 - Cria uma lista com todas essas classes
        3 - Injeta a lista com cada uma delas

     */
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public void agendar(DadosAgendamentoConsulta dados) {

        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        validadores.forEach(v -> v.validar(dados)); //Passa por todos os validadores chamando o método validar, passando os dados de agendamento

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedicoAutomaticamente(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);
    }

    private Medico escolherMedicoAutomaticamente(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido !");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe !");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
    }
}

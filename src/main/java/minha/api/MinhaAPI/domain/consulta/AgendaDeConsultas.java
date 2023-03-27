package minha.api.MinhaAPI.domain.consulta;

import minha.api.MinhaAPI.domain.medico.MedicoRepository;
import minha.api.MinhaAPI.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //Classe de serviço, executa validações, regras de negócio
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository

    public void agendar(DadosAgendamentoConsulta dados) {



        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepository.findById(dados.idMedico()).get();
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);
    }



}

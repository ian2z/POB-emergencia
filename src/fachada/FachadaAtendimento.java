package fachada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import repositorio.Repositorio;
import repositorio.RepositorioAtendimento;
import repositorio.RepositorioPaciente;
import repositorio.RepositorioUpa;

public class FachadaAtendimento {
    private RepositorioAtendimento repAtendimento = new RepositorioAtendimento();
    private RepositorioPaciente repPaciente = new RepositorioPaciente();
    private RepositorioUpa repUpa = new RepositorioUpa();

    public void cadastrarAtendimento(String dataString, String triagem, String cpfPaciente, String nomeUpa) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            Paciente p = repPaciente.ler(cpfPaciente);
            if (p == null) throw new Exception("Paciente com CPF " + cpfPaciente + " não cadastrado!");

            Upa u = repUpa.ler(nomeUpa);
            if (u == null) throw new Exception("UPA com nome '" + nomeUpa + "' não cadastrada!");

            Atendimento a = new Atendimento(dataString, triagem, p, u);
            repAtendimento.criar(a);

            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }

    public List<Atendimento> listarAtendimentos() {
        Repositorio.abrir();
        return repAtendimento.listarTodos();
    }

    public void excluirAtendimento(int id) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            Atendimento a = repAtendimento.ler(id);
            if (a == null) throw new Exception("Atendimento não encontrado!");

            // Remover vínculos em memória para evitar inconsistências
            a.getPaciente().remover(a);
            a.getUpa().remover(a);

            repAtendimento.apagar(a);
            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }

    // Encapsulamento das consultas
    public List<Atendimento> consultarAtendimentosPorData(String dataString) {
        Repositorio.abrir();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.parse(dataString, formato);
        return repAtendimento.buscarPorData(data);
    }

    public List<Atendimento> consultarAtendimentosPorCpfPaciente(String cpf) {
        Repositorio.abrir();
        return repAtendimento.buscarPorCpfPaciente(cpf);
    }
}
package requisito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import repossitorio.Repositorio;
import repossitorio.RepositorioAtendimento;
import repossitorio.RepositorioPaciente;
import repossitorio.RepositorioUpa;

public class FachadaAtendimento {
    private RepositorioAtendimento repAtendimento = new RepositorioAtendimento();
    private RepositorioPaciente repPaciente = new RepositorioPaciente();
    private RepositorioUpa repUpa = new RepositorioUpa();

    public void cadastrarAtendimento(String dataString, String triagem, String cpfPaciente, String nomeUpa) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            Paciente p = repPaciente.localizar(cpfPaciente);
            if (p == null) throw new Exception("Paciente com CPF " + cpfPaciente + " não cadastrado!");

            Upa u = repUpa.localizar(nomeUpa);
            if (u == null) throw new Exception("UPA com nome '" + nomeUpa + "' não cadastrada!");

            Atendimento a = new Atendimento(dataString, triagem, p, u);
            repAtendimento.criar(a);

            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Atendimento> listarAtendimentos() {
        try {
            Repositorio.conectar();
            List<Atendimento> lista = repAtendimento.listar();
            for (Atendimento a : lista) {
                a.getPaciente().getNome();
                a.getUpa().getNome();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public void excluirAtendimento(int id) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            Atendimento a = repAtendimento.localizar(id);
            if (a == null) throw new Exception("Atendimento não encontrado!");

            // Remover vínculos em memória para evitar inconsistências
            a.getPaciente().remover(a);
            a.getUpa().remover(a);

            repAtendimento.deletar(a);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // Encapsulamento das consultas
    public List<Atendimento> consultarAtendimentosPorData(String dataString) {
        try {
            Repositorio.conectar();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate data = LocalDate.parse(dataString, formato);
            List<Atendimento> lista = repAtendimento.buscarPorData(data);
            for (Atendimento a : lista) {
                a.getPaciente().getNome();
                a.getUpa().getNome();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Atendimento> consultarAtendimentosPorCpfPaciente(String cpf) {
        try {
            Repositorio.conectar();
            List<Atendimento> lista = repAtendimento.buscarPorCpfPaciente(cpf);
            for (Atendimento a : lista) {
                a.getPaciente().getNome();
                a.getUpa().getNome();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Atendimento> consultarAtendimentosPorTriagem(String palavraChave) {
        try {
            Repositorio.conectar();
            List<Atendimento> lista = repAtendimento.buscarPorTriagemLike(palavraChave);
            for (Atendimento a : lista) {
                a.getPaciente().getNome();
                a.getUpa().getNome();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }
}
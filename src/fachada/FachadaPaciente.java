package fachada;

import java.util.List;
import modelo.Paciente;
import repositorio.Repositorio;
import repositorio.RepositorioPaciente;

public class FachadaPaciente {
    private RepositorioPaciente repPaciente = new RepositorioPaciente();

    public void cadastrarPaciente(String cpf, String nome, byte[] foto) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11) {
                throw new Exception("CPF deve conter exatamente 11 dígitos.");
            }
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }

            Paciente p = repPaciente.ler(cpf);
            if (p != null) {
                throw new Exception("Paciente com CPF " + cpf + " já existe!");
            }

            p = new Paciente(cpf, nome, foto);

            repPaciente.criar(p);
            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }

    public void atualizarPaciente(String cpf, String novoNome, byte[] novaFoto) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            Paciente p = repPaciente.ler(cpf);
            if (p == null) {
                throw new Exception("Paciente não encontrado!");
            }

            // Regra de validação
            if (novoNome == null || novoNome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }

            // Alterando atributos em memória
            // Por termos Fetch LAZY, tome cuidado com atributos relacionados
            p = repPaciente.atualizar(p);

            // Se criarmos métodos de atualização
            // p.setNome(novoNome);
            // p.setFoto(novaFoto);

            repPaciente.atualizar(p);
            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }

    public List<Paciente> listarPacientes() {
        Repositorio.abrir();
        return repPaciente.listarTodos();
    }

    public Paciente buscarPaciente(String cpf) {
        Repositorio.abrir();
        return repPaciente.ler(cpf);
    }

    public void excluirPaciente(String cpf) throws Exception {
        Repositorio.abrir();
        Repositorio.iniciarTransacao();
        try {
            Paciente p = repPaciente.ler(cpf);
            if (p == null) throw new Exception("Paciente não encontrado!");
            repPaciente.apagar(p);
            Repositorio.confirmarTransacao();
        } catch (Exception e) {
            Repositorio.cancelarTransacao();
            throw e;
        }
    }
}
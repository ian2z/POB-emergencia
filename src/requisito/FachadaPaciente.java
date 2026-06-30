package requisito;

import java.util.List;
import modelo.Paciente;
import repossitorio.Repositorio;
import repossitorio.RepositorioPaciente;

public class FachadaPaciente {
    private RepositorioPaciente repPaciente = new RepositorioPaciente();

    public void cadastrarPaciente(String cpf, String nome, byte[] foto) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11) {
                throw new Exception("CPF deve conter exatamente 11 dígitos.");
            }
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }

            Paciente p = repPaciente.localizar(cpf);
            if (p != null) {
                throw new Exception("Paciente com CPF " + cpf + " já existe!");
            }

            p = new Paciente(cpf, nome, foto);

            repPaciente.criar(p);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    public void atualizarPaciente(String cpf, String novoNome, byte[] novaFoto) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            Paciente p = repPaciente.localizar(cpf);
            if (p == null) {
                throw new Exception("Paciente não encontrado!");
            }

            // Regra de validação
            if (novoNome == null || novoNome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }

            // Alterando atributos em memória
            p = repPaciente.atualizar(p);
            p.setNome(novoNome);
            if (novaFoto != null) {
                p.setFoto(novaFoto);
            }
            repPaciente.atualizar(p);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Paciente> listarPacientes() {
        try {
            Repositorio.conectar();
            List<Paciente> lista = repPaciente.listar();
            for (Paciente p : lista) {
                p.getAtendimentos().size();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public Paciente buscarPaciente(String cpf) {
        try {
            Repositorio.conectar();
            Paciente p = repPaciente.localizar(cpf);
            if (p != null) {
                for (modelo.Atendimento a : p.getAtendimentos()) {
                    a.getUpa().getNome(); // Force load of Upa lazy proxy
                }
                if (p.getFoto() != null) {
                    int loadFoto = p.getFoto().length; // Force load of lazy field
                }
            }
            return p;
        } finally {
            Repositorio.desconectar();
        }
    }

    public void excluirPaciente(String cpf) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            Paciente p = repPaciente.localizar(cpf);
            if (p == null) throw new Exception("Paciente não encontrado!");
            repPaciente.deletar(p);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    // Consultas
    public List<Paciente> consultarPacientesComMaisAtendimentos(String nomeUpa, long limite) {
        try {
            Repositorio.conectar();
            List<Paciente> lista = repPaciente.consultarPacientesComMaisAtendimentos(nomeUpa, limite);
            for (Paciente p : lista) {
                p.getAtendimentos().size();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Paciente> consultarPacientesMultiplasUpas() {
        try {
            Repositorio.conectar();
            List<Paciente> lista = repPaciente.consultarPacientesMultiplasUpas();
            for (Paciente p : lista) {
                p.getAtendimentos().size();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }
}
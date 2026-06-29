package requisito;

import java.util.List;
import modelo.Upa;
import repossitorio.Repositorio;
import repossitorio.RepositorioUpa;

public class FachadaUpa {
    private RepositorioUpa repUpa = new RepositorioUpa();

    public void cadastrarUpa(String nome) throws Exception {
        Repositorio.conectar();
        Repositorio.begin();
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome da UPA inválido.");
            }
            Upa u = repUpa.localizar(nome);
            if (u != null) {
                throw new Exception("UPA com este nome já cadastrada.");
            }

            u = new Upa(nome);
            repUpa.criar(u);
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw e;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Upa> listarUpas() {
        try {
            Repositorio.conectar();
            List<Upa> lista = repUpa.listar();
            for (Upa u : lista) {
                u.getAtendimentos().size();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Object[]> obterRankingLotacao() {
        try {
            Repositorio.conectar();
            return repUpa.obterRankingLotacao();
        } finally {
            Repositorio.desconectar();
        }
    }

    public List<Upa> consultarUpasPorCpfPaciente(String cpf) {
        try {
            Repositorio.conectar();
            List<Upa> lista = repUpa.consultarUpasPorCpfPaciente(cpf);
            for (Upa u : lista) {
                u.getAtendimentos().size();
            }
            return lista;
        } finally {
            Repositorio.desconectar();
        }
    }
}
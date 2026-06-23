package repositorio;

import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import modelo.Atendimento;

public class RepositorioAtendimento extends Repositorio<Atendimento> {

    @Override
    public Atendimento ler(Object chave) {
        try {
            int id = (int) chave;
            return manager.find(Atendimento.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Atendimento> listarTodos() {
        TypedQuery<Atendimento> q = manager.createQuery("SELECT a FROM Atendimento a", Atendimento.class);
        return q.getResultList();
    }

    // Consulta 1: Atendimentos na data X
    public List<Atendimento> buscarPorData(LocalDate data) {
        TypedQuery<Atendimento> q = manager.createQuery(
                "SELECT a FROM Atendimento a WHERE a.data = :data", Atendimento.class);
        q.setParameter("data", data);
        return q.getResultList();
    }

    // Consulta 2: Atendimentos que possuem paciente de CPF X
    public List<Atendimento> buscarPorCpfPaciente(String cpf) {
        TypedQuery<Atendimento> q = manager.createQuery(
                "SELECT a FROM Atendimento a WHERE a.paciente.cpf = :cpf", Atendimento.class);
        q.setParameter("cpf", cpf);
        return q.getResultList();
    }

    // Consulta 4: Busca por palavra-chave na triagem
    public List<Atendimento> buscarPorTriagemLike(String palavraChave) {
        TypedQuery<Atendimento> q = manager.createQuery(
                "SELECT a FROM Atendimento a WHERE LOWER(a.triagem) LIKE LOWER(:palavra)", Atendimento.class);
        q.setParameter("palavra", "%" + palavraChave + "%");
        return q.getResultList();
    }
}
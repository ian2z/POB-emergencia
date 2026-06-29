package repossitorio;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import modelo.Upa;
import util.Util;

public class RepositorioUpa extends Repositorio<Upa> {

    @Override
    public Upa localizar(Object chave) {
        try {
            String nome = (String) chave;
            TypedQuery<Upa> q = Util.getManager().createQuery(
                    "SELECT u FROM Upa u WHERE u.nome = :nome", Upa.class);
            q.setParameter("nome", nome);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Upa> listar() {
        TypedQuery<Upa> q = Util.getManager().createQuery("SELECT u FROM Upa u ORDER BY u.id", Upa.class);
        return q.getResultList();
    }

    // Consulta 6: Ranking de Lotação das UPAs
    public List<Object[]> obterRankingLotacao() {
        TypedQuery<Object[]> q = Util.getManager().createQuery(
                "SELECT u.nome, COUNT(a) FROM Upa u LEFT JOIN u.atendimentos a " +
                        "GROUP BY u.nome " +
                        "ORDER BY COUNT(a) DESC", Object[].class);
        return q.getResultList();
    }

    // Consulta 7: Listagem das UPAs que o paciente X foi atendido
    public List<Upa> consultarUpasPorCpfPaciente(String cpf) {
        TypedQuery<Upa> q = Util.getManager().createQuery(
                "SELECT DISTINCT u FROM Upa u JOIN u.atendimentos a WHERE a.paciente.cpf = :cpf ORDER BY u.nome", Upa.class);
        q.setParameter("cpf", cpf);
        return q.getResultList();
    }
}
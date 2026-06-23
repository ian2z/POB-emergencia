package repositorio;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import modelo.Upa;

public class RepositorioUpa extends Repositorio<Upa> {

    @Override
    public Upa ler(Object chave) {
        try {
            String nome = (String) chave;
            TypedQuery<Upa> q = manager.createQuery(
                    "SELECT u FROM Upa u WHERE u.nome = :nome", Upa.class);
            q.setParameter("nome", nome);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Upa> listarTodos() {
        TypedQuery<Upa> q = manager.createQuery("SELECT u FROM Upa u", Upa.class);
        return q.getResultList();
    }

    // Consulta 6: Ranking de Lotação das UPAs
    public List<Object[]> obterRankingLotacao() {
        TypedQuery<Object[]> q = manager.createQuery(
                "SELECT u.nome, COUNT(a) FROM Upa u LEFT JOIN u.atendimentos a " +
                        "GROUP BY u.nome " +
                        "ORDER BY COUNT(a) DESC", Object[].class);
        return q.getResultList();
    }
}
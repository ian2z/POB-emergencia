package repositorio;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import modelo.Paciente;

public class RepositorioPaciente extends Repositorio<Paciente> {

    @Override
    public Paciente ler(Object chave) {
        try {
            String cpf = (String) chave;
            TypedQuery<Paciente> q = manager.createQuery(
                    "SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class);
            q.setParameter("cpf", cpf);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Paciente> listarTodos() {
        TypedQuery<Paciente> q = manager.createQuery("SELECT p FROM Paciente p", Paciente.class);
        return q.getResultList();
    }

    // Consulta 3: Pacientes com mais de N atendimentos na UPA X
    public List<Paciente> consultarPacientesComMaisAtendimentos(String nomeUpa, long limite) {
        TypedQuery<Paciente> q = manager.createQuery(
                "SELECT p FROM Paciente p JOIN p.atendimentos a " +
                        "WHERE a.upa.nome = :nomeUpa " +
                        "GROUP BY p " +
                        "HAVING COUNT(a) > :limite", Paciente.class);
        q.setParameter("nomeUpa", nomeUpa);
        q.setParameter("limite", limite);
        return q.getResultList();
    }

    // Consulta 5: Pacientes que frequentaram MÚLTIPLAS UPAs diferentes
    public List<Paciente> consultarPacientesMultiplasUpas() {
        TypedQuery<Paciente> q = manager.createQuery(
                "SELECT p FROM Paciente p JOIN p.atendimentos a " +
                        "GROUP BY p " +
                        "HAVING COUNT(DISTINCT a.upa) > 1", Paciente.class);
        return q.getResultList();
    }
}
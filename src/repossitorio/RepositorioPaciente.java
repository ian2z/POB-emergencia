package repossitorio;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import modelo.Paciente;
import util.Util;

public class RepositorioPaciente extends Repositorio<Paciente> {

    @Override
    public Paciente localizar(Object chave) {
        try {
            String cpf = (String) chave;
            TypedQuery<Paciente> q = Util.getManager().createQuery(
                    "SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class);
            q.setParameter("cpf", cpf);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Paciente> listar() {
        TypedQuery<Paciente> q = Util.getManager().createQuery("SELECT p FROM Paciente p ORDER BY p.id", Paciente.class);
        return q.getResultList();
    }

    // Consulta 3: Pacientes com mais de N atendimentos na UPA X
    public List<Paciente> consultarPacientesComMaisAtendimentos(String nomeUpa, long limite) {
        TypedQuery<Paciente> q = Util.getManager().createQuery(
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
        TypedQuery<Paciente> q = Util.getManager().createQuery(
                "SELECT p FROM Paciente p JOIN p.atendimentos a " +
                        "GROUP BY p " +
                        "HAVING COUNT(DISTINCT a.upa) > 1", Paciente.class);
        return q.getResultList();
    }
}
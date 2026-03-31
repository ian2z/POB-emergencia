package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import modelo.Atendimento;
import modelo.Paciente;
import util.Util;

public class Consultar {

    public Consultar() {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        System.out.println("\n=======================================================");

        //quais os atendimentos na data X
        String dataBusca = "01-01-2026";
        System.out.println("Buscando atendimentos na data: " + dataBusca);
        Query q1 = manager.query();
        q1.constrain(Atendimento.class);
        q1.descend("data").constrain(dataBusca);
        List<Atendimento> res1 = q1.execute();

        for (Atendimento a : res1) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        //quais os atendimentos que possuem paciente de cpf X
        String cpfBusca = "15654836502";
        System.out.println("Buscando atendimentos do paciente com CPF: " + cpfBusca);
        Query q2 = manager.query();
        q2.constrain(Atendimento.class);
        q2.descend("paciente").descend("cpf").constrain(cpfBusca);
        List<Atendimento> res2 = q2.execute();

        for (Atendimento a : res2) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        //quais os pacientes que tem mais de N atendimentos na upa X
        int minAtendimentos = 1;
        String nomeUpaBusca = "Bessa";
        System.out.println("Pacientes com mais de " + minAtendimentos + " atendimentos na UPA: " + nomeUpaBusca);

        List<Paciente> res3 = manager.query(new Predicate<Paciente>() {
            public boolean match(Paciente p) {
                int cont = 0;
                for (Atendimento a : p.getAtendimentos()) {
                    //verifica se o atendimento atual foi na UPA desejada
                    if (a.getUpa().getNome().equalsIgnoreCase(nomeUpaBusca)) {
                        cont++;
                    }
                }
                //se a contagem for maior que o limite estipulado, retorna true (inclui no resultado)
                return cont > minAtendimentos;
            }
        });

        for (Paciente p : res3) {
            System.out.println(p);
        }

        System.out.println("\n=======================================================");
        Util.desconectarBanco();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}
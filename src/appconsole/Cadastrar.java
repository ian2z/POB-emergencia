package appconsole;

import com.db4o.ObjectContainer;

import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import util.Util;

public class Cadastrar {
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
    private ObjectContainer manager;

        public Cadastrar(){
            Util.conectar();
            manager = Util.getManager();

            System.out.println("cadastrando...");

            Paciente ian,davi,arthur,caio,igor,miguel;
            ian = new Paciente("777.777.777-77","Ian");
            davi = new Paciente("888.888.888-88", "Davi");
            arthur = new Paciente("999.999.999-99", "Arthur");
            caio = new Paciente("111.111.111-11", "Caio");
            igor = new Paciente("222.222.222-22", "Igor");
            miguel = new Paciente("333.333.333-33", "Miguel");

            Upa upaJaguaribe;
            upaJaguaribe = new Upa("UPA Jaguaribe");

            Atendimento atendimento1, atendimento2,atendimento3, atendimento4, atendimento5, atendimento6, atendimento7, atendimento8;
            atendimento1 = new Atendimento(1,"27/03/2026", ian, upaJaguaribe);
            atendimento2 = new Atendimento(2, "28/03/2026", davi, upaJaguaribe);
            atendimento3 = new Atendimento(3, "29/03/2026", arthur, upaJaguaribe);
            atendimento4 = new Atendimento(4, "30/03/2026", ian, upaJaguaribe);
            atendimento5 = new Atendimento(5,"01/03/2026", davi, upaJaguaribe);
            atendimento6 = new Atendimento(6, "02/03/2026", caio, upaJaguaribe);
            atendimento7 = new Atendimento(7, "03/03/2026", igor, upaJaguaribe);
            atendimento8 = new Atendimento(8, "04/03/2026", miguel, upaJaguaribe);

            ian.adicionar(atendimento1);
            ian.adicionar(atendimento4);
            manager.store(ian);
            manager.commit();

            davi.adicionar(atendimento2);
            davi.adicionar(atendimento5);
            manager.store(davi);
            manager.commit();

            arthur.adicionar(atendimento3);
            manager.store(arthur);
            manager.commit();

            caio.adicionar(atendimento6);
            manager.store(caio);
            manager.commit();

            igor.adicionar(atendimento7);
            manager.store(igor);
            manager.commit();

            miguel.adicionar(atendimento8);
            manager.store(miguel);
            manager.commit();

            upaJaguaribe.adicionar(atendimento1);
            upaJaguaribe.adicionar(atendimento2);
            upaJaguaribe.adicionar(atendimento3);
            upaJaguaribe.adicionar(atendimento4);
            upaJaguaribe.adicionar(atendimento5);
            upaJaguaribe.adicionar(atendimento6);
            upaJaguaribe.adicionar(atendimento7);
            upaJaguaribe.adicionar(atendimento8);
            manager.store(upaJaguaribe);
            manager.commit();

            System.out.println("cadastrou.");
            Util.desconectar();
            System.out.println("fim da appconsole");
        }

        //=================================================
        public static void main(String[] args) {
            new Cadastrar();
        }
}


package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import util.Util;

public class Listar {

    public Listar(){
        Util.conectarBanco();
        ObjectContainer manager = Util.();

        Query q;

        System.out.println("\n---listagem de pacientes:");
        q = manager.query();
        q.constrain(Paciente.class);
        List<Atendimento> atendimentos = q.execute();
        for(Atendimento a: atendimentos){
            System.out.println(a);
        }


        System.out.println("\n---listagem de motores:");
        q = manager.query();
        q.constrain(Motor.class);
        List<Motor> motores = q.execute();
        for(Motor m: motores){
            System.out.println(m);
        }


        System.out.println("\n---listagem de motoristas:");
        q = manager.query();
        q.constrain(Motorista.class);
        List<Motorista> motoristas = q.execute();
        for(Motorista m: motoristas){
            System.out.println(m);
        }
        Util.desconectar();

        System.out.println("\n aviso: feche sempre o plugin OME antes de executar aplicação");
    }



    //=================================================
    public static void main(String[] args) {
        new Listar();
    }
}


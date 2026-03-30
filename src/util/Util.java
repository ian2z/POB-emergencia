package util;

import java.util.Properties;
import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.config.EmbeddedConfiguration;

// IMPORTANTE: Importe suas classes de modelo
import com.db4o.cs.config.ClientConfiguration;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;

public class Util {
    private static ObjectContainer manager;
    private static String ipservidor;

    public static ObjectContainer conectar() {
        if (manager != null)
            return manager;

        try {
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            // Paciente
            config.common().objectClass(Paciente.class).cascadeOnUpdate(true);
            config.common().objectClass(Paciente.class).cascadeOnActivate(true);
            config.common().objectClass(Paciente.class).cascadeOnDelete(false);

            // Upa
            config.common().objectClass(Upa.class).cascadeOnUpdate(true);
            config.common().objectClass(Upa.class).cascadeOnActivate(true);
            config.common().objectClass(Upa.class).cascadeOnDelete(false);

            // Atendimento
            config.common().objectClass(Atendimento.class).cascadeOnUpdate(true);
            config.common().objectClass(Atendimento.class).cascadeOnActivate(true);
            config.common().objectClass(Atendimento.class).cascadeOnDelete(false);

            // Abre o arquivo local
            manager = Db4oEmbedded.openFile(config, "banco.db4o");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir banco: " + e.getMessage());
            System.exit(0);
        }
        return manager;
    }

    public static void desconectarBanco() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }

    private static void conectarBancoRemoto() {
        if (manager != null)
            return ; // ja tem uma conexao

        // ---------------------------------------
        // configurar e conectar banco remoto
        // ---------------------------------------
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().messageLevel(0); // 0,1,2,3...

        // Paciente
        config.common().objectClass(Paciente.class).cascadeOnUpdate(true);
        config.common().objectClass(Paciente.class).cascadeOnActivate(true);
        config.common().objectClass(Paciente.class).cascadeOnDelete(false);

        // Upa
        config.common().objectClass(Upa.class).cascadeOnUpdate(true);
        config.common().objectClass(Upa.class).cascadeOnActivate(true);
        config.common().objectClass(Upa.class).cascadeOnDelete(false);

        // Atendimento
        config.common().objectClass(Atendimento.class).cascadeOnUpdate(true);
        config.common().objectClass(Atendimento.class).cascadeOnActivate(true);
        config.common().objectClass(Atendimento.class).cascadeOnDelete(false);

        // **************************************
        // Conexão client-server
        // **************************************
        try {
            manager = Db4oClientServer.openClient((ClientConfiguration) config, ipservidor, 34000, "usuario1", "senha1");
            //System.out.println("conectado ao banco " + manager);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao conectar ao banco remoto ip=" + ipservidor + "\n" + e.getMessage());
            System.exit(0);
        }
    }

    public static ObjectContainer getManager() {
        return manager;
    }

    public static String getIPservidor() {
        return ipservidor;
    }
}
package appswing;

import requisito.FachadaAtendimento;
import requisito.FachadaPaciente;
import requisito.FachadaUpa;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelConsulta extends JPanel {

    private JComboBox<String> cbConsultas;
    private JLabel lblFiltro1;
    private JTextField txtFiltro1;
    private JLabel lblFiltro2;
    private JSpinner spinnerFiltro;
    private JButton btnConsultar;
    
    private JTable table;
    private DefaultTableModel tableModel;

    private FachadaPaciente fachadaPaciente = new FachadaPaciente();
    private FachadaAtendimento fachadaAtendimento = new FachadaAtendimento();
    private FachadaUpa fachadaUpa = new FachadaUpa();

    public PainelConsulta() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblTitle = new JLabel("Consultas Avançadas (JPA)");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.PRIMARY_COLOR);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Theme.BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Configuração da Consulta"));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel.setBackground(Theme.BACKGROUND_COLOR);
        comboPanel.add(new JLabel("Selecione a Consulta:"));
        
        String[] consultas = {
            "1) Atendimentos por Data",
            "2) Atendimentos por CPF do Paciente",
            "3) UPAs onde o Paciente foi Atendido",
            "4) Pacientes com mais de N Atendimentos em UPA",
            "5) Atendimentos por palavra-chave na Triagem",
            "6) Pacientes atendidos em Múltiplas UPAs",
            "7) Ranking de Lotação das UPAs"
        };
        cbConsultas = new JComboBox<>(consultas);
        cbConsultas.setFont(Theme.FONT_NORMAL);
        comboPanel.add(cbConsultas);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Theme.BACKGROUND_COLOR);

        lblFiltro1 = new JLabel("Data (DD-MM-AAAA):");
        lblFiltro1.setFont(Theme.FONT_NORMAL);
        txtFiltro1 = new JTextField(15);
        txtFiltro1.setFont(Theme.FONT_NORMAL);

        lblFiltro2 = new JLabel("Mín. Atendimentos:");
        lblFiltro2.setFont(Theme.FONT_NORMAL);
        spinnerFiltro = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
        spinnerFiltro.setFont(Theme.FONT_NORMAL);
        spinnerFiltro.setPreferredSize(new Dimension(60, 25));

        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(Theme.FONT_NORMAL);
        btnConsultar.setBackground(Theme.PRIMARY_COLOR);
        btnConsultar.setForeground(Color.WHITE);

        filterPanel.add(lblFiltro1);
        filterPanel.add(txtFiltro1);
        filterPanel.add(lblFiltro2);
        filterPanel.add(spinnerFiltro);
        filterPanel.add(btnConsultar);

        controlPanel.add(comboPanel);
        controlPanel.add(filterPanel);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Theme.BACKGROUND_COLOR);
        topContainer.add(lblTitle, BorderLayout.NORTH);
        topContainer.add(controlPanel, BorderLayout.CENTER);
        
        add(topContainer, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(Theme.FONT_NORMAL);
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set default filter fields visibility
        atualizarCamposFiltro();

        // Listeners
        cbConsultas.addActionListener(e -> atualizarCamposFiltro());
        btnConsultar.addActionListener(e -> executarConsulta());
    }

    private void atualizarCamposFiltro() {
        int index = cbConsultas.getSelectedIndex();
        // Reset visibility
        lblFiltro1.setVisible(false);
        txtFiltro1.setVisible(false);
        lblFiltro2.setVisible(false);
        spinnerFiltro.setVisible(false);

        if (index == 0) { // Atendimentos por Data
            lblFiltro1.setText("Data (DD-MM-AAAA):");
            lblFiltro1.setVisible(true);
            txtFiltro1.setVisible(true);
            txtFiltro1.setText("29-03-2026");
        } else if (index == 1) { // Atendimentos por CPF
            lblFiltro1.setText("CPF do Paciente:");
            lblFiltro1.setVisible(true);
            txtFiltro1.setVisible(true);
            txtFiltro1.setText("22222222222");
        } else if (index == 2) { // UPAs por CPF
            lblFiltro1.setText("CPF do Paciente:");
            lblFiltro1.setVisible(true);
            txtFiltro1.setVisible(true);
            txtFiltro1.setText("22222222222");
        } else if (index == 3) { // Pacientes com mais de N em UPA
            lblFiltro1.setText("Nome da UPA:");
            lblFiltro1.setVisible(true);
            txtFiltro1.setVisible(true);
            txtFiltro1.setText("Bessa");
            
            lblFiltro2.setText("Mínimo Atendimentos:");
            lblFiltro2.setVisible(true);
            spinnerFiltro.setVisible(true);
            spinnerFiltro.setValue(1);
        } else if (index == 4) { // Atendimentos por palavra-chave
            lblFiltro1.setText("Palavra-chave:");
            lblFiltro1.setVisible(true);
            txtFiltro1.setVisible(true);
            txtFiltro1.setText("fratura");
        }
        // index 5 (Múltiplas UPAs) and index 6 (Ranking) don't have inputs
        
        revalidate();
        repaint();
    }

    private void executarConsulta() {
        tableModel.setRowCount(0);
        int index = cbConsultas.getSelectedIndex();
        String param1 = txtFiltro1.getText().trim();

        try {
            if (index == 0) { // Atendimentos por Data
                if (param1.isEmpty()) throw new Exception("Digite a data para a busca.");
                tableModel.setColumnIdentifiers(new String[]{"ID", "Data", "Triagem", "Paciente (CPF)", "Paciente (Nome)", "UPA"});
                
                List<Atendimento> atendimentos = fachadaAtendimento.consultarAtendimentosPorData(param1);
                if (atendimentos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum atendimento encontrado para esta data.");
                }
                for (Atendimento a : atendimentos) {
                    tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getData(),
                        a.getTriagem(),
                        a.getPaciente().getCpf(),
                        a.getPaciente().getNome(),
                        a.getUpa().getNome()
                    });
                }
            } 
            else if (index == 1) { // Atendimentos por CPF
                if (param1.isEmpty()) throw new Exception("Digite o CPF do paciente.");
                tableModel.setColumnIdentifiers(new String[]{"ID", "Data", "Triagem", "Paciente (CPF)", "Paciente (Nome)", "UPA"});
                
                List<Atendimento> atendimentos = fachadaAtendimento.consultarAtendimentosPorCpfPaciente(param1);
                if (atendimentos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum atendimento encontrado para este CPF.");
                }
                for (Atendimento a : atendimentos) {
                    tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getData(),
                        a.getTriagem(),
                        a.getPaciente().getCpf(),
                        a.getPaciente().getNome(),
                        a.getUpa().getNome()
                    });
                }
            } 
            else if (index == 2) { // UPAs por CPF
                if (param1.isEmpty()) throw new Exception("Digite o CPF do paciente.");
                tableModel.setColumnIdentifiers(new String[]{"ID", "Nome da UPA"});
                
                List<Upa> upas = fachadaUpa.consultarUpasPorCpfPaciente(param1);
                if (upas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhuma UPA encontrada para este CPF.");
                }
                for (Upa u : upas) {
                    tableModel.addRow(new Object[]{u.getId(), u.getNome()});
                }
            } 
            else if (index == 3) { // Pacientes com mais de N em UPA
                if (param1.isEmpty()) throw new Exception("Digite o nome da UPA.");
                int limite = (int) spinnerFiltro.getValue();
                tableModel.setColumnIdentifiers(new String[]{"CPF", "Nome"});
                
                List<Paciente> pacientes = fachadaPaciente.consultarPacientesComMaisAtendimentos(param1, (long) limite);
                if (pacientes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum paciente atendeu a este critério.");
                }
                for (Paciente p : pacientes) {
                    tableModel.addRow(new Object[]{p.getCpf(), p.getNome()});
                }
            } 
            else if (index == 4) { // Atendimentos por palavra-chave
                if (param1.isEmpty()) throw new Exception("Digite a palavra-chave.");
                tableModel.setColumnIdentifiers(new String[]{"ID", "Data", "Triagem", "Paciente (Nome)", "UPA"});
                
                List<Atendimento> atendimentos = fachadaAtendimento.consultarAtendimentosPorTriagem(param1);
                if (atendimentos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum atendimento contendo o termo '" + param1 + "'.");
                }
                for (Atendimento a : atendimentos) {
                    tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getData(),
                        a.getTriagem(),
                        a.getPaciente().getNome(),
                        a.getUpa().getNome()
                    });
                }
            } 
            else if (index == 5) { // Pacientes em múltiplas UPAs
                tableModel.setColumnIdentifiers(new String[]{"CPF", "Nome"});
                
                List<Paciente> pacientes = fachadaPaciente.consultarPacientesMultiplasUpas();
                if (pacientes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum paciente frequentou múltiplas UPAs.");
                }
                for (Paciente p : pacientes) {
                    tableModel.addRow(new Object[]{p.getCpf(), p.getNome()});
                }
            } 
            else if (index == 6) { // Ranking de Lotação
                tableModel.setColumnIdentifiers(new String[]{"Posição", "Nome da UPA", "Total Atendimentos"});
                
                List<Object[]> ranking = fachadaUpa.obterRankingLotacao();
                if (ranking.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhuma UPA cadastrada.");
                }
                int pos = 1;
                for (Object[] linha : ranking) {
                    tableModel.addRow(new Object[]{
                        pos++,
                        linha[0],
                        linha[1]
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro na Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }
}

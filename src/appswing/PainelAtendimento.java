package appswing;

import fachada.FachadaAtendimento;
import modelo.Atendimento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelAtendimento extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private FachadaAtendimento fachada = new FachadaAtendimento();

    public PainelAtendimento() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblTitle = new JLabel("Gerenciar Atendimentos");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Data", "Triagem", "Paciente", "UPA"};
        tableModel = new DefaultTableModel(columns, 0) {
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

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Theme.BACKGROUND_COLOR);

        JButton btnNovo = new JButton("Novo Atendimento");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        btnNovo.setFont(Theme.FONT_NORMAL);
        btnExcluir.setFont(Theme.FONT_NORMAL);
        btnAtualizar.setFont(Theme.FONT_NORMAL);

        btnPanel.add(btnAtualizar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnNovo);

        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        btnAtualizar.addActionListener(e -> carregarAtendimentos());
        
        btnNovo.addActionListener(e -> {
            JTextField txtData = new JTextField("DD-MM-YYYY");
            String[] triagens = {"Azul", "Verde", "Amarelo", "Laranja", "Vermelho"};
            JComboBox<String> cbTriagem = new JComboBox<>(triagens);
            JTextField txtCpf = new JTextField();
            JTextField txtUpa = new JTextField();
            
            Object[] message = {
                "Data (dd-MM-yyyy):", txtData,
                "Triagem:", cbTriagem,
                "CPF do Paciente:", txtCpf,
                "Nome da UPA:", txtUpa
            };
            
            int option = JOptionPane.showConfirmDialog(this, message, "Novo Atendimento", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String data = txtData.getText();
                    String triagem = (String) cbTriagem.getSelectedItem();
                    String cpf = txtCpf.getText();
                    String upa = txtUpa.getText();
                    
                    fachada.cadastrarAtendimento(data, triagem, cpf, upa);
                    JOptionPane.showMessageDialog(this, "Atendimento cadastrado com sucesso!");
                    carregarAtendimentos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (Integer) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o atendimento ID " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        fachada.excluirAtendimento(id);
                        carregarAtendimentos();
                        JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um atendimento para excluir.");
            }
        });

        carregarAtendimentos();
    }

    private void carregarAtendimentos() {
        tableModel.setRowCount(0);
        try {
            List<Atendimento> atendimentos = fachada.listarAtendimentos();
            for (Atendimento a : atendimentos) {
                String nomePaciente = (a.getPaciente() != null) ? a.getPaciente().getNome() : "Desconhecido";
                String nomeUpa = (a.getUpa() != null) ? a.getUpa().getNome() : "Desconhecida";
                
                tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getData(),
                    a.getTriagem(),
                    nomePaciente,
                    nomeUpa
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

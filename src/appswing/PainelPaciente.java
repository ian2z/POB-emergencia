package appswing;

import fachada.FachadaPaciente;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PainelPaciente extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private FachadaPaciente fachada = new FachadaPaciente();

    public PainelPaciente() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblTitle = new JLabel("Gerenciar Pacientes");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"CPF", "Nome"};
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

        JButton btnNovo = new JButton("Novo Paciente");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        btnNovo.setFont(Theme.FONT_NORMAL);
        btnEditar.setFont(Theme.FONT_NORMAL);
        btnExcluir.setFont(Theme.FONT_NORMAL);
        btnAtualizar.setFont(Theme.FONT_NORMAL);

        btnPanel.add(btnAtualizar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnEditar);
        btnPanel.add(btnNovo);

        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        btnAtualizar.addActionListener(e -> carregarPacientes());
        
        btnNovo.addActionListener(e -> {
            JTextField txtCpf = new JTextField();
            JTextField txtNome = new JTextField();
            JButton btnFoto = new JButton("Escolher Foto...");
            JLabel lblFoto = new JLabel("Nenhuma foto selecionada");
            
            final byte[][] fotoBytes = {null};

            btnFoto.addActionListener(ev -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        fotoBytes[0] = java.nio.file.Files.readAllBytes(file.toPath());
                        lblFoto.setText(file.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao ler foto: " + ex.getMessage());
                    }
                }
            });

            Object[] message = {
                "CPF:", txtCpf,
                "Nome:", txtNome,
                "Foto:", btnFoto, lblFoto
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Novo Paciente", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    fachada.cadastrarPaciente(txtCpf.getText(), txtNome.getText(), fotoBytes[0]);
                    JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
                    carregarPacientes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String cpf = (String) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o paciente " + cpf + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        fachada.excluirPaciente(cpf);
                        carregarPacientes();
                        JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
            }
        });
        
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String cpf = (String) tableModel.getValueAt(selectedRow, 0);
                String nomeAtual = (String) tableModel.getValueAt(selectedRow, 1);
                
                JTextField txtNome = new JTextField(nomeAtual);
                JButton btnFoto = new JButton("Escolher Nova Foto...");
                JLabel lblFoto = new JLabel("Manter foto atual");
                
                final byte[][] fotoBytes = {null};

                btnFoto.addActionListener(ev -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
                    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            fotoBytes[0] = java.nio.file.Files.readAllBytes(file.toPath());
                            lblFoto.setText(file.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Erro ao ler foto: " + ex.getMessage());
                        }
                    }
                });

                Object[] message = {
                    "CPF: " + cpf,
                    "Novo Nome:", txtNome,
                    "Atualizar Foto:", btnFoto, lblFoto
                };
                int option = JOptionPane.showConfirmDialog(this, message, "Editar Paciente", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        fachada.atualizarPaciente(cpf, txtNome.getText(), fotoBytes[0]);
                        JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                        carregarPacientes();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para editar.");
            }
        });

        carregarPacientes();
    }

    private void carregarPacientes() {
        tableModel.setRowCount(0);
        try {
            List<Paciente> pacientes = fachada.listarPacientes();
            for (Paciente p : pacientes) {
                tableModel.addRow(new Object[]{p.getCpf(), p.getNome()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

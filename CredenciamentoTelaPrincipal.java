
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.List;

import java.util.Formatter;

import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JOptionPane;

import javax.swing.BoxLayout;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class CredenciamentoTelaPrincipal extends JFrame{

	private JTabbedPane colTabs;

	private Participante currentEntry;
	private ParticipanteQueries participanteQueries;
	private List<Participante> Participantes;
        private List<Participante> listPresentes = null;
	private int numberOfEntries = 0;
	private int currentEntryIndex;
	
	private JButton btnNavega;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblNome;
	private JLabel lblEmpresa;
	private JTextField txtNome;
	//private JTextArea txtNome;
	private JTextField txtEmpresa;
	private JLabel lblID;
	private JTextField txtID;
	private JTextField txtIndice;
	//private JTextField txtMax;
	private JButton btnProximo;
	private JLabel lblDe;
	private JLabel lblConsulta;
	private JLabel lblCategoria;	
		
	//private JButton btnAnterior;
	private JButton btnConsulta;
	
	private JPanel panelConsulta;
	private JPanel navigatePanel;
	private JPanel displayPanel;
	private JTextField txtConsulta;
	private JButton btnSalvar;
	
	private JButton btnDummy;
	private JButton btnNome;
	private JButton btnEmpresa;
	private JButton btnEmail;
	private JButton btnCategoria;
	
	private JPanel panelCadastro;
	private JPanel panelEstatisticas;
	
	/////////////////////////////////////////////
	private JPanel panelExportacao;
	private JPanel panelImportacao;
	private JPanel panelImpressora;
	private JTable tabelaBusca;
	
	private JButton btnExportacao;
	private JButton btnNovo;
	private JTextField txtPathExportacao;	
	
	private JLabel lblFone;
	private JTextField txtFone;
	private JButton btnFone;
	
	private JLabel lblPresente;
	private JCheckBox chkPresente;
        
        private JLabel lblAtivarImpressao;
	private JCheckBox chkAtivarImpressao;
	
	private JLabel lblTodosCadastrados_Estatico;
	private JLabel lblTodosCadastrados_Dinamico;
	
	private JLabel lblTodosPresentes_Estatico;
	private JLabel lblTodosPresentes_Dinamico;	
	
	/*
	private JLabel lblLocalTrabalho;
	private JTextField txtLocalTrabalho;
	private JButton btnLocalTrabalho;		
	
	private JLabel lblSupervisaoSaude;
	private JTextField txtSupervisaoSaude;
	private JButton btnSupervisaoSaude;		
	*/
	private ListSelectionModel listSelectionModel;
	
	private JComboBox cmbImpressora;
	private static final String[] Impressoras = {"Padrao", "Brother QL-570", "Brother QL-550", "Brother QL-5702"};
	
	private JComboBox cmbCategoria;
	private static final String[] Categorias = {"MEDICO", "ENFERMEIRO", "OUTRO"};
	
	public CredenciamentoTelaPrincipal(){
	
		super("Dragon Eventos - Credenciamento");
		
		try {		
			participanteQueries = new ParticipanteQueries();
		}
		catch (ClassNotFoundException classNotFoundException){
			System.err.printf("\nExcecao:%s\n", classNotFoundException);
			System.exit(0);
		}
		
		colTabs = new JTabbedPane();	
				
		/////////////////////////////////////////////////////////////
		//
		// TAB BUSCA
		//
		//////////////////////////////////////////////////////////////
		panelImportacao = new JPanel();
		JPanel panelEspacoTopo = new JPanel();
		
		panelConsulta = new JPanel();
		lblConsulta = new JLabel();
		txtConsulta = new JTextField(30);
		btnConsulta = new JButton();
		panelEstatisticas = new JPanel();
		lblTodosCadastrados_Estatico = new JLabel("Cadastrados:");
		lblTodosCadastrados_Dinamico = new JLabel();
		lblTodosPresentes_Estatico = new JLabel("Presentes:");
		lblTodosPresentes_Dinamico = new JLabel();
				
		panelConsulta.setLayout(new BoxLayout(panelConsulta, BoxLayout.X_AXIS));
		
		//panelConsulta.setBorder(BorderFactory.createTitledBorder("Busca por nome"));
		
		//panelConsulta.setText("Nome:");
		panelEspacoTopo.add(Box.createVerticalStrut(10));
		panelImportacao.add(panelEspacoTopo);
		
		panelConsulta.add(Box.createHorizontalStrut(150));
		panelConsulta.add(lblConsulta);
		panelConsulta.add(Box.createHorizontalStrut(10));
                
                //************************************************************
                // Adding key binding (tecla ENTER) ao campo Busca
                // Works on MAC OS X
                //************************************************************
                txtConsulta.addActionListener(
			new ActionListener(){
                                @Override
				public void actionPerformed(ActionEvent evt){
					btnConsultaActionPerformed(evt);			
				}		
			}		
		);
                panelConsulta.add(txtConsulta);
                
		panelConsulta.add(Box.createHorizontalStrut(10));
		
		btnConsulta.setText("Buscar");
		btnConsulta.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					btnConsultaActionPerformed(evt);			
				}		
			}		
		);
		panelConsulta.add(btnConsulta);
		panelConsulta.add(Box.createHorizontalStrut(200));
		panelImportacao.add(panelConsulta);
		
		//panelEspacoIntermedio1.add(Box.createVerticalStrut(10));
		//panelImportacao.add(panelEspacoIntermedio1);

		/////////////////////////////////////////////////////////////////////////////////////////
		// Tabela com resultados da busca
		/////////////////////////////////////////////////////////////////////////////////////////
		tabelaBusca = new JTable();
		
		listSelectionModel = tabelaBusca.getSelectionModel();
		listSelectionModel.addListSelectionListener(new RowListener());
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaBusca.setSelectionModel(listSelectionModel);
		
		
		JScrollPane scrollPaneTabelaBusca = new JScrollPane(tabelaBusca);
		
		panelImportacao.add(scrollPaneTabelaBusca);
		
		
		scrollPaneTabelaBusca.setVisible(true);	
		
		panelImportacao.add(Box.createHorizontalStrut(400));
		
		panelEstatisticas.setLayout(new GridLayout(1, 4, 10, 10));
		
		panelEstatisticas.add(lblTodosCadastrados_Estatico);
		panelEstatisticas.add(lblTodosCadastrados_Dinamico);
		panelEstatisticas.add(lblTodosPresentes_Estatico);
		panelEstatisticas.add(lblTodosPresentes_Dinamico);		
		
		panelImportacao.add(panelEstatisticas);
		
		colTabs.addTab("Busca", null, panelImportacao, "");
		
		
		/////////////////////////////////////////////////////////////////////////////////////
		// 
		// TAB CADASTRO
		// 
		/////////////////////////////////////////////////////////////////////////////////////		
		panelCadastro = new JPanel();
		
		navigatePanel = new JPanel();
		//btnAnterior = new JButton();
		txtIndice = new JTextField(2);
		lblDe = new JLabel();
		//txtMax = new JTextField(2);
		btnProximo = new JButton();
		
		displayPanel = new JPanel();
		
		lblID = new JLabel("ID:");
		txtID = new JTextField(10);
		btnDummy = new JButton("");
		
		lblNome = new JLabel("Nome:");
		txtNome = new JTextField(20);
		btnNome = new JButton("Editar");
		
		lblEmpresa = new JLabel("Empresa:");
		txtEmpresa = new JTextField(20);
		btnEmpresa = new JButton("Editar");
		
		lblFone = new JLabel("Fone:");
		txtFone = new JTextField(10);
		btnFone = new JButton("Editar");
		
		lblEmail = new JLabel("Email:");
		txtEmail = new JTextField(20);
		btnEmail = new JButton("Editar");	

		lblPresente = new JLabel("Presente:");
		chkPresente = new JCheckBox();
		
		/*
		lblCategoria = new JLabel("Categoria:");
		cmbCategoria = new JComboBox(Categorias);
		btnCategoria = new JButton("Editar");
		
		lblLocalTrabalho = new JLabel("Local de trabalho:");
		txtLocalTrabalho = new JTextField(20);
		btnLocalTrabalho = new JButton("Editar");		
		
		lblSupervisaoSaude = new JLabel("Supervisao de Saude:");
		txtSupervisaoSaude = new JTextField(20);
		btnSupervisaoSaude = new JButton("Editar");		
		*/
		
		
		
		btnNavega = new JButton();
		btnSalvar = new JButton();
		btnNovo = new JButton();
		
		panelCadastro.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		setSize(800, 600);
		setResizable(true);
		
		/////////////////////////////////////////////////////////////////////////////////////
		// Display Panel
		/////////////////////////////////////////////////////////////////////////////////////
		displayPanel.setLayout(new GridLayout(6, 3, 10, 10));
		
		//lblID.setText("ID:");
		displayPanel.add(lblID);
		txtID.setEditable(false);
		displayPanel.add(txtID);
		displayPanel.add(btnDummy);
		btnDummy.setVisible(false);
				
		//Nome
		displayPanel.add(lblNome);				
		displayPanel.add(txtNome);
		btnNome.setVisible(false);
		btnNome.addActionListener(
			new ActionListener(){
                                @Override
				public void actionPerformed(ActionEvent evt){
					txtNome.setEditable(true);
					txtEmpresa.setEditable(false);
					txtEmail.setEditable(false);
					//cmbCategoria.setEnabled(false);
					txtFone.setEditable(false);
					//txtLocalTrabalho.setEditable(false);
					//txtSupervisaoSaude.setEditable(false);				
				}		
			}		
		);
		displayPanel.add(btnNome);
			
		
		//Empresa
		displayPanel.add(lblEmpresa);
		txtEmpresa.setText("");
		displayPanel.add(txtEmpresa);
		btnEmpresa.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					txtEmpresa.setEditable(true);
					txtNome.setEditable(false);
					txtEmail.setEditable(false);
					//cmbCategoria.setEnabled(false);
					txtFone.setEditable(false);
					//txtLocalTrabalho.setEditable(false);
					//txtSupervisaoSaude.setEditable(false);
				}		
			}		
		);
		btnEmpresa.setVisible(false);
		displayPanel.add(btnEmpresa);
		
		
		//Telefone		
		displayPanel.add(lblFone);				
		displayPanel.add(txtFone);
		btnFone.setVisible(false);
		btnFone.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					txtEmail.setEditable(false);
					txtNome.setEditable(false);
					txtEmpresa.setEditable(false);
					//cmbCategoria.setEnabled(false);
					txtFone.setEditable(true);
					//txtLocalTrabalho.setEditable(false);
					//txtSupervisaoSaude.setEditable(false);
				}		
			}		
		);
		displayPanel.add(btnFone);
		
			
		//Email		
		displayPanel.add(lblEmail);				
		displayPanel.add(txtEmail);
		btnEmail.setVisible(false);
		btnEmail.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					txtEmail.setEditable(true);
					txtNome.setEditable(false);
					txtEmpresa.setEditable(false);
					//cmbCategoria.setEnabled(false);
					txtFone.setEditable(false);
					//txtLocalTrabalho.setEditable(false);
					//txtSupervisaoSaude.setEditable(false);
				}		
			}		
		);
		displayPanel.add(btnEmail);
		
		//Presenca
		displayPanel.add(lblPresente);				
		displayPanel.add(chkPresente);
				
		panelCadastro.add(displayPanel);	
		
		/////////////////////////////////////////////////////////////////////////////////////
		// Buttons
		/////////////////////////////////////////////////////////////////////////////////////
		btnNovo.setText("Novo");
		btnNovo.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					btnNovoActionPerformed(evt);					
				}		
			}		
		);
		btnNovo.setVisible(false);
		panelCadastro.add(btnNovo);
		
		btnSalvar.setText("Salvar");
		btnSalvar.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					btnSalvarActionPerformed(evt);
				}		
			}		
		);
		panelCadastro.add(btnSalvar);
		
		panelCadastro.setVisible(true);		
		
		colTabs.addTab("Cadastro", null, panelCadastro, "");
		
		
		
		
		
		
		
		
		
		
		
		
		
		/////////////////////////////////////////////////////////////
		//
		// TAB EXPORTACAO
		//
		//////////////////////////////////////////////////////////////
		panelExportacao = new JPanel();
		btnExportacao = new JButton();
		txtPathExportacao = new JTextField("C:", 30);
		
		panelExportacao.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		
		
		panelExportacao.add(txtPathExportacao);
		
		btnExportacao.setText("Exportar no formato CSV");
		
		btnExportacao.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					btnExportacaoActionPerformed(evt);			
				}		
			}		
		);
		
		panelExportacao.add(btnExportacao);
		
		colTabs.addTab("Exportacao", null, panelExportacao, "");
		
		/////////////////////////////////////////////////////////////
		//
		// TAB IMPRESSORA
		//
		//////////////////////////////////////////////////////////////
                JPanel panelImpressora = new JPanel();
		JPanel panelImpressoraLinha1 = new JPanel();
                JPanel panelImpressoraLinha2 = new JPanel();
               
                chkAtivarImpressao = new JCheckBox();
                chkAtivarImpressao.setSelected(true);
                chkAtivarImpressao.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					
                                    if (chkAtivarImpressao.isSelected()){
                                        cmbImpressora.setEnabled(true);
                                    }
                                    else {
                                        cmbImpressora.setEnabled(false);
                                    }
					
				}		
			}		
		);
                
                lblAtivarImpressao = new JLabel("Ativar impressao");
		
                JLabel lblImpressora = new JLabel("Impressora:");
		cmbImpressora = new JComboBox(Impressoras);
 
		panelImpressoraLinha1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                panelImpressoraLinha2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                
                panelImpressoraLinha1.add(chkAtivarImpressao);
                panelImpressoraLinha1.add(lblAtivarImpressao);
                
                panelImpressora.add(panelImpressoraLinha1);
                
                panelImpressoraLinha2.add(lblImpressora);
		panelImpressoraLinha2.add(cmbImpressora);
                
                panelImpressora.add(panelImpressoraLinha2);
                
		colTabs.addTab("Impressora", null, panelImpressora, "");
		
		/////////////////////////////////////////////////////////////
		//
		// 
		//
		//////////////////////////////////////////////////////////////
		add(colTabs);
		txtNome.requestFocusInWindow();
		
	}
	
	private void btnNovoActionPerformed(ActionEvent evt){
	
		setEstadoNovoParticipante();			
	
	}
	
	private void btnExportacaoActionPerformed(ActionEvent evt){
	
		String path = txtPathExportacao.getText();
		Formatter output;
	
		if (path.length() == 0){
			JOptionPane.showMessageDialog(null, "Caminho de destino do CSV em branco", "Erro", JOptionPane.PLAIN_MESSAGE);
			txtPathExportacao.requestFocusInWindow();
			return;
		}
		
		//path = path + "\\" + "Relatorio.csv";
		
		try{
			output = new Formatter(path);
		}
		catch(FileNotFoundException fileNotFoundException){
			JOptionPane.showMessageDialog(null, "Caminho invalido", "Erro", JOptionPane.PLAIN_MESSAGE);
			txtPathExportacao.requestFocusInWindow();
			return;
		}
		
		output.format("ID;NOME;POSTO;STATUS\r\n");
		
		listPresentes = participanteQueries.getTodosPresentes();
                
                if (listPresentes.size() > 0){
			
                    for (Participante p : listPresentes) {
                            output.format("%s;%s;%s;%s\r\n", p.getID(), p.getNome(), p.getEmpresa(), p.getFone());
                    }

                    if (output != null) output.close();

                    JOptionPane.showMessageDialog(null, "Arquivo de presentes" + path + " gerado com sucesso", "Atencao", JOptionPane.PLAIN_MESSAGE);
                
                }
                else
                    
                    JOptionPane.showMessageDialog(null, "Nao ha nenhum presente, portanto nao se pode exportar" , "Atencao", JOptionPane.PLAIN_MESSAGE);
                
	}
		
	private void btnConsultaActionPerformed(ActionEvent evt){
	
		Participantes = participanteQueries.getParticipantesPorNome(txtConsulta.getText());
		
		numberOfEntries = Participantes.size();
		
		DefaultTableModel aModel = new DefaultTableModel(){
				
			@Override
			public boolean isCellEditable(int row, int column){
				return (false);
			}
			
		};
		
		if (numberOfEntries > 0){
			
			Object[] tabelaNomesColunas = new Object[6];
			tabelaNomesColunas[0] = "ID";
			tabelaNomesColunas[1] = "Nome";
			tabelaNomesColunas[2] = "Empresa";
			tabelaNomesColunas[3] = "Fone";
			tabelaNomesColunas[4] = "Email";
			tabelaNomesColunas[5] = "Presente";
			//tabelaNomesColunas[6] = "";
			//tabelaNomesColunas[7] = "";
			
			aModel.setColumnIdentifiers(tabelaNomesColunas);		
					
			int i = 0;
			Object[] objects = new Object[6];
			for (Participante p : Participantes){
				
				objects[0] = (p.getID() + " ");
				objects[1] = p.getNome();
				objects[2] = p.getEmpresa();
				objects[3] = p.getFone();
				objects[4] = p.getEmail();
				objects[5] = ( ( p.getPresente() == 1) ? "X" : "");
				
				/*
				objects[6] = p.getLocalTrabalho();
				objects[7] = p.getSupervisaoSaude();			
				*/
				
				aModel.addRow(objects);			
				
			}
			
			tabelaBusca.setModel(aModel);
			tabelaBusca.setVisible(true);
			lblTodosCadastrados_Dinamico.setVisible(true);
			lblTodosPresentes_Dinamico.setVisible(true);
			
			lblTodosCadastrados_Dinamico.setText(participanteQueries.contaTodosCadastrados() + "");
			lblTodosPresentes_Dinamico.setText(participanteQueries.contaTodosPresentes() + "");
			
		}
		else{
			tabelaBusca.setModel(aModel);
			JOptionPane.showMessageDialog(null, "Nada encontrado", "Atencao", JOptionPane.PLAIN_MESSAGE);
		}
	
	}
		
	private void btnSalvarActionPerformed(ActionEvent evt){
	
		String strID = txtID.getText().trim();
		String nome = txtNome.getText();
		String empresa = txtEmpresa.getText();
		String email = txtEmail.getText();
		//String categoria = (String)cmbCategoria.getSelectedItem();
		String categoria = "Categoria hard code";
		String fone = txtFone.getText();
		int intPresente = chkPresente.isSelected() ? 1 : 0;
		/*
		String localTrabalho = txtLocalTrabalho.getText();
		String supervisaoSaude = txtSupervisaoSaude.getText();
		*/
		String nomeImpressora = (String)cmbImpressora.getSelectedItem();
		
		tabelaBusca.setVisible(false);
		lblTodosCadastrados_Dinamico.setVisible(false);
		lblTodosPresentes_Dinamico.setVisible(false);
	
		if (nome.length() == 0){
			JOptionPane.showMessageDialog(this, "Nome invalido", "Atencao", JOptionPane.PLAIN_MESSAGE);
			txtNome.requestFocusInWindow();
			return;	
		}
		
		if (empresa.length() == 0){
			JOptionPane.showMessageDialog(this, "Empresa invalida", "Atencao", JOptionPane.PLAIN_MESSAGE);
			txtEmpresa.requestFocusInWindow();
			return;
		}
		
		/*
		if (email.length() == 0){
			JOptionPane.showMessageDialog(this, "Email invalido", "Atencao", JOptionPane.PLAIN_MESSAGE);
			txtEmail.requestFocusInWindow();
			return;
		}
		*/		
		
		
		//strCmd = strCmd + nome.replace(' ', '?') + " " + nomeImpressora.replace(' ', '?');
		
		String strCmd = "wscript BcdLabel.vbs " + nome.replace(' ', '?') + " " + empresa.replace(' ', '?');		
		strCmd = strCmd + " " + nomeImpressora.replace(' ', '?');		
				
		if (strID.length() == 0){//Inserir
			int result = participanteQueries.insereParticipante(nome, empresa, email, null, fone, null, null, intPresente);						
			if (result == 0) JOptionPane.showMessageDialog(this, "Participante nao foi adicionado. Verifique se ele ja esta cadastrado.", "Erro", JOptionPane.PLAIN_MESSAGE);
			
			if (chkAtivarImpressao.isSelected()){
                        
                            try {
                                    Process p = Runtime.getRuntime().exec(strCmd);		
                            }
                            catch (IOException ioException){
                                    System.out.println("Impossivel executar o script BcdLabel.vbs");			
                            }
                        
                        }
			
		}
		else{//Atualizar
			int result = participanteQueries.atualizaParticipante(strID, nome, empresa, email, null, fone, null, null, intPresente);
			if (result == 0) JOptionPane.showMessageDialog(this, "Participante nao foi atualizado", "Erro", JOptionPane.PLAIN_MESSAGE);
			
                        if (chkAtivarImpressao.isSelected()){
                            Object arrOpcoes[] = { "Sim", "Nao" };
                            int resposta = JOptionPane.showOptionDialog(this, "Deseja imprimir ?", "Atencao", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, arrOpcoes, arrOpcoes[0]);	
                            if (resposta == JOptionPane.YES_OPTION){

                                    try {
                                            Process p = Runtime.getRuntime().exec(strCmd);		
                                    }
                                    catch (IOException ioException){
                                            System.out.println("Impossivel executar o script BcdLabel.vbs");			
                                    }

                            }
                        }
						
		}
		
		setEstadoNovoParticipante();
		colTabs.setSelectedIndex(0);
		
	}
	
	private void setEstadoNovoParticipante(){
	
		txtID.setText("");
		
		txtNome.setText("");
		txtNome.setEditable(true);
		btnNome.setVisible(false);
		
		txtEmpresa.setText("");
		txtEmpresa.setEditable(true);
		btnEmpresa.setVisible(false);
		
		txtFone.setText("");
		txtFone.setEditable(true);
		btnFone.setVisible(false);
		
		txtEmail.setText("");
		txtEmail.setEditable(true);
		btnEmail.setVisible(false);
		
		chkPresente.setSelected(false);
		
		/*
		cmbCategoria.setEnabled(true);
		cmbCategoria.setSelectedIndex(0);
		btnCategoria.setVisible(false);
		
		txtLocalTrabalho.setText("");
		txtLocalTrabalho.setEditable(true);
		btnLocalTrabalho.setVisible(false);
		
		txtSupervisaoSaude.setText("");
		txtSupervisaoSaude.setEditable(true);
		btnSupervisaoSaude.setVisible(false);
		*/
		
		btnNovo.setVisible(false);	
		txtNome.requestFocusInWindow();		
	
	}
	
	private class RowListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent event) {
		
            if (event.getValueIsAdjusting()) {
                return;
            }
			
			int linha = tabelaBusca.getSelectionModel().getLeadSelectionIndex();
			
			if (linha >= 0) {

				txtID.setText((String) tabelaBusca.getValueAt(linha, 0));
				txtNome.setText((String) tabelaBusca.getValueAt(linha, 1));
				txtEmpresa.setText((String) tabelaBusca.getValueAt(linha, 2));
				txtFone.setText((String) tabelaBusca.getValueAt(linha, 3));			
				//String categoriaSelecionada = (String) tabelaBusca.getValueAt(linha, 4);
				txtEmail.setText((String) tabelaBusca.getValueAt(linha, 4));
				boolean isPresente = ((String) tabelaBusca.getValueAt(linha, 5)).equals("X") ? true : false;
				chkPresente.setSelected(isPresente); 
				
				btnNome.setVisible(true);
				btnEmpresa.setVisible(true);
				btnEmail.setVisible(true);
				btnFone.setVisible(true);
				
				txtNome.setEditable(false);
				txtEmpresa.setEditable(false);
				txtEmail.setEditable(false);
				txtFone.setEditable(false);
				
				btnNovo.setVisible(true);
				
				colTabs.setSelectedIndex(1);
				
			}
        }
    }		
}
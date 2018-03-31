import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
//import org.sqlite.JDBC;

public class ParticipanteQueries {
        
        //*********************************************************************
        // For Command Line project
		private static final String URL = "jdbc:sqlite:./Credenciamento.db";
        //*********************************************************************
        
        //*********************************************************************
        // For Netbeans project
		//private static final String URL = "jdbc:sqlite:/Users/marcelloturnbull/Desktop/Credenciamento.db";
        //*********************************************************************
    
    
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	
	private Connection connection = null;
	private PreparedStatement selectTodosParticipantes = null;
        private PreparedStatement selectTodosPresentes = null;
	private PreparedStatement selectParticipantePorNome = null;
        private PreparedStatement verificaParticipanteCadastrado = null;
	private PreparedStatement insereNovoParticipante = null;
	private PreparedStatement updateParticipante = null;
	private PreparedStatement countTodosCadastrados = null;
	private PreparedStatement countTodosPresentes = null;
	
	public ParticipanteQueries() throws ClassNotFoundException{

		Class.forName("org.sqlite.JDBC");
	
		try{		
		
			connection = DriverManager.getConnection(URL);
			
			selectTodosParticipantes = connection.prepareStatement("SELECT ID, Nome, Empresa, Email, Categoria, Fone, LocalTrabalho, SupervisaoSaude, Presente FROM Participantes ORDER BY Nome, Empresa");
			
                        selectTodosPresentes = connection.prepareStatement("SELECT ID, Nome, Empresa, Email, Categoria, Fone, LocalTrabalho, SupervisaoSaude, Presente FROM Participantes WHERE Presente = 1 ORDER BY Nome, Empresa");

			selectParticipantePorNome = connection.prepareStatement("SELECT ID, Nome, Empresa, Email, Categoria, Fone, LocalTrabalho, SupervisaoSaude, Presente FROM Participantes WHERE Nome LIKE ? OR Empresa LIKE ? ORDER BY Nome, Empresa");

                        verificaParticipanteCadastrado = connection.prepareStatement("SELECT ID, Nome, Empresa, Email, Categoria, Fone, LocalTrabalho, SupervisaoSaude, Presente FROM Participantes WHERE Nome = ? AND Empresa = ? AND Fone = ?");
						
			insereNovoParticipante = connection.prepareStatement("INSERT INTO Participantes (Nome, Empresa, Email, Categoria, Fone, LocalTrabalho, SupervisaoSaude, Presente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			
			updateParticipante = connection.prepareStatement("UPDATE Participantes SET Nome = ?, Empresa = ?, Email = ? , Categoria = ?, Fone = ?, LocalTrabalho = ?, SupervisaoSaude = ?, Presente = ? WHERE ID = ?");
			
			countTodosCadastrados = connection.prepareStatement("SELECT COUNT(ID) AS DUMMY FROM Participantes");
			
			countTodosPresentes = connection.prepareStatement("SELECT COUNT(ID) AS DUMMY FROM Participantes WHERE Presente = 1");
			
		}
		catch (SQLException sqlException){
		
			sqlException.printStackTrace();
			System.exit(1);
			
		}
	
	}
	
	public int contaTodosCadastrados(){
	
		ResultSet resultSet = null;
		int cont = 0;
		
		try{
		
			resultSet = countTodosCadastrados.executeQuery();
			cont = resultSet.getInt(1);	
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			try{
				resultSet.close();
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
				close();		
			}		
		}
		
		return cont;

	}
	
	public int contaTodosPresentes(){
	
		ResultSet resultSet = null;
		int cont = 0;
	
		try{
		
			resultSet = countTodosPresentes.executeQuery();
			cont = resultSet.getInt(1);
				
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			try{
				resultSet.close();
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
				close();		
			}		
		}
		
		return cont;
	
	
	}
	
	public List<Participante> getTodosParticipantes(){
	
		List<Participante> results = null;
		ResultSet resultSet = null;
	
		try{
		
			resultSet = selectTodosParticipantes.executeQuery();
			results = new ArrayList<Participante>();	
		
			while (resultSet.next()){
			
				results.add(new Participante(
						   resultSet.getInt("ID"),
						   resultSet.getString("Nome"),
						   resultSet.getString("Empresa"),
						   resultSet.getString("Email"),
						   resultSet.getString("Categoria"),
						   resultSet.getString("Fone"),
						   resultSet.getString("LocalTrabalho"),
						   resultSet.getString("SupervisaoSaude"),
						   resultSet.getInt("Presente")
						   ));
						   
			}

		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			try{
				resultSet.close();
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
				close();		
			}		
		}
		
		return results;
	
	}
        
        public List<Participante> getTodosPresentes(){
	
		List<Participante> results = null;
		ResultSet resultSet = null;
	
		try{
		
			resultSet = selectTodosPresentes.executeQuery();
			results = new ArrayList<Participante>();	
		
			while (resultSet.next()){
			
				results.add(new Participante(
						   resultSet.getInt("ID"),
						   resultSet.getString("Nome"),
						   resultSet.getString("Empresa"),
						   resultSet.getString("Email"),
						   resultSet.getString("Categoria"),
						   resultSet.getString("Fone"),
						   resultSet.getString("LocalTrabalho"),
						   resultSet.getString("SupervisaoSaude"),
						   resultSet.getInt("Presente")
						   ));
						   
			}

		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			try{
				resultSet.close();
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
				close();		
			}		
		}
		
		return results;
	
	}
	
	public List<Participante> getParticipantesPorNome(String nome){
	
		//Statement st = null;
		List<Participante> results = null;
		ResultSet resultSet = null;
		
		try{
		
			selectParticipantePorNome.setString(1, "%" + nome + "%");
			selectParticipantePorNome.setString(2, "%" + nome + "%");
			//st = connection.createStatement();
			
			//st.executeQuery("SELECT * FROM Participantes WHERE Nome = A");
			
			resultSet = selectParticipantePorNome.executeQuery();			
			
			results = new ArrayList<Participante>();
			
			while(resultSet.next()){
				
					results.add(new Participante(
					   resultSet.getInt("ID"),
					   resultSet.getString("Nome"),
					   resultSet.getString("Empresa"),
					   resultSet.getString("Email"),
					   resultSet.getString("Categoria"),
					   resultSet.getString("Fone"),
					   resultSet.getString("LocalTrabalho"),
				       resultSet.getString("SupervisaoSaude"),
					   resultSet.getInt("Presente")
					   
					   ));
					   
			}
		}
		catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
		finally{
			try{
				resultSet.close();
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
				close();		
			}		
		}
		
		return results;

	}
	
	public int insereParticipante(String nome, String empresa, String email, String categoria, String fone, String localTrabalho, String supervisaoSaude, int intPresente){
	
		int result = 0;
		List<Participante> results = null;
		ResultSet resultSet = null;
                
                try{
                    
                    verificaParticipanteCadastrado.setString(1, nome);
                    verificaParticipanteCadastrado.setString(2, empresa);
                    verificaParticipanteCadastrado.setString(3, fone);

                    resultSet = verificaParticipanteCadastrado.executeQuery();
                    
                    if (resultSet.next()) return 0;
                    
                    insereNovoParticipante.setString(1, nome);
                    insereNovoParticipante.setString(2, empresa);
                    insereNovoParticipante.setString(3, email);
                    insereNovoParticipante.setString(4, ""); //categoria
                    insereNovoParticipante.setString(5, fone);
                    insereNovoParticipante.setString(6, ""); //LOCAL TRABALHO
                    insereNovoParticipante.setString(7, "");	//SUPERVISAO SAUDE
                    insereNovoParticipante.setInt(8, intPresente);			

                    result = insereNovoParticipante.executeUpdate();
		
		}
		catch (SQLException sqlException){
		
			sqlException.printStackTrace();
			close();
		
		}
		
		return result;
	
	}
	
	public int atualizaParticipante(String strID, String nome, String empresa, String email, String categoria, String fone, String localTrabalho, String supervisaoSaude, int intPresente){
	
		int result = 0;
		
		try{
		
			updateParticipante.setString(1, nome);
			updateParticipante.setString(2, empresa);
			updateParticipante.setString(3, email);
			updateParticipante.setString(4, ""); //categoria
			updateParticipante.setString(5, fone);
			updateParticipante.setString(6, ""); //local trabalho
			updateParticipante.setString(7, ""); //supervisao saude	
			updateParticipante.setInt(8, intPresente);	
			updateParticipante.setInt(9, Integer.parseInt(strID));	
			
			result = updateParticipante.executeUpdate();
		
		}
		catch (SQLException sqlException){
		
			sqlException.printStackTrace();
			close();
		
		}
		
		return result;
	
	}
	
	public void close(){
	
		try{
			connection.close();
		}
		catch( SQLException sqlException){
			sqlException.printStackTrace();
		}
	
	}

}
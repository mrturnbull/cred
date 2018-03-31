public class Participante{

	private int ID;
	private String nome;
	private String empresa;
	private String email;
	private String categoria;
	private String fone;
	private String localTrabalho;
	private String supervisaoSaude;
	private int intPresente;
		
	public Participante(){}

	public Participante(int ID, String nome, String empresa, String email, String categoria, String fone, String localTrabalho, String supervisaoSaude, int intPresente){
	
		setID(ID);
		setNome(nome);
		setEmpresa(empresa);
		setEmail(email);
		setCategoria(categoria);
		setFone(fone);
		setLocalTrabalho(localTrabalho);
		setSupervisaoSaude(supervisaoSaude);
		setPresente(intPresente);
	
	}
		
	////////////////////////////////////////////////////////////
	// Setters
	////////////////////////////////////////////////////////////
	
	public void setID(int _ID){
		ID = _ID;	
	}

	public void setNome(String _nome){
		nome = _nome;	
	}
	
	public void setEmpresa(String _empresa){
		empresa = _empresa;	
	}
	
	public void setEmail(String _email){
		email = _email;	
	}
	
	public void setCategoria(String _categoria){
		categoria = _categoria;
	}
	
	public void setFone(String _fone){
		fone = _fone;
	}
	
	public void setLocalTrabalho(String _localTrabalho){
		localTrabalho = _localTrabalho;	
	}
	
	public void setSupervisaoSaude(String _supervisaoSaude){
		supervisaoSaude = _supervisaoSaude;	
	}
		
	////////////////////////////////////////////////////////////
	// Getters
	////////////////////////////////////////////////////////////
	
	public int getID(){	
		return ID;
	}

	public String getNome(){	
		return nome;
	}
	
	public String getEmpresa(){	
		return empresa;
	}
	
	public String getEmail(){	
		return email;
	}
	
	public String getCategoria(){
		return categoria;
	}
		
	public String getFone(){	
		return fone;
	}
	
	public String getLocalTrabalho(){	
		return localTrabalho;
	}
	
	public String getSupervisaoSaude(){
		return supervisaoSaude;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	// is
	/////////////////////////////////////////////////////////////////////////////////////////////
	public int getPresente(){
		return this.intPresente;
	}
	
	public void setPresente(int intPresente){
		this.intPresente = intPresente;
	}
	
	
}
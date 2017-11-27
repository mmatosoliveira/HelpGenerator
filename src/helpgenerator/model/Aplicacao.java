package helpgenerator.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(schema="BlueHelper", name="Aplicacao")
public class Aplicacao {
	public Aplicacao() {}
	
	@Id
	@Column(name="Id")
	public long id;
	
	@Column(name="NomePadrao")
	public String nomeAplicacao;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="aplicacao")
	@JoinColumn(name="Id")
	private List<HelpAplicacao> passosDaAjuda = new ArrayList<HelpAplicacao>();

	public long getIdAplicacao() {
		return id;
	}

	public void setIdAplicacao(long idAplicacao) {
		this.id = idAplicacao;
	}

	public String getNomeAplicacao() {
		return nomeAplicacao;
	}

	public void setNomeAplicacao(String nomeAplicacao) {
		this.nomeAplicacao = nomeAplicacao;
	}
	
	

}

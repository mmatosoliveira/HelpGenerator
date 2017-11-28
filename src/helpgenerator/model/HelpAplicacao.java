package helpgenerator.model;

import javax.persistence.*;

@Entity
@Table(schema="dbo", name="HelpAplicacao")
public class HelpAplicacao {
	public HelpAplicacao(){}
	
	@Id
	@Column(name="Id")
	public long id;
	
	@Column(name="Detalhamento")
	public String detalhamento;
	
	@Column(name="Ordem")
	public int ordem;
	
	@Column(name="Titulo")
	public String titulo;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="IdAplicacao", referencedColumnName="Id")
	public Aplicacao aplicacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDetalhamento() {
		return "'" + detalhamento + "'";
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getTitulo() {
		return "'" + titulo + "'";
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

public long getIdAplicacao() {
	return this.aplicacao.getIdAplicacao();
}
	
	/*@Column(name="IdAplicacao")
	public long idAplicacao;*/

}

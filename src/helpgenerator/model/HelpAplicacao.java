package helpgenerator.model;

import javax.persistence.*;

@Entity
@Table(schema="BlueHelper", name="HelpAplicacao")
public class HelpAplicacao {
	public HelpAplicacao(){}
	
	@Id
	@Column(name="Id")
	public long id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="IdAplicacao", referencedColumnName="Id")
	public Aplicacao aplicacao;

}

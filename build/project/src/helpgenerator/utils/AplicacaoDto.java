package helpgenerator.utils;

public class AplicacaoDto {

	private long idAplicacao;
	
	private String nomeAplicacao;
	
	public AplicacaoDto() {}

	public long getIdAplicacao() {
		return idAplicacao;
	}

	public void setIdAplicacao(long idAplicacao) {
		this.idAplicacao = idAplicacao;
	}

	public String getNomeAplicacao() {
		return nomeAplicacao;
	}

	public void setNomeAplicacao(String nomeAplicacao) {
		this.nomeAplicacao = nomeAplicacao;
	}

	@Override
	public String toString() {
		return "(" + idAplicacao + ")"+ " - " + nomeAplicacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idAplicacao ^ (idAplicacao >>> 32));
		result = prime * result + ((nomeAplicacao == null) ? 0 : nomeAplicacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AplicacaoDto other = (AplicacaoDto) obj;
		if (idAplicacao != other.idAplicacao)
			return false;
		if (nomeAplicacao == null) {
			if (other.nomeAplicacao != null)
				return false;
		} else if (!nomeAplicacao.equals(other.nomeAplicacao))
			return false;
		return true;
	}
}
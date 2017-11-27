package helpgenerator.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;

import helpgenerator.model.Aplicacao;
import helpgenerator.utils.AplicacaoDto;

public class HelpGeneratorService {

	private EntityManagerFactory emf;
	private EntityManager        em;
	
	private void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("BlueHelper");
	}

	private void closeEntityManagerFactory() {
		emf.close();
	}

	private void createEntityManager() {
		em  = emf.createEntityManager();
	}

	private void closeEntityManager() {
		em.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<AplicacaoDto> getAplicacoesQuePossuemHelp() {
		createEntityManagerFactory();
		createEntityManager();	
		List<Aplicacao> aplicacoes = new ArrayList<>();
		List<AplicacaoDto> retorno = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
			Query query;
		
			query = em.createNativeQuery("select distinct A.id, A.nomePadrao from aplicacao a " + 
					"inner join helpaplicacao ha on a.id = ha.IdAplicacao", Aplicacao.class);
			
			aplicacoes = query.getResultList();
			
		closeEntityManager();
		closeEntityManagerFactory();
		
		for(Aplicacao item : aplicacoes) {
			retorno.add(mapper.map(item, AplicacaoDto.class));
		}
		return retorno;
	}
	
	public String gerarScriptHelps(long idAplicacao, boolean gerarTodos) {
		createEntityManagerFactory();
			createEntityManager();
				Query query;
				
				if(gerarTodos)
					query = em.createNativeQuery("CALL ASPGeraHelpsAplicacao ("+null+")");
				else
					query = em.createNativeQuery("CALL ASPGeraHelpsAplicacao ("+idAplicacao+")");
				
				String retorno = (String)query.getSingleResult();
			closeEntityManager();
		closeEntityManagerFactory();
		
		return retorno;
	}
}
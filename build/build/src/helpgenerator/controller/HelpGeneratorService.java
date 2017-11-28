package helpgenerator.controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.modelmapper.ModelMapper;
import helpgenerator.model.Aplicacao;
import helpgenerator.model.HelpAplicacao;
import helpgenerator.utils.AplicacaoDto;

public class HelpGeneratorService {

	private EntityManagerFactory emf;
	private EntityManager        em;
	
	private void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("dbo");
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
	
	@SuppressWarnings("unchecked")
	public String gerarScriptHelps(long idAplicacao, boolean gerarTodos) {
		createEntityManagerFactory();
			createEntityManager();
			Query q;
			if(!gerarTodos) {
				q = em.createQuery("select o from HelpAplicacao o where o.aplicacao.id = :idAplicacao");
				q.setParameter("idAplicacao", idAplicacao);
			}
			else
				 q = em.createQuery("select o from HelpAplicacao o");
			
			
			
			String scriptFinal = "";
			
			List<HelpAplicacao> passos = q.getResultList();
			
			for(HelpAplicacao item : passos) {
				String validacao = "IF EXISTS (SELECT * FROM HelpAplicacao WHERE Id = " + item.getId() + " AND Ordem = " + item.getOrdem() + ") ";
				String update = " UPDATE HelpAplicacao SET Titulo = " + item.getTitulo() + ", Detalhamento = " + item.getDetalhamento() + " WHERE Id = " + item.getId() + " AND Ordem = " + item.getOrdem();
				String insert = " ELSE INSERT INTO HelpAplicacao (IdAplicacao, Ordem, Titulo, Detalhamento)";
				String values = "VALUES ("+ item.getIdAplicacao() + ", " + item.getOrdem() + ", "+ item.getTitulo() +", "+ item.getDetalhamento() +")";
				
				
				String script = validacao + update + insert + values;
				
				scriptFinal += script;
			}
				
			closeEntityManager();
		closeEntityManagerFactory();
		
		return scriptFinal;
	}
}
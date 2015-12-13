package br.gov.serpro.inscricao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.serpro.inscricao.config.InscricaoConfig;
import br.gov.serpro.inscricao.entity.Aluno;
import br.gov.serpro.inscricao.exception.TurmaException;

@Controller
public class Turma {


	@Inject
	private Logger logger;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private InscricaoConfig config;

	@Inject
	private EntityManager em;


	public boolean estaMatriculado(Aluno aluno) {
		return this.obterAlunosMatriculados().contains(aluno);
	}

	@Transactional
	public void matricular(Aluno aluno) {
		if (this.estaMatriculado(aluno) || this.obterAlunosMatriculados().size() == this.config.getCapacidadeTurma()) { throw new TurmaException(); }
		this.em.persist(aluno);
		this.logger.info(this.bundle.getString("matricula.sucesso"));
	}

	public List<Aluno> obterAlunosMatriculados() {
		return this.em.createQuery("select a from Aluno a").getResultList();
	}

	@ExceptionHandler
	public void tratar(TurmaException e) {
		this.logger.warn(this.bundle.getString("matricula.erro"));
		throw e;
	}
}

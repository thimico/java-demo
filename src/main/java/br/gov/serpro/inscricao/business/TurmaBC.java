package br.gov.serpro.inscricao.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.security.RequiredPermission;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.serpro.inscricao.config.InscricaoConfig;
import br.gov.serpro.inscricao.entity.Aluno;
import br.gov.serpro.inscricao.exception.TurmaException;

@BusinessController
public class TurmaBC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private InscricaoConfig config;

	@Inject
	private AlunoBC alunoBC;

	@RequiredPermission(resource = "aluno", operation = "consultar")
	public boolean estaMatriculado(Aluno aluno) {
		return this.obterAlunosMatriculados().contains(aluno);
	}


	@Startup
	public void iniciar() {
		this.logger.info("Iniciando ...");
	}

	@Transactional
	@RequiredPermission(resource = "aluno", operation = "matricular")
	public void matricular(Aluno aluno) {
		if (this.estaMatriculado(aluno) || this.obterAlunosMatriculados().size() == this.config.getCapacidadeTurma()) { throw new TurmaException(); }
		this.alunoBC.insert(aluno);
		this.logger.info(this.bundle.getString("matricula.sucesso"));
	}

	public List<Aluno> obterAlunosMatriculados() {
		return this.alunoBC.findAll();
	}

	@ExceptionHandler
	public void tratar(TurmaException e) {
		this.logger.warn(this.bundle.getString("matricula.erro"));
		throw e;
	}
}

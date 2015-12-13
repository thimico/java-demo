package br.gov.serpro.inscricao.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.junit.Before;

import br.gov.frameworkdemoiselle.security.RequiredPermission;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.serpro.inscricao.business.TurmaBC;
import br.gov.serpro.inscricao.entity.Aluno;
import br.gov.serpro.inscricao.security.Credenciais;

@ViewController
public class TurmaMB implements Serializable {

	private static final long serialVersionUID = 1L;


	@Inject
	private SecurityContext securityContext;

	@Inject
	private Credenciais credenciais;


	@Inject
	private TurmaBC bc;

	@Getter
	@Setter
	private String nomeAluno;

	public List<Aluno> getAlunosMatriculados() {
		return this.bc.obterAlunosMatriculados();
	}

	@Transactional
	@RequiredPermission(resource = "aluno", operation = "matricular")
	public void matricular() {
		this.setUp();
		this.bc.matricular(new Aluno(this.nomeAluno));
	}

	@Before
	public void setUp() {
		this.credenciais.setNome("secretaria");
		this.credenciais.setSenha("segredo");
		this.securityContext.login();
	}
}

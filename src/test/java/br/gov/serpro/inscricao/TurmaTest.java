package br.gov.serpro.inscricao;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.serpro.inscricao.business.TurmaBC;
import br.gov.serpro.inscricao.entity.Aluno;
import br.gov.serpro.inscricao.exception.TurmaException;
import br.gov.serpro.inscricao.security.Credenciais;


@RunWith(DemoiselleRunner.class)
public class TurmaTest {

	@Inject
	private TurmaBC turma;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private Credenciais credenciais;

	@Test(expected = TurmaException.class)
	public void falhaAoTentarMatricularAlunoDuplicado() {
		this.turma.matricular(new Aluno("Orville Wright", 1));
		this.turma.matricular(new Aluno("Orville Wright", 1));
	}

	@Test(expected = TurmaException.class)
	public void falhaAoTentarMatricularAlunoNaTurmaCheia() {
		for (int i = 5; i <= 10; i++) {
			this.turma.matricular(new Aluno("Aluno " + i, i));
		}

		this.turma.matricular(new Aluno("Aluno 11", 11));
	}

	@Test
	public void matricularAlunoComSucesso() {
		Aluno aluno = new Aluno("Santos Dumont", 19);
		this.turma.matricular(aluno);
		Assert.assertTrue(this.turma.estaMatriculado(aluno));
	}


	@Before
	public void setUp() {
		this.credenciais.setNome("secretaria");
		this.credenciais.setSenha("segredo");
		this.securityContext.login();
	}


}

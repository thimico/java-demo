package br.gov.serpro.inscricao.security;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.SecurityContext;


public class Autorizador implements Authorizer {

	private static final long serialVersionUID = 1L;

	@Inject
	private SecurityContext securityContext;

	@Override
	public boolean hasPermission(String res, String op) {
		boolean autorizado = false;

		String usr = this.securityContext.getUser().getName();
		if (usr.equals("secretaria") && res.equals("aluno") && (op.equals("consultar") || op.equals("matricular"))) {
			autorizado = true;
		}
		return autorizado;
	}

	@Override
	public boolean hasRole(String rol) {
		return true;
	}
}

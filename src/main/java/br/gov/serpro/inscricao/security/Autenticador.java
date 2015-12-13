package br.gov.serpro.inscricao.security;


import java.security.Principal;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.util.ResourceBundle;


public class Autenticador implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private Credenciais credenciais;

	@Inject
	private ResourceBundle bundle;


	@Override
	public void authenticate() {
		if (this.credenciais.getNome().equals("secretaria") && this.credenciais.getSenha().equals("segredo")) {

		} else {
			throw new RuntimeException(this.bundle.getString("usuarioNaoAutenticado"));
		}
	}

	@Override
	public Principal getUser() {
		return new Principal() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return Autenticador.this.credenciais.getNome();
			}


		};
	}

	@Override
	public void unauthenticate() throws Exception {

	}

}

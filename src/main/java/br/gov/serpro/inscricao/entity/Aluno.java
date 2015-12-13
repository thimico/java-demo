package br.gov.serpro.inscricao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluno {


	public Aluno(String nomeAluno) {
		this.nome = nomeAluno;
	}

	private String nome;

	@Id
	@GeneratedValue
	private Integer matricula;

}

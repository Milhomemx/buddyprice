package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * @author Natan
 * Classe de modelo que abstrai o conceito de Produto no sistema. Recursos básicos de get/set, seus atributos e suas respectivas validações.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.ProductValidator")
@Table(name="Produto")
public class Produto extends CommonEntity{


@SearchField
@Validate(notNull = true, min = 2, max = 50)	
@Column(name="nome", nullable=false, updatable =true, length = 30)
	private String nome;

@Column(name="tamanho", nullable=true, updatable =true, length = 10)
private String tamanho;

@Column(name="peso", nullable=true, updatable =true, length = 20)
private String peso;

@Column(name="cor", nullable=true, updatable =true, length = 15)
private String cor;

@Column(name="versao", nullable=true, updatable =true, length = 10)
private String versao;

@Column(name="marca", nullable=true, updatable =true, length = 15)
private String marca;

@Column(name="descricao", nullable=true, updatable =true, length = 100)
private String descricao;

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public String getTamanho() {
	return tamanho;
}

public void setTamanho(String tamanho) {
	this.tamanho = tamanho;
}

public String getPeso() {
	return peso;
}

public void setPeso(String peso) {
	this.peso = peso;
}

public String getCor() {
	return cor;
}

public void setCor(String cor) {
	this.cor = cor;
}

public String getVersao() {
	return versao;
}

public void setVersao(String versao) {
	this.versao = versao;
}

public String getMarca() {
	return marca;
}

public void setMarca(String marca) {
	this.marca = marca;
}

public String getDescricao() {
	return descricao;
}

public void setDescricao(String descricao) {
	this.descricao = descricao;
}

@Override
	public String toString() {
		
		return getNome();
	}

}

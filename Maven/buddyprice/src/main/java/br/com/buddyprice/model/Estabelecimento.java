package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.EstabelecimentoValidator")
@Table(name="Estabelecimento")
public class Estabelecimento {



@Validate(notNull = true, min = 2, max = 50)	
@Column(name="nome", nullable=false, updatable =true, length = 40)
	private String nome;

@Column(name="endereco", nullable=true, updatable =true, length = 100)
private String endereco;

@Column(name="cidade", nullable=true, updatable =true, length = 50)
private String cidade;

@Column(name="estado", nullable=true, updatable =true, length = 20)
private String estado;

@Column(name="pais", nullable=true, updatable =true, length = 20)
private String pais;

@Column(name="site", nullable=true, updatable =true, length = 100)
private String site;

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public String getEndereco() {
	return endereco;
}

public void setEndereco(String endereco) {
	this.endereco = endereco;
}

public String getCidade() {
	return cidade;
}

public void setCidade(String cidade) {
	this.cidade = cidade;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public String getPais() {
	return pais;
}

public void setPais(String pais) {
	this.pais = pais;
}

public String getSite() {
	return site;
}

public void setSite(String site) {
	this.site = site;
}


}
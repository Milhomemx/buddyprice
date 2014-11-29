package br.com.buddyprice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * @author Natan
 * Classe de modelo que abstrai o conceito de Estabelecimento no sistema. Recursos básicos de get/set, seus atributos e suas respectivas validações.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass = "br.com.buddyprice.control.validator.EstablishmentValidator")
@Table(name = "Estabelecimento")
public class Estabelecimento extends CommonEntityDated {

	@SearchField
	@Validate(notNull = true, min = 2, max = 50)
	@Column(name = "nome", nullable = false, updatable = true, length = 40, unique = true)
	private String nome;

	@Column(name = "endereco", nullable = true, updatable = true, length = 100)
	private String endereco;

	@Column(name = "cidade", nullable = true, updatable = true, length = 50)
	private String cidade;

	@Column(name = "estado", nullable = true, updatable = true, length = 20)
	private String estado;

	@Column(name = "pais", nullable = true, updatable = true, length = 20)
	private String pais;

	@Column(name = "site", nullable = true, updatable = true, length = 100)
	private String site;

	@Column(name = "descricao", nullable = true, updatable = true, length = 500)
	private String descricao;

	@OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY)
	private List<Oferta> ofertas;

	@OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY)
	private List<AtributoExtra> atributoExtras;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

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

	@Override
	public String toString() {
		return getNome();
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public List<AtributoExtra> getAtributoExtras() {
		return atributoExtras;
	}

	public void setAtributoExtras(List<AtributoExtra> atributoExtras) {
		this.atributoExtras = atributoExtras;
	}

}

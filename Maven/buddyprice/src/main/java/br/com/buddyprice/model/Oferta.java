package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * @author Natan
 * Classe de modelo que abstrai o conceito de Oferta no sistema. Recursos básicos de get/set, seus atributos e suas respectivas validações.
 * Abstração do relacionamento Produto<->Estabelecimento recorrente na construção da oferta.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass = "br.com.buddyprice.control.validator.OfferValidator")
@Table(name = "Oferta")
public class Oferta extends CommonEntityDated {

	@Validate(notNull = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", insertable = true, updatable = true)
	private Produto produto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", insertable = true, updatable = false)
	private Usuario usuario;

	@Validate(notNull = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = true, updatable = true)
	private Estabelecimento estabelecimento;

	@Validate(notNull = true)
	@Column(name = "valor", nullable = false, updatable = true)
	private String valor;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}

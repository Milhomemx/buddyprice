package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass = "br.com.buddyprice.control.validator.ExtraAttributeValidator")
@Table(name = "atributo_extra")
public class AtributoExtra extends CommonEntity {

	@SearchField
	@Validate(notNull = true, min = 2, max = 50)
	@Column(name = "valor", nullable = false, updatable = true, length = 30)
	private String valor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = true, updatable = true)
	private Estabelecimento estabelecimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", insertable = true, updatable = true)
	private Produto produto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_atributo_extra", insertable = true, updatable = true)
	protected TipoAtributoExtra tipoAtributoExtra;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public TipoAtributoExtra getTipoAtributoExtra() {
		return tipoAtributoExtra;
	}

	public void setTipoAtributoExtra(TipoAtributoExtra tipoAtributoExtra) {
		this.tipoAtributoExtra = tipoAtributoExtra;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}

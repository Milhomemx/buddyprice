package br.com.buddyprice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass = "br.com.buddyprice.control.validator.TipoExtraAttributesValidator")
@Table(name = "tipo_atributo_extra")
public class TipoAtributoExtra extends CommonEntity {

	@SearchField
	@Validate(notNull = true, min = 2, max = 50)
	@Column(name = "nome", nullable = false, updatable = true, length = 40, unique = true)
	private String nome;

	@OneToMany(mappedBy = "tipoAtributoExtra", fetch = FetchType.LAZY)
	private List<AtributoExtra> atributoExtras;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<AtributoExtra> getAtributoExtras() {
		return atributoExtras;
	}

	public void setAtributoExtras(List<AtributoExtra> atributoExtras) {
		this.atributoExtras = atributoExtras;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}

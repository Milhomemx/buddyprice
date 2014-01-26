package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.AvaliacaoValidator")
@Table(name="Avaliacoes")
public class Avalicacao extends CommonEntity{

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_oferta", insertable = true, updatable = true)
private Oferta oferta;

@Validate(notNull = true)	
@Column(name="valor", nullable=false, updatable =true)
	private Boolean valor;


}

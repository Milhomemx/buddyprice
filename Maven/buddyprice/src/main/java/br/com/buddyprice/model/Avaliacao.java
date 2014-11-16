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

/**
 * @author Natan
 * Classe de modelo que abstrai o conceito de Avalia��o no sistema. Recursos b�sicos de get/set, seus atributos e suas respectivas valida��es.
 * Abstra��o do multi-relacionamento com o modelo Oferta, uma vez que uma avalia��o avalia uma oferta.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.EvaluationValidator")
@Table(name="Avaliacoes")
public class Avaliacao extends CommonEntity{

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_oferta", insertable = true, updatable = true)
private Oferta oferta;

@Validate(notNull = true)	
@Column(name="valor", nullable=false, updatable =true)
	private Boolean valor;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_usuario", insertable = true, updatable = false)
private Usuario usuario;

}

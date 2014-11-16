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
 * Classe de modelo que abstrai o conceito de Comentário no sistema. Recursos básicos de get/set, seus atributos e suas respectivas validações.
 * Abstração do multi-relacionamento com o modelo Oferta, uma vez que um comentário é efetuado numa oferta.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.CommentValidator")
@Table(name="Comentario")
public class Comentario extends CommonEntity{

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_oferta", insertable = true, updatable = true)
private Oferta oferta;

@Validate(notNull = true)	
@Column(name="comentario", nullable=false, updatable =true)
	private String comentario;


}

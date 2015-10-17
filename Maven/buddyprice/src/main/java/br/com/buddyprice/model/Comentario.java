package br.com.buddyprice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.buddyprice.model.enums.AvaliacaoComentario;
import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

/**
 * @author Natan Classe de modelo que abstrai o conceito de Comentário no
 *         sistema. Recursos básicos de get/set, seus atributos e suas
 *         respectivas validações. Abstração do multi-relacionamento com o
 *         modelo Oferta, uma vez que um comentário é efetuado numa oferta.
 */
@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass = "br.com.buddyprice.control.validator.CommentValidator")
@Table(name = "Comentario")
public class Comentario extends CommonEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_oferta", insertable = true, updatable = true)
	private Oferta oferta;

	@JoinColumn(name = "avaliacao", insertable = true, updatable = true)
	@Enumerated(EnumType.STRING)
	private AvaliacaoComentario avaliacao;

	@Validate(notNull = true)
	@Column(name = "comentario", nullable = false, updatable = true, length=100)
	private String comentario;

	@Column(name = "data", nullable = false, updatable = true)
	private Date data;

	public Comentario() {
		this.data = new Date();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public AvaliacaoComentario getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(AvaliacaoComentario avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return getUsuario().getName() + " diz: " + getComentario();
	}
	
}

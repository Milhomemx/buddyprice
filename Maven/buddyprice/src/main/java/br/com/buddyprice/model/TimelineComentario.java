package br.com.buddyprice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("Comentario")
public class TimelineComentario extends Timeline<Comentario> {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objeto")
	private Comentario item;

	@Override
	public Comentario getItem() {
		return item;
	}

	@Override
	public void setItem(Comentario item) {
		this.item = item;
	}
	
}

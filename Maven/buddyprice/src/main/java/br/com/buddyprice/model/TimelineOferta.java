package br.com.buddyprice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("Oferta")
public class TimelineOferta extends Timeline<Oferta> {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objeto")
	private Oferta item;

	@Override
	public Oferta getItem() {
		return item;
	}

	@Override
	public void setItem(Oferta item) {
		this.item = item;
	}
	
}

package br.com.buddyprice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("Avaliacao")
public class TimelineAvaliacao extends Timeline<Avaliacao> {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objeto")
	private Avaliacao item;

	@Override
	public Avaliacao getItem() {
		return item;
	}

	@Override
	public void setItem(Avaliacao item) {
		this.item = item;
	}
	
}

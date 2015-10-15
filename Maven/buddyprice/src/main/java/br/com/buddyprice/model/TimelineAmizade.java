package br.com.buddyprice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.vexillum.model.Friendship;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("Amizade")
public class TimelineAmizade extends Timeline<Friendship> {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_objeto")
	private Friendship item;

	@Override
	public Friendship getItem() {
		return item;
	}

	@Override
	public void setItem(Friendship item) {
		this.item = item;
	}
	
}

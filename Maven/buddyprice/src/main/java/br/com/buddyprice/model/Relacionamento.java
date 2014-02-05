package br.com.buddyprice.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.Category;
import br.com.vexillum.model.Friendship;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.FriendshipValidator")
@Table(name="Relacionamento")
public class Relacionamento extends Friendship {

	public Relacionamento() {
		Category c = new Category();
		c.setId(1l);
		this.setActive(true);
	}
	


}

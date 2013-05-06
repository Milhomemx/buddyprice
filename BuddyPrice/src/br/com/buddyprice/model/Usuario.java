package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.UserBasic;

@SuppressWarnings("serial")
@Entity
@Table(name="Usuario")
public class Usuario extends UserBasic {

@Column(name="tel", nullable=true, updatable =false, length = 30)
	private String tel;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}

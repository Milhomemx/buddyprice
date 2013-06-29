package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.UserBasic;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.UsuarioValidator")
@Table(name="Usuario")
public class Usuario extends UserBasic {

@Column(name="tel", nullable=true, updatable =false, length = 30)
	private String tel;

@Column(name="sobreNome", nullable=true, updatable =true, length = 150)
private String sobreNome;


	public String getSobreNome() {
	return sobreNome;
}

public void setSobreNome(String sobreNome) {
	this.sobreNome = sobreNome;
}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}

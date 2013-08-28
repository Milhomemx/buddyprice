package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.vexillum.model.Category;
import br.com.vexillum.model.UserBasic;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.model.annotations.ValidatorClass;

@SuppressWarnings("serial")
@Entity
@ValidatorClass(validatorClass="br.com.buddyprice.control.validator.UsuarioValidator")
@Table(name="Usuario")
public class Usuario extends UserBasic {

	public Usuario() {
		Category c = new Category();
		c.setId(1l);
		this.setActive(true);
		this.setCategory(c);
	}
	
@Column(name="tel", nullable=true, updatable =true, length = 30)
	private String tel;

public String getCidade() {
	return cidade;
}

public void setCidade(String cidade) {
	this.cidade = cidade;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public String getPais() {
	return pais;
}

public void setPais(String pais) {
	this.pais = pais;
}

@Validate(notNull = true, min = 2, max = 50)
@Column(name="sobreNome", nullable=false, updatable =true, length = 150)
private String sobreNome;

@Column(name="cidade", nullable=true, updatable =true, length = 150)
private String cidade;

@Column(name="estado", nullable=true, updatable =true, length = 150)
private String estado;

@Column(name="pais", nullable=true, updatable =true, length = 150)
private String pais;

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

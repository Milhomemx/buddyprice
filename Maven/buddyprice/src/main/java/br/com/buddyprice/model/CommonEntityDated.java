package br.com.buddyprice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import br.com.vexillum.model.CommonEntity;

@SuppressWarnings({ "serial" })
@MappedSuperclass
public class CommonEntityDated extends CommonEntity {

	public CommonEntityDated() {
		this.date = new Date();
	}
	
	@Column(name = "date", nullable = false)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

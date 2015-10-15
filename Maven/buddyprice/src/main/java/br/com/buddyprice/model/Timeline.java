package br.com.buddyprice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;

@Entity
@Table(name="timeline")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="interacao", discriminatorType=DiscriminatorType.STRING)
public abstract class Timeline<T> extends CommonEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4864740064624527815L;

	@Column(name = "data")
	private Date data;
	
	@Column(name = "momento")
	private String momento;

	public abstract T getItem();
	public abstract void setItem(T item);

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMomento() {
		return momento;
	}

	public void setMomento(String momento) {
		this.momento = momento;
	}
	
}

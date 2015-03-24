package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.buddyprice.control.AtributoExtraController;
import br.com.vexillum.model.CommonEntity;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.model.annotations.SearchField;
import br.com.vexillum.model.annotations.Validate;
import br.com.vexillum.util.SpringFactory;

@SuppressWarnings("serial")
@Entity
@Table(name = "atributo_extra_valor")
public class AtributoExtraValor extends CommonEntity implements Comparable<AtributoExtraValor> {

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=AtributoExtra.class)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="atributo_extra_id", nullable=false)
	private AtributoExtra atributoExtra;
	
	@SearchField
	@Column(name = "id_entidade", nullable = false, updatable = true)
	private Long idEntidade;
	
	@Validate(notNull = true, min = 2, max = 50)
	@Column(name = "valor", nullable = false, updatable = true, length = 50)
	private String valor;
	
	@SearchField
	@Validate(notNull = true, min = 2, max = 50)
	@Column(name = "class_name", nullable = false, updatable = true, length = 50)
	private String className;
	
	public AtributoExtraValor() {
		super();
	}
	
	public AtributoExtraValor(CommonEntity idEntidade, String className) {
		this();
		this.idEntidade = idEntidade.getId();
		this.className = className;
	}
	
	public AtributoExtraValor(CommonEntity idEntidade, Class<? extends ICommonEntity> className) {
		this();
		this.idEntidade = idEntidade.getId();
		setClassName(className);
	}

	public AtributoExtra getAtributoExtra() {
		return atributoExtra;
	}

	public void setAtributoExtra(AtributoExtra atributoExtra) {
		this.atributoExtra = atributoExtra;
	}
	
	public void setAtributoExtra(String atributoExtra) {
		AtributoExtraController controller = SpringFactory.getController("atributoExtraController", AtributoExtraController.class, null);
		AtributoExtra atributo = new AtributoExtra(); 
		this.atributoExtra = controller.setAtributoExtraNome(atributo, atributoExtra);
	}
	
	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setClassName(Class<? extends ICommonEntity> classz) {
		this.className = classz.getName();
	}

	@Override
	public int compareTo(AtributoExtraValor o) {
		return this.getAtributoExtra().getNome().compareToIgnoreCase(o.getAtributoExtra().getNome());
	}
	
}

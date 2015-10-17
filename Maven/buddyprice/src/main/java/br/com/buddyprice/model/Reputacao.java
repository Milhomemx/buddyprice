package br.com.buddyprice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.vexillum.model.CommonEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="reputacao")
public class Reputacao extends CommonEntity {
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Column(name="avaliacoes_positivas")
	private Integer avaliacoesPositivas;
	
	@Column(name="avaliacoes_negativas")
	private Integer avaliacoesNegativas;
	
	@Column(name="qtd_ofertas")
	private Integer qtdOfertas;
	
	@Column(name="comentarios_positivos")
	private Integer comentariosPositivos;
	
	@Column(name="comentarios_negativos")
	private Integer comentariosNegativos;
	
	@Column(name="comentarios_neutros")
	private Integer comentariosNeutros;
	
	@Column(name="nota")
	private Integer nota;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getAvaliacoesPositivas() {
		return avaliacoesPositivas;
	}

	public void setAvaliacoesPositivas(Integer avaliacoesPositivas) {
		this.avaliacoesPositivas = avaliacoesPositivas;
	}

	public Integer getAvaliacoesNegativas() {
		return avaliacoesNegativas;
	}

	public void setAvaliacoesNegativas(Integer avaliacoesNegativas) {
		this.avaliacoesNegativas = avaliacoesNegativas;
	}

	public Integer getQtdOfertas() {
		return qtdOfertas;
	}

	public void setQtdOfertas(Integer qtdOfertas) {
		this.qtdOfertas = qtdOfertas;
	}

	public Integer getComentariosPositivos() {
		return comentariosPositivos;
	}

	public void setComentariosPositivos(Integer comentariosPositivos) {
		this.comentariosPositivos = comentariosPositivos;
	}

	public Integer getComentariosNegativos() {
		return comentariosNegativos;
	}

	public void setComentariosNegativos(Integer comentariosNegativos) {
		this.comentariosNegativos = comentariosNegativos;
	}

	public Integer getComentariosNeutros() {
		return comentariosNeutros;
	}

	public void setComentariosNeutros(Integer comentariosNeutros) {
		this.comentariosNeutros = comentariosNeutros;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}
	
	
}

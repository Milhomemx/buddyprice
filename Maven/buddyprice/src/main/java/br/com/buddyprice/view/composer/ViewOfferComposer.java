package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

import br.com.buddyprice.model.Avaliacao;
import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.model.enums.AvaliacaoComentario;
import br.com.buddyprice.view.renderer.ComentaryRenderer;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;

/**
 * @author Natan
 * Exibe a "consulta detalhada" da abstração Oferta. 
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ViewOfferComposer extends OfferComposer {

	private ComentaryRenderer comentaryRenderer;
	
	private String fldComentario;
	
	private AvaliacaoComentario fldAvaliacao;
	
	private Comentario selectedComentario;
	
	public String getFldComentario() {
		return fldComentario;
	}

	public void setFldComentario(String fldComentario) {
		this.fldComentario = fldComentario;
	}

	public AvaliacaoComentario getFldAvaliacao() {
		return fldAvaliacao;
	}

	public void setFldAvaliacao(AvaliacaoComentario fldAvaliacao) {
		this.fldAvaliacao = fldAvaliacao;
	}

	public Comentario getSelectedComentario() {
		return selectedComentario;
	}

	public void setSelectedComentario(Comentario selectedComentario) {
		this.selectedComentario = selectedComentario;
	}
	
	public ComentaryRenderer getComentaryRenderer(){
		if(comentaryRenderer == null){
			comentaryRenderer = new ComentaryRenderer((Usuario) getUserLogged(), this);
		}
		return comentaryRenderer;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(session.getAttribute("oferta")!=null)
			setEntity((Oferta) session.getAttribute("oferta"));
			session.setAttribute("oferta", null);
		initEvaluateButtons();
		loadBinder();
	}

	public void publishOfferOnFacebook(){
		postStatusMessageFacebook(getPostMessage());
		showMessagePublish();
	}

	public void publishOfferOnTwitter(){
		updateStatusTwitter(getPostMessage());
		showMessagePublish();
	}

	private void showMessagePublish(){
		Return ret = new Return(true);
		ret.addMessage(new Message(null, "Publicado com sucesso!"));
		treatReturn(ret);
	}
	
	private String getPostMessage(){
		Oferta oferta = getEntity();
		String post = "Veja o que encontrei: \n\n"+oferta.getProduto() + " por apenas " + oferta.getValor() + " em " + oferta.getEstabelecimento();
		return post;
	}
	
	private void initEvaluateButtons() {
		if(getComponentById("negativeEvaluate") != null && getComponentById("positiveEvaluate") != null){
			if(getEntity().getAvaliacoes() != null ){
				for (Avaliacao avaliacao : getEntity().getAvaliacoes()) {
					if(avaliacao.getUsuario().equals(getUserLogged())){
						((Button)getComponentById("negativeEvaluate")).setDisabled(avaliacao.getValor());
						((Button)getComponentById("positiveEvaluate")).setDisabled(!avaliacao.getValor());
						return;
					}
				}
			}
			((Button)getComponentById("negativeEvaluate")).setDisabled(false);
			((Button)getComponentById("positiveEvaluate")).setDisabled(false);
		}
	}
	
	public void evaluateOffer(Boolean evaluation){
		if(evaluation){
			doAction("evaluateOfferPositive");
		} else {
			doAction("evaluateOfferNegative");
		}
		getControl().refresh(getEntity());
		initEvaluateButtons();
		loadBinder();
	}

	public Return evaluateOfferNegative() {
		return getControl().doAction("evaluateOfferNegative");
	}


	public Return evaluateOfferPositive() {
		return getControl().doAction("evaluateOfferPositive");
	}
	
	public AvaliacaoComentario[] getListAvaliacaoComentarios(){
		return AvaliacaoComentario.values();
	}
	
	public List<Comentario> getListComentarios(){
		List<Comentario> list = getControl().getCommentsFromOffer();
		if(list != null && !list.isEmpty()){
			getComponentById("boxComentarios").setVisible(true);
		} else {
			getComponentById("boxComentarios").setVisible(false);
		}
		return list;
	}
	
	public Return saveComentary(){
		Return ret = getControl().doAction("saveComentary");
		if(ret.isValid()){
			loadBinder();
		} else {
			treatReturn(ret);
		}
		return ret;
	}

	public Return editComentary(){
		return null;
	}
	
	public Return deleteComentary(){
		return null;
	}
}

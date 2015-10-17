
package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import br.com.buddyprice.control.TimelineController;
import br.com.buddyprice.model.Avaliacao;
import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Timeline;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.renderer.TimelineRenderer;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings({ "serial", "rawtypes" })
public class TimelineComposer extends BaseComposer<Timeline, TimelineController> {

	private TimelineRenderer renderer;
	
	private Usuario userTimeline;
	
	private Oferta selectedOffer;
	
	public Usuario getUserTimeline() {
		return userTimeline;
	}

	public void setUserTimeline(Usuario userTimeline) {
		this.userTimeline = userTimeline;
	}

	public Oferta getSelectedOffer() {
		return selectedOffer;
	}

	public void setSelectedOffer(Oferta selectedOffer) {
		this.selectedOffer = selectedOffer;
	}

	public TimelineRenderer getTimelineRenderer(){
		if(this.renderer == null){
			renderer = new TimelineRenderer(this);
		}
		return this.renderer;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initUserTimeline();
		loadBinder();
	}
	
	private void initUserTimeline(){
		try {
			userTimeline = getControl().getUserById(Long.parseLong(Executions.getCurrent().getParameter("userId")));
		} catch (Exception e) {} 
		if(userTimeline == null){
			userTimeline = (Usuario) getUserLogged();
		}
	}
	
	public List<Timeline<?>> getTimelineItens(){
		return getControl().getTimelineItens();
	}
	
	public ListModel<Comentario> getCommentsFromOffer(Oferta oferta){
		List<Comentario> list = getControl().getCommentsFromOffer(oferta);
		return new ListModelList<>(list);
	}
	
	public void evaluateOffer(Boolean evaluation, Oferta oferta, Component buttonPositive, Component buttonNegative){
		setSelectedOffer(oferta);
		if(evaluation){
			getControl().doAction("evaluateOfferPositive");
		} else {
			getControl().doAction("evaluateOfferNegative");
		}
		getControl().refresh(oferta);
		initEvaluateButtons(buttonPositive, buttonNegative, oferta);
	}
	
	public void initEvaluateButtons(Component buttonPositive, Component buttonNegative, Oferta oferta) {
		if(buttonNegative != null && buttonPositive != null){
			if(oferta.getAvaliacoes() != null ){
				for (Avaliacao avaliacao : oferta.getAvaliacoes()) {
					if(avaliacao.getUsuario().equals(getUserLogged())){
						((Button)buttonNegative).setDisabled(avaliacao.getValor());
						((Button)buttonPositive).setDisabled(!avaliacao.getValor());
						return;
					}
				}
			}
			((Button)buttonNegative).setDisabled(false);
			((Button)buttonPositive).setDisabled(false);
		}
	}
	
	@Override
	public TimelineController getControl() {
		 return SpringFactory.getController("timelineController",
				TimelineController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Timeline getEntityObject() {
		return null;
	}

}

package br.com.buddyprice.view.renderer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Timeline;
import br.com.buddyprice.model.TimelineAmizade;
import br.com.buddyprice.model.TimelineAvaliacao;
import br.com.buddyprice.model.TimelineComentario;
import br.com.buddyprice.model.TimelineOferta;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.utils.MediaUtils;
import br.com.buddyprice.utils.NumberUtils;
import br.com.buddyprice.view.composer.TimelineComposer;
import br.com.vexillum.util.HibernateUtils;

@SuppressWarnings("static-access")
public class TimelineRenderer implements ListitemRenderer<Timeline<?>> {

	private TimelineComposer composer;
	
	public TimelineRenderer(TimelineComposer composer) {
		super();
		this.composer = composer;
	}

	@Override
	public void render(Listitem item, Timeline<?> tl, int num) throws Exception {
		Listcell cell = new Listcell();
		
		Groupbox groupbox = new Groupbox();
		groupbox.setHflex("1");
		groupbox.setHeight("260px");
		
		if(tl instanceof TimelineAmizade){
			groupbox.appendChild(fillAmizadeTimeline(tl));
		} else {
			groupbox.appendChild(fillOfertaInvolvedTimeline(tl, item, num));
		}
		
		Separator sep = new Separator();
		sep.setHeight("5px");
		
		groupbox.appendChild(sep);
		
		cell.appendChild(groupbox);
		
		item.appendChild(cell);
	}

	private Component fillAmizadeTimeline(Timeline<?> tl) {
		TimelineAmizade itl = (TimelineAmizade) tl;
		Usuario userNotOwner = (Usuario) (itl.getItem().getOwner().equals(itl.getUsuarioDono()) ? HibernateUtils.materializeProxy(itl.getItem().getFriend()):HibernateUtils.materializeProxy(itl.getItem().getOwner()));
		
		Vbox vbox = new Vbox();
		vbox.setWidth("100%");
		
		Label labelMessage = new Label(itl.getUsuarioDono().getName() +
								" adicionou " + userNotOwner.getName() + 
								" como amigo(a).");
		
		Label labelMommento = new Label(itl.getMomento());
		labelMommento.setStyle("font-size: 11px;");
		 
		Groupbox groupImage = new Groupbox();
		groupImage.setWidth("150px");
		groupImage.setHeight("150px");
		groupImage.setSclass("groupboxImage");
		Hbox hboxImage = new Hbox();
		hboxImage.setWidth("100%");
		hboxImage.setHeight("100%");
		hboxImage.setPack("center");
		hboxImage.setAlign("center");
		Image image = new Image(composer.getProperty("DefaultAvatar"));
		MediaUtils.setImageUser(image, userNotOwner);
		hboxImage.appendChild(image);
		groupImage.appendChild(hboxImage);
		
		Hbox hboxButton = new Hbox();
		hboxButton.setWidth("100%");
		hboxButton.setPack("end");
		Button viewButton = new Button("Visualizar");
		viewButton.setZclass("btn btn-danger");
		viewButton.setHref("/pages/timeline/index.zul?userId=" + userNotOwner.getId());
		hboxButton.appendChild(viewButton);
		
		vbox.appendChild(labelMessage);
		vbox.appendChild(labelMommento);
		vbox.appendChild(groupImage);
		vbox.appendChild(hboxButton);
				 
		return vbox;
	}

	private Component fillOfertaInvolvedTimeline(Timeline<?> tl, Listitem item, int num) {
		Oferta oferta = getOfferFromTimeline(tl);
		
		Vbox vbox = new Vbox();
		vbox.setWidth("100%");
		
		Label labelMessage = new Label(getMessageByTimelineType(tl, oferta));
		
		Label labelMommento = new Label(tl.getMomento());
		labelMommento.setStyle("font-size: 11px;");
		 
		Hbox hboxOferta = new Hbox();
		hboxOferta.setWidth("100%");
		
		Groupbox groupImage = new Groupbox();
		groupImage.setWidth("150px");
		groupImage.setHeight("150px");
		groupImage.setSclass("groupboxImage");
		Hbox hboxImage = new Hbox();
		hboxImage.setWidth("100%");
		hboxImage.setHeight("100%");
		hboxImage.setPack("center");
		hboxImage.setAlign("center");
		Image image = new Image(composer.getProperty("DefaultProduct"));
		MediaUtils.setImageProduct(image, oferta.getProduto());
		hboxImage.appendChild(image);
		groupImage.appendChild(hboxImage);
		
		Groupbox groupComments = new Groupbox();
		groupComments.setHflex("1");
		groupComments.setHeight("150px");
		Listbox listComments = new Listbox();
		listComments.setWidth("100%");
		listComments.setHeight("100%");
		listComments.setModel(composer.getCommentsFromOffer(oferta));
		listComments.setEmptyMessage("Nenhum comentário!");
		groupComments.appendChild(listComments);
		
		hboxOferta.appendChild(groupImage);
		hboxOferta.appendChild(groupComments);
		
		Hbox hboxButton = new Hbox();
		hboxButton.setWidth("100%");
		hboxButton.setAlign("center");
		
		Hbox hboxEvaluations = new Hbox();
		Button buttonEvaluatePositive = new Button("Positiva");
		Label labelPositive = new Label(oferta.getNumAvaliacoesPositivas().toString());
		Separator sepEvaluate = new Separator();
		sepEvaluate.setWidth("5px");
		Button buttonEvaluateNegative = new Button("Negativa");
		Label labelNegative = new Label(oferta.getNumAvaliacoesNegativas().toString());
		buttonEvaluatePositive.addEventListener("onClick", getEvaluateOfferEvent(true, oferta, buttonEvaluatePositive, buttonEvaluateNegative, item, tl, num));
		buttonEvaluateNegative.addEventListener("onClick", getEvaluateOfferEvent(false, oferta, buttonEvaluatePositive, buttonEvaluateNegative, item, tl, num));
		composer.initEvaluateButtons(buttonEvaluatePositive, buttonEvaluateNegative, oferta);
		hboxEvaluations.appendChild(buttonEvaluatePositive);
		hboxEvaluations.appendChild(labelPositive);
		hboxEvaluations.appendChild(sepEvaluate);
		hboxEvaluations.appendChild(buttonEvaluateNegative);
		hboxEvaluations.appendChild(labelNegative);
		
		Hbox hboxPublications = new Hbox();
		hboxPublications.setHflex("1");
		hboxPublications.setPack("center");
		Image imageFacebook = new Image("/images/social/facebook.jpg");
		Separator sepPublications = new Separator();
		sepEvaluate.setWidth("5px");
		Image imageTwitter = new Image("/images/social/twitter.jpg");
		hboxPublications.appendChild(imageFacebook);
		hboxPublications.appendChild(sepPublications);
		hboxPublications.appendChild(imageTwitter);
		
		Button viewButton = new Button("Visualizar");
		viewButton.setZclass("btn btn-danger");
		viewButton.setHref("/pages/offers/view.zul?id=" + oferta.getId());
		
		hboxButton.appendChild(hboxEvaluations);
		hboxButton.appendChild(hboxPublications);
		hboxButton.appendChild(viewButton);
		
		vbox.appendChild(labelMessage);
		vbox.appendChild(labelMommento);
		vbox.appendChild(hboxOferta);
		vbox.appendChild(hboxButton);
				 
		return vbox;
	}

	private Oferta getOfferFromTimeline(Timeline<?> tl) {
		if(tl instanceof TimelineComentario ){
			TimelineComentario tc = (TimelineComentario) tl;
			return tc.getItem().getOferta();
		} else if(tl instanceof TimelineAvaliacao) {
			TimelineAvaliacao ta = (TimelineAvaliacao) tl;
			return ta.getItem().getOferta();
		} else {
			TimelineOferta to = (TimelineOferta) tl;
			return to.getItem();
		}
	}

	private String getMessageByTimelineType(Timeline<?> tl, Oferta oferta) {
		String message = null;
		if(tl instanceof TimelineComentario){
			TimelineComentario tc = (TimelineComentario) tl;
			message = tc.getUsuarioDono().getName() + " comentou sobre ";
		} else if(tl instanceof TimelineAvaliacao) {
			TimelineAvaliacao ta = (TimelineAvaliacao) tl;
			message = ta.getUsuarioDono().getName() + " avaliou " + (ta.getItem().getValor() ? "positivamente " : "negativamente ");
		} else if(tl instanceof TimelineOferta) {
			TimelineOferta to = (TimelineOferta) tl;
			message = to.getUsuarioDono().getName() + " comentou sobre ";
		}
		message +=  oferta.getProduto().getNome() + " em " +
				  oferta.getEstabelecimento().getNome() + " por R$" +
				  NumberUtils.formatToCurrecy(oferta.getValor(), "#,##0.00") + ".";
		return message;
	}
	
	public EventListener<Event> getEvaluateOfferEvent(final Boolean evaluate, final Oferta oferta, final Button buttonPositive, final Button buttonNegative, final Listitem item, final Timeline<?> tl, final int num){
		return new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				composer.evaluateOffer(evaluate, oferta, buttonPositive, buttonNegative);
				item.getChildren().clear();
				TimelineRenderer.this.render(item, tl, num);
			}
		};
	}
	
}

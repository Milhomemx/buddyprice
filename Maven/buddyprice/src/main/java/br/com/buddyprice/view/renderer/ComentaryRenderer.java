package br.com.buddyprice.view.renderer;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;

import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.composer.ViewOfferComposer;
import br.com.vexillum.util.Return;

public class ComentaryRenderer implements ListitemRenderer<Comentario> {

	private Usuario userLogged;
	
	private ViewOfferComposer composer;
	
	public ComentaryRenderer(Usuario userLogged, ViewOfferComposer composer) {
		super();
		this.userLogged = userLogged;
		this.composer = composer;
	}

	@Override
	public void render(Listitem item, Comentario coment, int arg2) throws Exception {
		item.appendChild(new Listcell(coment.getAvaliacao().toString()));
		item.appendChild(new Listcell(coment.getUsuario().getName() + " diz:"));
		
		Hbox hboxComent = new Hbox();
		hboxComent.setHflex("1");
		Label labelComentario = getLabelComentario(coment);
		Textbox textboxComentario = getTextboxComentario(coment, labelComentario);
		hboxComent.appendChild(textboxComentario);
		hboxComent.appendChild(labelComentario);
		Listcell cellComentario = new Listcell();
		cellComentario.appendChild(hboxComent);
		item.appendChild(cellComentario);
		
		if(coment.getUsuario().equals(this.userLogged)){
			Listcell cell = new Listcell();
			
			Hbox hbox = new Hbox();
			hbox.appendChild(getEditarButton(coment, textboxComentario, labelComentario));
			Separator sep = new Separator();
			sep.setWidth("10px");
			hbox.appendChild(sep);
			hbox.appendChild(getExcluirButton(coment, item));
			
			cell.appendChild(hbox);
			item.appendChild(cell);
		}
	}

	private Textbox getTextboxComentario(final Comentario comentario, final Label lbComent) {
		final Textbox text = new Textbox(comentario.getComentario());
		text.setInplace(true);
		text.setHflex("1");
		text.setVisible(false);
		
		text.addEventListener("onOK", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				text.setFocus(false);
			}
		});
		
		text.addEventListener("onBlur", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				text.setVisible(false);
				lbComent.setVisible(true);
			}
		});
		
		text.addEventListener("onChange", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				comentario.setComentario(text.getValue());
				composer.setSelectedComentario(comentario);
				Return ret = composer.editComentary();
				if(ret.isValid()){
					lbComent.setValue(text.getValue());
				} else {
					text.setValue(lbComent.getValue());
				}
			}
		});
		return text;
	}
	
	private Label getLabelComentario(Comentario comentario) {
		Label label = new Label(comentario.getComentario());
		label.setHflex("1");
		return label;
	}

	private A getExcluirButton(final Comentario coment, final Listitem item) {
		A button = new A("Excluir");
		button.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				composer.setSelectedComentario(coment);
				composer.showModalDeleteComentary(item);
			}
		});
		return button;
	}

	private A getEditarButton(final Comentario coment, final Textbox tbComent, final Label lbComent) {
		A button = new A("Editar");
		button.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				tbComent.setVisible(true);
				tbComent.setFocus(true);
				lbComent.setVisible(false);
			}
		});
		return button;
	}

}

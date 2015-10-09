package br.com.buddyprice.view.renderer;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;

import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.composer.ViewOfferComposer;

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
		item.appendChild(new Listcell(coment.getComentario()));
		if(coment.getUsuario().equals(this.userLogged)){
			Listcell cell = new Listcell();
			
			Hbox hbox = new Hbox();
			hbox.appendChild(getEditarButton(coment));
			Separator sep = new Separator();
			sep.setWidth("10px");
			hbox.appendChild(sep);
			hbox.appendChild(getExcluirButton(coment));
			
			cell.appendChild(hbox);
			item.appendChild(cell);
		}
	}

	private A getExcluirButton(final Comentario coment) {
		A button = new A("Excluir");
		button.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				composer.setSelectedComentario(coment);
				composer.deleteComentary();
			}
		});
		return button;
	}

	private A getEditarButton(final Comentario coment) {
		A button = new A("Editar");
		button.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				composer.setSelectedComentario(coment);
				composer.editComentary();
			}
		});
		return button;
	}

}

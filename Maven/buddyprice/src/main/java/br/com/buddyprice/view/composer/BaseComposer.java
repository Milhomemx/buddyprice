package br.com.buddyprice.view.composer;

import java.io.File;
import java.util.List;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Row;

import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.model.ICommonEntity;
import br.com.vexillum.view.CRUDComposer;

@SuppressWarnings("serial")
public abstract class BaseComposer<E extends ICommonEntity, G extends GenericControl<E>>
		extends CRUDComposer<E, G> {

	private CRUDComposer<?, ?> parentComposer;

	public CRUDComposer<?, ?> getParentComposer() {
		return parentComposer;
	}

	public void setParentComposer(CRUDComposer<?, ?> parentComposer) {
		this.parentComposer = parentComposer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setParentComposer((CRUDComposer<E, G>) arg.get("thisComposer"));
	}
	
	protected void loadBinderParentComposer() {
		getParentComposer().loadBinder();
	}
	
	public void loadImages(Grid grid){
		List<Component> list = grid.getRows().getChildren();
		for(Component row : list){
			Usuario user = ((Row)row).getValue();
			showImageProfile((Image) getComponentByType(row, "image"), user, "67x67");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showImageProfile(Image comp, String widthHeight) {
		Attachment att = new AttachmentMedia();
		String name = "image_profile" + ((widthHeight != null && !widthHeight.isEmpty()) ? ("_" + widthHeight) : "");
		try {
			File image = att.getAttachment(name, getUserInSession());
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void showImageProfile(Image comp, Object user, String widthHeight) {
		Attachment att = new AttachmentMedia();
		String name = "image_profile" + ((widthHeight != null && !widthHeight.isEmpty()) ? ("_" + widthHeight) : "");
		try {
			File image = att.getAttachment(name, (Usuario) user);
			if (image != null) {
				comp.setContent(new AImage(image));
			}
		} catch (Exception e) {
			new ExceptionManager(e).treatException();
		}
	}
}

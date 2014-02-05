package br.com.buddyprice.view.composer;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Window;

import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ProfileComposer extends UsuarioComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setEntity((Usuario) getUserInSession());
		loadBinder();
	}

	private String actualPassword;
	private String newPassword;
	private String confirmNewPassword;

	public String getActualPassword() {
		return actualPassword;
	}

	public void setActualPassword(String actualPassword) {
		this.actualPassword = actualPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	@SuppressWarnings("unchecked")
	public void deleteProfileImage() {
		@SuppressWarnings("rawtypes")
		Attachment att = new AttachmentMedia();
		Return ret = new Return(true);
		if (ret.isValid()) {
			att.deleteAttachment("image_profile", getUserInSession());
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void changeProfileImage(UploadEvent event) {
		Media media = event.getMedia();
		ImageValidator val = new ImageValidator(media);
		Return ret = val.upload();
		if (ret.isValid()) {
			Attachment att = new AttachmentMedia();
			att.uploadAttachment(media, "image_profile", getUserInSession());
			Executions.sendRedirect("");
		}
		treatReturn(ret);
	}

	public Return deactivate() {
		Executions.sendRedirect("/buddyprice/logout");
		return getControl().doAction("deactivate");

	}

	public void callModalWindow(String page) {
		Map<String, Object> map = ReflectionUtils
				.prepareDataForPersistence(this);

		Component comp = Executions.createComponents(page, null, map);

		if (comp instanceof Window) {
			((Window) comp).doModal();
		}
	}

	public Return updateInformation() {
		Return ret = null;
		ret = saveEntity();
		if (ret.isValid()) {
			Executions.sendRedirect("edit.zul?sucess=true");
			component.detach();
		}
		return ret;
	}

	public void changePasswordUser() {
		Return ret = new Return(true);
		ret.concat(getControl().doAction("changePasswordUser"));
		if (ret.isValid())
			getComponentById(getComponent(), "frmChangePassword").detach();
		treatReturn(ret);
	}

}
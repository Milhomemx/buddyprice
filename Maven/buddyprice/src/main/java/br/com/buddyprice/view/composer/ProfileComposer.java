package br.com.buddyprice.view.composer;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Window;

import br.com.buddyprice.control.UsuarioController;
import br.com.buddyprice.model.Usuario;
import br.com.buddyprice.view.attachments.AttachmentMedia;
import br.com.buddyprice.view.validator.ImageValidator;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ProfileComposer extends CRUDComposer<Usuario, UsuarioController> {

	private String actualPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String nameUser;
	private Usuario user;
	
	private String thisContextPath;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setThisContextPath(getContextPath());
		setUser((Usuario) getUserInSession());
		setNameUser(getUser().getName());
		loadBinder();
	}

	public String getThisContextPath() {
		return thisContextPath;
	}

	public void setThisContextPath(String thisContextPath) {
		this.thisContextPath = thisContextPath;
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

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

	public void deactivate() {
		showActionConfirmation("Deseja realmente desativar sua conta?", "realyDeactivate");
	}
	
	public Return realyDeactivate(){
		entity = user;
		Return ret = getControl().doAction("deactivate");
		if(ret.isValid())
			Executions.sendRedirect("/buddyprice/logout");
		return ret;
	}

	public void callModalWindow(String page) {
		Map<String, Object> map = ReflectionUtils
				.prepareDataForPersistence(this);

		Component comp = Executions.createComponents(page, null, map);

		if (comp instanceof Window) {
			((Window) comp).doModal();
		}
	}

	public void updateInformation() {
		Return ret = new Return(true);
		entity = getUser();
		ret = getControl().doAction("updateInformation");
		if (ret.isValid()) {
			Executions.sendRedirect("edit.zul?sucess=true");
			component.detach();
		}
		treatReturn(ret);
		loadBinder();
	}

	public void changePasswordUser() {
		Return ret = new Return(true);
		entity = user;
		ret.concat(getControl().doAction("changePasswordUser"));
		if (ret.isValid())
			getComponentById(getComponent(), "frmChangePassword").detach();
		treatReturn(ret);
	}

	@Override
	public UsuarioController getControl() {
		return SpringFactory.getController("usuarioController",
				UsuarioController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}


	@Override
	public Usuario getEntityObject() {
		return new Usuario();
	}

}
package br.com.buddyprice.view.attachments;

import java.io.File;
import java.io.InputStream;

import org.zkoss.io.Files;
import org.zkoss.util.media.Media;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.ZKUtils;

/**
 * @author Natan
 * Classe que anexa imagens. Recebe um par�metro de caminho (din�mico) e nome da imagem.
 */
public class AttachmentMedia implements Attachment<Media, Usuario> {

	final String FOLDERATTACHMENTS = "profiles";
	final String PATH = "C:"+File.separator+"Images";

	@Override
	public Return uploadAttachment(Media file, String name, Usuario user) {
		Return ret = new Return(true);
		try {
			InputStream in = ZKUtils.mediaToStream(file);
			Files.copy(new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + user.getId() + File.separator + name), in);
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;		
	}

	@Override
	public Return deleteAttachment(String name, Usuario user) {
		Return ret = new Return(true);
		try {
			File f = getAttachment(name, user);
			if(f == null || f.exists()){
				f.delete();
			}	
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}

	@Override
	public File getAttachment(String name, Usuario user) {
		File f = new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + user.getId() + File.separator + name); 
		if(!f.exists()) return null;
		return f;
	}

}

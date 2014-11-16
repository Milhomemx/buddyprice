package br.com.buddyprice.view.attachments;

import java.io.File;
import java.io.InputStream;

import org.zkoss.io.Files;
import org.zkoss.util.media.Media;

import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.ZKUtils;

/**
 * @author Natan
 * Classe que faz upload das imagens de estabelecimentos cadastrados/alterados.
 */
public class AttachmentMediaEstablishment implements Attachment<Media, Estabelecimento> {

	final String FOLDERATTACHMENTS = "establishment";
	final String PATH = "C:"+File.separator+"Images";

	@Override
	public Return uploadAttachment(Media file, String name, Estabelecimento establishment) {
		Return ret = new Return(true);
		try {
			InputStream in = ZKUtils.mediaToStream(file);
			Files.copy(new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + establishment.getId() + File.separator + name), in);
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;		
	}

	@Override
	public Return deleteAttachment(String name, Estabelecimento establishment) {
		Return ret = new Return(true);
		try {
			File f = getAttachment(name, establishment);
			if(f == null || f.exists()){
				f.delete();
			}	
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}

	@Override
	public File getAttachment(String name, Estabelecimento establishment) {
		File f = new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + establishment.getId() + File.separator + name); 
		if(!f.exists()) return null;
		return f;
	}

}

package br.com.buddyprice.view.attachments;

import java.io.File;
import java.io.InputStream;

import org.zkoss.io.Files;
import org.zkoss.util.media.Media;

import br.com.buddyprice.model.Produto;
import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.control.util.Attachment;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.ZKUtils;

/**
 * @author Natan
 * Classe que faz upload das imagens de produtos cadastrados/alterados.
 */
public class AttachmentMediaProduct implements Attachment<Media, Produto>{
	
	
	final String FOLDERATTACHMENTS = "product";
	final String PATH = "C:"+File.separator+"Images";

	@Override
	public Return uploadAttachment(Media file, String name, Produto product) {
		Return ret = new Return(true);
		try {
			InputStream in = ZKUtils.mediaToStream(file);
			Files.copy(new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + product.getId() + File.separator + name), in);
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;		
	}

	@Override
	public Return deleteAttachment(String name, Produto product) {
		Return ret = new Return(true);
		try {
			File f = getAttachment(name, product);
			if(f == null || f.exists()){
				f.delete();
			}	
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}

	@Override
	public File getAttachment(String name, Produto product) {
		File f = new File(PATH + File.separator + FOLDERATTACHMENTS + File.separator + product.getId() + File.separator + name); 
		if(!f.exists()) return null;
		return f;
	}

}

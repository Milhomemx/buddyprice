package br.com.buddyprice.view.validator;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;

import br.com.vexillum.control.manager.ExceptionManager;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.ZKUtils;


/**
 * @author Natan
 * Classe responsável por validar dimensões, tamanho e extensão da imagem enviada via upload HTTP.
 * Os parâmetros para tais informações podem ser alterados na própria classe ou poderá ser definido via arquivo .properties
 */
public class ImageValidator{
	
	Media media;

	public ImageValidator(Media media) {
		this.media = media;
	}
	
	/**
	 * @return
	 * Retorna o resultado das validações efetuadas nesta classe,
	 */
	public Return upload(){
		Return ret = new Return(true);
		ret.concat(maxSize());
		ret.concat(contentType());
		ret.concat(maxHeightAndWidth());
		return ret;
	}
	
	/**
	 * @return
	 * Verifica se a imagem enviada possui o tamanho igual ou menor ao mínimo definido.
	 */
	private Return maxSize(){
		Return ret = new Return(true);
		if(media.getByteData().length > 1000*500){
			ret.concat(new Return(false, new Message(null, "A imagem não pode ter tamanho superior a 500KB!")));
		}
		return ret;
	}
	
	/**
	 * @return
	 * Verifica se a imagem enviada se enquadra nos formatos parametrizados.
	 */
	private Return contentType(){
		Return ret = new Return(true);
		if(!media.getFormat().equalsIgnoreCase("png") && !media.getFormat().equalsIgnoreCase("gif") && !media.getFormat().equalsIgnoreCase("jpg") && !media.getFormat().equalsIgnoreCase("jpeg")){
			ret.concat(new Return(false, new Message(null, "A imagem não suportada! A imagem dever ser JPG, PNG ou GIF!")));
		}
		return ret;
	}
	
	/**
	 * @return
	 * Verifica se a imagem enviada possui o dimensões iguais ou menores ao mínimo definido.
	 */
	private Return maxHeightAndWidth(){
		Return ret = new Return(true);
		try {
			AImage img = (media instanceof AImage) ? (AImage) media : new AImage("photo", ZKUtils.mediaToStream(media));
			if(img.getHeight() > 150 || img.getWidth() > 150){
				ret.concat(new Return(false, new Message(null, "A imagem não pode ter resolução superior a 150x150!")));
			}
		} catch (Exception e) {
			ret.concat(new ExceptionManager(e).treatException());
		}
		return ret;
	}	

}

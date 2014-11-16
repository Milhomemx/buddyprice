package br.com.buddyprice.view.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.RadiogroupSelectedItemConverter;

/**
 * @author Natan
 * @param <T>
 * Utilitário para enumerar um radioGroup de dados.
 */
@SuppressWarnings({ "serial", "rawtypes", "deprecation" })
public class EnumRadiogroupConverter<T extends Enum> extends RadiogroupSelectedItemConverter {
    private final Class<T> className;

    public EnumRadiogroupConverter(Class<T> className) {
        this.className = className;
    }

    @Override
    public Object coerceToUi(Object val, Component component) {
    	if (val instanceof Enum) {
            Enum value = (Enum) val;
            String constanteName = value.name();
            return super.coerceToUi(constanteName, component);
        } else if (val == null){
            return null;
        } else {
            throw new IllegalArgumentException("val Object must be an Enum");
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object coerceToBean(Object val, Component component) {
        String enumName = (String) super.coerceToBean(val, component);
        return Enum.valueOf(className, enumName);
    }
}

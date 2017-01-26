package misc;

import java.text.NumberFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class CustomPrimitiveNumberEditor extends CustomNumberEditor {
	private final boolean allowEmpty;
	public CustomPrimitiveNumberEditor(Class<? extends Number> numberClass,
			NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
		super(numberClass, numberFormat, allowEmpty);
		this.allowEmpty = allowEmpty;
	}
	public CustomPrimitiveNumberEditor(Class<? extends Number> numberClass,
			boolean allowEmpty)	throws IllegalArgumentException {
		this(numberClass, null, allowEmpty);
	}
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if((text != null && text.trim().length() != 0) || (allowEmpty==false)) {
			super.setAsText(text);
		} else {
			setValue(0);
		}
	}
}

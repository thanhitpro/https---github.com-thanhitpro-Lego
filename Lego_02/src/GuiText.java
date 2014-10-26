
import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;
import remixlab.dandelion.geom.Vec;

/**
 * 
 */

/**
 * @author Tran
 *
 */
public class GuiText {
	private String content;
	private Vec position;
	private Color color;
	private PFont font;
	private String fontName;
	private PApplet parent;
	private int fontSize;
	private int textAlign;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the position
	 */
	public Vec getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Vec position) {
		this.position = position;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the font
	 */
	public PFont getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(PFont font) {
		this.font = font;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName
	 *            the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @return the textAlign
	 */
	public int getTextAlign() {
		return textAlign;
	}

	/**
	 * @param textAlign
	 *            the textAlign to set
	 */
	public void setTextAlign(int textAlign) {
		this.textAlign = textAlign;
	}

	@SuppressWarnings("static-access")
	public GuiText(PApplet parent) {
		super();
		this.parent = parent;
		parent.smooth();
		//parent.stroke(parent.SQUARE);

		fontSize = Util.FONT_SIZE_DEFAULT;
		fontName = Util.FONT_FILE_NAME_DEFAULT;
		textAlign = parent.LEFT;
		color = new Color(255, 0, 0);

		loadFont();
	}

	public void loadFont() {
		font = parent.loadFont(fontName);
	}

	public void draw() {
		parent.fill(color.getRGB());
		parent.textFont(font, fontSize);
		parent.textAlign(textAlign);
		parent.text(content, position.x(), position.y());
	}
}

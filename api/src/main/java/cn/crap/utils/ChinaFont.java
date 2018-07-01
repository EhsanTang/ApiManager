package cn.crap.utils;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

public class ChinaFont implements FontProvider {

	// TODO 字体可配置，和图形验证码字体保持一致即可
	@Override
	public boolean isRegistered(String fontname) {
		return false;
	}

	public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {

		try {
			BaseFont bfChinese;
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			return new Font(bfChinese, size, style, color);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
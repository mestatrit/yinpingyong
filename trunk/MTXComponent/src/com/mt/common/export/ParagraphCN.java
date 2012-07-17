package com.mt.common.export;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;

/**
 * 用于中文的itext pdf 显示
 * @author @CL
 *
 */
@SuppressWarnings("serial")
public class ParagraphCN extends Paragraph{

	private static Logger logger = LoggerFactory.getLogger(ParagraphCN.class);
	
	private static int defaultFontSize = 12;
	
	private static int defaultFontStyle = Font.NORMAL;
	
	public ParagraphCN(String content){
		super(content,getChineseFont());
	}
	
	public ParagraphCN(String content,int fontSize){
		super(content,getChineseFont(fontSize));
	}
	
	public ParagraphCN(String content,int fontSize,int fontStyle){
		super(content,getChineseFont(fontSize,fontStyle));
	}
	
	/**
	 * add the chunkCN getter
	 * @param content
	 * @return
	 */
	public static Chunk getChunk(String content){
		return new Chunk(content,getChineseFont());
	}
	
	public static Chunk getChunk(String content,int fontSize){
		return new Chunk(content,getChineseFont(fontSize));
	}
	
	public static Chunk getChunk(String content,int fontSize,int fontStyle){
		return new Chunk(content,getChineseFont(fontSize,fontStyle));
	}
	
	/**
	 * 设置默认中文字体 可供利用的中文字体有
	 * STSong-Light，AdobeSongStd-Light-Acro，及STSongStd-Light-Acro 
	 * 支持的编码有：
	 * UniGB-UCS2-H，UniGB-UCS2-V，UniGB-UTF16-H，UniGB- UTF16-V，
	 * GB-EUC-H，GB-EUC-V，GBpc-EUC-H，GBpc-EUC-V， 
	 * GBK-EUC-H，GBK-EUC-V，GBKp-EUC-H，GBKp-EUC-V， GBK2K-H，及GBK2K-V。
	 * 各编码的定义如下:
	 * 
	 * UniGB-UCS2-H 
	 * UniGB-UCS2-V Unicode (UCS-2) encoding for the Adobe-GB1 character collection 
	 * 
	 * UniGB-UTF16-H 
	 * UniGB-UTF16-V Unicode (UTF-16BE) encoding for the Adobe-GB1 character collection.Contains mappings for all
	 * 					characters in the GB18030-2000 character set. 
	 * 
	 * GB-EUC-H 
	 * GB-EUC-V Microsoft Code Page 936 (charset 134), GB 2312-80 character set, EUC-CN encoding
	 * 
	 * GBpc-EUC-H 
	 * GBpc-EUC-V Macintosh, GB 2312-80 character set, EUC-CN encoding, Script Managercode 2 
	 * 
	 * GBK-EUC-H 
	 * GBK-EUC-V Microsoft Code Page 936 (charset 134), GBK character set, GBK encoding 
	 * 
	 * GBKp-EUC-H 
	 * GBKp-EUC-V Same as GBK-EUC-H, but replaces half-width Latin characters
	 * 				withproportional forms and maps code 0x24 to dollar ($) instead of yuan (￥). 
	 * 
	 * GBK2K-H 
	 * GBK2K-V 		GB 18030-2000 character set, mixed 1-, 2-, and 4-byte encoding
	 * 
	 * 编码以-H结尾的，表示字体将会横向输出；以 –V结尾的，表示字体将会纵向输出。
	 * 以Uni开头的是Unicode类编码，如果你的输入字符串是Unicode，则应选择此类编码。
	 * 以GB开头的是CP936类编码，如果你的输入字符串是Code Page 936，则应选择此类编码
	 * 
	 * @param fontSize
	 * @return
	 */
	public static final Font getChineseFont(int fontSize,int fontStyle) {
        Font FontChinese = null;
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            FontChinese = new Font(bfChinese, fontSize, fontStyle);
        } catch (DocumentException de) {
            logger.error(de.getMessage(),de);;
        } catch (IOException ioe) {
        	logger.error(ioe.getMessage(),ioe);;
        }
        return FontChinese;
    }
	
	/**
	 * fontstyle = normal
	 * @param fontSize
	 * @return
	 */
	public static final Font getChineseFont(int fontSize) {
        return getChineseFont(fontSize,defaultFontStyle);
    }
	
	/**
	 * fontsize = 12
	 * fontstyle = normal
	 * @return
	 */
	public static final Font getChineseFont() {
       return getChineseFont(defaultFontSize,defaultFontStyle);
    }
}

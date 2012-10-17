/**
 * Copyright Moderntimes
 *
 * $Id: DoubleCommaDoc.java 22391 2012-04-10 11:15:24Z hanhui $
 *
 * $Log$
 * Revision 1.6  2008/07/03 09:04:57  cailei
 * �����ʱ䶯�����е�bp �������븺��
 *
 * Revision 1.5  2008/07/03 08:44:51  cailei
 * ʹ�ÿ������븺��
 *
 * Revision 1.4  2008/07/03 08:21:39  cailei
 * ʹ�ÿ������븺��
 *
 * Revision 1.3  2007/09/11 05:44:03  cailei
 * С��λ��������
 *
 * Revision 1.1.2.11  2007/07/27 03:16:37  walker
 * fix detect length bug.
 *
 * Revision 1.1.2.10  2007/07/26 02:05:21  walker
 * fix maxInt bug
 *
 * Revision 1.1.2.9  2007/07/26 02:02:40  walker
 * fix maxFrac / maxInt bug
 *
 * Revision 1.1.2.8  2007/07/25 16:21:46  walker
 * add set the max and min IntegerDigitals
 *
 * Revision 1.1.2.7  2007/07/18 02:06:51  walker
 * remove log to simple the log files.
 *
 * Revision 1.1.2.6  2007/07/10 07:01:49  walker
 * if text == "." , do not parse it.
 *
 * Revision 1.1.2.5  2007/07/09 01:45:56  walker
 * fix bug if user delete the last word.
 *
 * Revision 1.1.2.4  2007/07/06 08:17:12  walker
 * add auto format when delete char.
 *
 * Revision 1.1.2.3  2007/07/06 07:16:14  walker
 * fix the bug that the position will move to end if type in the middle.
 *
 * Revision 1.1.2.2  2007/04/17 10:37:55  iyuan
 * original code restored
 *
 * Revision 1.1.2.1  2007/04/13 10:47:52  iyuan
 * new algorithm
 *
 * Revision 1.1  2006/08/10 08:34:19  leafy
 * Init import from IRTS to BOC
 *
 * Revision 1.3  2006/07/25 11:25:49  iyuan
 * reject negative numbers
 *
 * Revision 1.2  2005/07/20 01:45:21  leafy
 * Convert to S-Chinese in UTF-8
 *
 * Revision 1.1  2005/07/19 10:04:04  iyuan
 * From MoneyLine Project, 2005/07/19
 *
 * Revision 1.1  2005/07/19 08:03:58  iyuan
 * From IRS, 2005/07/19
 *
 * Revision 1.1  2005/07/19 03:11:37  iyuan
 * Copy of MoneyLine 2005/07/19
 *
 * Revision 1.7  2005/01/12 04:12:24  leafy
 * *** empty log message ***
 *
 * Revision 1.5  2004/09/17 09:54:10  leafy
 * Add serialization UID
 *
 * Revision 1.4  2004/06/09 03:41:09  leafy
 * 1. Change logging to use JDK1.4 loggin API
 * 2. Add a new logging config file to settings
 *
 * Revision 1.3  2004/06/03 03:57:37  leafy
 * Huge refactoring!
 *
 * Revision 1.2  2004/03/24 09:25:48  leafy
 * Organized Import
 *
 * Revision 1.1  2004/03/08 03:35:00  leafy
 * Init for MoneyLine
 *
 * Revision 1.1  2003/07/03 06:55:26  leafy
 * *** empty log message ***
 *
 * Revision 1.7  2003/03/13 02:26:57  leafy
 * Remove unused local methods and variables
 *
 * Revision 1.6  2003/03/12 08:25:06  leafy
 * Enforce some coding guidelines
 * 1. Always remove excessive import
 * 2. imports >4 will be replaced by *
 * 3. static references should always start with Class instead of object
 * 4. Remove useless assignments
 *
 * Revision 1.5  2003/02/27 04:11:33  iyuan
 * bug fixes
 *
 * Revision 1.4  2003/02/25 04:36:48  iyuan
 * fix a bug when MaxFractionDigit==0
 *
 * Revision 1.3  2003/02/24 02:34:15  iyuan
 * 可在小数点后输入0
 *
 * Revision 1.2  2003/01/28 08:55:43  leafy
 * Some comments added, but you are not expected to
 * understand this
 *
 * Revision 1.1  2003/01/28 08:48:03  leafy
 * Rewritten
 *
 *
 */
package com.mt.common.gui.model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 文本框数字控制模型
 *
 * @author leafy
 */
public class DoubleCommaDoc extends PlainDocument {

    private static final long serialVersionUID = 4994102977297139443L;
    private NumberFormat nf = NumberFormat.getInstance();
    private NumberFormat f = NumberFormat.getInstance();
    int maxFrac;
    int minFrac;
    int maxInt;
    int minInt;

    /**
     * 
     * @param maxIntegerDigits 最大整数位
     * @param minIntegerDigits 最小整数位
     * @param maxFractionDigits 最大小数位
     * @param minFractionDigits 最小小数位
     */
    public DoubleCommaDoc(int maxIntegerDigits, int minIntegerDigits, int maxFractionDigits, int minFractionDigits) {
        super();
        nf.setMaximumIntegerDigits(maxIntegerDigits);
        nf.setMinimumIntegerDigits(minIntegerDigits);
        nf.setMaximumFractionDigits(maxFractionDigits);
        nf.setMinimumFractionDigits(minFractionDigits);

        f.setMaximumIntegerDigits(maxIntegerDigits);
        f.setMinimumIntegerDigits(minIntegerDigits);

        maxFrac = maxFractionDigits;
        minFrac = minFractionDigits;
        maxInt = maxIntegerDigits;
        minInt = minIntegerDigits;
    }

    //是否需要格式化数字
    boolean needComma = true;

    public DoubleCommaDoc(int maxIntegerDigits, int minIntegerDigits,
                          int maxFractionDigits, int minFractionDigits, boolean comma) {
        this(maxIntegerDigits, minIntegerDigits, maxFractionDigits, minFractionDigits);
        this.needComma = comma;
    }

    //是否可以输入负数
    boolean valiedNegative = false;

    public DoubleCommaDoc(int maxIntegerDigits, int minIntegerDigits,
                          int maxFractionDigits, int minFractionDigits, boolean comma, boolean validNegative) {
        this(maxIntegerDigits, minIntegerDigits, maxFractionDigits, minFractionDigits);
        this.needComma = comma;
        this.valiedNegative = validNegative;
    }

    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);

        String oldText = super.getText(0, super.getLength());
        if (oldText.equals("") || oldText.equals(".") || oldText.equals("-")) {
            return;
        }
        int leng = getLength();
        String formattedStr;
        try {
            formattedStr = nf.format(new BigDecimal(oldText.replaceAll(",", "")));
            if (offset < leng) {
                String frontStr = formattedStr.substring(0, offset);
                String rearStr = formattedStr.substring(offset);
                super.insertString(leng, rearStr, null);
                super.remove(0, leng);
                super.insertString(0, frontStr, null);
            } else {
                super.insertString(leng, formattedStr, null);
                super.remove(0, leng);
            }
        } catch (Exception e) {
            //logger.error(e.getMessage(),e);;
        }
    }

    /**
     * @see javax.swing.text.Document#insertString(int, String, AttributeSet)
     */
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        String oldText = super.getText(0, super.getLength());

//		UtilFunc.log(this, "oldText=[" + oldText + "], str=[" + str + "]");

        nf.setGroupingUsed(needComma);
        f.setGroupingUsed(needComma);

        // Cannot add '-' to a negative number, no matter where the
        // insert offset is
        if (str.equals("-") && offset > 0) {
            return;
        } else if (str.equals("-")) {
            if (oldText.startsWith("-")) { // '-' + "-aabbcc"
                return;
            }
            if (valiedNegative) {
                super.insertString(0, str, a);
            }
        } else if (str.equals(".")) {
            if (oldText.indexOf(".") >= 0) {
                return;
            } else if (oldText.equals("")) { // single '.'
                super.insertString(offset, str, a);
            } else if (offset == getLength()) { // ending '.'
                try {
                    if (maxFrac == 0) {
                        return;
                    }
                    if (oldText.equals("-0")) {
                        super.insertString(2, str, a);
                    } else {
                        super.remove(0, getLength());
                        super.insertString(0, nf.format(new BigDecimal(oldText.replaceAll(",", ""))) + str, a);
                    }

                } catch (Exception pe) {
                    //UtilFunc.log(this, pe);
                }
            } else { // somewhere in the middle or beginning
                try {
                    int leng = getLength();
                    String formattedStr = nf.format(new BigDecimal(new StringBuffer(oldText).insert(offset, str).toString().replaceAll(",", "")));
                    String frontStr = formattedStr.substring(0, formattedStr.indexOf(".") + 1);
                    String rearStr = formattedStr.substring(formattedStr.indexOf(".") + 1);
                    super.insertString(leng, rearStr, a);
                    super.remove(0, leng);
                    super.insertString(0, frontStr, a);
                } catch (Exception pe) {
                    //UtilFunc.log(this, pe);
                }
            }
        } else { // str cannot be "-" here
            if (str.trim().equals("")) {
                super.remove(0, getLength());
                return;
            }
            if (str.equals("0") && oldText.equals("-")) {
                super.insertString(1, str, a);
                return;
            }
            try {
                int remindOff = getLength() - offset;

                // ///////////////////////////////
                String newStr = new StringBuffer(oldText).insert(offset, str).toString();
                int tlen = newStr.replaceAll(",", "").length();
                int pivot = newStr.replaceAll(",", "").lastIndexOf(".");

                BigDecimal db;
                if (pivot != -1) {
                    int tunit = tlen - pivot - 1; // 已输入位数
                    int digit = tlen - tunit - 1;

                    if (digit > maxInt || tunit > maxFrac) {
                        return;
                    }

                    f.setMaximumFractionDigits(tunit);
                    f.setMinimumFractionDigits(tunit);
                } else {
                    if (newStr.replaceAll(",", "").length() > maxInt) {
                        return;
                    }

                    f.setMaximumFractionDigits(maxFrac);
                    f.setMinimumFractionDigits(minFrac);
                }

                db = new BigDecimal(newStr.replaceAll(",", ""));
                String formatedStr = f.format(db);
                String frontStr = formatedStr.substring(0, formatedStr.length() - remindOff);

                if (offset == getLength()) {
                    super.remove(0, getLength());
                    super.insertString(0, f.format(db), a);
                } else {
                    super.remove(0, offset);
                    super.insertString(0, frontStr, a);
                }

            } catch (Exception pe) {
                //UtilFunc.log(this, pe);
            }
        }
    }
}

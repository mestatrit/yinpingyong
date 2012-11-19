package test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.CommonMsg;
import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

/**
 * @author:Ryan
 * @date:2012-11-19
 */
public class FieldMapSetTester4 {
	
	public static final String UTCTimeStampe = "yyyyMMdd-HH:mm:ss";
	
	public static Date getDateValue(String dateValue) {
		
		if (dateValue != null && dateValue.equals("") == false) {
			SimpleDateFormat sf = new SimpleDateFormat(UTCTimeStampe);
	        sf.setLenient(false);
	        try {
	            return sf.parse(dateValue);
	        } catch (ParseException e) {
	            return null;
	        }
		} else {
			return null;
		}
	}
	
	public static String getDateString(Date date, String formatString) {
        return new SimpleDateFormat(formatString).format(date);
    }
	
	private Map<PaymentSchd, FieldMapSet> processFieldMapSet(FieldMapSet fms) {
		
		Map<PaymentSchd, FieldMapSet> fmsMap = new HashMap<PaymentSchd, FieldMapSet>();
		
		int count = fms.getFieldMapCount();
		
		for (int index = 0;index<count;index++) {
			FieldMap fm = fms.getFieldMap(index);
			fm.setName("MAP");
			String portfolioSeq = fm.getStringValue("PORTFOLIO_SEQ");
			String serialNumber = fm.getStringValue("SERIAL_NUMBER");
			Date modifyDate = getDateValue(fm.getStringValue("MODIFY_DATE"));
			
			PaymentSchd ps = new PaymentSchd();
			ps.setPortfolioSeq(portfolioSeq);
			ps.setSerialNumber(serialNumber);
			
			if (fmsMap.containsKey(ps)) {
				FieldMapSet fieldMapSet = fmsMap.get(ps);
				fieldMapSet.addFieldMap(fm);
				Field attr = fieldMapSet.getAttr("MODIFY_DATE");
				if (attr != null) {
					Date tempDate = getDateValue(attr.getStringValue());
					if (tempDate != null && tempDate.before(modifyDate)) {
						fieldMapSet.setAttr("MODIFY_DATE", getDateString(modifyDate, UTCTimeStampe));
					}	
				}
			} else {
				FieldMapSet temp = new FieldMapSet("detail");
				temp.setAttr("PORTFOLIO_SEQ", portfolioSeq);
				temp.setAttr("SERIAL_NUMBER", serialNumber);
				temp.setAttr("MODIFY_DATE", getDateString(modifyDate, UTCTimeStampe));
				temp.addFieldMap(fm);
				fmsMap.put(ps, temp);
			}
		}
		
		return fmsMap;
	}
	
	private String transfer2CommonMsg(Map<PaymentSchd, FieldMapSet> fmsMap) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(FieldMapUtil.XMLHeader);
		stringBuilder.append("<PAYMENT_SCHD>");
		Iterator<Entry<PaymentSchd, FieldMapSet>> it = fmsMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<PaymentSchd, FieldMapSet> entry = it.next();
			stringBuilder.append(FieldMapUtil.createXMLString(entry.getValue(), false));
		}
		stringBuilder.append("</PAYMENT_SCHD>");
		
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		FieldMapSetTester4 t4 = new FieldMapSetTester4();
		
		Document doc = XMLUtil.createDocumentFromPath("/NewFile3.xml");
		Element root = doc.getDocumentElement();
		NodeList children = root.getChildNodes();
		for(int index = 0; index < children.getLength(); index++) {
			
			Node child = children.item(index);
			
			if(child instanceof Element) {
				Element childElement = (Element) child;
				String name = childElement.getTagName();
				if (name.equals("PAYMENT_SCHD")) {
					FieldMapSet fms = FieldMapUtil.createFieldMapSet(childElement);
					Map<PaymentSchd, FieldMapSet> fmsMap = t4.processFieldMapSet(fms);
					String str = t4.transfer2CommonMsg(fmsMap);
					doc.replaceChild(XMLUtil.createDocument(str).getDocumentElement(), childElement);
				}
			}
		}
	}
	
	class PaymentSchd{
		
		private String portfolioSeq;
		
		private String serialNumber;
		
		public String getPortfolioSeq() {
			return portfolioSeq;
		}

		public void setPortfolioSeq(String portfolioSeq) {
			this.portfolioSeq = portfolioSeq;
		}

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		@Override
		public int hashCode() {
			int hash = 7;
	        hash = 31 * hash + portfolioSeq.hashCode() + serialNumber.hashCode();
	        return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PaymentSchd) {
				PaymentSchd temp = (PaymentSchd)obj;
				if (temp.getPortfolioSeq().equals(portfolioSeq) && temp.getSerialNumber().equals(serialNumber)) {
					return true;
				} else {
					return false;	
				}
			} else {
				return false;
			}
		}
	}
}

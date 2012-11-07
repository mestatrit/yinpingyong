package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

/**
 * @author:Ryan
 * @date:2012-11-7
 */
public class FieldMapSetTester2 {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {

		Document doc = XMLUtil.createDocumentFromPath("/NewFile1.xml");
		Element root = doc.getDocumentElement();
		NodeList children = root.getElementsByTagName("SET");
		for(int index = 0; index < children.getLength(); index++ ) {
			Node child = children.item(index);
			if(child instanceof Element) {
				Element childElement = (Element) child;
				String tagName = childElement.getTagName();
				if (tagName.equalsIgnoreCase("SET")) {
					FieldMapSet fms = FieldMapUtil.createFieldMapSet(childElement);
					System.err.println("=============================================");
					System.out.println(FieldMapUtil.createXMLString(fms));
				}
			}
		}
		
	}
}

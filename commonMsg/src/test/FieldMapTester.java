package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

public class FieldMapTester {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		/**
		 * 构造FieldMap对象
		 */
		FieldMap fm = new FieldMap("root");
		fm.putField("node1", "n1");
		fm.putField("node2", "n2");
		fm.putField("node3", "n3");
		fm.putField("node4", "n4");;
		System.out.println(FieldMapUtil.createXMLString(fm));
		
		/**
		 * 读取文件构造FieldMap对象
		 */
		Document doc = XMLUtil.createDocumentFromPath("/FieldMap.xml");
		FieldMap fm1 = FieldMapUtil.createFieldMap(doc.getDocumentElement());
		System.out.println(FieldMapUtil.createXMLString(fm1));
	}
}

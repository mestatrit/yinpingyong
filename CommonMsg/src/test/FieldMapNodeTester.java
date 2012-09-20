package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

public class FieldMapNodeTester {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		/**
		 * 读取文件方式，构造FieldMapNode对象
		 */
		Document doc = XMLUtil.createDocumentFromPath("/FieldMapNode.xml");
		FieldMapNode fmn = FieldMapUtil.createFieldMapNode(doc.getDocumentElement());
		System.out.println(FieldMapUtil.createXMLString(fmn));
		
		/**
		 * 通过构造，创建FieldMapNode对象
		 */
		FieldMapNode rootNode = new FieldMapNode("root");
		rootNode.addField("name", "根节点");
		
		FieldMapNode childNode = new FieldMapNode("item1");
		FieldMapNode leafNode = new FieldMapNode("leaf");
		childNode.addField("name", "子节点1");
		leafNode.addField("name", "叶子节点");
		childNode.addChildNode(leafNode);
		rootNode.addChildNode(childNode);
		
		childNode = new FieldMapNode("item1");
		childNode.addField("name", "子节点2");
		rootNode.addChildNode(childNode);
		System.out.println(FieldMapUtil.createXMLString(rootNode));
	}

}
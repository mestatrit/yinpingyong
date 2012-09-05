package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

/**
 * FieldMapNode 使用样例
 * 
 * 2012-7-26 Ryan
 */
public class FieldMapNodeTest {

	private static Document initXml() throws ParserConfigurationException, SAXException, IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<TREE CNAME=\"合计\">");
		sb.append("<TREE CNAME=\"国债\"/>");
		sb.append("<TREE CNAME=\"企业债\"/>");
		sb.append("<TREE CNAME=\"央行票据\"/>");
		sb.append("<TREE CNAME=\"其他\"/>");
		sb.append("</TREE>");
		
		return XMLUtil.createDocument(sb.toString());
	} 
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Document doc = initXml();
		
		/**
		 * 从Document创建FieldMapNode
		 */
		FieldMapNode fmd = FieldMapUtil.createFieldMapNode(doc.getDocumentElement());
		System.out.println(fmd.getChildCount());
		System.out.println(FieldMapUtil.createXMLString(fmd));
		
		/**
		 * 递归查询FieldMapNode，找到属性为CNAME并且值等于国债的节点，在此节点上添加属性
		 */
		fmd.searchNode("CNAME", "国债").addField("ATTR1", "属性1");
		fmd.searchNode("CNAME", "国债").addField("ATTR2", "属性2");
		System.out.println(FieldMapUtil.createXMLString(fmd));
		
		/**
		 * 构建一个子节点FieldMapNode，并为其创建一个属性名为ATTR1值为属性1
		 * 
		 * 递归查询FieldMapNode，找到属性为CNAME并且值等于国债的节点，在此节点上添加子节点
		 */
		FieldMapNode childNode = new FieldMapNode("LEAF");
		childNode.addField("ATTR1", "属性1");
		fmd.searchNode("CNAME", "国债").addChildNode(childNode);
		System.out.println(FieldMapUtil.createXMLString(fmd));
	}
}

package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;

public class FieldMapSetTester {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		/**
		 * 构造FieldMapSet对象
		 */
		FieldMapSet fms = new FieldMapSet("root");
		fms.addAttr(new Field("attr1", "a1"));
		fms.addAttr(new Field("attr2", "a2"));
		FieldMap fm = new FieldMap("map1");
		fm.putField("node1", "n1");
		fm.putField("node1", "n1");
		fm.putField("node2", "n1");
		fm.putField("node3", "n1");
		fms.addFieldMap(fm);
		System.out.println(FieldMapUtil.createXMLString(fms));
		System.err.println("-------------------1------------------------");
		
		/**
		 * 更新
		 */
		for (int index=0;index<fms.getFieldMapCount();index++) {
			FieldMap fieldMap =  fms.getFieldMap(index);
			Field field = fieldMap.getField("node1");
			if (field != null) {
				field.setValue("haha");
			}
		}
		System.out.println(FieldMapUtil.createXMLString(fms));
		System.err.println("--------------------2----------------------");
		
		/**
		 * 读取文件构造FieldMapSet对象
		 */
		Document doc = XMLUtil.createDocumentFromPath("/FieldMapSet.xml");
		FieldMapSet fms1 = FieldMapUtil.createFieldMapSet(doc.getDocumentElement());
		System.out.println(FieldMapUtil.createXMLString(fms1));
		System.err.println("----------------------3---------------------");
		
		/**
		 * 删除FieldMap
		 */
		for (int index=0; index < fms1.getFieldMapCount(); index ++) {
			if (fms1.getFieldMap(index).getStringValue("field1").equals("f2")){
				fms1.removeFieldMap(index);
				index--;
			}
		}
		System.out.println(FieldMapUtil.createXMLString(fms1));
	}
}

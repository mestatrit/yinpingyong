package test;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapUtil;

public class FieldMapTester {

	public static void main(String[] args) {
		FieldMap fm = new FieldMap("root");
		fm.putField("node1", "n1");
		fm.putField("node2", "n2");
		fm.putField("node3", "n3");
		fm.putField("node4", "n4");
		System.out.println(FieldMapUtil.createXMLString(fm));
	}
}

package test;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;

public class FieldMapSetTester {

	public static void main(String[] args) {
		FieldMapSet fms = new FieldMapSet("root");
		fms.addAttr(new Field("attr1", "a1"));
		fms.addAttr(new Field("attr2", "a2"));
		FieldMap fm = new FieldMap("map1");
		fm.putField("node1", "n1");
		fm.putField("node2", "n1");
		fm.putField("node3", "n1");
		fm.putField("node4", "n1");
		fms.addFieldMap(fm);
		
		System.out.println(FieldMapUtil.createXMLString(fms));
	}

}

package test;

import java.io.Serializable;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.localStore.LocalStore;
import com.mt.common.localStore.StorePath;

public class Test {

	public static void main(String[] args) {
		FieldMapSet curCol = new FieldMapSet("curCol");
		FieldMap fm = new FieldMap("col");
		fm.putField("colName", "test");
		fm.putField("colIndex", 1);
		curCol.addFieldMap(fm);
		LocalStore.toLocalXMLData("testCC", curCol, StorePath.LoginUserPath);

		user user = new user();
		user.setName("admin");
		user.setPassword("00000000");
		LocalStore.toLocalXMLData("user", user, StorePath.LoginUserPath);

		Object obj = LocalStore.fromLocalXMLData(FieldMapSet.class,"c:/testCC.db");
		System.err.println(obj);
	}

}

class user implements Serializable {
	
	private static final long serialVersionUID = 4558342082931625231L;
	
	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public user() {

	}
}

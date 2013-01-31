package test;

import imix.ConfigError;
import imix.DataDictionary;
import imix.InvalidMessage;
import imix.Message;
import imix.field.FreeMsgID;
import imix.field.FreeMsgType;
import imix.field.Text;
import imix.imix10.FreeFormatMessage;

/**
 * @author:Ryan
 * @date:2013-1-31
 */
public class TestMessage {

	private static DataDictionary dd;

	static {
		try {
			dd = new DataDictionary("IMIX10.xml");
		} catch (ConfigError e) {
			throw new RuntimeException("Fail to initialize the cfg file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		FreeFormatMessage freeFormatMessage = new FreeFormatMessage();
		FreeMsgID msgId = new FreeMsgID("0001");
		
		FreeMsgType fid = new FreeMsgType("C33B");
		Text text = new Text("<Page><DATE>20130122</DATE></Page>");
		
		freeFormatMessage.set(msgId);
		freeFormatMessage.set(fid);
		freeFormatMessage.set(text);
		
		/*System.out.println(freeFormatMessage);
		System.out.println(freeFormatMessage.toXML());*/
		
		try {
			Message msg = new Message(freeFormatMessage.toString(), dd, true);
			System.out.println(msg);
			System.out.println(msg.toXML());
		} catch (InvalidMessage e) {
			e.printStackTrace();
		}
	}
}

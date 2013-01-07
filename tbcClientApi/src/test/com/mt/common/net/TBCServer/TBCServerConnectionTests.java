package test.com.mt.common.net.TBCServer;

import java.io.IOException;

import com.mt.common.net.TBCServer.CommonMsg;
import com.mt.common.net.TBCServer.CommonMsgCallback;
import com.mt.common.net.TBCServer.NetConstants;
import com.mt.common.net.TBCServer.TBCServerConnection;
import com.mt.common.net.TBCServer.TBCServerConnectionException;
import com.mt.common.net.TBCServer.TBCServerConnectionInfo;

/**
 * @author:Ryan
 * @date:2013-1-3
 */
public class TBCServerConnectionTests {

	public static void main(String[] args) throws IOException, TBCServerConnectionException {
		TBCServerConnectionInfo info = new TBCServerConnectionInfo();
		info.IP = "210.22.151.39";
		info.port = "8004";
		info.userName = "comstp1user1";
		info.password = "comstp1user1";
		info.loginMsgFormat = NetConstants.PLAIN;
		info.name = "CMS";
		
		TBCServerConnection con = TBCServerConnection.createTBCServerConnection(info);
		con.requestRemoteService("C599",new CommonMsgCallback() {
			
			@Override
			public void onMessage(CommonMsg msg) {
				if (msg.isError()) {
					System.err.println("未查询到机构");
				} else {
					System.out.println(msg.toString());	
				}
			}
		});

	}

}

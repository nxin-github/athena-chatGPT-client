package ning.xin.core;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author :宁鑫
 * @date : 2023/2/25 12:11
 */
public class SocketClient {
    public static void executor() {
        try {
            final String serviceIp = LinkRegistrationCenter.client();
            // 连接服务器
            System.out.println("获取到服务器ip：" + serviceIp);
            Socket socket = new Socket(serviceIp, 24264);

            // 获取输入输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 循环读取用户输入并发送给服务器
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = reader.readLine();
                if (line == null || "exit".equals(line)) {
                    break;
                }
                out.println(line);
                String resp = in.readLine();
                final List<String> respList = JSON.parseArray(resp, String.class);
                for (String str : respList) {
                    System.out.println(str);
                }
            }
            // 关闭连接
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

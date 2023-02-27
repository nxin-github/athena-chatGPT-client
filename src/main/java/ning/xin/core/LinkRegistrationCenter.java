package ning.xin.core;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import ning.xin.dto.YamlDTO;
import ning.xin.dto.YamlRegistrationCenterDTO;
import ning.xin.util.Utils;

import java.util.List;
import java.util.Properties;

/**
 * @author :宁鑫
 * @date : 2023/2/27 13:05
 */
public class LinkRegistrationCenter {
    public static String client() {
        try {
            final YamlDTO yaml = Utils.readYaml();
            if (yaml == null) {
                System.out.println("yaml文件读取失败");
                return "";
            }
            final YamlRegistrationCenterDTO yamlRegistrationCenter = yaml.getYamlRegistrationCenterDTO();

            Properties properties = new Properties();
            properties.put("serverAddr", yamlRegistrationCenter.getServerAddr());

            NamingService namingService = new NacosNamingService(properties);
            List<Instance> instances = namingService.getAllInstances("Athena");
            Instance instance = instances.get(0);
            return instance.getIp();
        } catch (NacosException e) {
            System.out.println("链接Nacos失败！！");
            e.printStackTrace();
            return "";
        }
    }
}

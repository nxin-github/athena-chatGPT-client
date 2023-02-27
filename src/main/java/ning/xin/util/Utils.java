package ning.xin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ning.xin.AthenaClient;
import ning.xin.dto.YamlDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author :宁鑫
 * @date : 2023/2/26 11:29
 */
public class Utils {
    public static YamlDTO readYaml() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            //这么读取yaml有点蠢，但是没想到什么好办法
            String jarPath = new File(AthenaClient.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
            int lastIndex = jarPath.lastIndexOf("/");
            String path = jarPath.substring(0, lastIndex).concat("/athena-client.yaml");
            final File file = new File(path);
            final InputStream resourceAsStream = new FileInputStream(file);
            return mapper.readValue(resourceAsStream, YamlDTO.class);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

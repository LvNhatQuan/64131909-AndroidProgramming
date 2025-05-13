package nhatquan.ntu.geographyquiz.util;

import java.util.HashMap;
import java.util.Map;

public class SubTopicUtil {
    public static String[] getSubtopics(String topic) {
        Map<String, String[]> map = new HashMap<>();
        map.put("Địa lý Việt Nam", new String[]{"Khí hậu", "Kinh tế", "Tài nguyên"});
        map.put("Châu Á", new String[]{"Đông Á", "Đông Nam Á", "Nam Á"});
        map.put("Châu Âu", new String[]{"Tây Âu", "Đông Âu", "Bắc Âu"});
        map.put("Châu Mỹ", new String[]{"Bắc Mỹ", "Nam Mỹ", "Trung Mỹ"});
        map.put("Châu Phi", new String[]{"Bắc Phi", "Nam Phi"});

        return map.getOrDefault(topic, new String[]{"Chủ đề 1", "Chủ đề 2"});
    }
}

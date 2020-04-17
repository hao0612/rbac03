import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreemarkeTEst {
    @Test
    public void  test() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        // 指定模板文件从何处加载的数据源，这里设置文件目录位置。
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        // 创建根哈希表
        Map root = new HashMap();
        // 在根中放入字符串"user"
        root.put("name", "Big Joe");
        // 获取模板文件
        Template temp = cfg.getTemplate("test.ftl");
        // 设置输出为新的文件
        Writer out = new OutputStreamWriter(new FileOutputStream("test.html"));
        // 执行输出
        temp.process(root, out);
        out.flush();
        out.close();
    }


}

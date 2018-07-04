package com.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMakerTest {
	@Test
	public void testFreeMakerTest() throws Exception {
		//第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration=new Configuration(Configuration.getVersion());
		//第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("D:\\workspaces\\javaEE\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		//第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
		//第四步：加载一个模板，创建一个模板对象。
		//Template template=configuration.getTemplate("hello.ftl");
		Template template=configuration.getTemplate("student.ftl");
		//第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map data=new HashMap<>();
		data.put("hello", "hello freemaker");
		//创建一个pojo对象
		Student student=new Student(1,"小李",18,"新化");
		data.put("student", student);
		//添加一个List
		List<Student> stuList=new ArrayList<>();
		stuList.add(new Student(2,"小李",18,"新化"));
		stuList.add(new Student(3,"小李",13,"新化"));
		stuList.add(new Student(4,"小李",12,"新化"));
		stuList.add(new Student(5,"小李",11,"新化"));
		stuList.add(new Student(6,"小李",16,"新化"));
		data.put("stuList", stuList);
		data.put("date", new Date());
		data.put("val", 1);
		//第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		//Writer out=new FileWriter(new File("E:\\freemaker\\hello.txt"));
		Writer out=new FileWriter(new File("E:\\freemaker\\student.html"));
		//第七步：调用模板对象的process方法输出文件。
		template.process(data, out);
		//第八步：关闭流。
		out.close();
	}
}

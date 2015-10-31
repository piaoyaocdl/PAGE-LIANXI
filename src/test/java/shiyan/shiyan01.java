package shiyan;

import java.io.IOException;
import java.net.URL;

import org.dom4j.DocumentException;
import org.junit.Test;

public class shiyan01
{

	@Test
	public void shiyan() throws DocumentException, IOException
	{
		URL ls = ClassLoader.getSystemResource("ReportTemplate/shiyan.xml");
		System.out.println(ls);
	}
}

package shiyan;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.junit.Test;

public class shiyan01
{

	@Test
	public void shiyan() throws DocumentException, IOException
	{
		// String ls = "/F:/WorkSpacesEclipse/RISwell/target/classes/";
		String ls = "file:/F:/shishi/RISwell.jar!/";
		String re = null;
		if (ls.indexOf(".jar") >= 0)
		{
			re = ls.substring(6, ls.indexOf(".jar")-7);
		}

		if (ls.indexOf("target") >= 0)
		{
			re = ls.substring(1, ls.indexOf("target"));
		}

		System.out.println(re);

	}
}

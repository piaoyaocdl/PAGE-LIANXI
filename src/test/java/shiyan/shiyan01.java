package shiyan;

import java.io.File;

import org.junit.Test;

public class shiyan01
{

	@Test
	public void deleteFile()
	{
		File file = new File("C:/Users/HSWC/Desktop/11");

		if (file.exists() && file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].renameTo(files[i]))
				{
					files[i].delete();
				}
			}
		}
	}
}

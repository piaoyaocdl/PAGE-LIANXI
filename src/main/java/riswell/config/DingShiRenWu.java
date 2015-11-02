package riswell.config;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import riswell.util.GongJu;

/**
 * 
 * 定时任务
 * 
 * @author HSWC
 *
 */
@Component
public class DingShiRenWu
{

	/**
	 * "0 1 1 ? * *" 每天凌晨1：1，触发清空临时文件夹。
	 */
	@Scheduled(cron = "0 1 1 ? * *")
	public void reportCurrentTime()
	{
		File file = new File(GongJu.getXiangMuLuJing() + "临时文件/");

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

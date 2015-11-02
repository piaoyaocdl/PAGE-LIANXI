package riswell.config;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

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
	 * 每天凌晨1：30分,发送异常邮件,并清空异常文件夹。
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	// @Scheduled(cron = "0 30 1 ? * *")
	@Scheduled(fixedRate = 10000)
	public void yichangyoujianfasong() throws MessagingException, InterruptedException
	{
		File file = new File(GongJu.getXiangMuLuJing() + "异常记录/");

		if (file.exists() && file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				GongJu.fasongyoujian(files[i].getAbsolutePath());

				Thread.sleep(50000);

			}
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].renameTo(files[i]))
				{
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 每天凌晨1：2分,清空临时文件夹。
	 */
	@Scheduled(cron = "0 2 1 ? * *")
	public void linshiwenjianqingkong()
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

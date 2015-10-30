package riswell.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class GongJu
{
	/**
	 * Spring MVC 的重定向
	 */
	public static final String 重定向 = "redirect:";

	/**
	 * 把数组转换为HashSet
	 * 
	 * @param shuzu
	 *            需要转换的数组
	 * @return 转化后的 Set
	 */
	public static <E> Set<E> shuzu2set(E[] shuzu)
	{
		Set<E> re = new HashSet<>();

		for (int i = 0; i < shuzu.length; i++)
		{
			re.add(shuzu[i]);
		}
		return re;
	}

	/**
	 * List 转换为 Set
	 * 
	 * @param list
	 * @return
	 */
	public static <E> Set<E> list2set(List<E> list)
	{
		Set<E> re = new HashSet<>();
		re.addAll(list);
		return re;
	}

	/**
	 * 密码加密函数
	 * 
	 * @param yuanmima
	 *            原密码
	 * @return 加密后密码
	 */
	public static String jiami_mima(String yuanmima)
	{
		return new Md5Hash(yuanmima, "!@#$&^").toString();
	}

	/**
	 * 获得项目部署的路径
	 * 
	 * @return
	 */
	public static String getXiangMuLuJing()
	{
		String ls = Thread.currentThread().getContextClassLoader().getResource(".").getPath();

		return ls.substring(1).split("target")[0];
	}

	/**
	 * 下载文件,只能用于controller 调用
	 * 
	 * @param wenjian
	 *            要下载的文件
	 * @param baocunwei
	 *            要保存为
	 *
	 */
	public static ResponseEntity<byte[]> xiazai(String wenjian,String wenjianjia, String baocunwei) throws IOException
	{
		String path = GongJu.getXiangMuLuJing() +wenjianjia+ "/" + wenjian;
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment",new String(baocunwei.getBytes(), "ISO8859-1") );
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}
	
	public static ResponseEntity<byte[]> xiazai(String wenjian, String baocunwei) throws IOException
	{
		return GongJu.xiazai(wenjian, "上传文件", baocunwei);
	}
}

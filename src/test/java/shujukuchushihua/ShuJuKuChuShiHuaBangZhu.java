package shujukuchushihua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HSW 这是一个代码生成器，用来生成权限表的管理员权限插入语句
 */
public class ShuJuKuChuShiHuaBangZhu
{

	private static List<String> neichunwenjian = new ArrayList<>(0);

	public static void main(String[] args)
	{
		String wenjianlujing = Thread.currentThread().getContextClassLoader().getResource(".").getPath()
				.split("target")[0] + "附件/ChuLiRequestMapping.txt";

		readTxtFile(wenjianlujing);
		chulichengyuju();
	}

	// 处理成语句
	public static void chulichengyuju()
	{
		String dangqianyuju = null;
		String quanxianmiaoshu = null;
		String quanxianming = null;

		for (int i = 0; i < neichunwenjian.size(); i++)
		{
			dangqianyuju = neichunwenjian.get(i);
			int indexRequestMapping = dangqianyuju.indexOf("@RequestMapping");
			// 不含有RequestMapping停止处理
			if (indexRequestMapping == -1)
			{
				continue;
			}
			// 含有RequestMapping
			dangqianyuju = dangqianyuju.substring(indexRequestMapping + 17).split("\"")[0];
			System.out.println("qxs = quanxiandao.findByUrl(\"" + dangqianyuju + "\");");
			System.out.println("if (qxs.size() == 1){quanxian = qxs.get(0);	} else 	{ quanxian = new QuanXian();}");
			System.out.println("quanxian.setUrl(\"" + dangqianyuju + "\");");
			System.out.println("quanxian.setXinjiubiaozhi(1);");
			switch (dangqianyuju)
			{
			case "/dengchu":
				quanxianming = "登出系统";
				quanxianmiaoshu = "已经登陆的用户，能够登出系统。";
				break;
			case "/denglu":
				quanxianming = "登陆系统";
				quanxianmiaoshu = "尝试登陆系统，当帐号密码正确的时候，能够成功的登陆系统。";
				break;
			case "/xitongshouye":
				quanxianming = "系统首页";
				quanxianmiaoshu = "用户连接到系统欢迎页面。";
				break;
			case "/":
				quanxianming = "登陆页面";
				quanxianmiaoshu = "使得用户能够看见系统的登陆页面。";
				break;
			case "/chakanquanxian":
				quanxianming = "查看权限";
				quanxianmiaoshu = "查看系统所有的权限。";
				break;
			case "/tanchuquanxian":
				quanxianming = "弹出权限";
				quanxianmiaoshu = "弹出窗口，显示角色拥有的权限。";
				break;
			case "/charujuese":
				quanxianming = "插入角色";
				quanxianmiaoshu = "数据库新加一个角色。";
				break;
			case "/chakanjuese":
				quanxianming = "查看角色";
				quanxianmiaoshu = "查看系统的角色。";
				break;
			case "/tianjiajuese":
				quanxianming = "添加角色页面";
				quanxianmiaoshu = "跳转到添加角色页面";
				break;
			case "/main":
				quanxianming = "主界面";
				quanxianmiaoshu = "使得用户能够调用系统的主界面框架。";
				break;
			case "/top":
				quanxianming = "主界面上页面";
				quanxianmiaoshu = "使得用户能够看见系统的主界面框架的顶部页面。";
				break;
			case "/left":
				quanxianming = "主界面左页面";
				quanxianmiaoshu = "使得用户能够看见系统的主界面框架的左部页面。";
				break;
			case "/right":
				quanxianming = "主界面右页面";
				quanxianmiaoshu = "使得用户能够看见系统的主界面框架的右部页面。";
				break;
			case "/jishuzhichi":
				quanxianming = "技术支持";
				quanxianmiaoshu = "使得用户能够看见系统的技术支持页面。";
				break;
			default:
				System.err.println(dangqianyuju + "--语句没有处理！！！");
				break;
			}
			System.out.println("quanxian.setQuanxianmiaoshu(\"" + quanxianmiaoshu + "\");");
			System.out.println("quanxian.setQuanxianming(\"" + quanxianming + "\");");
			System.out.println("quanxian.setPinyinming();");
			System.out.println("quanxiandao.saveAndFlush(quanxian);");
		}
	}

	// 读取txt文件到内存
	public static void readTxtFile(String filePath)
	{
		try
		{
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.exists() && file.isFile())
			{
				// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					neichunwenjian.add(lineTxt);
				}
				read.close();
			} else
			{
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e)
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

}

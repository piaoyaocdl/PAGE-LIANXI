package riswell.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLGongJu
{

	public static enum XMLType
	{
		ID, TEXT;
	}

	/**
	 * 替换 文本
	 * 
	 * @param doc
	 *            要替换的 Document
	 * @param beitihuanwenben
	 *            被替换的文字
	 * @param tihuanwenben
	 *            替换成
	 */
	public static void tiHuanWenBen(Document doc, String beitihuanwenben, String tihuanwenben)
	{
		Element root = doc.getRootElement();
		Element ele = getElement(root, beitihuanwenben, XMLType.TEXT);
		ele.setText(tihuanwenben);
	}

	/**
	 * 把xml写入 文件
	 * 
	 * @param filePathAndName
	 *            要写入那个文件？
	 * @param document
	 *            指定的xml
	 * @throws IOException
	 */
	public static void xieXML(String filePathAndName, Document document) throws IOException
	{
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filePathAndName), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}

	/**
	 * 读取指定XML文件
	 * 
	 * @param filePathAndName
	 *            文件的路径和名字
	 * @return XML的内存文件
	 * @throws DocumentException
	 */
	public static Document getDocument(String filePathAndName) throws DocumentException
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filePathAndName));
		return document;
	}

	/**
	 * 获得指定元素下的某个子元素
	 * 
	 * @param element
	 *            指定元素
	 * @param idOrText
	 *            id或者text的值
	 * @param type
	 *            根据id找？还是text？
	 * @return 子元素
	 */
	public static Element getElement(Element element, String idOrTextString, XMLType type)
	{
		Element re = null;
		List<?> list = element.elements();

		for (Iterator<?> its = list.iterator(); its.hasNext();)
		{
			if (re != null)
			{
				break;
			}

			Element chileEle = (Element) its.next();

			String ls = null;

			if (type == XMLType.ID)
			{
				ls = chileEle.attributeValue("id");
			}
			if (type == XMLType.TEXT)
			{
				ls = chileEle.getText();
			}

			if (ls != null && ls.equals(idOrTextString))
			{
				re = chileEle;
			} else
			{
				re = getElement(chileEle, idOrTextString, type);
			}
		}
		return re;

	}
}

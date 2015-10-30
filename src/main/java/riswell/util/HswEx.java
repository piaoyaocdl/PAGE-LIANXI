package riswell.util;

/**
 * 定义本系统的异常，用于AOP捕获，并处理
 * 
 * @author HSW
 *
 */
public class HswEx extends Exception
{

	private static final long serialVersionUID = -7375356953233079790L;

	public HswEx(String message)
	{
		super(message);
	}

}

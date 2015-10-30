package util
{
	import com.adobe.serialization.json.JSON;
	
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLVariables;
	
	public dynamic class WenJianXiaZai
	{
		public function WenJianXiaZai()
		{
		}
		
		
		/**
		 * 下载器：主要用来实现下载功能
		 */
		var xiazaiqi:FileReference=new FileReference();
		
		
		/**
		 * url 下载请求的URL 
		 */
		var url:URLRequest = new URLRequest();
		
		
		/**
		 * 请求下载时候要携带的参数 
		 */
		var canshu:URLVariables = new URLVariables();
		
		
		/**
		 *  设置请求的URL 
		 * @param url URL的地址，string 格式
		 * 
		 */
		public function s1setURL(url:String):void
		{
			this.url.url=url;
		}
		
		/**
		 *
		 * 设置要携带的参数 
		 * @param canshu 这个应该是object 格式，在这个函数中会转为json格式的发送出去
		 * 
		 */
		public function s2setCanShu(canshu:Object):void
		{
			this.canshu.json=JSON.encode(canshu);
		}
		
		
		public function s3xiaZai(wenjianming:String):void
		{
			this.url.data=this.canshu;
			xiazaiqi.download(this.url,wenjianming);
		}
		
	}
}
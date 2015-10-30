package util
{
	import com.adobe.serialization.json.JSON;
	
	import flash.events.Event;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.FileReferenceList;
	import flash.net.URLRequest;
	import flash.net.URLVariables;
	
	import mx.collections.ArrayList;
	import mx.rpc.events.ResultEvent;
	
	public dynamic class WenJianShangChuan
	{
		public function WenJianShangChuan()
		{
			this.guolvqi="*.*";
			this.wenjians=new FileReferenceList();
		}
		
		/**
		 * 选择文件时候的过滤器，默认可以选择一切文件，指定格式："*.pdf;*.doc;*.txt";
		 */
		public var s1guolvqi:String;
		
		
		/**
		 * 用户选择的文件，相关信息
		 */
		public  var wenjians:FileReferenceList;
		
		
		/**
		 * 
		 * @return 返回用户选择的文件的名字，数组类型
		 * 
		 */
		public function s4wenjianmings():Array
		{
			var re:Array=new Array();
			
			if(wenjians.fileList !=null && wenjians.fileList.length>0)
			{
				for(var i:int=0 ;i<wenjians.fileList.length;i++)
				{
					re.push(wenjians.fileList[i].name);
				}
			}
			
			return re;
		}
		
		/**
		 *	处理点击事件，打开系统的文件选择 
		 * @return 
		 * 
		 */		
		public function s3xuanzewenjian():Boolean
		{
			var gl:FileFilter=new FileFilter("Excel文件:"+this.guolvqi,this.guolvqi);
			
			return wenjians.browse([gl]);
		}
		
		
		/**
		 * 
		 * @param f 用户选择完毕后，要执行的函数
		 * 
		 */
		public function  s2xuanzewanbi(f:Function):void
		{
			wenjians.addEventListener(Event.SELECT,f);
		}
		
		
		/**
		 * 实际开始上传文件，
		 * 首先需要向服务器发送请求，请求每个文件的在服务器端的储存名字。
		 * 服务器端需要返回一个map，原文件名字为K，新名字为V
		 * ------------------------------------------------
		 * 可以参考  组织配型--临床HLA--添加检测申请单的流程
		 * @param event 服务器端的返回结果，里面应该包含新旧名字信息
		 * @return 
		 * 
		 */
		public function s5shangchuan(event:Object):void
		{
			if( wenjians.fileList && wenjians.fileList.length>0)
			{
				var mingzimap:Object=JSON.decode(event.result as String);
				
				for(var i:int=0 ;i<wenjians.fileList.length;i++)
				{
					var ls:FileReference=wenjians.fileList[i] as FileReference ;
					var data:URLVariables = new URLVariables();
					var url:URLRequest=new URLRequest();
					data.wenjianming=mingzimap[ls.name];
					url.url="/shangchuanwenjian";
					url.method="POST";
					url.data=data;
					ls.upload(url,"file");
				}
			}
		}
		
	}
}
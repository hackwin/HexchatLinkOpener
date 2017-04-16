import java.awt.Desktop;
import java.io.RandomAccessFile;
import java.net.URL;

public class HexchatLinkOpener {
	
	static RandomAccessFile urlLog;
	static int checkDuration = 5000; //milliseconds
	static String[] whiteList = {"aliexpress", "ebay", "imgur","youtube","banggood"};

	public static void main(String[] args) throws Exception {

		urlLog = new RandomAccessFile ("C:/Users/"+System.getProperty("user.name")+"/AppData/Roaming/HexChat/url.log","r");
		
		while(true){
			long length = urlLog.length();
			System.out.println("Waiting "+checkDuration+" milliseconds");
			Thread.sleep(checkDuration);
			long length2 = urlLog.length();
			System.out.println("Checking for new URLs");
			
			if (length2 > length){
				System.out.println("Size changed from " + length + " to " + length2 + " bytes");
				byte[] newUrlBuffer = new byte[(int)(length2-length)];
				System.out.println("Buffer size = " + newUrlBuffer.length);
				urlLog.seek(length);
				urlLog.read(newUrlBuffer);
				urlLog.seek(0);
				
				String newUrlString = new String(newUrlBuffer, "UTF-8");
				String[] urls = newUrlString.split("\r\n");
				
				System.out.println("Found "+urls.length+" new URLs");
				
				for(int i=0; i<urls.length; i++){
					System.out.println(urls[i]);
					for(int j=0; j<whiteList.length; j++){
						if(urls[i].contains(whiteList[j])){
							System.out.println("Opening "+urls[i]);
							Desktop.getDesktop().browse(new URL(urls[i]).toURI());	
						}
					}					
				}				
				System.out.println();

			}			
		}
	}

}
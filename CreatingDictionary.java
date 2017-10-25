import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;


public class CreatingDictionary {
	static HashMap<String,Integer> map = new HashMap<String,Integer>();
	static int Count = 0;
	public static void main(String[] args) throws IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("C:/PHD_work_sem2_649/Dictionary.txt"), "utf-8"))) {
			writer.write("{ \n");
		
	   	String filePath = "C:/PHD_work_sem2_649/TSRKeys.txt";
		Scanner scanner1 = new Scanner(new File(filePath));
		while (scanner1.hasNextLine()) {
			  String line1 = scanner1.nextLine();
			  if(!line1.equals("")){
				  String TSR1 = line1 ;
				  System.out.println(TSR1);
				  writer.write("\t"+TSR1+": {"+"\n");
				  map = new HashMap<String,Integer>();
				  goToEachFile("C:/PHD_work_sem2_649/SCOPbetaProt/SCOPbetaProt",TSR1);
				  Iterator it = map.entrySet().iterator();
				   while (it.hasNext()) {
					   Map.Entry pair = (Map.Entry)it.next();
				        String TSR=(String) pair.getKey();
				        int freq=(int) pair.getValue();
				        if(it.hasNext()){
				        	writer.write("\t\t"+TSR+": "+freq+", \n");
				        }
				        else {
				        	writer.write("\t\t"+TSR+": "+freq+" \n");
				        	writer.write("\t\t}, \n");
				        }
				        	
				   }

			  }
		}
		 writer.write("}"); 
		scanner1.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void goToEachFile(String directoryName,String TSR1) throws IOException{
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                readFilesFindCoOccurance(file.getAbsolutePath(),TSR1);
            } else if (file.isDirectory()){
            	goToEachFile(file.getAbsolutePath(), TSR1);
            }
        }
		
    }
	public static void readFilesFindCoOccurance(String filePath,String TSR1) throws IOException{
		File f = new File(filePath);
		List<String> lines = FileUtils.readLines(f, "UTF-8");
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
        for (String line : lines) {
        	String[] tokens = line.split("\\s+");
        	if(!hm.containsKey(tokens[0])){
        		hm.put(tokens[0],Integer.parseInt(tokens[1]));
        	}
        	else 
        	{
        		 hm.put(tokens[0], hm.get(tokens[0]) + Integer.parseInt(tokens[1]));
        	}
        }
        if(hm.containsKey(TSR1)){
        	Iterator it = hm.entrySet().iterator();
        	while (it.hasNext()) {
				   Map.Entry pair = (Map.Entry)it.next();
			        String TSR=(String) pair.getKey();
			        int freq=(int) pair.getValue();
			        if(!TSR.equals(TSR1)){
			        	if(!map.containsKey(TSR)){
			        		map.put(TSR, freq* (hm.get(TSR1)));
			        	}
			        	else 
			        	{
			        		map.put(TSR,map.get(TSR)+( freq* (hm.get(TSR1))));
			        	}
			        	
			        }
        	}
        	
        }
	}

}

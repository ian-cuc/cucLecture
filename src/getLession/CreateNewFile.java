package getLession;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CreateNewFile{

 public void createNewFile(String fileDirectoryAndName,String fileContent){
  try{

   File myFile = new File(fileDirectoryAndName);
   if(!myFile.exists())
    myFile.createNewFile();
   else 
	   throw new Exception("The new file already exists!");
   FileWriter resultFile = new FileWriter(myFile);
   PrintWriter myNewFile = new PrintWriter(resultFile);
   myNewFile.println(fileContent);
   resultFile.close();   
  }catch(Exception ex){
   System.out.println("");
   ex.printStackTrace();
  } 
 }

 public boolean deleteFile(String sPath) {  
     boolean flag = false;  
     File file = new File(sPath);  
  
     if (file.isFile() && file.exists()) {  
         file.delete();  
         flag = true;  
     }  
     return flag;  
 }  
 

}

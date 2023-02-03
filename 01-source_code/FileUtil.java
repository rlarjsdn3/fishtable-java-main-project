package databaseProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {
	
	public static boolean copy(String originalFilePath, String copyFilePath) {
		File originalFile = new File(originalFilePath);
		File copyFile = new File(copyFilePath);
		
		File dirCopyFile = new File(copyFilePath.substring(0, copyFilePath.lastIndexOf("/")));
		
		if(!dirCopyFile.exists()) {
			dirCopyFile.mkdirs();
		}
		
		try {
			FileInputStream fis = new FileInputStream(originalFile);
			FileOutputStream fos = new FileOutputStream(copyFile);

			int n = 0;
			while ((n = fis.read()) != -1) {
				fos.write(n);
			}
		
			fis.close();
			fos.close();
		} catch (Exception e) {
			System.out.println("예외: FileUtil > copy() > Exception");
			return false;
		}
		
		return true;
	}
	
	public static boolean delete(String filePath) {
		File file = new File(filePath);
		
		if (!(file.exists() && file.delete())) {
			return false;	
		}
		
		return true;
	}
}

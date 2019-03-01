package com.controller.htpdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//��������
public class CopyFile {

	  /**
     * @param oldPath
     * @param newPath
     * @param map
     * @Description: �ݹ鿽��
     * @param name
     * @return 
     */
    public static void copyDir(File oldPath, File newPath){
        File[] filePath = oldPath.listFiles();
        if (!newPath.exists()) {
        	newPath.mkdir();
        }
        for (int i = 0; i < filePath.length; i++) {
        	//�����Ŀ¼
            if ((new File(oldPath+"/"+ filePath[i])).isDirectory()) {
            	copyDir(filePath[i],new File(newPath+"/"+ filePath[i]));
            }
            //������ļ�
            if (new File(oldPath+"/"+ filePath[i]).isFile()) {
                try {
					copyFile(filePath[i],new File(newPath+"/"+ filePath[i]));
				} catch (IOException e) {
					throw new RuntimeException("����ʧ��");
				}
            }
        }
    }
    //�ļ�����
	public static void copyFile(File oldFile,File file) throws IOException {
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;
        byte[] buffer=new byte[1024];
        while((in.read(buffer)) != -1){
            out.write(buffer);
        } 
    }

}

package com.controller.htpdf;
import java.io.File;
//�ļ�ɾ��
public class DeleteFile {
	/**
	 * @Description: �ݹ�ɾ���ļ�  ���� Ŀ¼�µ��ļ�
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {//���Դ˳���·������ʾ���ļ��Ƿ���һ��Ŀ¼��
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {//û��ɾ���ɹ�����false
					return false;
				}
			}
		}
		return dir.delete();//���ҽ����ɹ�ɾ���ļ���Ŀ¼ʱ������ true�����򷵻� false 
	}
}

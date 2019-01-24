package com.controller.icbc;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;
public class FileUploadTool {
  TransfMediaTool transfMediaTool = new TransfMediaTool();
  // �ļ����500M
  private static long upload_maxsize = 800 * 1024 * 1024;
  // �ļ������ʽ
  private static String[] allowFiles = { ".rar", ".doc", ".docx", ".zip",
      ".pdf", ".txt", ".swf", ".xlsx", ".gif", ".png", ".jpg", ".jpeg",
      ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv",
      ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb" };
  // ����ת�����Ƶ��ʽ��ffmpeg��
  private static String[] allowFLV = { ".avi", ".mpg", ".wmv", ".3gp",
      ".mov", ".asf", ".asx", ".vob",".mp4",".wmv9", ".rm", ".rmvb" };
  // �������Ƶת���ʽ(mencoder)
  private static String[] allowAVI = { ".wmv9", ".rm", ".rmvb",".mp4" };
//  public FileEntity createFile(MultipartFile multipartFile, HttpServletRequest request) {
//    FileEntity entity;
//    boolean bflag = false;
//    String fileName = multipartFile.getOriginalFilename().toString();
//    // �ж��ļ���Ϊ��
//    if (multipartFile.getSize() != 0 && !multipartFile.isEmpty()) {
//      bflag = true;
//      // �ж��ļ���С
//      if (multipartFile.getSize() <= upload_maxsize) {
//        bflag = true;
//        // �ļ������ж�
//        if (this.checkFileType(fileName)) {
//          bflag = true;
//        } else {
//          bflag = false;
//          System.out.println("�ļ����Ͳ�����");
//        }
//      } else {
//        bflag = false;
//        System.out.println("�ļ���С����Χ");
//      }
//    } else {
//      bflag = false;
//      System.out.println("�ļ�Ϊ��");
//    }
//    if (bflag) {
//      String logoPathDir = "/video/";
//      String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
//      // �ϴ������ش���
//      // String logoRealPathDir = "E:/upload";
//      File logoSaveFile = new File(logoRealPathDir);
//      if (!logoSaveFile.exists()) {
//        logoSaveFile.mkdirs();
//      }
//      String name = fileName.substring(0, fileName.lastIndexOf("."));
//      System.out.println("�ļ����ƣ�" + name);
//      // �µ��ļ���
//      String newFileName = this.getName(fileName);
//      // �ļ���չ��
//      String fileEnd = this.getFileExt(fileName);
//      // ����·��
//      String fileNamedirs = logoRealPathDir + File.separator + newFileName + fileEnd;
//      System.out.println("����ľ���·����" + fileNamedirs);
//      File filedirs = new File(fileNamedirs);
//      // ת���ļ�
//      try {
//        multipartFile.transferTo(filedirs);
//      } catch (IllegalStateException e) {
//        e.printStackTrace();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      // ���·��
//      entity.setType(fileEnd);
//      String fileDir = logoPathDir + newFileName + fileEnd;
//      StringBuilder builder = new StringBuilder(fileDir);
//      String finalFileDir = builder.substring(1);
//      // size�洢ΪString
//      String size = this.getSize(filedirs);
//      // Դ�ļ�����·��
//      String aviPath = filedirs.getAbsolutePath();
//      // ת��Avi
////      boolean flag = false;
//      if (this.checkAVIType(fileEnd)) {
//        // ����ת��ΪAVI��ʽ���ļ��ı���·��
//        String codcAviPath = logoRealPathDir + File.separator + newFileName + ".avi";
//        // ��ȡ���õ�ת�����ߣ�mencoder.exe���Ĵ��·��
//        String mencoderPath = request.getSession().getServletContext().getRealPath("/tools/mencoder.exe");
//        aviPath = transfMediaTool.processAVI(mencoderPath, filedirs.getAbsolutePath(), codcAviPath);
//        fileEnd = this.getFileExt(codcAviPath);
//      }
//      if (aviPath != null) {
//        // ת��Flv
//        if (this.checkMediaType(fileEnd)) {
//          try {
//            // ����ת��Ϊflv��ʽ���ļ��ı���·��
//            String codcFilePath = logoRealPathDir + File.separator + newFileName + ".flv";
//            // ��ȡ���õ�ת�����ߣ�ffmpeg.exe���Ĵ��·��
//            String ffmpegPath = request.getSession().getServletContext().getRealPath("/tools/ffmpeg.exe");
//            transfMediaTool.processFLV(ffmpegPath, aviPath,  codcFilePath);
//            fileDir = logoPathDir + newFileName + ".flv";
//            builder = new StringBuilder(fileDir);
//            finalFileDir = builder.substring(1);
//          } catch (Exception e) {
//            e.printStackTrace();
//          }
//        }
//        entity.setSize(size);
//        entity.setPath(finalFileDir);
//        entity.setTitleOrig(name);
//        entity.setTitleAlter(newFileName);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        entity.setUploadTime(timestamp);
//        return entity;
//      } else {
//        return null;
//      }
//    } else {
//      return null;
//    }
//  }
  /**
   * �ļ������ж�
   *
   * @param fileName
   * @return
   */
  private boolean checkFileType(String fileName) {
    Iterator<String> type = Arrays.asList(allowFiles).iterator();
    while (type.hasNext()) {
      String ext = type.next();
      if (fileName.toLowerCase().endsWith(ext)) {
        return true;
      }
    }
    return false;
  }
  /**
   * ��Ƶ�����ж�(flv)
   *
   * @param fileName
   * @return
   */
  boolean checkMediaType(String fileEnd) {
    Iterator<String> type = Arrays.asList(allowFLV).iterator();
    while (type.hasNext()) {
      String ext = type.next();
      if (fileEnd.equals(ext)) {
        return true;
      }
    }
    return false;
  }
  /**
   * ��Ƶ�����ж�(AVI)
   *
   * @param fileName
   * @return
   */
  private boolean checkAVIType(String fileEnd) {
    Iterator<String> type = Arrays.asList(allowAVI).iterator();
    while (type.hasNext()) {
      String ext = type.next();
      if (fileEnd.equals(ext)) {
        return true;
      }
    }
    return false;
  }
  /**
   * ��ȡ�ļ���չ��
   *
   * @return string
   */
  static String getFileExt(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }
  /**
   * ����ԭʼ�ļ����������ļ���
   * @return
   */
  private String getName(String fileName) {
    Iterator<String> type = Arrays.asList(allowFiles).iterator();
    while (type.hasNext()) {
      String ext = type.next();
      if (fileName.contains(ext)) {
        String newFileName = fileName.substring(0, fileName.lastIndexOf(ext));
        return newFileName;
      }
    }
    return "";
  }
  /**
   * �ļ���С������kb.mb
   *
   * @return
   */
  private String getSize(File file) {
    String size = "";
    long fileLength = file.length();
    DecimalFormat df = new DecimalFormat("#.00");
    if (fileLength < 1024) {
      size = df.format((double) fileLength) + "BT";
    } else if (fileLength < 1048576) {
      size = df.format((double) fileLength / 1024) + "KB";
    } else if (fileLength < 1073741824) {
      size = df.format((double) fileLength / 1048576) + "MB";
    } else {
      size = df.format((double) fileLength / 1073741824) + "GB";
    }
    return size;
  }
}

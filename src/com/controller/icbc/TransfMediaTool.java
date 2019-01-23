package com.controller.icbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class TransfMediaTool {
  /**
   * ��Ƶת��flv
   *
   * @param ffmpegPath
   *      ת�빤�ߵĴ��·��
   * @param upFilePath
   *      ����ָ��Ҫת����ʽ���ļ�,Ҫ��ͼ����ƵԴ�ļ�
   * @param codcFilePath
   *      ��ʽת����ĵ��ļ�����·��
   * @return
   * @throws Exception
   */
  public void processFLV(String ffmpegPath, String upFilePath, String codcFilePath) {
    // ����һ��List����������ת����Ƶ�ļ�Ϊflv��ʽ������
    List<String> convert = new ArrayList<String>();
    convert.add(ffmpegPath); // ���ת������·��
    convert.add("-i"); // ��Ӳ�����-i�����ò���ָ��Ҫת�����ļ�
    convert.add(upFilePath); // ���Ҫת����ʽ����Ƶ�ļ���·��
    convert.add("-ab");
    convert.add("56");
    convert.add("-ar");
    convert.add("22050");
    convert.add("-q:a");
    convert.add("8");
    convert.add("-r");
    convert.add("15");
    convert.add("-s");
    convert.add("600*500");
    /*
     * convert.add("-qscale"); // ָ��ת�������� convert.add("6");
     * convert.add("-ab"); // ������Ƶ���� convert.add("64"); convert.add("-ac");
     * // ���������� convert.add("2"); convert.add("-ar"); // ���������Ĳ���Ƶ��
     * convert.add("22050"); convert.add("-r"); // ����֡Ƶ convert.add("24");
     * convert.add("-y"); // ��Ӳ�����-y�����ò���ָ���������Ѵ��ڵ��ļ�
     */
    convert.add(codcFilePath);
    try {
      Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
      new PrintStream(videoProcess.getInputStream()).start();
      videoProcess.waitFor();
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  /**
   * ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��), ����mencoderת��Ϊavi(ffmpeg�ܽ�����)��ʽ
   *
   * @param mencoderPath
   *      ת�빤�ߵĴ��·��
   * @param upFilePath
   *      ����ָ��Ҫת����ʽ���ļ�,Ҫ��ͼ����ƵԴ�ļ�
   * @param codcFilePath
   *      ��ʽת����ĵ��ļ�����·��
   * @return
   * @throws Exception
   */
  public String processAVI(String mencoderPath, String upFilePath, String codcAviPath) {
//    boolean flag = false;
    List<String> commend = new ArrayList<String>();
    commend.add(mencoderPath);
    commend.add(upFilePath);
    commend.add("-oac");
    commend.add("mp3lame");
    commend.add("-lameopts");
    commend.add("preset=64");
    commend.add("-lavcopts");
    commend.add("acodec=mp3:abitrate=64");
    commend.add("-ovc");
    commend.add("xvid");
    commend.add("-xvidencopts");
    commend.add("bitrate=600");
    commend.add("-of");
    commend.add("avi");
    commend.add("-o");
    commend.add(codcAviPath);
    try {
      // Ԥ�������
      ProcessBuilder builder = new ProcessBuilder();
      builder.command(commend);
      builder.redirectErrorStream(true);
      // ������Ϣ���������̨
      Process p = builder.start();
      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = null;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      p.waitFor();// ֱ�����������ִ���꣬������ִ��
      return codcAviPath;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
class PrintStream extends Thread {
  java.io.InputStream __is = null;
  public PrintStream(java.io.InputStream is) {
    __is = is;
  }
  public void run() {
    try {
      while (this != null) {
        int _ch = __is.read();
        if (_ch != -1)
          System.out.print((char) _ch);
        else
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

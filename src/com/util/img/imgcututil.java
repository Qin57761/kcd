package com.util.img;


import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.Graphics2D; 
import java.awt.Image; 
import java.awt.Toolkit; 
import java.awt.geom.AffineTransform; 
import java.awt.image.AffineTransformOp; 
import java.awt.image.BufferedImage; 
import java.awt.image.CropImageFilter; 
import java.awt.image.FilteredImageSource; 
import java.awt.image.ImageFilter; 
import java.io.File; 
import java.io.IOException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
  
import javax.imageio.ImageIO; 
  
public class imgcututil { 
	
	/**
	 * �Ƿ�ΪͼƬ
	 * @param file
	 * @return
	 */
    public static boolean isImage(File file){  
        if(file.isDirectory()){  
            return false;  
        }  
        String fileName = file.getName();  
        int len = fileName.indexOf(".");  
        String imagesPattern  = fileName.substring(len+1,fileName.length()).toLowerCase();  
        if("jpg".equals(imagesPattern)){  
            return true;  
        }else if("bmp".equals(imagesPattern)){  
            return true;  
        }else if("gif".equals(imagesPattern)){  
            return true;  
        }else if("psd".equals(imagesPattern)){  
            return true;  
        }else if("pcx".equals(imagesPattern)){  
            return true;  
        }else if("png".equals(imagesPattern)){  
            return true;  
        }else if("dxf".equals(imagesPattern)){  
            return true;  
        }else if("cdr".equals(imagesPattern)){  
            return true;  
        }else if("ico".equals(imagesPattern)){  
            return true;  
        }else if("bmp".equals(imagesPattern)){  
            return true;  
        }else if("jpeg".equals(imagesPattern)){  
            return true;  
        }else if("svg".equals(imagesPattern)){  
            return true;  
        }else if("wmf".equals(imagesPattern)){  
            return true;  
        }else if("emf".equals(imagesPattern)){  
            return true;  
        }else if("eps".equals(imagesPattern)){  
            return true;  
        }else if("tga".equals(imagesPattern)){  
            return true;  
        }else if("nef".equals(imagesPattern)){  
            return true;  
        }else if("tif".equals(imagesPattern)){  
            return true;  
        }else if("tiff".equals(imagesPattern)){  
            return true;  
        }else{  
            return false;  
        }  
  
    }  
	
   /** 
   * �ı�ͼƬ�ߴ� 
   * @param srcFileName ԴͼƬ·�� 
   * @param tagFileName Ŀ��ͼƬ·�� 
   * @param width �޸ĺ�Ŀ�� 
   * @param height �޸ĺ�ĸ߶� 
   */
  public static void zoomImage(String srcFileName,String tagFileName,int width,int height){  
     try { 
     BufferedImage bi = ImageIO.read(new File(srcFileName)); 
     BufferedImage tag=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB); 
     tag.getGraphics().drawImage(bi, 0, 0, width, height, null); 
     ImageIO.write(tag, "jpg", new File(tagFileName));//��ͼ 
     } catch (IOException e) { 
     e.printStackTrace(); 
     } 
  } 
    
    public static void main(String[] args) {
		
//    	String path="F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/kcd/image/upload/img/20171102/cedea33c94a342c9a307203adf9e49c8.jpg";	 
//   	     String imgpath=path+".new.jpg";
//   	  //zoomImage(path,imgpath,800,800);
//   	 cut(imgpath,imgpath+".jpg",51,86,713,201);
    	
    	String str = "8.08";
    	double testDou = Double.parseDouble(str);
    	System.out.println((int)testDou);
    	System.out.println(str.substring(0,str.indexOf(".")));
    	int in = Integer.parseInt(str.substring(0,str.indexOf(".")));
    	System.out.println(in);
	}
    
  /** 
   * ����ͼ�񣨰��߶ȺͿ�����ţ� 
   * @param srcImageFile Դͼ���ļ���ַ 
   * @param result ���ź��ͼ���ַ 
   * @param height ���ź�ĸ߶� 
   * @param width ���ź�Ŀ�� 
   * @param bb ��������ʱ�Ƿ���Ҫ���ף�trueΪ����; falseΪ������; 
   */
  public void scale(String srcImageFile, String result, int height, int width, boolean bb) { 
    try { 
      double ratio = 0.0; // ���ű��� 
      File f = new File(srcImageFile); 
      BufferedImage bi = ImageIO.read(f); 
      Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
      // ������� 
      if ((bi.getHeight() > height) || (bi.getWidth() > width)) { 
        if (bi.getHeight() > bi.getWidth()) { 
          ratio = (new Integer(height)).doubleValue() 
              / bi.getHeight(); 
        } else { 
          ratio = (new Integer(width)).doubleValue() / bi.getWidth(); 
        } 
        AffineTransformOp op = new AffineTransformOp(AffineTransform 
            .getScaleInstance(ratio, ratio), null); 
        itemp = op.filter(bi, null); 
      } 
      if (bb) {//���� 
        BufferedImage image = new BufferedImage(width, height, 
            BufferedImage.TYPE_INT_RGB); 
        Graphics2D g = image.createGraphics(); 
        g.setColor(Color.white); 
        g.fillRect(0, 0, width, height); 
        if (width == itemp.getWidth(null)) 
          g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, 
              itemp.getWidth(null), itemp.getHeight(null), 
              Color.white, null); 
        else
          g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, 
              itemp.getWidth(null), itemp.getHeight(null), 
              Color.white, null); 
        g.dispose(); 
        itemp = image; 
      } 
      ImageIO.write((BufferedImage) itemp, "JPEG", new File(result)); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } 
  } 
    
    
  /** 
   * ͼ���и�(��ָ���������Ϳ���и�) 
   * @param srcImageFile Դͼ���ַ 
   * @param result ��Ƭ���ͼ���ַ 
   * @param x Ŀ����Ƭ�������X 
   * @param y Ŀ����Ƭ�������Y 
   * @param width Ŀ����Ƭ��� 
   * @param height Ŀ����Ƭ�߶� 
   */
  public static void cut(String srcImageFile, String result, 
      int x, int y, int width, int height) { 
    try { 
      // ��ȡԴͼ�� 
      BufferedImage bi = ImageIO.read(new File(srcImageFile)); 
      int srcWidth = bi.getHeight(); // Դͼ��� 
      int srcHeight = bi.getWidth(); // Դͼ�߶� 
      if (srcWidth > 0 && srcHeight > 0) { 
        Image image = bi.getScaledInstance(srcWidth, srcHeight, 
            Image.SCALE_DEFAULT); 
        // �ĸ������ֱ�Ϊͼ���������Ϳ�� 
        // ��: CropImageFilter(int x,int y,int width,int height) 
        ImageFilter cropFilter = new CropImageFilter(x, y, width, height); 
        Image img = Toolkit.getDefaultToolkit().createImage( 
            new FilteredImageSource(image.getSource(), 
                cropFilter)); 
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        Graphics g = tag.getGraphics(); 
        g.drawImage(img, 0, 0, width, height, null); // �����и���ͼ 
        g.dispose(); 
        // ���Ϊ�ļ� 
        ImageIO.write(tag, "JPEG", new File(result)); 
      } 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } 
  } 
//  //����ļ����� 
//  public String getFileName(MultipartFile file, HttpServletRequest request,HttpSession session){ 
//    String FILE_PATH = session.getServletContext().getRealPath("/") + "upload"; 
//    String fileName = file.getOriginalFilename();  
//    String[] suffixNameArr = fileName.split("\\."); 
//    String suffixName = suffixNameArr[suffixNameArr.length-1]; 
//    String userName = SecurityContextHolder.getContext().getAuthentication().getName(); 
//      
//    return getTime() + userName+"."+suffixName; 
//  } 
  //�ļ��ϴ�,�����ļ�·�� 
//  public String uploadFile(MultipartFile file, HttpServletRequest request,HttpSession session) throws IOException { 
//    String FILE_PATH = session.getServletContext().getRealPath("/") + "upload"; 
//    String fileName = getFileName(file,request,session); 
//    File tempFile = new File(FILE_PATH, fileName);  
//      
//    if (!tempFile.getParentFile().exists()) {  
//      tempFile.getParentFile().mkdir();  
//    }  
//    if (!tempFile.exists()) {  
//      tempFile.createNewFile();  
//    }  
//    file.transferTo(tempFile); //���ϴ��ļ�д����������ָ�����ļ��� 
//      
//    return FILE_PATH + "\\" + tempFile.getName();  
//  }  
   
  /* public static File getFile(String fileName) {  
    return new File(FILE_PATH, fileName);  
  } */ 
    
  public String getTime(){ 
    Date date = new Date(); 
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//�������ڸ�ʽ 
    String nowTime = df.format(date).toString(); 
    return nowTime; 
  } 
} 

package com.mapper.newAdd;
import java.util.List;
import com.model.newAdd.pdfdownload;
public interface PDFdownloadMapper {
	//���
	public void addPDFdownload(pdfdownload PDFdl);	
	//��ѯδ���ռ�
	public List<pdfdownload> NoPDFdownload(int status,int start,int num);
	//�޸��ļ���״̬
	public void updatePDFdownload(pdfdownload PDFdl);
	//������ѯ
	public List<pdfdownload> selectByCompanyOrCode(int status,String downloadCompany);
	//��ѯ δ�ջؼ� �����ջؼ� ����
	public int PDFCounts(int status);
	// ��ȡ�ļ�����
	public String[] getFileName(int aid,int pdf_fenlei,int page,int size);
	// ��ȡ�ļ�����
	public List getFileCode(int aid,int pdf_fenlei,int page,int size);
}

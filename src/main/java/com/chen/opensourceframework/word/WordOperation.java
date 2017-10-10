package com.chen.opensourceframework.word;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.chen.database.mongo.model.KVModel;


public class WordOperation {
	public void createWord() {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage("D:/in.docx"));
			int rows = 3;
			int cols = 4;
			Iterator<XWPFTable> itTable = document.getTablesIterator();
	        while (itTable.hasNext()) {
	            XWPFTable table = itTable.next();
				System.out.println(table.getWidth());
	            for(int i =0; i < rows; i ++) {
					XWPFTableRow row = table.createRow();
					for(int m = 0; m < cols; m ++) {
						XWPFTableCell cell = row.getCell(m);
						cell.setText("text");
					}
				}
	        }
			FileOutputStream fos = new FileOutputStream(new File("D:/out.docx"));
			document.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createWordWriteArticle() {
		try {
			XWPFDocument document = new XWPFDocument();
			// XWPFWordExtractor word = new XWPFWordExtractor(document);
			// String fileContent = word.getText();
			// 创建一个段落
			XWPFParagraph para = document.createParagraph();
			// 一个XWPFRun代表具有相同属性的一个区域。
			XWPFRun run = para.createRun();
			run.setBold(true); // 加粗
			run.setText("加粗的内容");
			FileOutputStream fos = new FileOutputStream(new File("D:/out.docx"));
			document.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据mongo中查询出来的数据构建一个word文档
	 */
	public static void createWordNew(List<String> list, Map<String, List<KVModel>> map) {
		try {
			
			XWPFDocument document = new XWPFDocument();
			int cols = 4;
			for(String tstr : list){
				if(!map.containsKey(tstr)){
					continue;
				}
				XWPFParagraph para = document.createParagraph();
				// 一个XWPFRun代表具有相同属性的一个区域。
				XWPFRun run = para.createRun();
				run.setBold(true); // 加粗
				run.setText(tstr);
				XWPFTable table = document.createTable(1, 4);
				table.setWidth(5000);
				String[] str = new String[]{"字段名", "字段类型", "字段说明", "备注"};
				XWPFTableRow hrow = table.getRow(0);
				hrow.setHeight(30);
				int i = 0;
				for(String tem : str) {
					XWPFTableCell cell = hrow.getCell(i ++);
					XWPFParagraph p = cell.getParagraphs().get(0);
					XWPFRun r0 = p.createRun();
					r0.setText(tem);
					r0.setFontSize(12);
				}
				List<KVModel> collist = map.get(tstr);
				for(KVModel col : collist) {
					if(col.getKey().equals("_id")){
						continue;
					}
					XWPFTableRow row = table.createRow();
					XWPFTableCell cl0 = row.getCell(0);
					XWPFParagraph p = cl0.getParagraphs().get(0);
					XWPFRun r0 = p.createRun();
					r0.setText(col.getKey());
					r0.setFontSize(12);
					
					String clotype = col.getVal();
					XWPFTableCell cl1 = row.getCell(1);
					XWPFParagraph p1 = cl1.getParagraphs().get(0);
					XWPFRun r1 = p1.createRun();
					r1.setText(clotype);
					r1.setFontSize(12);
				}
			}
			FileOutputStream fos = new FileOutputStream(new File("D:/out.docx"));
			document.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		WordOperation wo = new WordOperation();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String,String> col = new HashMap<String, String>();
		col.put("id", "String");
		col.put("name", "String");
		map.put("T_User", col);
//		wo.createWordNew(map);
//		wo.createWord();
//		wo.createWordWriteArticle();
	}
}

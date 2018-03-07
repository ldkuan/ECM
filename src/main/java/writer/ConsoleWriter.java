package writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import manager.ChainCreator;
import manager.KeyWordCalculator;
import model.FactModel;
import model.EvidenceModel;

import org.xml.sax.SAXException;

import reader.ReaderFactory;
import reader.XMLReader;

public class ConsoleWriter {

	public static void main(String[] args) {
		String path = "Users/dongyixuan/workspace/证据链/裁判文书/故意杀人罪/2007/result";
		File file = new File(path);
		String[] fileList = file.list();
		String filePath = path+"/"+fileList[0];
//		String filePath = path+"\\64174.xml";
		
		ReaderFactory fac = new ReaderFactory();
		XMLReader xmlReader = new XMLReader();
		String type = xmlReader.getType(filePath);
		xmlReader = fac.createXMLReader(type);
		
		System.out.println("文件："+filePath);
		HashSet<String> typeList = new HashSet<>();
		
		try {
			@SuppressWarnings("unchecked")
			//读取事实集及事实相关证据集
			ArrayList<FactModel> fList = xmlReader.getFactlist(filePath,typeList);
			//读取无关联点证据集
			ArrayList<EvidenceModel> eList = xmlReader.getEvidencelist(filePath,typeList);
			
			//测试事实集和证据集获取
//			for(FactModel fact : fList){
//				System.out.println("事实:"+fact.getContent());
//				ArrayList<EvidenceModel> ceList = fact.getEvidenceList();
//				for(EvidenceModel evi : ceList){
//					System.out.println(evi.getContent());
//				}
//			}
//			System.out.println("无关联证据集：");
//			for(EvidenceModel evi : eList){
//				System.out.println(evi.getContent());
//			}
			
			//提取关键要素
			KeyWordCalculator calculator = new KeyWordCalculator();
			calculator.calcKeyWord(fList,eList);
			
			ChainCreator creator = new ChainCreator();
			//构建证据事实关系
//			creator.calcLink(fList, eList);
			//计算证据链头
//			creator.calcHead(fList);
			
			for(FactModel fact : fList){
				System.out.println("事实:"+fact.getContent());
				ArrayList<EvidenceModel> ceList = fact.getEvidenceList();
				for(EvidenceModel evi : ceList){
					System.out.println("证据:"+evi.getContent());
					ArrayList<String> headList = evi.getHeadList();
					for(String head : headList){
						System.out.println("	链头:"+head);
					}
				}
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

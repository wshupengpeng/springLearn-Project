package poi.doc;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
@Slf4j
public class WordDocxHeaderFooter {

	public static void main(String[] args) throws Exception {
		wordHeaderFooter("word_docx_header_footer.docx");
	}

	public static void wordHeaderFooter(final String wordFileName) throws Exception {
//		XWPFDocument doc = new XWPFDocument(new FileInputStream("页眉.docx"));
		XWPFDocument doc = new XWPFDocument();
		XWPFHeader header = doc.createHeader(HeaderFooterType.FIRST);
//		header.addPictureData(new FileInputStream("3629452b-e66e-42a4-941b-d1cd07498e1a.png"),5);
		XWPFParagraph paragraph = header.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText("页眉");
		XWPFPicture xwpfPicture = run.addPicture(new FileInputStream("3629452b-e66e-42a4-941b-d1cd07498e1a.png"), XWPFDocument.PICTURE_TYPE_PNG, "1.png", 50, 50);
		XWPFFooter footer = doc.createFooter(HeaderFooterType.FIRST);
		for( XWPFPictureData picturedata : header.getAllPackagePictures() ) { // 这段必须有，不然打开的logo图片不显示
			String relationId = header.getRelationId(picturedata);
			xwpfPicture.getCTPicture().getBlipFill().getBlip().setEmbed( relationId );
		}
		// 2. 获取到图片数据
		CTDrawing drawing = run.getCTR().getDrawingArray(0);
		CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();

		//拿到新插入的图片替换添加CTAnchor 设置浮动属性 删除inline属性
		CTAnchor anchor = getAnchorWithGraphic(graphicalobject, "TEST1",
				Units.toEMU(50), Units.toEMU(50),//图片大小
				Units.toEMU(50), Units.toEMU(50), true);//相对当前段落位置 需要计算段落已有内容的左偏移
		drawing.setAnchorArray(new CTAnchor[]{anchor});//添加浮动属性
		drawing.removeInline(0);//删除行内属性
		footer.addPictureData(new FileInputStream("3629452b-e66e-42a4-941b-d1cd07498e1a.png"),6);
		paragraph = footer.createParagraph();
		run = paragraph.createRun();
		run.setText("页脚");
//		run.addPicture(new FileInputStream("3629452b-e66e-42a4-941b-d1cd07498e1a.png"),6,"a",50,50);
		XWPFRun run2 = doc.createParagraph().createRun();
		run2.setText("文本1111111111111111111111111111111111");
		run2.addBreak(BreakType.PAGE);
//		doc.createParagraph().createRun().addBreak(BreakType.PAGE);
		doc.createParagraph().createRun().addBreak(BreakType.PAGE);
		XWPFParagraph paragraph1 = doc.createHeader(HeaderFooterType.DEFAULT).createParagraph();
		XWPFRun run1 = paragraph1.createRun();
		run1.setText("第二页页眉");
		paragraph = doc.createFooter(HeaderFooterType.DEFAULT).createParagraph();
		run = paragraph.createRun();
		run.setText("第二页页脚");

//
//		paragraph1 = doc.createHeader(HeaderFooterType.EVEN).createParagraph();
//		run1 = paragraph1.createRun();
//		run1.setText("第三页页眉");
//		paragraph = doc.createFooter(HeaderFooterType.DEFAULT).createParagraph();
//		run = paragraph.createRun();
//		run.setText("第三页页脚");
//		XWPFDocument document= new XWPFDocument();
//
//		XWPFParagraph paragraph;
//		XWPFRun run;
//
//		// the body content
//		paragraph = document.createParagraph();
//		run=paragraph.createRun();
//		run.setText("Lorem ipsum.... page 1");
//
//		paragraph = document.createParagraph();
//		run=paragraph.createRun();
//		run.addBreak(BreakType.PAGE);
//		run.setText("Lorem ipsum.... page 2");
//
//		// create even header
//		document
//				.createHeader(HeaderFooterType.EVEN).addPictureData(new )
//				.createParagraph()
//				.createRun()
//				.setText("even");
		doc.write(new FileOutputStream(wordFileName));

//		int pageCount = new XWPFDocument(new FileInputStream(wordFileName));

//		int pageCount = doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
//		//总页数
//		System.out.println("doc 总页数:"+pageCount);

//		List<XWPFHeader> headerList = doc.getHeaderList();
//		int pageSize = 0;
//		for (XWPFHeader xwpfHeader : headerList) {
//			for (XWPFParagraph paragraph : xwpfHeader.getParagraphs()) {
//				for (XWPFRun run : paragraph.getRuns()) {
//					System.out.println("runText:"+run.text());
//					run.setText("");
//				}
//				if(paragraph.isPageBreak()){
//					pageSize++;
//				}
//				System.out.println("paragraph:"+paragraph.getParagraphText());
//			}
//		}
//		doc.createHeaderFooterPolicy().createHeader(XWPFHeaderFooterPolicy.FIRST,);
//		System.out.println("pageSize:"+pageSize);


		// create a paragraph with justify alignment
//		XWPFParagraph p1 = doc.createParagraph();
//
//		// first line indentation in the paragraph
//		p1.setFirstLineIndent(400);
//
//		// justify alignment
//		p1.setAlignment(ParagraphAlignment.DISTRIBUTE);
//
//		// wrap words
//		p1.setWordWrapped(true);
//
//		// XWPFRun object defines a region of text with a common set of
//		// properties
//		XWPFRun r1 = p1.createRun();
//		String t1 = "Paragraph 1. Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog."
//				+ " Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
//		r1.setText(t1);
//
//		// create a paragraph with left alignment
//		XWPFParagraph p2 = doc.createParagraph();
//
//		// first line indentation in the paragraph
//		p2.setFirstLineIndent(400);
//
//		// left alignment
//		p2.setAlignment(ParagraphAlignment.LEFT);
//
//		// wrap words
//		p2.setWordWrapped(true);
//
//		// XWPFRun object defines a region of text with a common set of
//		// properties
//		XWPFRun r2 = p2.createRun();
//		String t2 = "Paragraph 2. Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog."
//				+ " Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
//		r2.setText(t2);
//
//		XWPFParagraph[] pars;
//
//		try {
//			CTP ctP = CTP.Factory.newInstance();
//
//			// header text
//			CTText t = ctP.addNewR().addNewT();
//			t.setStringValue("Sample Header Text");
//
//			pars = new XWPFParagraph[1];
//			p1 = new XWPFParagraph(ctP, doc);
//			pars[0] = p1;
//
//			XWPFHeaderFooterPolicy hfPolicy = doc.createHeaderFooterPolicy();
//			hfPolicy.createHeader(XWPFHeaderFooterPolicy.EVEN, pars);
//
//			ctP = CTP.Factory.newInstance();
//			t = ctP.addNewR().addNewT();
//
//			// footer text
//			t.setStringValue("Sample Footer Text1");
//			pars = new XWPFParagraph[2];
//
//			pars[0] = new XWPFParagraph(ctP, doc);
//
//			ctP = CTP.Factory.newInstance();
//			t = ctP.addNewR().addNewT();
//			t.setStringValue("Sample Footer Text2");
//			pars[1] = new XWPFParagraph(ctP, doc);
//
//			hfPolicy.createFooter(XWPFHeaderFooterPolicy.EVEN, pars);
//
//			// write to word docx
//			OutputStream os = new FileOutputStream(new File(wordFileName));
//			doc.write(os);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject,
												String deskFileName, int width, int height,
												int leftOffset, int topOffset, boolean behind) {
		String anchorXML =
				"<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
						+ "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"" + ((behind) ? 1 : 0) + "\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
						+ "<wp:simplePos x=\"0\" y=\"0\"/>"
						+ "<wp:positionH relativeFrom=\"column\">"
						+ "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
						+ "</wp:positionH>"
						+ "<wp:positionV relativeFrom=\"paragraph\">"
						+ "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
						"</wp:positionV>"
						+ "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
						+ "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
						+ "<wp:wrapNone/>"
						+ "<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\"" + deskFileName + "\"/><wp:cNvGraphicFramePr/>"
						+ "</wp:anchor>";

		CTDrawing drawing = null;
		try {
			drawing = CTDrawing.Factory.parse(anchorXML);
		} catch (XmlException e) {
			e.printStackTrace();
		}
		CTAnchor anchor = drawing.getAnchorArray(0);
		anchor.setGraphic(ctGraphicalObject);
		return anchor;
	}

}

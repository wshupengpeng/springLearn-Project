package poi.doc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;

public class WordDocxHeaderFooter {

	public static void main(String[] args) throws Exception {
		wordHeaderFooter("word_docx_header_footer.docx");
	}

	public static void wordHeaderFooter(final String wordFileName) throws Exception {
		XWPFDocument doc = new XWPFDocument(new FileInputStream("页眉.docx"));
//		XWPFDocument doc = new XWPFDocument();
//		XWPFParagraph paragraph = doc.createHeader(HeaderFooterType.FIRST).createParagraph();
//		XWPFRun run = paragraph.createRun();
//		run.setText("页眉");
//		paragraph = doc.createFooter(HeaderFooterType.FIRST).createParagraph();
//		run = paragraph.createRun();
//		run.setText("页脚");
//		doc.createParagraph().createRun().addBreak(BreakType.PAGE);
//		doc.createParagraph().createRun().addBreak(BreakType.PAGE);
//		XWPFParagraph paragraph1 = doc.createHeader(HeaderFooterType.DEFAULT).createParagraph();
//		XWPFRun run1 = paragraph1.createRun();
//		run1.setText("第二页页眉");
//		paragraph = doc.createFooter(HeaderFooterType.DEFAULT).createParagraph();
//		run = paragraph.createRun();
//		run.setText("第二页页脚");
//
//		doc.write(new FileOutputStream(wordFileName));

//		int pageCount = new XWPFDocument(new FileInputStream(wordFileName))

		int pageCount = doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
		//总页数
		System.out.println("doc 总页数:"+pageCount);

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

}

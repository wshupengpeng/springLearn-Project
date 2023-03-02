package poi;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.*;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/22 16:53
 */
public class PoiConvert {

    /**
     *  通过poifs无法转换base64image图片
     * @throws IOException
     */
    @Test
    public void htmlConvertWordByPoi() throws IOException {
        String srcFile = "D:\\hpp\\1.html";
        String destFile = "d:\\hpp\\1.doc";
        FileInputStream fileInputStream = new FileInputStream(srcFile);
        String str = " <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val='Cambria Math'/><m:brkBin m:val='before'/><m:brkBinSub m:val='--'/><m:smallFrac m:val='off'/><m:dispDef/><m:lMargin m:val='0'/> <m:rMargin m:val='0'/><m:defJc m:val='centerGroup'/><m:wrapIndent m:val='1440'/><m:intLim m:val='subSup'/><m:naryLim m:val='undOvr'/></m:mathPr></w:WordDocument></xml><![endif]-->";
        byte [] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        String content = new String(data,"utf-8");
        String h = " <html xmlns:v='urn:schemas-microsoft-com:vml'xmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'  ";
        content = h + "<head>"+"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+str+"</head><body>"+content+"</body> </html>";
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry documentEntry = directory.createDocument("WordDocument", new ByteArrayInputStream(content.getBytes()));
        FileOutputStream ostream = new FileOutputStream(destFile);
        poifs.writeFilesystem(ostream);
        fileInputStream.close();
        ostream.close();
    }

    @Test
    public void htmlConvertWordByJsonp()throws IOException{
        String srcFile = "D:\\hpp\\1.html";
        String destFile = "d:\\hpp\\1.doc";
        File file = new File(srcFile);
        Document parse = Jsoup.parse(file, "utf-8");
    }
}

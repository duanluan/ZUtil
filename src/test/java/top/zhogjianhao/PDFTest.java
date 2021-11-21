// package top.zhogjianhao;
//
// import org.apache.commons.io.output.FileWriterWithEncoding;
// import org.dom4j.Attribute;
// import org.dom4j.Document;
// import org.dom4j.DocumentException;
// import org.dom4j.Element;
// import org.dom4j.io.OutputFormat;
// import org.dom4j.io.SAXReader;
// import org.dom4j.io.XMLWriter;
// import org.dom4j.tree.DefaultElement;
// import ws.vinta.pangu.Pangu;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.Iterator;
//
// import static java.lang.System.out;
//
// public class PDFTest {
//
//   private static final Pangu pangu = new Pangu();
//
//   private static final String BOOKMARK_ELEMENT_NAME = "书签";
//
//   public static void main(String[] args) throws DocumentException, IOException {
//     InputStreamReader reader = new InputStreamReader(new FileInputStream("E:/DesktopFiles/1.xml"), "GB2312");
//     Document doc = new SAXReader().read(reader);
//     reader.close();
//
//     Element newRootElement = new DefaultElement("PDF信息");
//     Element bookmarksElement = new DefaultElement("文档书签");
//
//     // 递归处理
//     Iterator<Element> bookmarkIterator = doc.getRootElement().element("文档书签").elementIterator(BOOKMARK_ELEMENT_NAME);
//     // 第一层书签默认打开
//     updateBookmarkName(bookmarkIterator, bookmarksElement);
//     bookmarkIterator = bookmarksElement.elementIterator(BOOKMARK_ELEMENT_NAME);
//     while (bookmarkIterator.hasNext()) {
//       Element bookmarkElement = bookmarkIterator.next();
//       if (bookmarkElement.elementIterator(BOOKMARK_ELEMENT_NAME).hasNext()) {
//         bookmarkElement.addAttribute("默认打开", "是");
//       }
//     }
//
//     newRootElement.add(bookmarksElement);
//     doc.setRootElement(newRootElement);
//
//     // 格式
//     OutputFormat format = OutputFormat.createPrettyPrint();
//     format.setEncoding("GB2312");
//     format.setNewlines(true);
//     format.setIndent(true);
//
//     XMLWriter writer = new XMLWriter(new FileWriterWithEncoding(new File("E:/DesktopFiles/1.xml"), "GB2312"), format);
//     writer.write(doc);
//     writer.flush();
//     writer.close();
//   }
//
//   private static void updateBookmarkName(Iterator<Element> bookmarkIterator, Element bookmarksElement) {
//     while (bookmarkIterator.hasNext()) {
//       Element bookmarkElement = bookmarkIterator.next();
//
//       Attribute bookmarkText = bookmarkElement.attribute("文本");
//       bookmarkText.setValue(pangu.spacingText(bookmarkText.getValue()));
//       out.println(bookmarkText.getValue());
//
//       Element newBookmarkElement = new DefaultElement(BOOKMARK_ELEMENT_NAME);
//       newBookmarkElement.addAttribute("文本", bookmarkText.getValue());
//       newBookmarkElement.addAttribute("默认打开", "否");
//       newBookmarkElement.addAttribute("动作", bookmarkElement.attribute("动作").getValue());
//       newBookmarkElement.addAttribute("页码", bookmarkElement.attribute("页码").getValue());
//       bookmarksElement.add(newBookmarkElement);
//
//       Iterator<Element> subBookmarkIterator = bookmarkElement.elementIterator(BOOKMARK_ELEMENT_NAME);
//       if (subBookmarkIterator.hasNext()) {
//         updateBookmarkName(subBookmarkIterator, newBookmarkElement);
//       }
//     }
//   }
// }

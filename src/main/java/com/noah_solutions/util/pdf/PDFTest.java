package com.noah_solutions.util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFTest {
    @Test
    public void testPDF() {
        generatePDF("D:\\PDFTEXT\\officialReceipt.PDF", "D:\\PDFTEXT\\ticket_out.pdf", "C:\\Users\\Administrator\\Desktop\\餐品图片\\优活餐品.jpg");
    }
    public static void main(String[] args) throws IOException, DocumentException {

        /* example inspired from "iText in action" (2006), chapter 2 */

        PdfReader reader = new PdfReader("D:\\PDFTEXT\\officialReceipt.PDF"); // input PDF
        PdfStamper stamper = new PdfStamper(reader,
                new FileOutputStream("D:\\PDFTEXT\\ticket_out.pdf")); // output PDF
        BaseFont bf = BaseFont.createFont(
                BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font

        //loop on pages (1-based)
        for (int i=1; i<=reader.getNumberOfPages(); i++){

            // get object for writing over the existing content;
            // you can also use getUnderContent for writing in the bottom layer
            PdfContentByte over = stamper.getOverContent(i);

            // write text
            over.beginText();
            over.setFontAndSize(bf, 10);    // set font and size
            over.setTextMatrix(107, 740);   // set x,y position (0,0 is at the bottom left)
            over.showText("I can write at page " + i);  // set text
            over.endText();

            // draw a red circle
            over.setRGBColorStroke(0xFF, 0x00, 0x00);
            over.setLineWidth(5f);
            over.ellipse(250, 450, 350, 550);
            over.stroke();
        }

        stamper.close();

    }

    /**
     * 生成新的PDF
     *
     * @param pdfPath    要编辑的PDF路径
     * @param newPDFPath 生成新的PDF路径
     * @param imagePath  　插入图片路径
     */
    public void generatePDF(String pdfPath, String newPDFPath, String imagePath) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            //创建一个pdf读入流
            reader = new PdfReader(pdfPath);
            //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.
            stamper = new PdfStamper(reader, new FileOutputStream(newPDFPath));
            //这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
            Font font = new Font(bf, 10);
            font.setStyle(Font.BOLD);
            font.getBaseFont();
            PdfContentByte over;
            //页数是从1开始的
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
                over = stamper.getOverContent(i);
                //当前页的下层打印内容  按自己需求选择
                //over = stamper.getUnderContent(i);
                //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.
                PdfDictionary p = reader.getPageN(i);
                //拿到mediaBox 里面放着该页pdf的大小信息.
                PdfObject po = p.get(new PdfName("MediaBox"));
                //po是一个数组对象.里面包含了该页pdf的坐标轴范围.
                PdfArray pa = (PdfArray) po;
                //开始写入文本
                over.beginText();
                //设置字体和大小
                over.setFontAndSize(font.getBaseFont(), 24);
                //设置字体颜色
                over.setColorFill(new BaseColor(0, 110, 107, 100));
                com.itextpdf.text.pdf.PdfGState gState = new PdfGState();
                gState.setStrokeOpacity(0.1f);
                over.setGState(gState);
                //要输出的text          对齐方式          写的字        设置字体的输出位置  字体是否旋转
                over.showTextAligned(0, "HELLO WORLD", 100, 100, 0);
                over.endText();
                //创建一个image对象.
                Image image = Image.getInstance(imagePath);
                //设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值  0, 0, 841.92, 595.32
                image.setAbsolutePosition(0, 500);
                //设置插入的图片大小
                image.scaleToFit(50, 50);
                over.addImage(image);
                //画一个圈.
                over.setRGBColorStroke(0xFF, 0x00, 0x00);
                over.setLineWidth(5f);
                over.ellipse(250, 450, 350, 550);
                over.stroke();
            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
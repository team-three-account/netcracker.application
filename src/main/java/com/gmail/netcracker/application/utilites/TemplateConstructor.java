package com.gmail.netcracker.application.utilites;

import com.lowagie.text.*;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class TemplateConstructor extends PdfPageEventHelper {

    private static final String footerPartOne = "© 2018 Cracker-Time Corporation CONFIDENTIAL AND PROPRIETARY";
    private static final String footerPartTwo = "Disclose and distribute solely to those individuals with a need to know";

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 20);
        Phrase header = new Phrase("Upcoming events:", font);
        Phrase footerOne = new Phrase(footerPartOne);
        Phrase footerTwo = new Phrase(footerPartTwo);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footerOne,
                (document.right() - document.left()) / 2 - document.leftMargin() ,
                document.bottom() + 5, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footerTwo,
                (document.right() - document.left()) / 2 - document.leftMargin(),
                document.bottom() - 10, 0);

    }
}

package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PdfConstructor {

    @Autowired
    EventService eventService;

    public File construct (List<Event> planedEvents) {

        File file = new File("attach.pdf");
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            TemplateConstructor event = new TemplateConstructor();
            writer.setPageEvent(event);

            document.open();

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            addTableHeader(table);
            addRows(table, planedEvents);

            document.add(table);
            document.close();
        }
        catch (FileNotFoundException e){
            return null;
        }
        catch (DocumentException e){
            return null;
        }
        return file;
    }

    private void addTableHeader(PdfPTable table) {

        Stream.of("Name", "Start Date", "End Date", "Location")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setMinimumHeight(30);
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, List<Event> planedEvents) {
        planedEvents.forEach(event ->{
            table.addCell(event.getName());
            table.addCell(event.getDateStart().substring(0,16));
            String endDate = eventService.getEndDateFromDuration(event.getDateStart(), event.getDuration());
            table.addCell(endDate.substring(0,16));
            table.addCell(event.getEventPlaceName());
        });
    }
}

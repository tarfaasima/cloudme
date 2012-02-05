package org.cloudme.notepad.stripes.action.export;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.notepad.export.excel.ExcelExportService;
import org.cloudme.notepad.meeting.Meeting;
import org.cloudme.notepad.meeting.MeetingService;
import org.cloudme.sugar.AbstractActionBean;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

@Getter
@Setter
@UrlBinding( "/app/export/{meeting.id}" )
public class ExportActionBean extends AbstractActionBean {
    @Inject private ExcelExportService excelExportService;
    @Inject private MeetingService meetingService;
    private Meeting meeting;

    @DefaultHandler
    public Resolution xls() throws IOException {
        meeting = meetingService.find(Id.of(meeting));
        HttpServletResponse response = getContext().getResponse();
        response.setContentType(excelExportService.getContentType());
        response.setHeader("Content-Diposition", "attachment; filename=\""
                + excelExportService.createFileName(meeting)
                + "\"");
        excelExportService.export(meeting, response.getOutputStream());
        return null;
    }
}

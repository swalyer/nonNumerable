package com.MDM.demo.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

@Service
public class RequestDocumentGenerator {

    public void generateRequestDocument(
        String requestType,
        String firstName,
        String lastName,
        String middleName,
        String personalNumber,
        LocalDateTime requestedDate,
        String additionalInfo,
        LocalDate vacationStartDate,
        LocalDate vacationEndDate,
        String unpaidLeaveReason, 
        LocalDate unpaidLeaveStartDate, 
        LocalDate unpaidLeaveEndDate 
    ) {
        String templateFilePath = getTemplateFilePath(requestType);
        String outputDirectory = "output/";
        createDirectoryIfNotExists(outputDirectory);
        String outputFilePath = outputDirectory + requestType.replaceAll(" ", "_") + "_" + personalNumber + ".docx";

        try (FileInputStream templateInputStream = new FileInputStream(templateFilePath);
             XWPFDocument document = new XWPFDocument(templateInputStream)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = text.replace("{{requestType}}", requestType)
     .replace("{{firstName}}", firstName)
    .replace("{{lastName}}", lastName)
     .replace("{{middleName}}", middleName)
    .replace("{{personalNumber}}", personalNumber)
    .replace("{{requestedDate}}", requestedDate.toString())
    .replace("{{additionalInfo}}", additionalInfo)
    .replace("{{vacationStartDate}}", vacationStartDate != null ? vacationStartDate.toString() : "")
    .replace("{{vacationEndDate}}", vacationEndDate != null ? vacationEndDate.toString() : "")
    .replace("{{unpaidLeaveReason}}", unpaidLeaveReason != null ? unpaidLeaveReason : "")
    .replace("{{unpaidLeaveStartDate}}", unpaidLeaveStartDate != null ? unpaidLeaveStartDate.toString() : "")
    .replace("{{unpaidLeaveEndDate}}", unpaidLeaveEndDate != null ? unpaidLeaveEndDate.toString() : "");
                        run.setText(text, 0);
                    }
                }
            }
            try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
                document.write(out);
            }

        } catch (IOException e) {
            System.err.println("Error with doc creation: " + e.getMessage());
        }
    }

    private String getTemplateFilePath(String requestType) {
        return "src/main/resources/" + switch (requestType) {
            case "Изменение в расписании спектаклей" -> "templates/change_schedule_template.docx";
            case "Заявка на финансирование" -> "templates/funding_request_template.docx";
            case "Заявка на отпуск" -> "templates/vacation_request_template.docx";
            case "Заявка на отпуск без содержания" -> "templates/unpaid_leave_request_template.docx";
            case "Аренда помещения" -> "templates/venue_rental_request_template.docx";
            case "Организация мероприятия" -> "templates/event_organization_request_template.docx";
            default -> throw new IllegalArgumentException("Неизвестный тип заявки: " + requestType);
        };
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.err.println("Error with doc direcroty: " + e.getMessage());
            }
        }
    }
}


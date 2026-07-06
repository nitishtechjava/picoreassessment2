package Service;

import Entity.CDRLogEntity;
import Entity.CallDetailRecord;
import Repository.CallDetailRecordRepository;
import Repository.CdrLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class FileLoaderService {

    @Value("${file.input.path}")
    private String inputFolder;

    @Value("${file.processed.path}")
    private String processedFolder;

    @Value("${file.failed.path}")
    private String failedFolder;

    private final CallDetailRecordRepository repository;

    private final CdrLogRepository logRepository;


    public void processFiles() {

        File folder = new File(inputFolder);

        File[] files = folder.listFiles(file ->
                file.isFile() &&
                        file.getName().endsWith(".log"));

        if(files==null)
            return;

        for(File file:files){

            processFile(file);

        }



    }

    private void processFile(File file) {

        int successCount = 0;
        int failedCount = 0;

        // Create log entry
        CDRLogEntity log = new CDRLogEntity();
        log.setFileName(file.getName());
        log.setUploadStartTime(LocalDateTime.now());
        log.setStatus("PROCESSING");

        log = logRepository.save(log);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {

                    String[] values = line.split("\\|", -1);

                    CallDetailRecord record = mapToEntity(values);

                    repository.save(record);

                    successCount++;

                } catch (Exception ex) {

                    failedCount++;

                    System.out.println("Failed Record : " + line);

                    ex.printStackTrace();
                }
            }

            log.setUploadEndTime(LocalDateTime.now());
            log.setSuccessRecords(successCount);
            log.setFailedRecords(failedCount);
            log.setTotalRecords(successCount + failedCount);

            if (failedCount == 0) {
                log.setStatus("SUCCESS");
            } else if (successCount == 0) {
                log.setStatus("FAILED");
            } else {
                log.setStatus("PARTIAL_SUCCESS");
            }

            logRepository.save(log);

            moveToProcessedFolder(file);

        } catch (Exception ex) {

            log.setUploadEndTime(LocalDateTime.now());
            log.setStatus("FAILED");
            logRepository.save(log);

            moveToFailedFolder(file);

            ex.printStackTrace();
        }
    }

    private CallDetailRecord mapToEntity(String[] values) {

        CallDetailRecord record = new CallDetailRecord();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        record.setRecordDate(LocalDateTime.parse(values[0], formatter));

        record.setLSpc(parseInteger(values[1]));
        record.setLSsn(parseInteger(values[2]));
        record.setLRi(parseInteger(values[3]));
        record.setLGtI(parseInteger(values[4]));
        record.setLGtDigits(values[5]);

        record.setRSpc(parseInteger(values[6]));
        record.setRSsn(parseInteger(values[7]));
        record.setRRi(parseInteger(values[8]));
        record.setRGtI(parseInteger(values[9]));
        record.setRGtDigits(values[10]);

        record.setServiceCode(values[11]);

        record.setOrNature(parseInteger(values[12]));
        record.setOrPlan(parseInteger(values[13]));
        record.setOrDigits(values[14]);

        record.setDeNature(parseInteger(values[15]));
        record.setDePlan(parseInteger(values[16]));
        record.setDeDigits(values[17]);

        record.setIsdnNature(parseInteger(values[18]));
        record.setIsdnPlan(parseInteger(values[19]));
        record.setMsisdn(values[20]);

        record.setVlrNature(parseInteger(values[21]));
        record.setVlrPlan(parseInteger(values[22]));
        record.setVlrDigits(values[23]);

        record.setImsi(values[24]);

        record.setStatus(values[25]);
        record.setType(values[26]);

        record.setTstamp(LocalDateTime.parse(values[27], formatter));

        record.setLocalDialogId(parseLong(values[28]));
        record.setRemoteDialogId(parseLong(values[29]));
        record.setDialogDuration(parseLong(values[30]));

        record.setUssdString(values[31]);

        record.setCdrId(values[32]);

        return record;
    }

    private Integer parseInteger(String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Integer.valueOf(value.trim());
    }

    private Long parseLong(String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return Long.valueOf(value.trim());
    }

}
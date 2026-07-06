package Service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FolderScheduler {

    private final FileLoaderService service;


    @Scheduled(fixedDelay = 60000)
    public void watchFolder(){

        service.processFiles();

    }

}
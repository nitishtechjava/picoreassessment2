package Controller;


import Service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileProcessorController {

@Autowired
private FileProcessorService fileProcessorService;


}

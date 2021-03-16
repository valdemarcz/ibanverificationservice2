package task.valdemarc.ibanvalidationtaskservice2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import task.valdemarc.ibanvalidationtaskservice2.model.FileInfo;
import task.valdemarc.ibanvalidationtaskservice2.model.ResponseMessage;
import task.valdemarc.ibanvalidationtaskservice2.service.FileStorageService;
import task.valdemarc.ibanvalidationtaskservice2.service.IbanValidationServices;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("ibanfile")
public class IbanController {

    @Autowired
    FileStorageService storageService;

    IbanValidationServices verif = new IbanValidationServices();

    @PostMapping("v1/process")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.deleteAll();
            storageService.init();
            storageService.save(file);

            verif.validateIbanFromFile(storageService.getRoot() + "/" + file.getOriginalFilename());
            verif.bankNameInsert(storageService.getRoot() + "/" + file.getOriginalFilename());
            System.out.println(storageService.getRoot() + "/" + file.getOriginalFilename());

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/v1/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(IbanController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


    @GetMapping("/v1/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    }



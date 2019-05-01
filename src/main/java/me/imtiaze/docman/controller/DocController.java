package me.imtiaze.docman.controller;

import me.imtiaze.docman.domain.Doc;
import me.imtiaze.docman.exception.DocNotFoundException;
import me.imtiaze.docman.repo.DocRepo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


/**
 *
 * This is the class that handles all the API requests.
 *
 */

@RestController
@RequestMapping(produces = "text/plain; charset=us-ascii")
public class DocController {

    private static final Logger logger = LoggerFactory.getLogger(DocController.class);
    private DocRepo docRepo;

    @Autowired
    public DocController(DocRepo docRepo) {
        this.docRepo = docRepo;
    }

    /**
     * Returns a Doc from DB if found and HTTP 200 OK status. If not found throws exception,
     * which returns a 404 Not Found status <br>
     */
    @GetMapping(value = "/storage/documents/{docId}")
    public String getDocById(@PathVariable String docId) {

        logger.trace("Returning Doc By ID: " + docId);

        return docRepo
                .findById(docId)
                .orElseThrow(
                        () -> new DocNotFoundException(docId)
                ).getDocContent();
    }

    /**
     * Returns a Doc from DB if found and HTTP 200 OK status. If not found throws exception,
     * which returns a 404 Not Found status <br>
     */
    @GetMapping(value = "/storage/documents/{docId}", params = "download")
    public ResponseEntity<Resource> getDocById(@PathVariable String docId, @RequestParam("download") String download) {

        logger.trace("Returning Doc By ID: " + docId);
        logger.trace("Download Requested: " + download);

        Doc doc = docRepo
                .findById(docId)
                .orElseThrow(
                        () -> new DocNotFoundException(docId)
                );

        ByteArrayResource resource = new ByteArrayResource(doc.getBinaryFile().getData());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getDocContent() + "\"")
                .contentType(
                        MediaType.parseMediaType(doc.getContentType()))
                .body(resource);
    }

    /**
     * Accepts the docContent from POST and creates a new doc, and saves it to DB,
     * returns docId when successful and HTTP 201 Created status <br>
     */
    @PostMapping(value = "/storage/documents", params = "docContent")
    @ResponseStatus(HttpStatus.CREATED)
    public String postNewDoc(@RequestParam String docContent) {

        Doc doc = new Doc();
        doc.setDocContent(docContent);

        logger.trace("Saving New Doc - Content: " + doc.getDocContent());
        return docRepo.save(doc).getId();

    }

    /**
     * Accepts file from POST and creates a new doc, and saves it to DB,
     * returns docId when successful and HTTP 201 Created status.
     */
    @PostMapping(value = "/storage/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public String postNewDoc(@RequestParam("docContent") MultipartFile docContent) {

        Doc doc = new Doc();
        try {
            doc.setDocContent(docContent.getOriginalFilename());
            doc.setContentType(docContent.getContentType());
            doc.setBinaryFile(new Binary(BsonBinarySubType.BINARY, docContent.getBytes()));
        } catch (IOException e) {
            logger.error("Error in postNewDoc MultipartFile");
        }

        return docRepo.save(doc).getId();

    }

    /**
     * Updates docContent if available by Id and returns HTTP 204 No Content. If no Doc is found with provided docId
     * throws exception which returns a 404 NOT FOUND
     *
     */
    @PutMapping(value = "/storage/documents/{docId}", params = "docContent")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDoc(@PathVariable String docId, @RequestParam String docContent) {

        logger.trace("Updating Doc - ID: " + docId);

        docRepo.findById(docId)
                .map(doc -> {
                    doc.setDocContent(docContent);
                    return docRepo.save(doc);
                })
                .orElseThrow(
                        () -> new DocNotFoundException(docId)
                );
    }

    @PutMapping(value = "/storage/documents/{docId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDoc(@PathVariable String docId, @RequestParam("docContent") MultipartFile docContent) {

        logger.trace("Updating Doc - ID: " + docId);

        docRepo.findById(docId)
                .map(doc -> {
                    doc.setDocContent(docContent.getOriginalFilename());
                    doc.setContentType(docContent.getContentType());
                    try {
                        doc.setBinaryFile(new Binary(BsonBinarySubType.BINARY, docContent.getBytes()));
                    } catch (IOException e) {
                        logger.error("Error in updateDoc MultipartFile");
                    }
                    return docRepo.save(doc);
                })
                .orElseThrow(
                        () -> new DocNotFoundException(docId)
                );
    }

    /**
     * Delete doc if available by Id and returns HTTP 204 No Content. If no Doc is found with provided docId
     * throws exception which returns a 404 NOT FOUND
     *
     */
    @DeleteMapping(value = "/storage/documents/{docId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoc(@PathVariable String docId) {

        logger.trace("Deleting Doc - ID: " + docId);

        Optional<Doc> docById = docRepo.findById(docId);

        if (docById.isPresent()) {
            docRepo.deleteById(docId);
        } else {
            throw new DocNotFoundException(docId);
        }

    }

}

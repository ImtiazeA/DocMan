package me.imtiaze.docman.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This is the domain class for the app. This is a simple POJO with some annotations.
 * Here, @Getter and @Setter are annotations to create Getter and Setter methods at compile time
 *
 * 1 - @Document is to mark the class as a Mongo Document
 * 2 - @Id indicates the annotated id as the object Id for the Mongo Record
 *
 * */
@Document
public class Doc {

    @Id
    private String id;
    private String docContent;
    private String contentType;
    private Binary binaryFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Binary getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(Binary binaryFile) {
        this.binaryFile = binaryFile;
    }
}

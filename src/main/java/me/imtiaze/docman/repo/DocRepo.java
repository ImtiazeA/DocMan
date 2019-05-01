package me.imtiaze.docman.repo;

import me.imtiaze.docman.domain.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * The implementation of this interface is created by Spring itself and provides
 * a lot of convenience methods to get data out of MongoDB. Addition methods can be
 * created based on properties right from the interface and declaring the methods
 * and passing parameters following Spring guidelines.
 *
 */
public interface DocRepo extends MongoRepository<Doc, String> {
}

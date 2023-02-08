package com.interview.interview.util;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonLongDocument;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class IdGenerator {
    private final Bucket bucket;

    public IdGenerator(Bucket bucket) {
        this.bucket = bucket;
    }

    public String getCustomerId() {
        Observable<JsonLongDocument> jsonLongDocumentObservable = bucket.async().counter("CUS", 1, 101);  // add 1 to the counter, and start at 101
        JsonLongDocument doc = jsonLongDocumentObservable.toBlocking().first();

        return doc.id() + doc.content();
    }
    public String getOrderId() {
        Observable<JsonLongDocument> jsonLongDocumentObservable = bucket.async().counter("ORD", 1, 101); // add 1 to the counter, and start at 101
        JsonLongDocument doc = jsonLongDocumentObservable.toBlocking().first();

        return doc.id() + doc.content();
    }

}
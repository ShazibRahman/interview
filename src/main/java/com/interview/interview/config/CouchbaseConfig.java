package com.interview.interview.config;

import com.couchbase.client.core.env.QueryServiceConfig;
import com.couchbase.client.core.retry.BestEffortRetryStrategy;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${couchbase.bucket.name}")
    private String bucketName;
    @Value("${couchbase.bucket.password}")
    private String bucketpass;
    @Value("${couchbase.bootstrap-hosts}")
    private String bootstrapHost;
    @Value("${couchbase.env.timeouts.connect}")
    private Long connectTimeOut;
    @Value("${couchbase.env.timeouts.query}")
    private Long queryTimeOut;
    @Value("${couchbase.env.timeouts.view}")
    private Long viewTimeOut;
    @Value("${couchbase.env.timeouts.key-value}")
    private Long keyValueTimeOut;
    @Value("${couchbase.env.timeouts.max-request-lifetime}")
    private Long maxRequestLifeTime;
    @Value("${couchbase.env.timeouts.auto-release-after}")
    private Long autoReleaseAfter;


    @Override
    protected List<String> getBootstrapHosts() {

        return Arrays.asList(bootstrapHost.split(","));
    }

    @Override
    protected String getBucketName() {

        return bucketName;
    }

    @Override
    protected String getBucketPassword() {

        return bucketpass;
    }

    @Override
    protected CouchbaseEnvironment getEnvironment() {
        return   DefaultCouchbaseEnvironment.builder().connectTimeout(connectTimeOut)
                .autoreleaseAfter(autoReleaseAfter)
                .queryServiceConfig(QueryServiceConfig.create(2,100))
                .queryTimeout(queryTimeOut).viewTimeout(viewTimeOut).kvTimeout(keyValueTimeOut)
                .retryStrategy(BestEffortRetryStrategy.INSTANCE).maxRequestLifetime(maxRequestLifeTime).build();
    }
}
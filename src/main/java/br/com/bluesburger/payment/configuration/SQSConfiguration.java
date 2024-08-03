package br.com.bluesburger.payment.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Profile("!test")
@Configuration
public class SQSConfiguration {

    @Value("${aws.region}")
    private String awsRegion;
    
    @Value("${aws.credentials.access-key}")
    private String awsAccessKeyId;

    @Value("${aws.credentials.secret-key}")
    private String awsSecretAccessKey;

    @Value("${aws.end-point.uri}")
    private String awsSqsUrl;

    @Bean
    @Primary
    AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsSqsUrl, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
                .build();
    }
    
    @Bean
    QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }
    
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

package br.com.bluesburger.payment.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class DynamoDBConfiguration {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.dynamodb.endpoint}")
    private String awsDynamoEndpoint;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        AwsClientBuilder.EndpointConfiguration awsClientBuilder = new AwsClientBuilder
                .EndpointConfiguration(awsDynamoEndpoint, awsRegion);

        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(awsClientBuilder)
                .build();
    }
}

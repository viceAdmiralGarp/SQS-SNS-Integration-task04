package com.task04;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.syndicate.deployment.annotations.events.SqsTriggerEventSource;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;


@LambdaHandler(
    lambdaName = "sqs_handler",
	roleName = "sqs_handler-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@SqsTriggerEventSource(
        targetQueue = "async_queue",
        batchSize = 10
)
public class SqsHandler implements RequestHandler<SQSEvent, Void> {
	@Override
	public Void handleRequest(SQSEvent event, Context context) {
		event.getRecords().forEach(record -> {
			System.out.println("SQS Message: " + record.getBody());
		});
		return null;
	}
}

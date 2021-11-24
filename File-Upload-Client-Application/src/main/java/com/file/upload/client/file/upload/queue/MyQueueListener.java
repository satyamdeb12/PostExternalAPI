package com.file.upload.client.file.upload.queue;

import com.file.upload.client.file.upload.entity.FileStatus;
import com.file.upload.client.file.upload.repository.FileStatusRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyQueueListener {

    @Autowired
    private FileStatusRepo fileStatusRepo;

    @RabbitListener(queues = Config.QUEUE)
    public void listener(StatusMessage message){
        System.out.println(message);
        FileStatus fileStatus = new FileStatus();
        fileStatus.setMessageId(UUID.randomUUID().toString());
        fileStatus.setUserId(message.getUserId());
        fileStatus.setStatusMessage(message.getStatus());
        fileStatus.setMessageTimestamp(message.getMessageTime());

        fileStatusRepo.save(fileStatus);

    }
}

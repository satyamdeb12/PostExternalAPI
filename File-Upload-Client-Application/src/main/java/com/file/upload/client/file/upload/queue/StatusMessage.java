package com.file.upload.client.file.upload.queue;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusMessage {
    private String userId;
    private String status;
    private Date messageTime;
}

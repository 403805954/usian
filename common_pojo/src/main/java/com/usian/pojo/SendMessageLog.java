package com.usian.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "send_message_log")
public class SendMessageLog {
    @Id
    private Long id;
    private Integer status;
    private String body;

    public SendMessageLog() {
    }


    public SendMessageLog(Long id, Integer status, String body) {
        this.id = id;
        this.status = status;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

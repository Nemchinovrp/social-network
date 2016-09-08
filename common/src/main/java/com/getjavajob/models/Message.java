package com.getjavajob.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message extends AbstractModel {
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;

    @Column(name = "creating_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatingDate;

    @Lob
    private String message;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column(name = "is_read", columnDefinition = "BIT")
    private boolean isRead;

    public enum Type {
        ACC_WALL, ACC_PRIVATE
    }

    public Message() {
    }

    public Message(Account sender, Account receiver, String message, Type type, boolean isRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.creatingDate = new Date();
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        return sender.equals(message1.sender) &&
                receiver.equals(message1.receiver) &&
                creatingDate.equals(message1.creatingDate) &&
                message.equals(message1.message) &&
                type == message1.type;

    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + receiver.hashCode();
        result = 31 * result + creatingDate.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender.getName() +
                ", receiver=" + receiver.getName() +
                ", creatingDate=" + creatingDate +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}

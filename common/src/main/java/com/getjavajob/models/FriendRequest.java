package com.getjavajob.models;

import javax.persistence.*;

@Entity
@Table(name = "friend_requests")
public class FriendRequest extends AbstractModel{

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private Account receiver;

    @ManyToOne
    @JoinColumn(name = "acc_id", referencedColumnName = "id")
    private Account sender;

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

    public FriendRequest() {
    }

    public FriendRequest(Account receiver, Account sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "FriendRequest{\n" +
                "receiver=" + receiver + '\n' +
                ", sender=" + sender + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendRequest that = (FriendRequest) o;

        if (!receiver.equals(that.receiver)) return false;
        return sender.equals(that.sender);

    }

    @Override
    public int hashCode() {
        int result = receiver.hashCode();
        result = 31 * result + sender.hashCode();
        return result;
    }
}

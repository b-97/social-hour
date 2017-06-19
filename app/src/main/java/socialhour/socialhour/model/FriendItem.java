package socialhour.socialhour.model;

import java.util.Date;

/**
 * Created by michael on 3/15/17.
 * Handles all of the data regarding friend requests.
 */

public class FriendItem{
    private PublicUserData initiator;
    private PublicUserData acceptor;
    private Date initiated_date;
    private Date accepted_date;
    private String key;
    private Boolean isAccepted;

    public FriendItem(PublicUserData initiator, PublicUserData acceptor,
                      Date initiatedDate, Date acceptedDate,
                      Boolean isAccepted){
        this.initiator = initiator;
        this.acceptor = acceptor;
        this.initiated_date = initiatedDate;
        this.accepted_date = acceptedDate;
        this.isAccepted = isAccepted;
        this.key = null;
    }

    //Blank Constructor for Google Firebase
    public FriendItem(){}

    /*
        ACCESSORS - EACH RESPECTIVE METHOD MERELY RETURNS THE VALUE
     */
    public PublicUserData get_initiator(){return this.initiator;}
    public PublicUserData get_acceptor(){return this.acceptor;}
    public Date get_initiated_date(){return this.initiated_date;}
    public Date get_accepted_date(){return this.accepted_date;}
    public Boolean get_isAccepted(){return this.isAccepted;}
    public String get_key() {return this.key;};
    /*
        MUTATORS - EACH RESPECTIVE METHOD MERELY MODIFIES THE VALUE
     */

    public void set_initiator(PublicUserData initiator){this.initiator = initiator;}
    public void set_acceptor(PublicUserData acceptor){this.acceptor = acceptor;}
    public void set_initiated_date(Date date){this.initiated_date = date;}
    public void set_accepted_date(Date date){this.accepted_date = date;}
    public void set_isAccepted(boolean isAccepted){this.isAccepted = isAccepted;}
    public void set_key(String key){this.key = key;}
}
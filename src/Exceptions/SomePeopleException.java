package Exceptions;

public class SomePeopleException extends Exception {

    private boolean toBeSomePeople;
    private String talkerName;

    public SomePeopleException(String msg, String talkerName, boolean toBeSomePeople){
        super(msg);
        this.toBeSomePeople = toBeSomePeople;
        this.talkerName = talkerName;
    }

    @Override
    public String getMessage() {
        return (super.getMessage()+ talkerName + " " + toBeSomePeople);
    }
}

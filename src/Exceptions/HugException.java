package Exceptions;

import Classes.LivingCreature;

public class HugException extends RuntimeException {

    private LivingCreature hugger;

    public HugException(String msg, LivingCreature hugger){
        super(msg);
        this.hugger = hugger;
    }

    @Override
    public String getMessage() {
        return (super.getMessage() + hugger);
    }
}

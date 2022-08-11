package uk.ac.ucl.command;

import uk.ac.ucl.visitor.Visitor;

public class Content implements Command{
    private final String s;

    public Content(String s){
        this.s = s;
    }

    public String getString(){
        return s;
    }

    @Override
    public void accept(Visitor visitor) {
    }
}

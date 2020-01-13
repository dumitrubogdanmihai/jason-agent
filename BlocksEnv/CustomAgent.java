package BlocksEnv;

import jason.asSemantics.Agent;
import jason.asSemantics.Event;
import jason.asSemantics.Intention;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Trigger;
import jason.bb.BeliefBase;
import jason.asSyntax.Literal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.lang.StringBuilder;


public class CustomAgent extends Agent {
	private Literal parseDesiredGoal() {
		StringBuilder sb = new StringBuilder("state([");
		
		
        try (InputStream input = new FileInputStream("input-desired-configuration.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            prop.forEach((k, v) -> {
            	String[] blockLetters = v.toString().split("");
            	String stack = "[" + String.join(",", blockLetters) + "]";
            	if (sb.toString().endsWith("]")) {
            		sb.append(",");
            	}
            	sb.append(stack);
            });
        } catch (IOException ex) {
        	// Pray to not happen.
        }
        
    	sb.append("])");
        return Literal.parseLiteral(sb.toString());
	}
	
    @Override
    public void addInitialGoalsInTS() {
        Literal initialGoal = parseDesiredGoal(); 
        		//Literal.parseLiteral("state([[a,e,b],[f,d,c],[g]])");
        initialGoal.makeVarsAnnon();
	    if (! initialGoal.hasSource())
	    	initialGoal.addAnnot(BeliefBase.TSelf);
	    getTS().getC().addAchvGoal(initialGoal, Intention.EmptyInt);
    }
}

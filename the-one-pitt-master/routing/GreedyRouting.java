/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows_X
 */
public class GreedyRouting implements RoutingDecisionEngineWithCalc {
    List<DTNHost> destination = new LinkedList<>();
    private int direct ;
    
    public GreedyRouting(Settings s) {
    }

    public GreedyRouting(GreedyRouting r) {
    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {
    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {
    }

    @Override
    public boolean newMessage(Message m) {
        return true;
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        return m.getTo() == aHost;
    }

    @Override
    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) {
        //PESAN DIMASUKAN KE BUFFER GAK?
        
        return m.getTo() != thisHost;
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost thisHost, DTNHost otherHost) {
        //        DTNHost dest = m.getTo();

//        System.out.println(destination);
//
        if (m.getTo() == otherHost) {
            return true;
        } 

        //kalo ketemu sensor atau terminal ga usah kirim
        if (otherHost.toString().startsWith("s") || otherHost.toString().startsWith("t")) {
            return false;
        }
        
        int a = cekRuteIndex(thisHost);
        int b = cekRuteIndex(otherHost);
        //System.out.println("b = " + b);

        if (b < a) {
            System.out.println("b = " + b + " " + otherHost + " ( " + m.getId() + " ) " + " a =  " + a + " " + thisHost);
            return true;
        }
//        } //        if (b == 0 && !otherHost.toString().startsWith("s") 
        //                && !otherHost.toString().startsWith("t")){
        //            return true;
        //        }else if(a == 0){
        //            return false;
        //        }
        //        if(!otherHost.toString().startsWith("s") || !otherHost.toString().startsWith("t")) {
        //            return true;
        //        
        //        } else if (b < a) {
        //            System.out.println("b = " + b + otherHost + " a = " + a + thisHost);
        //            return true;
        else {
            return false;
        }
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        return true;
    }
//        if (getOtherDecisionEngine(otherHost).toString().startsWith("s")) {
//            System.out.println(otherHost);
//            return true;
//        } if (getOtherDecisionEngine(otherHost).toString().startsWith("t")) {
//            System.out.println(otherHost + " t");
//            return false;
//        }
//        return false;
//    }

    private GreedyRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherHost = h.getRouter();
        assert otherHost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GreedyRouting) ((DecisionEngineRouter) otherHost).getDecisionEngine();
    }

    public int cekRuteIndex(DTNHost host) {
        String name = host.toString();
        if (name.startsWith("3A") || name.startsWith("3B") || name.startsWith("5A")
                || name.startsWith("5B")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        return true;
    }

    @Override
    public double hitungJarakEuclidan(DTNHost thisHost) {
        return 0;
    }

    @Override
    public RoutingDecisionEngineWithCalc replicate() {
        return new GreedyRouting(this);
    }

}

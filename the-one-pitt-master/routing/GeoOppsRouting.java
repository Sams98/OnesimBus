/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.Connection;
import core.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import movement.map.MapRoute;
import routing.*;

/**
 *
 * @author Windows_X
 */
public class GeoOppsRouting implements RoutingDecisionEngineWithCalc {
    
    List<DTNHost> destination = new LinkedList<>();
    
    private final double MAX_NP = 28000;
    private double nP = 28000;
    

    
    public GeoOppsRouting(Settings s) {
//        this.destination = new LinkedList<>();
    }
    
    public GeoOppsRouting(GeoOppsRouting geo) {
//        this.destination = new LinkedList<>();
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
        return true;
    }
    
    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {

//        if (this.MAX_NP == MAX_NP) {
//           hitungJarakEuclidan(otherHost);
//        }
        if (destination.isEmpty()) {
//            System.out.println(m);
//            System.out.println(destination.isEmpty());
            destination = getDestination();
        }
        
        if (this.nP == MAX_NP) {
            System.out.println("old " + nP);
            this.nP = hitungJarakEuclidan(otherHost);
            System.out.println("new " + this.nP);
        }
//        System.out.println(destination);
        GeoOppsRouting de = getOtherDecisionEngine(otherHost);
        double otherNp = de.nP;
        
        if (this.nP < otherNp) {
            return true;
        } else {
            return false;
        }
        
    }
    
    private GeoOppsRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherhost = h.getRouter();
        assert otherhost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GeoOppsRouting) ((DecisionEngineRouter) otherhost).getDecisionEngine();
    }
    
    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        return true;
    }
    
    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        return true;
    }

    /**
     * method hitungJarakEuclidian digunakan untuk mencari NP
     */
    @Override
    public double hitungJarakEuclidan(DTNHost thisHost) {
        //       System.out.println(thisHost);
        double hasilEuclidian = 0;
        List<List<Coord>> awal = MapRoute.getRouteCoord(cekRuteIndex(thisHost));
        //       System.out.println(""+awal);
        for (List<Coord> a : awal) {
            for (Coord b : a) {
                for (DTNHost c : destination) {
                    double jarak = b.distance(c.getLocation());
                    hasilEuclidian = (jarak < nP) ? jarak : nP;
                }
            }
        }
        return hasilEuclidian;
    }

    /**
     * cekRuteIndex untuk mengetahui host tersebut berada di jalur mana
     */
    public int cekRuteIndex(DTNHost host) {
        String name = host.toString();
        if (name.startsWith("1A")) {
            return 0;
        } else if (name.startsWith("1B")) {
            return 1;
        } else if (name.startsWith("2A")) {
            return 2;
        } else if (name.startsWith("2B")) {
            return 3;
        } else if (name.startsWith("3A")) {
            return 4;
        } else if (name.startsWith("3B")) {
            return 5;
        } else if (name.startsWith("4A")) {
            return 6;
        } else if (name.startsWith("4B")) {
            return 7;
        } else if (name.startsWith("5A")) {
            return 8;
        } else if (name.startsWith("5B")) {
            return 9;
        } else if (name.startsWith("6A")) {
            return 10;
        } else if (name.startsWith("6B")) {
            return 11;
        } else if (name.startsWith("7")) {
            return 12;
        } else if (name.startsWith("8")) {
            return 13;
        } else if (name.startsWith("9")) {
            return 14;
        } else if (name.startsWith("10")) {
            return 15;
        } else {
            return 16;
        }
    }
    
    public List<DTNHost> getDestination() {
//        System.out.println("1");
        List<DTNHost> allNodes = SimScenario.getInstance().getHosts();
//        System.out.println(""+ SimScenario.getInstance().getHosts());
//        System.out.println("data" + allNodes);
        List<DTNHost> dest = new LinkedList<>();
        for (DTNHost h : allNodes) {
            if (h.toString().startsWith("d")) {
                dest.add(h);
            }
        }
        return dest;
    }
    
    @Override
    public RoutingDecisionEngineWithCalc replicate() {
        return new GeoOppsRouting(this);
    }
    
}

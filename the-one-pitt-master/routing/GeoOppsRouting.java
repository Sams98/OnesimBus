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
    }

    public GeoOppsRouting(GeoOppsRouting geo) {
    }

    public double getnP() {
        return nP;
    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {

    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {
    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {
        DTNHost myHost = con.getOtherNode(peer);
        if (destination.isEmpty()) {
//            System.out.println(m);
//            System.out.println(destination.isEmpty());
            destination = getDestination();
        }

        if (nP == MAX_NP) {
//            System.out.println("old " + nP);
            nP = hitungJarakEuclidan(myHost);
//            System.out.println("new " + nP);
        }
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
        if (m.getTo() == otherHost) {
            return true;
        }

        GeoOppsRouting de = this.getOtherDecisionEngine(otherHost);

//        if (m.getFrom().toString().startsWith("s")){
//            return true;
//        } else {
//            if(otherHost.toString().startsWith("s") || otherHost.toString().startsWith("t")){
//                return false;
//            } else {
//                if(this.getnP() < de.getnP()){
//                return false;
//            } else {
//                    return true;
//                }
//        }
        if (otherHost.toString().startsWith("s") || otherHost.toString().startsWith("t")) {
            return false;
        } else {
            if (this.getnP() <= de.getnP()) {
//                System.out.println("thisNp = " + this.getnP() + " " + thisHost + " de = " + de.getnP() + " " + otherHost);
//            System.out.println("thisNP = " + this.nP + " deNp = " + de.nP);
                return false;
            } else {
                return true;
            }
        }
    }

    private GeoOppsRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherHost = h.getRouter();
        assert otherHost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GeoOppsRouting) ((DecisionEngineRouter) otherHost).getDecisionEngine();
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

    public double hitungJarakEuclidan(DTNHost thisHost) {
        //       System.out.println(thisHost);
        double hasilEuclidian = 0;
        List<List<Coord>> awal = MapRoute.getRouteCoord(cekRuteIndex(thisHost));
        //       System.out.println(""+awal);
        /*agar sensor dan terminal tidak hitung np */
        if (!thisHost.toString().startsWith("s") && !thisHost.toString().startsWith("t")) {
            for (List<Coord> a : awal) {
                for (Coord b : a) {
                    for (DTNHost c : destination) {
                        double jarak = b.distance(c.getLocation());
//                              System.out.println("jarak = " + jarak + " " + thisHost);
                        nP = (jarak < nP) ? jarak : nP;
                    }
                    hasilEuclidian = nP;
                    System.out.println("" + hasilEuclidian + " " + thisHost);
                }
            }
        }
        if (thisHost.toString().startsWith("s") || thisHost.toString().startsWith("t")) {
            for (DTNHost c : destination) {
                return MAX_NP;
            }
        }
//        if (thisHost.toString().startsWith("s") && thisHost.toString().startsWith("t")) {
//            System.out.println("");
//        } else {
//            System.out.println("This Host = " + thisHost);
//        }
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

    /*untuk mencari destinasi */
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

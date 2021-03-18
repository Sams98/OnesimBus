/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.Connection;
import core.*;
import java.util.LinkedList;
import java.util.List;
import movement.map.MapRoute;

/**
 *
 * @author Windows_X
 */
public class GeoOppsRouting implements RoutingDecisionEngineWithCalc {

    List<DTNHost> destinations;

    private double bobot = 28000;

//  destinasi  9647,1620;
    /**
     * Stores information about nodes with which this host has come in contact
     * (koordinat)
     */
//    protected Map<DTNHost, Double> recentEncounters;
    public GeoOppsRouting(Settings s) {
        destinations = getDestination();
    }

    public GeoOppsRouting(GeoOppsRouting geo) {
        destinations = getDestination();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
        GeoOppsRouting de = getOtherDecisionEngine(otherHost);
        double otherBobot = de.bobot;
        if (this.bobot < otherBobot) {
            return true;
        } else {
            return false;
        }
    }

    private GeoOppsRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherhost = h.getRouter();
        return (GeoOppsRouting) ((DecisionEngineRouter) otherhost).getDecisionEngine();
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * method hitungJarakEuclidian digunakan untuk mencari NP
     */
    @Override
    public void hitungJarakEuclidan(DTNHost thisHost) {
        List<List<Coord>> awal = MapRoute.getRouteCoord(cekRuteIndex(thisHost));
        for (List<Coord> a : awal) {
            for (Coord b : a) {
                for (DTNHost c : destinations) {
                    double jarak = b.distance(c.getLocation());
                    bobot = (jarak < bobot) ? jarak : bobot;
                }
            }
        }

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
        List<DTNHost> allNodes = SimScenario.getInstance().getHosts();
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

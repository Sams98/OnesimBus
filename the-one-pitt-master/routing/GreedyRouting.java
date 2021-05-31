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
        //mengambil jalur dari pesan
        List<String> jalur = (List) m.getProperty("jalur");
//        System.out.println("thisHos = " + thisHost + "pesan dari = " + m.getFrom() + " jalur = "  + jalur + "   other = " + otherHost);
//System.out.println(otherHost.toString().equals(jalur.get(0)));

//        System.out.println("Other = " + otherHost + " " + "Jalur = " + jalur.get(0) + " sensor = " + m.getFrom());
        if (m.getTo() == otherHost) {
            return true;
        }

//        if (thisHost.toString().startsWith("s8")){
//            if(otherHost.toString().startsWith("8")){
//                return true;
//            }
//        } else if (thisHost.toString().startsWith("s9")){
//            if (otherHost.toString().startsWith("10")){
//                return true;
//            }
//        } else if (thisHost.toString().startsWith("s10")){
//            if (otherHost.toString().startsWith("6A")){
//                return true;
//            }
//                
//        }
//        System.out.println(jalur.get(0));
//        if (m.getFrom().toString().startsWith("s8") && otherHost.toString().startsWith("8")){
//            return true;
//        } else if (m.getFrom().toString().startsWith("s9") && otherHost.toString().startsWith("10")){
//            return true;
//        }
//        System.out.println(otherHost + jalur.get(0));
        //jika jalur kosong berarti pesan sudah sampai di rute terakhir, tinggal tunggu ketemu tujuan
        if (jalur.isEmpty()) {
//            System.out.println("salah");
            return false;
        } //jika jalur masih ada yang lainnya
        else {
//            System.out.println("else");
            //jika node yang ditemui merupakan node yg tertulis di jalur yang harus dilewati berikutnya
//            if (otherHost.toString().equals(jalur.get(0))) {
            if (jalur.get(0).contains(otherHost.toString().substring(0, 2))) {
                //hapus node dari jalur
                jalur.remove(0);
                //update jalur ke pesan
                m.updateProperty("jalur", jalur);
//                System.out.println("jalur");
                //kirim pesan
                return true;
            } else {
                //jika node yang ditemui berbeda dgn yang tercatat dijalur pesan, tidak kirim
                return false;
            }
        }
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        return true;
    }

    private GreedyRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherHost = h.getRouter();
        assert otherHost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GreedyRouting) ((DecisionEngineRouter) otherHost).getDecisionEngine();
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

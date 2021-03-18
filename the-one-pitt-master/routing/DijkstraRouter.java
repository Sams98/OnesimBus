/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.Connection;
import core.DTNHost;
import core.Message;
import core.Settings;
import core.SimScenario;
import java.util.HashMap;
import java.util.List;
import movement.map.DijkstraPathFinder;
import movement.map.MapNode;
import movement.map.MapRoute;

/**
 * Router Dijkstra's algorithm to find shortest paths in map Jogyakarta
 */
public class DijkstraRouter implements RoutingDecisionEngine {

    /**
     * the Dijkstra shortest path finder
     */
    private DijkstraPathFinder pathFinder;

    /**
     * Per node group setting used for selecting a route file ({@value})
     */
    public static final String ROUTE_FILE_S = "routeFile";

    /**
     * Per node group setting used for selecting a route's type ({@value}).
     * Integer value from {@link MapRoute} class.
     */
    public static final String ROUTE_TYPE_S = "routeType";

    /**
     * Prototype's reference to all routes read for the group
     */
    private List<MapRoute> allRoutes = null;
    /**
     * next route's index to give by prototype
     */
    private Integer nextRouteIndex = null;
    /**
     * Map of node distances from the source node
     */
    private Distance_Map distances;

    /**
     * Value for infinite distance
     */
    private static final Double INFINITY = Double.MAX_VALUE;

    public DijkstraRouter(Settings s) {
        String fileName = s.getSetting(ROUTE_FILE_S);
        int type = s.getInt(ROUTE_TYPE_S);
        allRoutes = MapRoute.readRoutes(fileName, type, SimScenario.getInstance().getMap());
        nextRouteIndex = 0;
    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {}

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {}

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {}

    @Override
    public boolean newMessage(Message m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RoutingDecisionEngine replicate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Simple Map implementation for storing distances.
     */
    private class Distance_Map {

        private HashMap<MapNode, Double> map;

        /**
         * Constructor. Creates an empty distance map
         */
        public Distance_Map() {
            this.map = new HashMap<MapNode, Double>();
        }

        /**
         * Returns the distance to a node. If no distance value is found,
         * returns {@link DijkstraPathFinder#INFINITY} as the value.
         *
         * @param node The node whose distance is requested
         * @return The distance to that node
         */
        public double get(MapNode node) {
            Double value = map.get(node);
            if (value != null) {
                return value;
            } else {
                return INFINITY;
            }
        }

        /**
         * Puts a new distance value for a map node
         *
         * @param node The node
         * @param distance Distance to that node
         */
        public void put(MapNode node, double distance) {
            map.put(node, distance);
        }

        /**
         * Returns a string representation of the map's contents
         *
         * @return a string representation of the map's contents
         */
        public String toString() {
            return map.toString();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movement.map;

import core.Coord;
import java.util.List;

/**
 *
 * @author Windows_X
 */
public class TransferMapRoute {
   static List<MapRoute> route;

    public static List<MapRoute> getRoute() {
        return route;
    }

    public static void setRoute(List<MapRoute> route) {
        TransferMapRoute.route = route;
    }
   
   
}
   


package edu.wcu.cs.eliteraretrader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class supports the routing logic of this application
 *
 * Author Sean Facello
 * Version 18/3/15
 */
public class Router {

    public Router(){
        //do nothing, just to make the object
    }

    public void routeMaker(Intent i){
        int mode = -1;
        Bundle extra = i.getExtras();

        mode = extra.getInt(AppConstants.MODE);
        if(mode == AppConstants.SINGLES){
            singleRouter(extra);
        }else if(mode == AppConstants.LOOP){
            loopRouter(extra);
        }
    }

    private void singleRouter(Bundle extra){
        AppConstants.route = new ArrayList<System>();
        String start = extra.getString(AppConstants.START);
        String end = extra.getString(AppConstants.END);
        double x = extra.getDouble(AppConstants.X);
        double y = extra.getDouble(AppConstants.Y);
        double z = extra.getDouble(AppConstants.Z);
        int cargo = extra.getInt(AppConstants.CARGO);
        boolean halfstat = extra.getBoolean(AppConstants.HALFSTAT);
        singleRouterHelper(start, cargo, halfstat);
        AppConstants.route.add(new System(end,"","",0,0,"sell",x,y,z));
        for(System stuff: AppConstants.route){
            java.lang.System.out.println(stuff.getName());
        }
    }

    private void singleRouterHelper(String start, int cargo, boolean half){
        System current = findSystem(start);
        int cargoCurrent = 0;
        if(half){
            cargoCurrent += current.getCap() % 2;
            AppConstants.route.add(new System(current.getName(), current.getCommodity(),
                    current.getStation(), current.getDistance(), current.getCap() % 2, current.getBuy(),
                    current.getX(), current.getY(), current.getZ()));
        } else {
            cargoCurrent += current.getCap();
            AppConstants.route.add(new System(current.getName(), current.getCommodity(),
                    current.getStation(), current.getDistance(), current.getCap(), current.getBuy(),
                    current.getX(), current.getY(), current.getZ()));
        }
        while(cargoCurrent <= cargo) {
            ArrayList<System> closest = new ArrayList<>();
            ArrayList<Double> distances = new ArrayList<>();
            for (System comp : AppConstants.systems) {
                double dx = Math.pow(comp.getX() - current.getX(), 2);
                double dy = Math.pow(comp.getY() - current.getY(), 2);
                double dz = Math.pow(comp.getZ() - current.getZ(), 2);
                double dist = Math.sqrt(dx + dy + dz);
                if (dist < 200) {
                    if (!checkDup(comp.getCommodity())) {
                        closest.add(comp);
                        distances.add(dist);
                    }
                }
            }
            System close = closest.get(0);
            for (System sys : closest) {
                if (distances.get(closest.indexOf(close)) > distances.get(closest.indexOf(sys))) {
                    close = sys;
                }
            }
            if (half) {
                cargoCurrent += close.getCap() % 2;
                AppConstants.route.add(new System(close.getName(), close.getCommodity(),
                        close.getStation(), close.getDistance(), close.getCap() % 2, close.getBuy(),
                        close.getX(), close.getY(), close.getZ()));
            } else {
                cargoCurrent += close.getCap();
                AppConstants.route.add(new System(close.getName(), close.getCommodity(),
                        close.getStation(), close.getDistance(), close.getCap(), close.getBuy(),
                        close.getX(), close.getY(), close.getZ()));
            }
            current = close;
        }
    }

    private void loopRouter(Bundle extra){
        AppConstants.route = new ArrayList<System>();
        String start = extra.getString(AppConstants.START);
        String end = extra.getString(AppConstants.END);
        int cargo = extra.getInt(AppConstants.CARGO);
        boolean halfstat = extra.getBoolean(AppConstants.HALFSTAT);
        int legs = extra.getInt(AppConstants.LEGS);
        System endSys = findSystem(end);
        endSys = new System(endSys.getName(), endSys.getCommodity(), endSys.getStation(),
                endSys.getDistance(), endSys.getCap() % 2, "sell", endSys.getX(),
                endSys.getY(), endSys.getZ());
        loopRouterHelper(start, cargo, halfstat, legs, endSys);
        java.lang.System.out.println("testing");
        for(System stuff: AppConstants.route){
            java.lang.System.err.println(stuff.getName());
        }
    }

    private synchronized void loopRouterHelper(String start, int cargo, boolean halfstat, int legs, System end){
        int legcount = 0;
        while(legcount < legs - 1){
            singleRouterHelper(start, cargo, halfstat);
            System last = AppConstants.route.get(AppConstants.route.size() - 1);
            System sell = findSellPoint(last);
            AppConstants.route.add(sell);
            legcount++;
        }
        singleRouterHelper(start, cargo, halfstat);
        AppConstants.route.add(end);
    }

    private System findSellPoint(System last){
        ArrayList<System> potentialSell = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        for(System current: AppConstants.systems){
            double dx = Math.pow(last.getX() - current.getX(), 2);
            double dy = Math.pow(last.getY() - current.getY(), 2);
            double dz = Math.pow(last.getZ() - current.getZ(), 2);
            double dist = Math.sqrt(dx + dy + dz);
            if (dist > 145 && dist < 175) {
                potentialSell.add(current);
                distances.add(dist);
            }
        }
        /**for(System current: potentialSell){
            if(AppConstants.route.indexOf(current) == -1){
                distances.remove(potentialSell.indexOf(current));
                potentialSell.remove(potentialSell.indexOf(current));
            }
        }**/
        System close = potentialSell.get(0);
        for(System sys: potentialSell){
            if(distances.get(potentialSell.indexOf(close)) > distances.get(potentialSell.indexOf(sys))
                    && !AppConstants.route.contains(sys)){
                close = sys;
            }
        }
        return new System(close.getName(), close.getCommodity(),close.getStation(),
                            close.getDistance(), close.getCap(), "sell", close.getX(),
                            close.getY(), close.getZ());
    }

    private System findSystem(String name){
        System sys = null;
        for(System current: AppConstants.systems){
            if(current.getName().equals(name)){
                return sys = current;
            }
        }
        return sys;
    }

    private boolean checkDup(String item){
        boolean stat = false;
        for(System comp: AppConstants.route){
            if(item.equals(comp.getCommodity())){
                stat = true;
            }
        }
        return stat;
    }
}

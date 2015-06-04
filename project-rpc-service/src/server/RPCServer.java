/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javax.xml.ws.Endpoint;
import salesagg.SalesAggregationImpl;

/**
 *
 * @author hiclu995
 */
public class RPCServer {
    public static void main(String[] args) {
        System.out.println("Starting RPC service");
        Endpoint.publish("http://localhost:9001/sales", new SalesAggregationImpl());
        System.out.println("... Ready");
    }
}

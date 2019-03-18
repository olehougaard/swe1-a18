package dk.via.sales.server;

import java.rmi.Remote;

public interface SalesModel extends Remote, OrderManager, CustomerManager {
}

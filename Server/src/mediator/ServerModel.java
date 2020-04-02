package mediator;

import model.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServerModel implements RemoteModel, LocalListener<String,String>
{
  private Model model;
  private PropertyChangeProxy<String,String> property;
  public ServerModel(Model model) throws MalformedURLException, RemoteException
  {
    this.property = new PropertyChangeProxy<>(this, true);
    this.model = model;
    model.addListener(this,"message","add");
    startRegistry();
    startServer();
  }
  private void startRegistry() throws RemoteException
  {
    try
    {
      Registry reg = LocateRegistry.createRegistry(1099);
      System.out.println("Registry started...");
    }
    catch (java.rmi.server.ExportException e)
    {
      System.out.println("Registry already started?" + " " + e.getMessage());
    }
  }
  private void startServer() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Chat", this);
    System.out.println("Server started...");
  }

  public void close()
  {
    //property.close();
    try { UnicastRemoteObject.unexportObject(this, true); }
    catch (Exception e) {/*nothing*/ }
  }
  @Override public boolean verifyPass(String password, String username)
  {
    return model.verifyLog(password);
  }

  @Override public void addMessage(String message)
  {
    model.addLog(message);
    System.out.println("Message : " + message);
    model.addMessage(message,"user");
  }


  @Override public boolean addListener(GeneralListener<String, String> listener,
      String... propertyNames) throws RemoteException
  {
    return property.addListener(listener,propertyNames);
  }

  @Override public boolean removeListener(
      GeneralListener<String, String> listener, String... propertyNames)
      throws RemoteException
  {
    return property.removeListener(listener,propertyNames);
  }

  @Override public void propertyChange(ObserverEvent<String, String> event)
  {
    property.firePropertyChange(event.getPropertyName(),event.getValue1(),event.getValue2());
  }
}

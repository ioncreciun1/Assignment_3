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
  private String user;
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
      model.addLog("Registry started... ");
    }
    catch (java.rmi.server.ExportException e)
    {
      model.addLog("Registry already started?" + " " + e.getMessage());
    }
  }
  private void startServer() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Chat", this);
    model.addLog("Server started");
  }

  public void close()
  {
    try { UnicastRemoteObject.unexportObject(this, true); }
    catch (Exception e) {/*nothing*/ }
  }
  @Override public boolean verifyPass(String password, String username)
  {
    if(model.verifyLog(password))
    {
      model.addLog("User " + username + " is Connected");
      model.addUser(username);
    }
    else{
      model.addLog("User " + username + " is not Connected");
    }
    return model.verifyLog(password);
  }

  @Override public void addMessage(String message,String user)
  {
    model.addLog("User : " + user  + "  | Message : " + message);
    model.addMessage(message,user);
  }

  @Override public String getUsers() throws RemoteException
  {
    return model.getList();
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

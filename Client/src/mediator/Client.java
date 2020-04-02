package mediator;

import model.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client implements ClientModel, RemoteListener<String,String>
{
  public static final String HOST = "localhost";
  private String host;
  private Model model;
  private RemoteModel remoteModel;
  private PropertyChangeProxy<String,String> property;
  public Client(Model model,String host)
      throws RemoteException, NotBoundException, MalformedURLException
  {
    this.model = model;
    this.host = host;
    this.remoteModel = (RemoteModel) Naming
        .lookup("rmi://" + host + ":1099/Chat");
    UnicastRemoteObject.exportObject(this, 0);
    this.property = new PropertyChangeProxy<>(this);
    remoteModel.addListener(this,"message");
  }
  @Override public boolean verifyPass(String password, String username)
      throws RemoteException
  {
    return remoteModel.verifyPass(password,username);
  }

  @Override public void addMessage(String message) throws RemoteException
  {
    System.out.println("Message added");
remoteModel.addMessage(message);
  }

  @Override public boolean addListener(GeneralListener<String, String> listener,
      String... propertyNames)
  {
    return property.addListener(listener,propertyNames);
  }

  @Override public boolean removeListener(
      GeneralListener<String, String> listener, String... propertyNames)
  {
    return property.removeListener(listener,propertyNames);
  }

  @Override public void propertyChange(ObserverEvent<String, String> event)
      throws RemoteException
  {
    System.out.println("Client");
    System.out.println(event.getValue2());
  property.firePropertyChange(event);
  }
}
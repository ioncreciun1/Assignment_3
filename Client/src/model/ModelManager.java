package model;

import mediator.Client;
import mediator.ClientModel;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeProxy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ModelManager implements Model, LocalListener<String,String>
{

  private ClientModel client;
  private PropertyChangeProxy<String,String> property;

  public ModelManager() throws IOException
  {
    try{
      this.client = new Client(this,"localhost");
    }catch (Exception e)
    {
      e.printStackTrace();
    }
  property = new PropertyChangeProxy<>(this);
    client.addListener(this,"message");
  }

  @Override public void setMessage(String message) throws IOException
  {

  }

  @Override public void sendMessage(String message) throws IOException
  {
    client.addMessage(message);
  }

  @Override public boolean verifyLog(String password,String name) throws IOException
  {

return client.verifyPass(password,name);
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
  {
    System.out.println("Model");
  property.firePropertyChange(event);
  }
}

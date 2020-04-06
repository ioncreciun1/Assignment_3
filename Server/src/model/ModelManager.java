package model;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeProxy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ModelManager implements Model
{
  private Converter converter;
  private PropertyChangeProxy<String,String> property;
  private ArrayList<String> list = new ArrayList<>();

  public ModelManager()
  {
    this.converter = new Converter();
    property = new PropertyChangeProxy<>(this);
  }
  @Override
  public synchronized void addLog(String log)
  {
    String logValue = converter.getLogSize() + ": " + log;
    converter.addLog(logValue);
    property.firePropertyChange("add", null, log);
  }
  public void addUser(String user)
  {
    System.out.println("USER : " + user);
    list.add(user);
    System.out.println("LIST : " + list);
  }

  @Override public void removeUser(String user)
  {
    list.remove(user);
  }

  public String getList()
  {
    String s ="";
    for(int i=0;i<list.size()-1;i++){
      s+=list.get(i) + " , ";
    }
    s+=list.get(list.size()-1);
    return s;
  }

  @Override public boolean verifyLog(String request)
  {
    return  request.equals("123456");
  }

  @Override public void addMessage(String message,String user)
  {
    System.out.println("USER : " + user);
    property.firePropertyChange("message",user,message);

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
}

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ModelManager implements Model
{

  private PropertyChangeSupport property;

  public ModelManager() throws IOException
  {

    property = new PropertyChangeSupport(this);
  }

  @Override public void setMessage(String message) throws IOException
  {
    property.firePropertyChange("message", null, message);
  }

  @Override public void sendMessage(String message) throws IOException
  {

  }

  @Override public boolean verifyLog(String password,String name) throws IOException
  {

return true;
  }
  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(propertyName,listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(propertyName,listener);
  }
}

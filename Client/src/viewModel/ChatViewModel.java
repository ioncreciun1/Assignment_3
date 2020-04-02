package viewModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeProxy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ChatViewModel implements LocalListener<String,String>
{
  private Model model;
  private ObservableList<String> logs;
  private int index;
  public ChatViewModel(Model model)
  {
    this.model = model;
    logs = FXCollections.observableArrayList();
    logs.add("NOW YOU CAN CHAT WITH OTHER");
    model.addListener(this,"message");
    index = 1;
  }
  public ObservableList<String> getLogs()
  {
    return logs;
  }
  public void setMessage(String inputField) throws IOException
  {
    if(inputField.equals("chatters"))
    {
      logs.add(index,"CHATTERS : " + model.getUsers());
    }else
    {
      model.sendMessage(inputField);
    }
  }
  @Override public void propertyChange(ObserverEvent<String, String> event)
  {
    Platform.runLater(() -> {
      logs.add(index, event.getValue1() + " : "+ event.getValue2());
    });
  }
}

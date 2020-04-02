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
  public ChatViewModel(Model model)
  {
    this.model = model;
    logs = FXCollections.observableArrayList();
    logs.add("NOW YOU CAN CHAT WITH OTHER");
    model.addListener(this,"message");
  }
  public ObservableList<String> getLogs()
  {
    return logs;
  }
  public void setMessage(String inputField) throws IOException
  {
    System.out.println("Message sent");
    model.sendMessage(inputField);
  }
  @Override public void propertyChange(ObserverEvent<String, String> event)
  {
    Platform.runLater(() -> {
      System.out.println("Now I receive IT");
      logs.add(1, event.getValue2() + "");
    });
  }
}

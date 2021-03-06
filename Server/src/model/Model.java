package model;

import utility.observer.subject.LocalSubject;
import utility.observer.subject.NamedPropertyChangeSubject;

public interface Model extends LocalSubject<String,String>
{
  void addLog(String log);
  boolean verifyLog(String request);
  void addMessage(String message, String user);
  String getList();
  void addUser(String user);
  void removeUser(String user);
}

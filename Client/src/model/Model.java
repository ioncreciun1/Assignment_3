package model;

import utility.observer.subject.LocalSubject;
import utility.observer.subject.NamedPropertyChangeSubject;

import java.io.IOException;

public interface Model extends LocalSubject<String,String>
{
  void setMessage(String message) throws IOException;
  void sendMessage(String message) throws IOException;
  boolean verifyLog(String password, String name) throws IOException;
}

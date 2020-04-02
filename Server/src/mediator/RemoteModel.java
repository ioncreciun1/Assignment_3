package mediator;

import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject<String,String>
{
  boolean verifyPass(String password, String username) throws RemoteException;;
  void addMessage(String message) throws RemoteException;
  String getUsers() throws RemoteException;
}

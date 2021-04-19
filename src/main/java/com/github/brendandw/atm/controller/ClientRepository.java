/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository implements IClientRepository {
  private List<Client> clients = new ArrayList<>();

  @Override
  public void add(Client session) {
    synchronized (this.clients) {
      this.clients.add(session);
    }
  }

  @Override
  public void remove(Client session) {
    synchronized (this.clients) {
      this.clients.remove(session);
    }
  }

  @Override
  public void forEach(Consumer<Client> clientConsume) {
    synchronized (this.clients) {
      this.clients.forEach(clientConsume);
    }
  }

  @Override
  public List<Client> getAll() {
    return new ArrayList<>(this.clients);
  }

}

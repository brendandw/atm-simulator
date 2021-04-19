/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.controller;

import java.util.List;
import java.util.function.Consumer;



public interface IClientRepository {


  public void add(Client session);

  public void remove(Client session);

  public void forEach(Consumer<Client> clientConsume);

  public List<Client> getAll();

}

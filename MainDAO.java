package com.company;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class MainDAO {



    public static void main(String[] args) {


       User user1 = new User("nxxyyyyyxxx@gmail.com", "yyyyyy", "mojeHaslo");

     //userDAO.create(user1);
     userDAO.read(666);
     //userDAO.update(userDAO.read(7));
     //System.out.println(user1);
     userDAO.delete(8);
     //userDAO.findAll();


    }
}

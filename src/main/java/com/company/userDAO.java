package com.company;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class userDAO {


    private static final String CREATE = "insert into users(email,username, password) VALUES (?, ?, ?);";
    private static final String GET_ID = "select id from users where email = ?;";
    private static final String SHOW_USER = "select * from users where id = ?;";
    private static final String DELETE = "delete from users where id=?;";
    private static final String SHOW_ALL = "select * from users";
    private static final String HOW_MANY = "select count(*) from users;";
    private static final String UPDATE = "update users set email=?, username=?, password=? where id=?";


    public static void main(String[] args) {

    }

    public static User create(User user) {

        try (Connection conn = DBUtil.connect()) {

            PreparedStatement preStat = conn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

            preStat.setString(1, user.getEmail());
            preStat.setString(2, user.getUsername());
            preStat.setString(3, hashPassword(user.getPassword()));

            preStat.executeUpdate();
            ResultSet rs = preStat.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                //System.out.println("Inserted ID: " + id);
                user.setId(id);
            }

            return user;

        }catch (SQLException e){
            e.printStackTrace();
            return  null;

        }

    }

    public static User read(int user_Id) {

        //Scanner scan = new Scanner(System.in);
        //System.out.println("Podaj id");
        int user_id = user_Id;
        //user_id = scan.nextInt();


        try (Connection conn = DBUtil.connect()) {
            PreparedStatement pretStat2 = conn.prepareStatement(SHOW_USER);
            pretStat2.setInt(1, user_id);
            ResultSet rs = pretStat2.executeQuery();
            String email = null;
            String username = null;
            String password = null;
            int Id = 0;
            if (rs.next()) {
                User user = new User(email, username, password);
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                System.out.println(user.toString());
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return null;
    }

    public static void delete(int ID_to_delete) {

       /* *//*Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj ID");*//*
        int ID_to_delete = scanner.nextInt();*/

        try (Connection conn = DBUtil.connect()) {
            PreparedStatement pretStat3 = conn.prepareStatement(DELETE);
            pretStat3.setInt(1, ID_to_delete);
            pretStat3.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    public static User[] findAll (){
        User [] users = new User[0];

        try (Connection conn = DBUtil.connect()) {

            PreparedStatement preStat6 = conn.prepareStatement(HOW_MANY);
            ResultSet rs6 = preStat6.executeQuery();
            int quantity= 0;
            while (rs6.next()) {
                quantity = rs6.getInt("count(*)");
               // System.out.println(quantity);
            }

            PreparedStatement preStat7 = conn.prepareStatement(SHOW_ALL);
            ResultSet rs7 = preStat7.executeQuery();

            for (int i = 0; i < quantity; i++) {

                int id =0;
                String email = null;
                String username = null;
                String password = null;

                while (rs7.next()){
                    User user = new User(email,username,password);
                    id = rs7.getInt("id"); user.setId(id);
                    email = rs7.getString("email"); user.setEmail(email);
                    username = rs7.getString("username"); user.setUsername(username);
                    password = rs7.getString("password"); user.setPassword(password);
                    User [] tempUsers = Arrays.copyOf(users, users.length+1);
                    tempUsers[tempUsers.length-1]=user;
                    users=tempUsers;
                }
             System.out.println(users[i]);
            }
           //System.out.println(Arrays.toString(users));

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } return users;

    }

    public static String hashPassword (String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public static void update(User user) {


        user.getId();

        try (Connection conn = DBUtil.connect()) {

            Scanner scan = new Scanner(System.in);

            PreparedStatement preStat10 = conn.prepareStatement(UPDATE);
            System.out.println("podaj nowy email");
            String email = scan.nextLine();
            System.out.println("podaj nowy username");
            String username = scan.nextLine();
            System.out.println("podaj nowy password");
            String password = hashPassword(scan.nextLine());
            //hashPassword(password);

            preStat10.setString(1,email); user.setEmail(email);
            preStat10.setString(2, username); user.setUsername(username);
            preStat10.setString(3,password); user.setPassword(password);
            preStat10.setInt(4, user.getId());

            //hashPassword(user.getPassword())

            preStat10.executeUpdate();

            read(user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }






    }
}



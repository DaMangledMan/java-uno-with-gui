/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

import javax.swing.JFrame;

/**
 *
 * @author jacob
 */
public class Uno {
    
    public static void main(String[] args) throws Exception {
        Menu window = new Menu();
        window.setBounds(750,40,500,400);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.modelo;

import com.mycompany.proyecto.tda.Node;
import com.mycompany.proyecto.tda.Tree;

/**
 *
 * @author Administrator
 */
public class minimax {
    private final char MaquinaSimbolo;
    private final char JugadorSimbolo;
    
    public minimax(char MaquinaSimbolo, char JugadorSimbolo){
        this.MaquinaSimbolo = MaquinaSimbolo;
        this.JugadorSimbolo = JugadorSimbolo;
    }
    public Tree<Tablero> buildStateTree(Tablero actualTablero){
        Node<Tablero> raiz = new Node<>(actualTablero);
        for(Movimiento movDeMaquina : actualTablero.obtenerCasillasVacias()){
            Tablero tableroNivel1 = actualTablero.realizarJugada(movDeMaquina.getFila(), movDeMaquina.getColumna(), MaquinaSimbolo);
            Node<Tablero> nodoNivel1 = new Node<>(tableroNivel1);
            
            for(Movimiento movDeJugador : tableroNivel1.obtenerCasillasVacias()){
                Tablero tableroNivel2 = tableroNivel1.realizarJugada(movDeJugador.getFila(), movDeJugador.getColumna(), JugadorSimbolo);
                tableroNivel2.calcularUtilidad(MaquinaSimbolo, JugadorSimbolo);
                Node<Tablero> nodoNivel2 = new Node<>(tableroNivel2);
                nodoNivel1.addChild(nodoNivel2);
                
            }
            raiz.addChild(nodoNivel1);
        }
        return new Tree<>(raiz);
    }
    
}

package com.mycompany.proyecto.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexa
 */
public class Tablero {
    private char[][] matriz;
    private int valorUtilidad;

    public Tablero() {
        this.matriz = new char[3][3];
        this.valorUtilidad = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matriz[i][j] = Simbolo.VACIO;
            }
        }
    }
    
    // Constructor copia para generar estados sin modificar al original
    public Tablero(char[][] matrizOrigen){
        this.matriz = new char[3][3];
        this.valorUtilidad = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.matriz[i][j] = matrizOrigen[i][j];
            }
        }
    }
    
    public int contarLineasPosibles(char jugadorSimbolo, char oponenteSimbolo ){
        int contador = 0;
        //Evaluar 3 filas
        for(int i = 0; i<3; i++ ){
            if(matriz[i][0] != oponenteSimbolo && matriz[i][1] != oponenteSimbolo && matriz[i][2] != oponenteSimbolo){
                contador ++;    
            }
        }
        //Evaluar 3 columnas
        for(int j=0; j<3; j++){
            if(matriz[0][j] != oponenteSimbolo && matriz[1][j] != oponenteSimbolo && matriz[2][j] != oponenteSimbolo){
                contador ++;    
            }
        }
        //Evaluar DiagonalPrincipal
        if(matriz[0][0] != oponenteSimbolo && matriz[1][1] != oponenteSimbolo && matriz[2][2] != oponenteSimbolo){
                contador ++;    
        }
        //Evaluar DiagonalSecundaria
        if(matriz[0][2] != oponenteSimbolo && matriz[1][1] != oponenteSimbolo && matriz[2][0] != oponenteSimbolo){
            contador ++;    
        }
        return contador;        
    }
    
    public void calcularUtilidad(char maquinaSimbolo, char jugadorSimbolo) {
        
        char ganador = verificarGanador();
    
        if (ganador == maquinaSimbolo) {
            this.valorUtilidad = 1000;
            return;
        } 

        if (ganador == jugadorSimbolo) {
            this.valorUtilidad = -1000;
            return;
        }
        
        int pMaquina = contarLineasPosibles(maquinaSimbolo, jugadorSimbolo);
        int pJugador = contarLineasPosibles(jugadorSimbolo, maquinaSimbolo);
        
        this.valorUtilidad = pMaquina - pJugador;
    }
    
    
    public Tablero realizarJugada(int fila, int columna, char simbolo){
        Tablero t_nuevo = new Tablero(this.matriz);
        t_nuevo.matriz[fila][columna] = simbolo;
        return t_nuevo;
    }
    
    public List<Movimiento> obtenerCasillasVacias() {
        List<Movimiento> vacias = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j] == Simbolo.VACIO) {
                    vacias.add(new Movimiento(i, j));
                }
            }
        }
        return vacias;
    }

    public char verificarGanador() {
        for (int i = 0; i < 3; i++) {
            // verificación de filas
            if (matriz[i][0] != Simbolo.VACIO && matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2]) {
                return matriz[i][0];
            }
            // Verificación de columnas
            if (matriz[0][i] != Simbolo.VACIO && matriz[0][i] == matriz[1][i] && matriz[1][i] == matriz[2][i]) {
                return matriz[0][i];
            }
        }
        // Verificación de diagonales
        if (matriz[0][0] != Simbolo.VACIO && matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2]) {
            return matriz[0][0];
        }
        if (matriz[0][2] != Simbolo.VACIO && matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0]) {
            return matriz[0][2];
        }
        
        //no hay ganador
        return Simbolo.VACIO;
    }
    
    
    public boolean estaLleno() {
        return obtenerCasillasVacias().isEmpty();
    }
    
    //getters
    public int getValorUtilidad() {
        return valorUtilidad;
    }
    
    public char[][] getMatriz() {
        return this.matriz;
    }
}
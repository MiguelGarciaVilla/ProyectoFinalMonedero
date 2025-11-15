package co.edu.uniquindio.poo.proyectofinal.model;

public enum Rango {
    BRONCE(0, 500),
    PLATA(501, 1000),
    ORO(1001, 5000),
    PLATINO(5001, Integer.MAX_VALUE);

    private final int maximoValor;
    private final int minimoValor;

    Rango(int minimoValor, int maximoValor) {
        this.maximoValor = maximoValor;
        this.minimoValor = minimoValor;
    }

    public static Rango determinarRango(int puntos){
        if(puntos >= PLATINO.minimoValor){
            return PLATINO;
        }else if(puntos >= ORO.minimoValor){
            return ORO;
        }else if(puntos >= PLATA.minimoValor){
            return PLATA;
        }else{
            return BRONCE;
        }
    }
}

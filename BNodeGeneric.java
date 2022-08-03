package LAB8_ARBOL_B;   

import java.util.Vector;

public class BNodeGeneric <T>{

    protected int count; //Cantidad de claves
    protected Vector <T> keys;  //
    protected Vector<BNodeGeneric<T>> childs;
    

    public BNodeGeneric(int ncount) {
        this.keys = new Vector<T>(ncount);
        this.childs = new Vector<BNodeGeneric<T>>(ncount);
        this.count = 0;
        
        for (int i = 0; i < ncount; i++) {
            this.keys.add(null);
            this.childs.add(null);
        }
    }
    
    
    //Permiten ver el estado 
            //Condicion para evaluar si hacer una division o reestructuracion
    public boolean nodeFull(int nElem){
        return this.count == nElem;
    }
    
            //Regla del minimo, indica si hay underflow, para fusion o reestructuracion
    public boolean nodeEmpty(int nElem){
        return this.count < (nElem/2);
    }
            
    public String toString(){
        String s = "( ";
        for (int i = 0; i < this.count; i++) {
            s += this.keys.get(i)+", ";
        }
        return s + " )";
    }
}
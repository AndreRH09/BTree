package LAB8_ARBOL_B;

public class BTreeGeneric<E extends Comparable<? super E>> {
    BNodeGeneric<E> root;
    private int orden; 
    
    
    private boolean up; //Una especie de banderita, aun puede insertar pendiente
    private BNodeGeneric<E> nDes;

    public BTreeGeneric(int orden) {
        this.root = null;
        this.orden = orden;
        
    }
    
    
    public boolean add(E value) {
        //TODO implement here!

       up = false;
       E mediana;
       BNodeGeneric<E> pnew;
       mediana = push(this.root,value);
       
       if(up){
           pnew = new BNodeGeneric<E>(this.orden);
           pnew.count = 1;
           pnew.keys.set(0, mediana);
           pnew.childs.set(0, this.root);
           pnew.childs.set(1, nDes);
           this.root = pnew;
       }
       return up;
    }

    public E remove(E value) {
        //TODO implement here!
        return null;
    }

    public void clear() {
        //TODO implement here!
    }

    public boolean search(E value) {
        //TODO implement here!
        return recSearch(value, root);
    }
    
    public boolean recSearch(E key,BNodeGeneric<E> current){
        int pos[] = new int[1];
        boolean fl;

        //Ya no hay donde mas descender, busqueda sin exito 
        if(current == null)
            return false;
        //Si aun se puede descender 
        else{
            fl = searchNode(current,key, pos);
            if(fl){
                System.out.println("Clave encontrada en la posicion "+ pos[0]);
                return true;
            }
            else
                return recSearch(key,current.childs.get(pos[0]));

        }
    }
    
    //Devuelve la pos para seguir descendiendo y si existe un nodo con la clave
    public boolean searchNode(BNodeGeneric<E> current, E cl, int[] pos){
        pos[0] = 0;
        
        //La posicion es menor a la cantidad de claves, y se tiene una clave menor a la que se busca
        while(pos[0] < current.count && current.keys.get(pos[0]).compareTo(cl) < 0){
            //Incrementa la posicion
            pos[0]++;
        }
        //
        if(pos[0] == current.count)
            return false;
        
        //Si la clave buscada y la iterada son iguales devuelve verdadero
        return (cl.equals(current.keys.get(pos[0])));
    }
    
    
    private E push(BNodeGeneric<E> current, E key){
        int pos[] =new int[1];
        E mediana;
        
        //Busqueda sin exito es nulo 
        if(current == null){
            up = true;
            nDes = null;
            return key;
        }
        else{
            
            boolean fl = searchNode(current, key , pos);
            if(fl){
                System.out.println("Item duplicado");
                up = false;
                return null;
            }
            
            mediana = push(current.childs.get(pos[0]),key);         //Descender en el arbol hasta llegar a donde insertar
            
            if(up){
                if(current.nodeFull(this.orden -1)) //Para validar si esta lleno
                    mediana = divideNode(current,mediana,pos[0]);//Divide
                else{
                    up = false;
                    putNode(current, key, nDes, pos[0]);
                }
                
            }
            return mediana;
        }
    }
    
        
    
    public E divideNode(BNodeGeneric<E> current, E mediana, int k){
        BNodeGeneric <E> rd = nDes;
        int i, posMediana; //la posicion de la clave mediana en el nodo para
        posMediana = (k <= this.orden/2)? this.orden/2: this.orden/2 +1;
        
        nDes =new BNodeGeneric<E>(this.orden);
        for (i = posMediana; i < this.orden-1 ; i++) {      //Pasa las claves en la parte derecha a la clave
            nDes.keys.set(i-posMediana,current.keys.get(i));
            nDes.childs.set(i - posMediana + 1,current.childs.get(i+1));
        }
        
        nDes.count = (this.orden - 1) -  posMediana;
        current.count=posMediana;
        
        if(k<= this.orden/2)
            putNode(current, mediana, rd, k);
        else
            putNode(nDes, mediana, rd, k - posMediana);
        
        E median = current.keys.get(current.count-1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }
    public void putNode(BNodeGeneric<E> current, E key,BNodeGeneric<E> rd, int k){
        int i;
        for (i = current.count - 1; i >= k; i++) { //Recorre el arreglo desde el final hacia a la derecha
            current.keys.set(i+1,current.keys.get(i));
            current.childs.set(i+1,current.childs.get(i+1));
        }
        current.keys.set(k, key);       //Inserta en nodo en la posicion k, l
        current.childs.set(k+1,rd);     //Enlaza el descendiente derecho al nuevo nodo
        current.count++;
    }
    
    public String toString(){
        String s="";
        
        if(isEmpty())
            s = "Arbol B vacio...";
        else{
            s = treeString(this.root);
        }
            
        return s;
    }
    public String treeString(BNodeGeneric <E> current){
        int i;
        String s= "";
        
        if(current != null){
            s += current.toString() + "\n";
            for (i = 0; i < current.count; i++) {
                s += treeString(current.childs.get(i));
            }
        }
        
        return s;
       
    }
    
    public boolean isEmpty(){
        return this.root == null;
    }
    
    public int size() {
        //TODO implement here!
        return 0;
    }
}

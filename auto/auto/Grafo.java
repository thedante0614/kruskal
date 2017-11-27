package autoestudio;
import java.util.*;

public class Grafo {
    char[]  nodos;  // vector de caracteres para los nombres
    int[][] grafo;  // Matriz de adyacencia
   
    int     pesominimo = Integer.MAX_VALUE;   // flujo minimo
    List<Nodo>listos=null;// nodos revisados
 
   
    Grafo(String serieNodos) {
        nodos = serieNodos.toCharArray(); // construye el grafo 
        grafo = new int[nodos.length][nodos.length];
    }
 
    
    public void agregarruta(char origen, char destino, int distancia) {
        int n1 = posicionNodo(origen);
        int n2 = posicionNodo(destino);
        grafo[n1][n2]=distancia;// tamaño de la arista entre dos nodos
        grafo[n2][n1]=distancia;
    }
 
    
    private int posicionNodo(char nodo) {
        for(int i=0; i<nodos.length; i++) {// retorna la posición en el arreglo de un nodo específico
            if(nodos[i]==nodo) return i;
        }
        return -1;
    }
     
    
    public String rutaminima(char inicio, char fin) {// busca la ruta más corta desde un nodo origen a un nodo destino
        
    	rutaminima(inicio);// calcula la ruta más corta del inicio a los demás
        
        Nodo tmp = new Nodo(fin);// recupera el nodo final 
        if(!listos.contains(tmp)) {
            System.out.println("Error, nodo no alcanzable");
            return null;
        }
        tmp = listos.get(listos.indexOf(tmp));
        int distancia = tmp.distancia;  
        
        Stack<Nodo> pila = new Stack<Nodo>();//  pila para almacenar la ruta desde el nodo final al origen
        while(tmp != null) {
            pila.add(tmp);
            tmp = tmp.siguiente;
        }
       
        // recorre la pila para armar la ruta en el orden correcto
       
        return "flujo minimo del grafo : "+distancia ;
    }
 
    // encuentra la ruta más corta desde el nodo inicial a todos los demás
    public void rutaminima(char inicio) {
        Queue<Nodo>   cola = new PriorityQueue<Nodo>(); // cola 
        Nodo            ni = new Nodo(inicio);          // nodo inicial
         
        listos = new LinkedList<Nodo>();// lista de nodos ya revisados
        cola.add(ni);                   // nodo inicial a la cola
        while(!cola.isEmpty()) {        // mientras que la cola no esta vacia
            Nodo tmp = cola.poll();     // saca el primer elemento
            listos.add(tmp);            // lo manda a la lista de terminados
            int p = posicionNodo(tmp.id);   
            for(int j=0; j<grafo[p].length; j++) {  // revisa los nodos hijos del nodo tmp
                if(grafo[p][j]==0) continue;       
                if(estaterminado(j)) continue;      // si ya fue agregado a la lista de terminados
                Nodo nod = new Nodo(nodos[j],tmp.distancia+grafo[p][j],tmp);
                // si no está en la cola  lo agrega
                if(!cola.contains(nod)) {
                    cola.add(nod);
                    continue;
                }
                // si ya está en la cola d actualiza la distancia menor
                for(Nodo x: cola) {
                    // si la distancia en la cola es mayor que la distancia calculada
                   if(x.id==nod.id && x.distancia > nod.distancia) {
                       cola.remove(x); // remueve el nodo de la cola
                      cola.add(nod);  // agrega el nodo con la nueva distancia
                        break;          // no sigue revisando
                    }
                }
            }
        }
    }
 
    // verifica si un nodo ya está en lista de terminados
    public boolean estaterminado(int j) {
        Nodo tmp = new Nodo(nodos[j]);
        return listos.contains(tmp);
    }
 
  
    // evaluar la longitud de una ruta
    public int evaluar(Stack<Integer> resultado) {
        int  resp = 0;
        int[]   r = new int[resultado.size()];
        int     i = 0;
        for(int x: resultado) r[i++]=x;
        for(i=1; i<r.length; i++) resp+=grafo[r[i]][r[i-1]];
        return resp;
    }
 
    public static void main(String[] args) {
        Grafo g = new Grafo("abcdef");
        g.agregarruta('a','b', 5);
        g.agregarruta('a','e', 7);
        g.agregarruta('a','f', 50);
        g.agregarruta('b','c', 1);
        g.agregarruta('b','e', 11);
        g.agregarruta('c','d', 1);
        g.agregarruta('c','e', 23);
        g.agregarruta('c','f', 22);
        g.agregarruta('d','f', 12);
        g.agregarruta('e','f', 20);
        char inicio = 'a';
        char fin    = 'f';
        String respuesta = g.rutaminima(inicio, fin);
        System.out.println(respuesta);
    }
}
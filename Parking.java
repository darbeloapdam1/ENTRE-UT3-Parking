/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un nº elevado de horas y se benefician de esta 
 * tarifa más económica
 * (leer enunciado)
 * @author
 * Diego Arbeloa
 */
public class Parking
{
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 = 5.0;
    private final int HORA_INICIO_ENTRADA_TEMPRANA = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.0;
    private final double PRECIO_MEDIA_COMERCIAL = 3.0;
    private String nombre = "";
    private int cliente;
    private double importeTotal;
    private int regular;
    private int comercial;
    private int clientesLunes;
    private int clientesSabado;
    private int clientesDomingo;
    private int clienteMaximoComercial;
    private double importeMaximoComercial;
    /**
     * Inicializa el parking con el nombre indicada por el parámetro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
        nombre = queNombre;
        cliente = 0;
        importeTotal = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clienteMaximoComercial = 0;
        importeMaximoComercial = 0;

    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre(String nuevoNombre) {
        nombre = nuevoNombre;
    }

    /**
     *  Recibe cuatro parámetros que supondremos correctos:
     *    tipoTarifa - un carácter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida – hora de salida del parking
     *    dia – nº de día de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos parámetros el método debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizará adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro método) las estadísticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo día
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        int entradaHoras = entrada / 100 * 60;
        int entradaMinutos = entrada % 100;
        int totalMinutosEntrada = entradaHoras + entradaMinutos;
        int salidaHoras = salida / 100 * 60;
        int salidaMinutos = salida % 100;
        int totalMinutosSalida = salidaHoras + salidaMinutos;
        double importePagar = 0;
        String tarifaAplicar = "";
        String minutosEntradaCero = ""; //Esta variable y la de abajo son por si los minutos son menores que 10 para que delante aparezca un 0 "04","09"
        String minutosSalidaCero = "";
        cliente++;
        switch(tipoTarifa){
            case REGULAR: 
            regular++;
            if(totalMinutosEntrada > HORA_INICIO_ENTRADA_TEMPRANA && totalMinutosEntrada <= HORA_FIN_ENTRADA_TEMPRANA && 
            totalMinutosSalida >= HORA_INICIO_SALIDA_TEMPRANA && totalMinutosSalida <= HORA_FIN_SALIDA_TEMPRANA){
                importePagar = PRECIO_TARIFA_PLANA_REGULAR;
                tarifaAplicar = "REGULAR y TEMPRANA";
            }else{ 
                double importeRegular = 0;
                tarifaAplicar = "REGULAR";
                int totalMinutosParking = totalMinutosSalida - totalMinutosEntrada;
                if(totalMinutosEntrada <= 660){
                    if((totalMinutosSalida - 660) < 0){
                        int mediaParking = totalMinutosParking / 30;
                        importeRegular = mediaParking * PRECIO_MEDIA_COMERCIAL;
                    }else{
                        int minutosAntes11 = 660 - totalMinutosEntrada;
                        int minutosDespues11 = totalMinutosParking - minutosAntes11;
                        int mediaAntes11 = minutosAntes11 / 30;
                        int mediaDespues11 = minutosDespues11 / 30;
                        double importeMediaAntes11 = mediaAntes11 * PRECIO_MEDIA_COMERCIAL;
                        double importeMediaDespues11 = mediaDespues11 * PRECIO_PRIMERAS3_COMERCIAL;
                        importeRegular = importeMediaAntes11 + importeMediaDespues11; 
                    }
                }else{
                    int mediaParking = totalMinutosParking / 30;
                    importeRegular = mediaParking * PRECIO_PRIMERAS3_COMERCIAL;
                }
                importePagar = PRECIO_BASE_REGULAR + importeRegular;
            }
            importeTotal += importePagar;
            break;
            case COMERCIAL:
            comercial++;
            tarifaAplicar = "COMERCIAL";
            int tiempoTotalParking = totalMinutosSalida - totalMinutosEntrada;
            if(tiempoTotalParking > 180){
                int tiempoVale3 = tiempoTotalParking - 180;
                double importeTiempoVale3 = (tiempoVale3 / 30) * PRECIO_MEDIA_COMERCIAL;
                importePagar = PRECIO_PRIMERAS3_COMERCIAL + importeTiempoVale3;
                importeTotal += importePagar;
            }else{
                importePagar = PRECIO_PRIMERAS3_COMERCIAL;
                importeTotal += importePagar;
            }
            if(importeMaximoComercial < importePagar){
                importeMaximoComercial = importePagar;
                clienteMaximoComercial = cliente;
            }
            break;
        }
        //para que en los minutos si son menores que 10 aparezca un 0 delante
        if(entradaMinutos < 10){
            minutosEntradaCero = "0" + entradaMinutos;
        }else{
            minutosEntradaCero = "" + entradaMinutos;
        }
        if(salidaMinutos < 10){
            minutosSalidaCero = "0" + salidaMinutos;
        }else{
            minutosSalidaCero = "" + salidaMinutos;
        }
        System.out.println("************************************************************" + 
            "\nCliente nº" + cliente + 
            "\nHora entrada: " + entradaHoras / 60 + ":" + minutosEntradaCero + 
            "\nHora salida: " + salidaHoras / 60 + ":" + minutosSalidaCero + 
            "\nTarifa a aplicar: " + tarifaAplicar + 
            "\nImporte a pagar: " + importePagar + "€" +
            "\n************************************************************");
        switch(dia){
            case 1: clientesLunes++;
            break;
            case 2: clientesSabado++;
            break;
            case 3: clientesDomingo++;
            break;
        }
    }

    /**
     * Muestra en pantalla las estadísticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstadísticas() {
        System.out.println("************************************************************" + 
            "\nImporte total entre todos los clientes: " + importeTotal + "€" + 
            "\nNºclientes tarifa regular: " + regular + 
            "\nNºclientes tafira comercial: " + comercial + 
            "\nCliente tarifa COMERCIAL con factura máxima fue el nº" + clienteMaximoComercial + "\ny pagó " +  + importeMaximoComercial + "€" + 
            "\n************************************************************");
    }

    /**
     *  Calcula y devuelve un String que representa el nombre del día
     *  en el que más clientes han utilizado el parking - "SÁBADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        int diaMayor = 0;
        String letraDiaMayor = "";
        if(clientesLunes > clientesSabado){
            diaMayor = 1;
        }else{
            diaMayor = 2;
        }
        if(diaMayor > clientesDomingo){
            if(diaMayor == 1){
                letraDiaMayor = "LUNES";
            }else{
                letraDiaMayor = "SABADO";
            }
        }else{
            letraDiaMayor = "DOMINGO";
        }
        return letraDiaMayor;
    }
}

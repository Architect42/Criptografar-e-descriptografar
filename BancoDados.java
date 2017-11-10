package Utilitarios;

public class BancoDados {
    private final String[] login = new String[]{"Urias42","Edson42"};
    /*Cada fez que almentar +100 de caracteres criar + 3 nomes E: linha84...88*/
    private final String[] tusk = {
        "linha4","linha6","linha8","linha10","linha12",
        "linha14","linha16","linha18","linha20","linha22",
        "linha24","linha26","linha28","linha30","linha32",
        "linha34","linha36","linha38","linha40","linha42",
        "linha44","linha46","linha48","linha50","linha52",
        "linha54","linha56","linha58","linha60","linha62",
        "linha64","linha66","linha68","linha70","linha72",
        "linha74","linha76","linha78","linha80","linha82","sss"};
    
    public boolean getLogin(String login){  
        boolean acesso = false;
        for(int i = 0;i<this.login.length;i++){
            if(this.login[i].equals(login)){
                acesso = true;
            }
        }
        return acesso;
    }
    
    public int chooseTusk(String cod){
        int codAcesso = 0;
        for(int a = 0,b = 4;a<tusk.length;a++,b+=2){
            if(cod.equals(tusk[a])){
                codAcesso = b;
            }
        }
        if(codAcesso == 0){
            System.out.println(); 
            System.out.println("CODIGO DE ACESSO NEGADO!!!");
        }
        return codAcesso;
    }
    
    public String getTusk(int i){
        return tusk[i];
    }
    public int showTusk(){
        return tusk.length;
    }
    /*
    private void validaAcesso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}

package encriptaacao;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import Utilitarios.*;

public class EncriptaAcao { 
    static Scanner input = new Scanner(System.in);
    static BancoDados dados = new BancoDados();
    //Colunas só pode ir até o numero 20 de largura;
    static final int coluna = 30;
    static final int colunaMax = coluna*99;
    static final int colunaMin = coluna*50;
    
    public static void main(String[] args) throws IOException {        
        /* <--- Sistema de login --->
        funcaoBarra();
        System.out.print("INSIRA SEU CODIGO DE ACESSO: ");
        String senha = input.nextLine().replaceAll(" ","");
        
        validaAcesso(senha);
        */ 
        
        tipoEscolha(); //<--- Permite o acesso ao programa sem precisar fazer login --->
        versaoProduto(); //<--- Metodo para definir a versão do produto --->
    }
    
    public static void validaAcesso(String senha){
        if(!dados.getLogin(senha)){
            do{
                System.out.print("SENHA INCORRETA, DIGITE NOVAMENTE: ");
                senha = input.nextLine().replaceAll(" ","");
                dados.getLogin(senha);
            }while(!dados.getLogin(senha));
            tipoEscolha();
        }else{
            tipoEscolha();
        } 
    }
    
    public static void tipoEscolha(){ //<--- Valida o tipo de escolha do usuário --->
        String opcao;
        boolean status = true; 
        
        while(status){
            funcaoBarraTipoEscolha();
            centralizaTextos("ESCOLHA",0);
            funcaoBarraStile2();
            centralizaTextos("1 : PARA CRIPTOGRAFAR.",0);
            
            centralizaTextos("2 : PARA DESCRIPTOGRAFAR.",0);
            
            centralizaTextos("3 : PARA FECHAR O PROGRAMA.",0);
            funcaoBarraStile2();                           
            System.out.print("ESCOLHA: ");
            opcao = input.nextLine().replaceAll(" ","");
            switch(opcao){
                case "1":
                    applicateCls();
                    criptografar();
                    status = false;
                break;
                case "2":
                    descriptografar();
                    status = false;
                break;
                case "3":System.out.println();status=false;break;
                default:
                    System.out.println("ESTA OPÇÃO NÃO EXISTE!"); 
                break;
            }
        }
    }
    
    // <--- Campo para descriptografar --->
    public static void descriptografar(){
        System.out.print("INSIRA O CODIGO DE DESCRIPTOGRAFIA: ");
        String cod = input.nextLine().replaceAll(" ","");
        
        applicateCls();
        int codAcesso = dados.chooseTusk(cod);
        if(codAcesso != 0){
            see(codAcesso);
        }
    }

    public static void see(int qtdLinha){
        String[][] codDescrip = new String[qtdLinha][1];
        char[][] linha = new char[qtdLinha][];
        boolean youCan = true;
        
        funcaoBarra();
        centralizaTextos("DIGITE O CODIGO QUE QUER DESCRIPTOGRAFAR\n",1);
        funcaoBarraStile();
        for(int i = 0;i< qtdLinha;i++){
            codDescrip[i][0] = input.nextLine(); // <--- Recupera cada linha da coluna --->          
            linha[i] = codDescrip[i][0].toCharArray(); // <--- Recupera cada char da linha --->
        }
        
        char[][] formeLinha = new char[qtdLinha][linha[0].length]; // <--- Recebera somentes os caracteres da coluna --->
        int c = 0;
        for(int i = 0;i<qtdLinha;i++){           
            int l = 0;
            for(int j= 0;j<linha[0].length;j++){
                if((linha[i][j] != '|')&&(linha[i][j] != '-')){ 
                    formeLinha[c][l] = linha[i][j];
                    l++;
                }
            }
            // <--- Valida se a primeira linha da tabela possui as tarjas de acesso --->
            if((i == 0)&&(linha[i][0] == '-')&&(linha[i][1] == '-')&&(linha[i][2] == '-')&&(linha[i][3] == '-')){
                c = 0;
            }
            else{
                c++;
            }  
        }
        
        funcaoBarraStile();
        centralizaTextos("CODIGO TRADUZIDO COM SUCESSO!",0);
        centralizaTextos("-----------------------------\n",0);
        // <--- A variavel "h" é para horizontal e a "v" para vertical --->
        for(int h = 0,oh = 1;h <qtdLinha; h+=2,oh+=2){
            for(int v = 0; v <coluna; v++){
                if((v % 2 == 1)&& (youCan)){
                    findCharEspecail(formeLinha,h,v);
                    if(formeLinha[h][v] == '.'){
                        youCan = false;
                    }
                    System.out.print(formeLinha[h][v]+" ");
                }   
                if((v % 2 == 0)&&(youCan)){
                    findCharEspecail(formeLinha,oh,v);
                    if(formeLinha[oh][v] == '.'){
                        youCan = false;
                    }
                    System.out.print(formeLinha[oh][v]+" ");
                }           
            }
            System.out.println();
        }
    }      

    // <--- Campo de criptografia --->
    public static void criptografar(){
        String palavra;
        boolean yesYouCan=false;
        char[] text,codL,letr = null;
        int ideia = coluna,qtdVezes = 0,qtdDeSe = 0;
        funcaoBarraStile();
        centralizaTextos("DIGITE A FRASE QUE QUER CRIPTOGRAFAR",1);
        palavra = input.nextLine();
                
        if(("".equals(palavra))||(" ".equals(palavra))||("  ".equals(palavra))||("   ".equals(palavra))){
            nadaDigitado();
        }else{
            palavra += ".";
            letr = palavra.toCharArray();
            //Metodo para trasformar as letras minusculas em maiusculas
            transformMaiusculo(letr);
            for(int a = 0,b=0,c=2;a<=colunaMax;a+=coluna,b++,c+=2){
                if((letr.length > a)&&(letr.length <=a+coluna)){ 
                    ideia += (a+coluna);
                    qtdVezes = c;
                    qtdDeSe = b;
                    a = colunaMax+1;
                }
            }

            if(geraSenha(qtdDeSe,letr,yesYouCan,ideia)){
                text = new char[ideia];
                codL = new char[ideia];

                for(int i = 0; i <ideia ;i++){
                    autoIncrementa(text,i);
                    if(i < letr.length){
                        codL[i] = letr[i];
                    }else{
                        autoIncrementa(codL,i);
                    }   
                }
                instanciaTela(ideia,text,codL,qtdVezes);
            }
        }
    }
    
    public static void nadaDigitado(){
        funcaoBarraPontilhada();
        geraZeroUm(11);
        System.out.print("E|R|R|O|R");
        geraZeroUm(8);
        System.out.println();
        funcaoBarraPontilhada();
    }
    
    public static void instanciaTela(int ideia,char[]text, char[]codL,int qtdVezes){
        // numero de caracters na linha
        int count = 0, numColuna = coluna;
        
        funcaoBarraPontilhada();
        do{
            geradorColunas(numColuna,text,codL,count);
            count += coluna;
            numColuna += coluna;
            qtdVezes+= -2;
            System.out.println();     
        }while(qtdVezes >= 2);
        funcaoBarraPontilhada();
    }
    
    public static void geradorColunas(int numColuna,char[] text,char[]codL,int count){
        int i = count,j = numColuna;
        System.out.print("|");
        while(count < numColuna){
            if(count % 2 == 0){
                System.out.print(text[count]);
            }else{
                System.out.print(codL[count]);
            }
            System.out.print("|");
            count++;
        }
        System.out.println();
        System.out.print("|"); 
        while(i < j){
            if(i % 2 == 0){
                System.out.print(codL[i]);
            }else{
                System.out.print(text[i]);
            }
            System.out.print("|");
            i++;
        }
    }
        
    public static boolean geraSenha(int i,char[]letr,boolean yesYouCan,int ideia){
        if((ideia > colunaMin)||(dados.showTusk() < i)){
            yesYouCan = false;
            applicateCls();
            funcaoBarraErro();
            centralizaTextos("LIMITE DE CARACTERS EXCEDIDO!",0);
            centralizaTextos("VOCÊ DIGITOU: "+letr.length+" CARACTERES",1);
            System.out.println(ideia);
            System.out.println(i);
            System.out.println(dados.showTusk());
            funcaoBarraErro();
            funcaoBarraPontilhada();
            if(coluna == 20){
                System.out.print("|1|0|V|E|R|S|A|O| |D|O| |P|R|O|D|U|T|O|1|");
                System.out.println();
                System.out.print("|1|0|1|0|1|0|1|I|N|F|E|R|I|O|R|0|1|0|1|0|");
            }else{
                geraZeroUm(51);
                System.out.print("V|E|R|S|A|O| |D|O| |P|R|O|D|U|T|O| |I|N|F|E|R|I|O|R");
                geraZeroUm(51);
                System.out.println();
                geraZeroUm(59);
                System.out.print("T|E|N|T|E| |A|D|Q|U|I|R|I|R| |U|M|A| |N|O|V|A| |V|E|R|S|A|O");
                geraZeroUm(59);
            }
            System.out.println();
            funcaoBarraPontilhada();
            System.out.println();
        }else{
            yesYouCan = true;
            applicateCls();
            funcaoBarraStile();
            centralizaTextos("SENHA PARA DESCRIPTOGRAFIA",1);
            centralizaTextos("\""+dados.getTusk(i)+"\"",1);
            funcaoBarraStile();
            System.out.println();
        }
        return yesYouCan;
    }
    
    public static void autoIncrementa(char[]X,int i){
        Random gerada = new Random();
        int num = gerada.nextInt(26);
        switch(num){
            case 0:X[i] = 'A';break;
            case 1:X[i] = 'B';break;
            case 2:X[i] = 'C';break;
            case 3:X[i] = 'D';break;
            case 4:X[i] = 'E';break;
            case 5:X[i] = 'F';break;
            case 6:X[i] = 'G';break;
            case 7:X[i] = 'H';break;
            case 8:X[i] = 'I';break;
            case 9:X[i] = 'J';break;
            case 10:X[i] = 'K';break;
            case 11:X[i] = 'L';break;
            case 12:X[i] = 'M'; break;
            case 13:X[i] = 'N';break;
            case 14:X[i] = 'O';break;
            case 15:X[i] = 'P';break;
            case 16:X[i] = 'Q';break;
            case 17:X[i] = 'R';break;
            case 18:X[i] = 'S';break;
            case 19:X[i] = 'T';break;
            case 20:X[i] = 'U';break;
            case 21:X[i] = 'V';break;
            case 22:X[i] = 'W';break;
            case 23:X[i] = 'X';break;
            case 24:X[i] = 'Y';break;
            case 25:X[i] = 'Z';break;
            default:break;
        }
    }
    
    public static void transformMaiusculo(char[] letr){
        for(int i = 0;i < letr.length; i++){ 
            if(letr[i] == 'a'){letr[i] = 'A';}
            else if(letr[i] == 'b'){letr[i] = 'B';}
            else if(letr[i] == 'c'){letr[i] = 'C';}
            else if(letr[i] == 'd'){letr[i] = 'D';}
            else if(letr[i] == 'e'){letr[i] = 'E';}
            else if(letr[i] == 'f'){letr[i] = 'F';}
            else if(letr[i] == 'g'){letr[i] = 'G';}
            else if(letr[i] == 'h'){letr[i] = 'H';}
            else if(letr[i] == 'i'){letr[i] = 'I';}
            else if(letr[i] == 'j'){letr[i] = 'J';}
            else if(letr[i] == 'k'){letr[i] = 'K';}
            else if(letr[i] == 'l'){letr[i] = 'L';}
            else if(letr[i] == 'm'){letr[i] = 'M';}
            else if(letr[i] == 'n'){letr[i] = 'N';}
            else if(letr[i] == 'o'){letr[i] = 'O';}
            else if(letr[i] == 'p'){letr[i] = 'P';}
            else if(letr[i] == 'q'){letr[i] = 'Q';}
            else if(letr[i] == 'r'){letr[i] = 'R';}
            else if(letr[i] == 's'){letr[i] = 'S';}
            else if(letr[i] == 't'){letr[i] = 'T';}
            else if(letr[i] == 'u'){letr[i] = 'U';}
            else if(letr[i] == 'v'){letr[i] = 'V';}
            else if(letr[i] == 'w'){letr[i] = 'W';}
            else if(letr[i] == 'x'){letr[i] = 'X';}
            else if(letr[i] == 'y'){letr[i] = 'Y';}
            else if(letr[i] == 'z'){letr[i] = 'Z';}
            //Caracteres especiais.
            else if(letr[i] == '?'){letr[i] = 'Ф';}
            else if(letr[i] == ','){letr[i] = 'ĕ';}
            else if(letr[i] == ' '){letr[i] = '?';}
            else if(letr[i] == '.'){letr[i] = '!';}
            else if(letr[i] == '!'){letr[i] = 'Ћ';}
            else if((letr[i] == 'ç')||(letr[i] == 'Ç')){letr[i] = 'Щ';}
            else if((letr[i] == 'é')||(letr[i] == 'É')){letr[i] = '�';}
            else if((letr[i] == 'õ')||(letr[i] == 'Õ')){letr[i] = 'ى';}
            else if((letr[i] == 'ã')||(letr[i] == 'Ã')){letr[i] = '₩';}
            else if((letr[i] == 'ê')||(letr[i] == 'Ê')){letr[i] = 'Ю';}
        }
    }
    
    public static void findCharEspecail(char[][] formeLinha,int h,int v){
        switch (formeLinha[h][v]) {
            case 'Щ':formeLinha[h][v] = 'Ç';break;
            case 'Ф':formeLinha[h][v] = '?';break;
            case '�':formeLinha[h][v] = 'É';break;
            case 'ĕ':formeLinha[h][v] = ',';break;
            case '?':formeLinha[h][v] = ' ';break;
            case '!':formeLinha[h][v] = '.';break;
            case 'ى':formeLinha[h][v] = 'Õ';break;
            case '₩':formeLinha[h][v] = 'Ã';break;
            case 'Ћ':formeLinha[h][v] = '!';break;
            case 'Ю':formeLinha[h][v] = 'Ê';break;
            default:break;
        }
    }
    
    //Area de Perfumaria do codigo
    public static void funcaoBarraTipoEscolha(){
        int count = 0;
        while(count <= coluna+coluna){
            switch(count){
                case 0:System.out.print(" ");break;
                case coluna+coluna:System.out.print(" ");break;
                default:System.out.print("_");break;
                
            }
            count++;
        }
        System.out.println();
    }
    
    public static void versaoProduto(){
        funcaoBarra();
        centralizaTextos("VERSÃO DO PRODUTO: 1.0.0v",0);
        funcaoBarra();
    }

    public static void centralizaTextos(String numLetra,int c){
        for(int i=0;i<(((coluna+coluna+1)-(numLetra.length()-c))/2);i++){
            System.out.print(" ");
        }
        System.out.println(numLetra);         
    }
    
    public static void funcaoBarraErro(){
        int count = 1;
        System.out.println();
        while(count <= (coluna+coluna+1)){
            switch(count){
                case 1:System.out.print("<");break;
                case 2:System.out.print("X");break;
                case (((coluna-8)/2)+4):System.out.print("|");break;
                case (((coluna-8)/2)+3):System.out.print("X");break;
                case (((coluna-8)/2)+2):System.out.print("X");break;
                case (((coluna-8)/2)+1):System.out.print("|");break;
                case coluna-5:System.out.print(">");break;
                case coluna-4:System.out.print("<");break;
                case coluna-3:System.out.print("|");break;
                case coluna-2:System.out.print("-");break;
                case coluna-1:System.out.print("E");break;
                case coluna:System.out.print("R");break;
                case coluna+1:System.out.print("R");break;
                case coluna+2:System.out.print("O");break;
                case coluna+3:System.out.print("R");break;
                case coluna+4:System.out.print("-");break;
                case coluna+5:System.out.print("|");break;
                case coluna+6:System.out.print(">");break;
                case coluna+7:System.out.print("<");break;
                case ((coluna+7)+(((coluna-2)/2)-4)):System.out.print("|");break;
                case ((coluna+7)+(((coluna-2)/2)-3)):System.out.print("X");break;
                case ((coluna+7)+(((coluna-2)/2)-2)):System.out.print("X");break;
                case ((coluna+7)+(((coluna-2)/2)-1)):System.out.print("|");break;
                case (coluna+coluna):System.out.print("X");break;
                case ((coluna+coluna)+1):System.out.print(">");break;
                default:System.out.print("=");break;
            }
            count++;
        }
        System.out.println();
    }
    public static void funcaoBarra(){
        int count = 0;
        while(count <= coluna+coluna){
            System.out.print("=");
            count++;
        }
        System.out.println();
    }
    public static void funcaoBarraStile(){
        int count = 0;
        while(count <= coluna+coluna){
            switch(count){
                case 0:System.out.print("<");break;
                case 2:System.out.print("|");break;
                case coluna+coluna:System.out.print(">");break;
                case coluna+coluna-2:System.out.print("|");break;
                case coluna-1:System.out.print("{");break;
                case coluna:System.out.print(" ");break;
                case coluna+1:System.out.print("}");break;
                default:System.out.print("=");break;
            }
            count++;
        }
        System.out.println();
    }
    public static void funcaoBarraPontilhada(){
        int count = 0;
        while(count <= coluna+coluna){
            System.out.print("-");
            count++;
        }
        System.out.println();
    }   
    public static void applicateCls(){
        for(int i=0; i<35; i++){
            System.out.println();
        }
    }
    public static void funcaoBarraStile2(){
        int count = 0;
        while(count <= coluna+coluna){
            switch(count){
                case 0:System.out.print("<");break;
                case 2:System.out.print("|");break;
                case coluna+coluna:System.out.print(">");break;
                case coluna+coluna-2:System.out.print("|");break;
                case coluna-1:System.out.print("{");break;
                case coluna:System.out.print("X");break;
                case coluna+1:System.out.print("}");break;
                default:System.out.print("=");break;
            }
            count++;
        }
        System.out.println();
    }
    
    public static void geraZeroUm(int num){
        int count = 0,i=0;
        while(count <= ((coluna+coluna)-num)/2){
            if(count % 2 == 0){
                System.out.print("|");
            }else{
                if(i == 0){
                    System.out.print("0");
                }else{
                    System.out.print("1");
                }
                if(i == 0){i = 1;}
                else{i = 0;}
            }
            count++;
        }
    }
}

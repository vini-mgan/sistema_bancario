import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;   
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;


class Client{
    
    String nome;
    Long CPF;
    String senha;
    int conta;
    float saldo;

    //classe para as Movimentacoes de entrada e saída de valores

    class Movimento{
    
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
        float value; 
        LocalDateTime time;
        //System.out.println(dtf.format(time));
        
        String nome_Sent;
        Long CPF_Sent;
        
        String nome_Received;
        Long CPF_Received; 
    }

    //ArrayList de onde serão referenciados os objetos de tipo Movimento

    ArrayList<Movimento> extrato = new ArrayList<Movimento>();
    
    //Metodo para criacao de conta

    void Registro(ArrayList<Long> registros){

        System.out.println("----------------------------------------");
        System.out.println("\tCRIAÇÃO DE NOVA CONTA");
        System.out.println("Insira os seus dados cadastrais");
        System.out.println("----------------------------------------");

        Scanner input = new Scanner(System.in);

        //Nome

        System.out.println("Nome: ");
        nome = input.nextLine();

        //CPF

        System.out.println("CPF: ");
        CPF = input.nextLong();
        input.nextLine();

        while (registros.contains(CPF) || String.valueOf(CPF).length() != 11){

            System.out.println("----------------------------------------");
            System.out.println("CPF inválido ou já cadastrado");
            System.out.println("----------------------------------------");
            System.out.println("CPF: ");
            CPF = input.nextLong();
            input.nextLine();            
        }

        //Senha

        System.out.println("Senha: ");
        senha = input.nextLine();

        while (senha.length() < 8 || senha.length() > 20){
            System.out.println("----------------------------------------");
            System.out.println("Tamanho de senha inválido, tente denovo");
            System.out.println("----------------------------------------");
            System.out.println("Senha: ");
            senha = input.nextLine();
        }

        registros.add(CPF);

        System.out.println("----------------------------------------");
        System.out.println("Conta criada com sucesso, seja bem-vindo");

        saldo += 50;

        System.out.println("Você recebeu R$50 de cortesia!");
    }
    
    //Metodo para entrada em conta

    Client Login(ArrayList<Client> clients){

        int indexConta = -1;

        System.out.println("----------------------------------------");
        System.out.println("\t\tLOGIN");
        System.out.println("----------------------------------------");
        System.out.println("CPF: ");

        Scanner input = new Scanner(System.in);
        Long inputCPF = input.nextLong();
        input.nextLine();

        while(indexConta == -1){

            for(int i=0; i < clients.size(); i++){

                Client temp = clients.get(i);
                long tempCPF = temp.CPF;

                if(tempCPF == inputCPF){

                    System.out.println("Senha: ");
                    String inputSenha = input.nextLine();

                    if(inputSenha.equals(temp.senha)){

                        System.out.println("Seja bem-vindo!" );
                        indexConta = i;
                    }

                    else{
                        while(senha.equals(temp.senha) == false){
                            System.out.println("Senha incorreta, tente novamente:" );
                            senha = input.nextLine();
                        }
                    }    
                }
            }

            if(indexConta == -1){

                System.out.println("----------------------------------------");
                System.out.println("CPF não encontrado, tente novamente" );
                System.out.println("----------------------------------------");
                System.out.print("CPF: ");
                
                inputCPF = input.nextLong();
                input.nextLine();
            }
        } 
        
        Client user = new Client();
        user = clients.get(indexConta);

        return user;   
    }

    //Metodo para transferencia de valores entre contas

    void Transferencia(ArrayList<Client> clients){
        
        System.out.println("----------------------------------------");
        System.out.println("\tTRANSFERÊNCIA");
        System.out.println("insira conta destino e valor");
        System.out.println("----------------------------------------");
        System.out.println("CONTA: ");

        Scanner input = new Scanner(System.in);
        int contaDestino = input.nextInt();

        while (contaDestino == conta || contaDestino > clients.size()){ //Não pode transferir para si mesmo

            System.out.println("----------------------------------------");
            System.out.println("Numero de conta inválido");
            System.out.println("----------------------------------------");
            System.out.println("CONTA: ");
            contaDestino = input.nextInt();

        }

        Client clienteDestino = new Client();
        clienteDestino = clients.get(contaDestino);

        System.out.println("VALOR: ");
        float valorTrans = input.nextFloat(); //usar virgula
        input.nextLine();

        while (valorTrans > saldo || valorTrans == 0){

            System.out.println("----------------------------------------");
            System.out.println("Saldo insuficiente...");
            System.out.println("Seu saldo: R$" + saldo);
            System.out.println("----------------------------------------");
            System.out.println("VALOR: ");

            valorTrans = input.nextFloat(); //usar virgula
            input.nextLine();
        }

        System.out.println("----------------------------------------");
        System.out.println("\tConfirmar Transferência?\n");
        System.out.println("- Valor: R$" + valorTrans);
        System.out.println("- Nome: " + clienteDestino.nome);
        System.out.println("- Conta: " + clienteDestino.conta);
        System.out.println("\n\tSIM [1]\tNão[2]");
        System.out.println("----------------------------------------");
        System.out.print("--> ");

        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1){
            saldo -= valorTrans;
            clienteDestino.saldo += valorTrans;

            //Salva dados da movimentação na conta de quem envia

            Movimento mov = new Movimento();

            extrato.add(mov);
            clienteDestino.extrato.add(mov);

            mov.value = valorTrans;
            mov.time = LocalDateTime.now();
            mov.nome_Sent = nome;
            mov.CPF_Sent = CPF;
            mov.nome_Received = clienteDestino.nome;
            mov.CPF_Received = clienteDestino.CPF;

            //Salva dados da movimentação na conta de quem recebe

            System.out.println("----------------------------------------");
            System.out.println("Transferência efetuada com sucesso!\n");
        }
    }

    //Metodo para gerar arquivo de extrato com movimentacoes

    void getExtrato(){
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime time = LocalDateTime.now();   

        try {
            //File meuExtrato = new File("extrato.txt");

            PrintWriter writer = new PrintWriter("extrato_" + nome + ".txt");
            
            writer.println("\tSEU EXTRATO");
            writer.println("----------------------------------------");
            writer.println("- Nome: " + nome);
            writer.println("- Conta: " + conta);
            writer.println("- Saldo atual: R$" + saldo);
            writer.println("- Data de emissão: " + dtf.format(time));
            writer.println("----------------------------------------");

            for(int i=0; i < extrato.size(); i++){

                Movimento mov = new Movimento();
                mov = extrato.get(i);

                writer.println("[" + dtf.format(mov.time) + "]\n");
                
                if (mov.CPF_Sent == CPF){
                    writer.println("-R$"+ mov.value);
                    writer.println("Transferência para:\n" + mov.nome_Received);
                    writer.println("(CPF: " + mov.CPF_Received + ")");
                }

                else{
                    writer.println("R$"+ mov.value);
                    writer.println("Deposito de:\n" + mov.nome_Sent);
                    writer.println("(CPF: " + mov.CPF_Sent + ")");

                }

                writer.println("----------------------------------------\n");
                
            }

            writer.close();

            System.out.println("----------------------------------------");
            System.out.println("Extrato criado: extrato_" + nome + ".txt");
        } 
          
        catch (IOException e) {
            System.out.println("Ocorreu um erro.");
            e.printStackTrace();
        }
    }
}


class Menu{

    //Menu inicial

    int inicializarMenu0(){

        System.out.println("----------------------------------------");
        System.out.println("\tSISTEMA BANCÁRIO UNICAP");
        System.out.println("----------------------------------------");
        System.out.println("\t[1] Entrar\n\t[2] Nova Conta\n\t[3] Encerrar");
        System.out.println("----------------------------------------");
        System.out.print("--> ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        input.nextLine();

        return choice;
    }

    //Menu de conta

    void inicializarMenu1(Client currentUser, ArrayList<Client> clients){

        boolean menu = true;
        while(menu){
            System.out.println("----------------------------------------");
            System.out.println("USUARIO: " + currentUser.nome);
            System.out.println("CONTA: " + currentUser.conta);
            System.out.println("SALDO: R$" + currentUser.saldo);
            System.out.println("----------------------------------------");
            System.out.println("\t[1] Extrato\n\t[2] Transferência\n\t[3] Logout");
            System.out.println("----------------------------------------");
            System.out.print("--> ");

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            input.nextLine();

            switch(choice){

                case 1: //[1] Extrato
                    currentUser.getExtrato();
                    break;

                case 2: //[2] Transferencia
                    currentUser.Transferencia(clients);
                    break;

                case 3: //[3] Logout
                    currentUser = null;
                    menu = false;
                    break;
            }
        }
    }
}

public class Projeto{
    
	public static void main(String[] args) {

        //Array list de referencia aos objetos tipo Client
        ArrayList<Client> clients = new ArrayList<Client>();

        //Array list de referencia aos CPFs existentes
        ArrayList<Long> registros = new ArrayList<Long>();

        while(true){

            Menu myMenu = new Menu();

            switch(myMenu.inicializarMenu0()){

                case 1: //[1] Fazer Login

                    Client user = new Client();

                    myMenu.inicializarMenu1(user.Login(clients), clients);

                    break;

                case 2: //[2] Gerar nova conta

                    Client newUser = new Client();
                    newUser.Registro(registros);
                    
                    clients.add(newUser);

                    newUser.conta = clients.size()-1;

                    break;

                case 3: //[3] Encerrar
                    System.exit(0);

            }
        }
	}
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ataques.ListaAtaques;
import entidades.Chefao;
import entidades.Criatura;
import entidades.Inimigo;
import entidades.Personagem;
import equipamentos.Equipamento;
import equipamentos.Raridade;

public class Main {

	public static double multiplicador = 1;
	
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	
    //Lista de personagens pré cadastrados
    public static ArrayList<Personagem> personagens = new ArrayList<>(Arrays.asList(
            new Personagem(320, "Monge", 10, 12),
            new Personagem(315, "Ladino", 13, 12),
            new Personagem(330, "Guerreiro", 12, 12),
            new Personagem(340, "Barbaro", 15, 12),
            new Personagem(305, "Mago", 8, 12),
            new Personagem(305, "Clérigo", 7, 12),
            new Personagem(310, "Bardo", 9, 12)
    ));
    public static ArrayList<Chefao> chefoes = new ArrayList<>(Arrays.asList(
            new Chefao(250 * multiplicador, "Golem",(int) Math.floor( 15*multiplicador) , 20 * multiplicador),
            new Chefao(175 * multiplicador, "Rei troll",(int) Math.floor( 17*multiplicador), 30 * multiplicador),
            new Chefao(150 * multiplicador, "Dragão",(int) Math.floor(20* multiplicador), 40 * multiplicador)
    ));
    public static int estagio = 0;

    //Lista de inimigos que será gerada ao decorrer do jogo
    public static ArrayList<Inimigo> inimigos = new ArrayList<>();
    //Personagem selecionado
    public static Personagem personagem;
    public static int DIFICULDADE = 1;
    public static Scanner sc = new Scanner(System.in);
    

    public static void main(String[] args) {
    	System.out.println(Historia.historia);    	
    	System.out.println("Escolha a dificuldade desejada:\n1 = Facil\n2 = Média\n3 = Dificil");
    	escolherDificuldade();
    	
        //Iniciando o jogo
        System.out.println(ANSI_PURPLE+"Bem vindo à nossa campanha de RPG!!!");


        System.out.println("Que modo você deseja jogar?\n1 = Modo Historia\n2 = Modo Livre (Onde você pode criar seu personagem da forma que quiser)");
        int opc = 0;
        do{
        	 opc = sc.nextInt();
            if (opc == 2) {
    			iniciarModoLivre();
    		}else if(opc == 1){
    			
    			imprimirPersonagens();
    	        //Mandando usuário selecionar o personagem
    	        personagem = (Personagem) escolherCriatura(personagens);
    	        
    	        //Garantindo que haverá um personagem, caso haja, iniciar as 3 rodadas
    	        if (personagem != null) {
    	        		iniciarRaid();
    	        } 
    		}else{
    			System.out.println("Opção invalida, informe uma Opção Valida!");
    		}
        }while(opc <= 0 || opc >= 3);

        System.out.println("Acabou, obrigado por jogar!");
    }

    public static void gerarInimigos(int levelPersonagem){
        /**
         * Esta função cria os inimigos e adiciona-os na lista principal de inimigos.
         *
         * @param levelPersonagem O level atual do personagem.
         */
    	System.out.println(multiplicador);
    	for (int i = 0; i < 4; i++){
    		int n = getRandom(1, 5);
    		if (n == 1) inimigos.add(new Inimigo("Lobo", 50 * (levelPersonagem+1)*multiplicador,(int) Math.floor(3 + (levelPersonagem)* multiplicador),levelPersonagem*multiplicador));
    		else if (n == 2) inimigos.add(new Inimigo("Goblin", 55 * (levelPersonagem+1)*multiplicador,(int) Math.floor(3 + (levelPersonagem)* multiplicador),levelPersonagem*multiplicador));
    		else if (n == 3) inimigos.add(new Inimigo("Troll", 60 * (levelPersonagem+1)*multiplicador,(int) Math.floor(3 + (levelPersonagem)* multiplicador),levelPersonagem*multiplicador));
    		else if (n == 4) inimigos.add(new Inimigo("Zumbi", 65 * (levelPersonagem+1)*multiplicador,(int) Math.floor(3 + (levelPersonagem)* multiplicador),levelPersonagem*multiplicador));
    		else if (n == 5) inimigos.add(new Inimigo("Urso", 70 * (levelPersonagem+1)*multiplicador,(int) Math.floor(3 + (levelPersonagem)* multiplicador),levelPersonagem*multiplicador));
    		else throw new NoSuchElementException("Ocorreu um erro ao criar um inimigo!");
    	}

    }

    public static boolean iniciarRodada(){
        /**
         * Esta função controla uma unica onda de inimigos, ela gera os inimigos a
         * partir do nivel de xp do personagem, e logo em seguida ela controla o combate
         * pedindo ao usuário o ataque que ele deseja desferir e fazendo os inimigos
         * atacarem ele, além de verificar se todos os inimigos possuem algum efeito.
         */
        gerarInimigos(personagem.getLevel()); //Gerar inimigos
        int turno = 1; //Numero do turno atual
        while (inimigos.size() > 0 && personagem.getHp() > 0){
            System.out.println(ANSI_PURPLE+"Turno: " + turno+ANSI_RESET);
            System.out.println(ANSI_GREEN+"Sua vez de atacar!"+ANSI_RESET);

            //Imprimindo os inimigos na tela
            inimigos.forEach(inimigo -> inimigo.imprimirInimigo(inimigos.indexOf(inimigo)+1));

            System.out.print(ANSI_YELLOW+"Qual inimigo deseja atacar: "+ANSI_RESET);
            //Chamando a função de escolha de inimigo
            Inimigo inimigoTurno = (Inimigo) escolherCriatura(inimigos);

            //Chamando a função de ataque do personagem e desferindo o dano retornado ao inimigo
            int danoAtaque = personagem.atacar(inimigoTurno);
            inimigoTurno.tomarDano(danoAtaque);

            System.out.printf(ANSI_YELLOW+"Você desferiu uma quantidade de %d de dano ao %s!!!%n", danoAtaque, inimigoTurno.getNome()+ANSI_RESET);
            matarInimigo(inimigoTurno); //Verificar se o inimigo morreu

            //Turno dos inimigos
            System.out.println(ANSI_RED +"Vez dos inimigos!"+ANSI_RESET);
            for (Inimigo inimigo : inimigos){
                verificarSeInimigoMorreu(inimigo); //Verificar se o inimigo possui um efeito e se vai morrer
                //Atacar o personagem
                int dano = personagem.tomarDano(inimigo.atacar(personagem));
                System.out.println(ANSI_RED + "O inimigo " + inimigo.getNome() +
                        " desferiu a você uma quantidade de " + dano +
                        " de dano, lhe resta " + personagem.getHp() + " pontos de vida"+ANSI_RESET);
            }
            turno ++;
            if (personagem.getHp() <= 0) return true;
        }
        return false;
    }

    public static Criatura escolherCriatura(ArrayList<? extends Criatura> lista){
        /**
         * Esta função tem o papel de permitir o usuário escolher uma criatura, sendo
         * que esta pode ser um inimigo ou o seu personagem, ela trata as exceções como
         * IndexOutOfBoundsException (Caso o usuário escolha algo que não existe)
         * e InputMismatchException (Caso o usuário digite uma letra).
         *
         * @param lista A lista de criaturas que o sistema deseja fazer uma escolha.
         *
         * @return A criatura escolhida.
         */
        boolean flag = true;
        do {
            try{
                System.out.print(ANSI_GREEN+"Qual será sua opção: "+ANSI_RESET);
                int opc = sc.nextInt()-1; //Pegando a opção -1
                Criatura criatura = lista.get(opc); //Pegando a criatura
                System.out.println("Você selecionou o: " +  criatura.getNome());
                flag = false; //Encerrando o loop
                return criatura; //Retornando a criatura
            } catch (IndexOutOfBoundsException err){
                System.out.println("Verifique a opção selecionada!");
            } catch (InputMismatchException err){
                System.out.println("Digita um numero aí meu!");
                sc.next(); //Consumir o log de erro
            }
        } while(flag);
        return null;
    }

    public static void verificarSeInimigoMorreu(Inimigo inimigo){
        /**
         * Esta função tem o papel de verificar se o inimigo vai morrer
         * para o seu efeito (Caso ele possua algum).
         *
         * @param inimimo O inimigo para verificar se vai morrer para o efeito.
         */
        inimigo.tomarDano(inimigo.validarDanoEfeito());
        matarInimigo(inimigo);
    }

    public static void matarInimigo(Inimigo inimigo){
        /**
         * Esta função tem o papel de verificar se o inimigo morreu.
         *
         * @param inimigo O inimigo para verificar se está morto.
         */
        if (inimigo.getHp() <= 0){
            inimigos.remove(inimigo);
            System.out.println("Inimigo morto!");
            personagem.addXp(inimigo.getXpDrop());
        }
    }

    public static void iniciarRaid(){
        /**
         * Esta função controla as partes da caminhada, cada caminhada possui 3 ondas
         * de inimigos e ao fim de cada há um BOSS e um baú, além de que o personagem
         * cura sua vida.
         */
    	for (int j = 0; j < chefoes.size(); j++) {
	        for (int i = 1; i <= 2; i++){
	            System.out.println("Rodada: " + i);
	            if (iniciarRodada()) {
	                System.out.println("Você morreu!");
	                return;
	            }
	        }
	        System.out.println(personagem);
	        personagem.setHp(personagem.getHpBase());
	        System.out.println("Sua vida foi restaurada!");
	        abrirBau();
	        System.out.println("Um boss apareceu!!!");
	        batalharComBoss(estagio);
	        
	        estagio++;
    	}
        
    }

    public static void batalharComBoss(int n){
        Chefao chefao = chefoes.get(n);
        int turno = 1;
        while (chefao.getHp() > 0 && personagem.getHp() > 0){
            System.out.println("Turno: " + turno);
            System.out.println("Sua vez de atacar!");

            int danoAtaque = personagem.atacar(chefao);
            chefao.tomarDano(danoAtaque);

            System.out.printf("Você desferiu uma quantidade de %d de dano ao %s!!!%n", danoAtaque, chefao.getNome());

            if (chefao.getHp() <= 0){
                System.out.println("Chefão morto!");
                personagem.addXp(chefao.getXpDrop());
                break;
            }
            int dano = personagem.tomarDano(chefao.atacar(personagem));
            
            if (chefao.roubarEquipamento(getRandom(1, 100))) {
            	personagem.setEquipamento(null);
            	System.out.println("O chefão roubou seu equipamento!!!");
            }

            System.out.println("O inimigo " + chefao.getNome() +
                    " desferiu a você uma quantidade de " + dano +
                    " de dano, lhe resta " + personagem.getHp() + " pontos de vida");
            turno++;
        }
        estagio++;
    }

    public static int getRandom(int min, int max){
        /**
         * Esta função retorna um nímero aleatório com base no numero minimo
         * e no numero máximo informados.
         *
         * @param min Numero minimo.
         * @param max Numero máximo.
         *
         * @return O numero sorteado.
         */

        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    public static void abrirBau(){
        System.out.println("Você encontrou um baú no caminho para próxima raid!!");
        Equipamento equipamento = pegarEquipamento();
        System.out.println("Você ganhou um(a): " + equipamento.getNome() + "\n Veja seus atributos:");
        equipamento.imprimirAtributos();

        if (personagem.getEquipamento() == null){
            personagem.setEquipamento(equipamento);
            System.out.println("Você equipou um(a): " + equipamento.getNome());
        } else {
        	if (equipamento == personagem.getEquipamento()) {
        		System.out.println("Parece que você encontrou um equipamento repetido, deseja fundi-lo?");
        		char resposta = sc.next().charAt(0);
                if (resposta == 's' || resposta == 'S') {
                    personagem.getEquipamento().fundir();
                    System.out.println("Você fundiu o equipamento com sucesso!");
                }
                else System.out.println("Ok, fica pra próxima");
        	} else {
	            System.out.println("Você deseja substituir o seu " + personagem.getEquipamento().getNome() +
	                    " por um " + equipamento.getNome() + "? s - Sim, n - Não");
	            char resposta = sc.next().charAt(0);
	            if (resposta == 's' || resposta == 'S') {
	                personagem.setEquipamento(equipamento);
	                System.out.println("Você equipou um(a): " + equipamento.getNome());
	            }
	            else System.out.println("Ok, fica pra próxima");
        	}
        }
    }

    public static Equipamento pegarEquipamento() {
    	ArrayList<Equipamento> equipamentos = new ArrayList<>();
    	for (Equipamento equip : ListaAtaques.equipamentos){
    	  if (equip.getRaridade() == retornarRaridade()){
    	    equipamentos.add(equip);
    	  }
    	}
        return equipamentos.get(getRandom(0, equipamentos.size()-1));
        
    }
    
    public static Raridade retornarRaridade() {
    	int numero = getRandom(1, 100);
    	
    	if (numero <= Raridade.LENDARIO.getChance()) return Raridade.LENDARIO; // 5
    	else if (numero <= Raridade.EPICO.getChance()) return Raridade.EPICO; // 10
    	else if (numero <= Raridade.RARO.getChance()) return Raridade.RARO; // 20
    	else if (numero <= Raridade.INCOMUM.getChance()) return Raridade.INCOMUM; // 30
    	else return Raridade.COMUM; // 35
    }
    
    public static void escolherDificuldade(){
    	
    	do{
    	int n = sc.nextInt();

    	if(n >= 1 && n <=3) {
    		DIFICULDADE = n;
    		break;
    	}else{
    		System.out.println("A dificuldade informada esta incorreta!\nInforme um número de 1 a 3!!");
    	}
    	}while(true);
    	multiplicarDificuldade();
    }
    
    public static double multiplicarDificuldade(){
    	
    	
    	switch (DIFICULDADE) {
		case 1:
			 multiplicador = 0.7;
			 
			break;

		case 2:
			 multiplicador = 1.2;
			break;
			
		case 3:
			 multiplicador = 2;
			break;
		default:
			
			break;
		}
    	return multiplicador;
    }
    
    public static void iniciarModoLivre(){
    	int opc;
    	System.out.println("Você deseja:\n1 = Criar um novo personagem da sua maneira\n2 = Utilizar um personagem pronto");
    	opc = sc.nextInt();
    	
    	switch (opc) {
		case 1:
	        System.out.println("Crie seu personagem e inicie a sua aventura"+ANSI_RESET);
			System.out.println("Digite o Nome do persosnagem");
			String nome = sc.next();
			
			System.out.println("Digite a Vida do personagem");
			int vida = sc.nextInt();
			
			System.out.println("Digite o Dano do personagem");
			int dano = sc.nextInt();
			
			System.out.println("Digite o MP do personagem");
			int mp = sc.nextInt();
			
			personagem = new Personagem(vida, nome, dano, mp);
			break;

		case 2:
	        System.out.println("Escolha seu personagem e inicie a sua aventura"+ANSI_RESET);
			imprimirPersonagens();
			personagem = (Personagem) escolherCriatura(personagens);
			break;
			
		default:
			break;
		}
    	
    	int contador = 1;
    	while (personagem.getHp() > 0) {
    		if(contador % 5 == 0){
    			batalharComBoss(getRandom(1, 3));
    			abrirBau();
    		}else{
    			iniciarRodada();
    		}
    		contador++;
		}
    	
    }
    
    public static void imprimirPersonagens(){
    	
        //Listando os personagens cadastrados
        personagens.forEach( p -> {
            System.out.println("\nPersonagem " + (personagens.indexOf(p)+1) + ": ");
            p.mostrarAtributos();
        });
    }
}
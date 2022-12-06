package ataques;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import equipamentos.Equipamento;
import equipamentos.Tipo;

public class ListaAtaques {


    public static ArrayList<Efeito> efeitos = new ArrayList<>(
            Arrays.asList(
                    new Efeito("Nenhum", 0, 99, TipoEfeito.ATAQUE),
                    new Efeito("Chamas", 2, 3, TipoEfeito.ATAQUE),
                    new Efeito("Sangramento",3, 3, TipoEfeito.ATAQUE),
                    new Efeito("Envenenamento", 2, 4, TipoEfeito.ATAQUE),
                    new Efeito("Decomposição", 4, 2, TipoEfeito.ATAQUE),
                    new Efeito("Congelamento", 0, 3, TipoEfeito.STATUS),
                    new Efeito("Paralizia", 0, 3, TipoEfeito.STATUS),
                    new Efeito("Regeneração", 5, 2, TipoEfeito.BUFF),
                    new Efeito("Invencivel", 0, 1, TipoEfeito.BUFF),
                    new Efeito("Força divina", 10, 1, TipoEfeito.BUFF),
                    new Efeito("Fraqueza", 2, 1, TipoEfeito.DEBUFF)
            )
    );

    public static ArrayList<AtaqueBasico> ataquesBasicos = new ArrayList<>(
            Arrays.asList(
                    new AtaqueBasico("Soco", 12, 5, 12, 0, "Guerreiro", 1, pegarEfeito("Nenhum")),
                    new AtaqueBasico("Chute", 15, 8, 10, 0, "Guerreiro", 1, pegarEfeito("Nenhum")),
                    new AtaqueBasico("Cabeçada", 20, 10, 10, 0, "Guerreiro", 1, pegarEfeito("Nenhum")),
                    new AtaqueBasico("Joelhada", 13, 10, 10, 0, "Guerreiro", 1, pegarEfeito("Nenhum"))
            )
    );
    public static ArrayList<AtaqueEspecial> ataquesEspecial = new ArrayList<>(
            Arrays.asList(
                    new AtaqueEspecial("Jogo sujo", 30, 1, 0, 100, pegarEfeito("Fraqueza"), 6, "Guerreiro", 1)
            )
    );

    public static Efeito pegarEfeito(String nome){
        return efeitos.stream().filter(efeito -> efeito.getNome().equalsIgnoreCase(nome)).collect(Collectors.toCollection(ArrayList::new)).get(0);
    }
    
    public static ArrayList<Equipamento> equipamentos = new ArrayList<>(
            Arrays.asList( 
            		new Equipamento (Tipo.ATAQUE,"Espada Vingadora", 3, 17,1, 10),
            		new Equipamento (Tipo.CURA,"Elixir Vida", 1, 30, 11, 21),
            		new Equipamento (Tipo.DEFESA,"Escudo Templario", 2, 25, 22, 32),
            		new Equipamento (Tipo.ATAQUE,"Foice da Morte", 2, 19, 33, 43),
            		new Equipamento (Tipo.ATAQUE,"Machado viking", 3, 18, 44, 54),
            		new Equipamento (Tipo.DEFESA,"Armadura Templaria", 2, 25, 55, 100)
            		)
            );



}

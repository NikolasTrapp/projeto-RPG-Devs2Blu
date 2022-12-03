package ataques;

public abstract class Ataque {

    private String nome;
    private int dano;
    private int chanceCritico;
    private int chanceErro;
    private int chanceEfeito;
    private String classe;
    private int nivelMinimo;
    private Efeito efeito;


    public Ataque(String nome, int dano, int chanceCritico, int chanceErro, int chanceEfeito, String classe, int nivelMinimo, Efeito efeito) {
        this.nome = nome;
        this.dano = dano;
        this.chanceCritico = chanceCritico;
        this.chanceErro = chanceErro;
        this.chanceEfeito = chanceEfeito;
        this.classe = classe;
        this.nivelMinimo = nivelMinimo;
        this.efeito = efeito;
    }


    public abstract int calcularDanoAtaque();
    public abstract void mostrarAtributos();

    public int getRandom(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }



    //GETTERS E SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getChanceCritico() {
        return chanceCritico;
    }

    public void setChanceCritico(int chanceCritico) {
        this.chanceCritico = chanceCritico;
    }

    public int getChanceErro() {
        return chanceErro;
    }

    public void setChanceErro(int chanceErro) {
        this.chanceErro = chanceErro;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(int nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }

    public Efeito getEfeito() {
        return efeito;
    }

    public void setEfeito(Efeito efeito) {
        this.efeito = efeito;
    }

    public int getChanceEfeito() {
        return chanceEfeito;
    }

    public void setChanceEfeito(int chanceEfeito) {
        this.chanceEfeito = chanceEfeito;
    }
}

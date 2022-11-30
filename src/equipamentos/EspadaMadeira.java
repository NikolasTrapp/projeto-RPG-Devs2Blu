package equipamentos;

public class EspadaMadeira extends Equipamento{

    public EspadaMadeira() {
        super(Tipo.ATAQUE, "Espada de madeira", 2, 3);
    }

    @Override
    public int usar(int atributoPersonagem) {
        if (usos > 0) {
            usos--;
            System.out.println("Usou " + nome);
            return atributoPersonagem + forca;
        } else {
            System.out.println("Vix! Você não possui mais usos de " + nome);
            return 0;
        }
    }
}

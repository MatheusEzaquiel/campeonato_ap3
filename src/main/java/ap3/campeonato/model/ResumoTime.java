package ap3.campeonato.model;

public class ResumoTime {
	
	private String time;
    private int pontos;
    private int vitorias;
    private int derrotas;
    private int empates;
    private int golsMarcados;
    private int golsSofridos;

    public ResumoTime(String time) { this.time = time; }

    public int getSaldoGols() { 
    	int saldo = golsMarcados - golsSofridos; 
    	
    	if(saldo < 0)
    		saldo = 0;
    	
    	
    	return saldo;
    }

	public String getTime() {
		return time;
	}

	public int getPontos() {
		return pontos;
	}

	public int getVitorias() {
		return vitorias;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public int getEmpates() {
		return empates;
	}

	public int getGolsMarcados() {
		return golsMarcados;
	}

	public int getGolsSofridos() {
		return golsSofridos;
	}

	public void addPontos(int p) {
		pontos += p;
	}

	public void addVitoria() {
		vitorias++;
	}

	public void addDerrota() {
		derrotas++;
	}

	public void addEmpate() {
		empates++;
	}

	public void addGolsMarcados(int g) {
		golsMarcados += g;
	}

	public void addGolsSofridos(int g) {
		golsSofridos += g;
	}
}

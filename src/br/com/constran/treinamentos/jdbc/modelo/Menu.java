package br.com.constran.treinamentos.jdbc.modelo;


public class Menu {
	private int menuGrupo;
	private int id;
	private String tituloMenu;
	private int idMenuPai;
	private String pagina;
	private String descricao;
	private int ordem;
	private String tituloMenuPai;
	
	public int getMenuGrupo() {
		return menuGrupo;
	}
	
	public void setMenuGrupo(int menuGrupo) {
		this.menuGrupo = menuGrupo;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTituloMenu() {
		return tituloMenu;
	}
	
	public void setTituloMenu(String tituloMenu) {
		this.tituloMenu = tituloMenu;
	}
	
	public int getIdMenuPai() {
		return idMenuPai;
	}
	
	public void setIdMenuPai(int idMenuPai) {
		this.idMenuPai = idMenuPai;
	}
	
	public String getPagina() {
		return pagina;
	}
	
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getTituloMenuPai() {
		return tituloMenuPai;
	}

	public void setTituloMenuPai(String tituloMenuPai) {
		this.tituloMenuPai = tituloMenuPai;
	}
	
	
}

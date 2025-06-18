package it.uniroma3.siw.model.Dto;

public class MateriaDto {
    private boolean selected;
    private long numeroListing;
    private String nomeSubject;
    private Long id;

    public MateriaDto(boolean selected, long numeroListing, String nomeSubject, Long id) {
        this.selected = selected;
        this.numeroListing = numeroListing;
        this.nomeSubject = nomeSubject;
        this.id = id;
    }

    // Getters
    public boolean isSelected() {
        return selected;
    }

    public long getNumeroListing() {
        return numeroListing;
    }

    public String getNomeSubject() {
        return nomeSubject;
    }

    public Long getId() {
        return id;
    }

    // Setters (opzionali)
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setNumeroListing(long numeroListing) {
        this.numeroListing = numeroListing;
    }

    public void setNomeSubject(String nomeSubject) {
        this.nomeSubject = nomeSubject;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // toString (opzionale ma utile per debug/logging)
    @Override
    public String toString() {
        return "MateriaDto{" +
                "selected=" + selected +
                ", numeroListing=" + numeroListing +
                ", nomeSubject='" + nomeSubject + '\'' +
                ", id=" + id +
                '}';
    }
}


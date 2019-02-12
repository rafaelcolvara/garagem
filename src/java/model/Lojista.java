/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "lojista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lojista.findAll", query = "SELECT l FROM Lojista l")
    , @NamedQuery(name = "Lojista.findByIdlogista", query = "SELECT l FROM Lojista l WHERE l.idlogista = :idlogista")
    , @NamedQuery(name = "Lojista.findByNome", query = "SELECT l FROM Lojista l WHERE l.nome = :nome")
    , @NamedQuery(name = "Lojista.findByNomeContato", query = "SELECT l FROM Lojista l WHERE l.nomeContato = :nomeContato")
    , @NamedQuery(name = "Lojista.findByTipoContato", query = "SELECT l FROM Lojista l WHERE l.tipoContato = :tipoContato")
    , @NamedQuery(name = "Lojista.findByDataCadastro", query = "SELECT l FROM Lojista l WHERE l.dataCadastro = :dataCadastro")
    , @NamedQuery(name = "Lojista.findByEndereco", query = "SELECT l FROM Lojista l WHERE l.endereco = :endereco")
    , @NamedQuery(name = "Lojista.findByCep", query = "SELECT l FROM Lojista l WHERE l.cep = :cep")
    , @NamedQuery(name = "Lojista.findByNumero", query = "SELECT l FROM Lojista l WHERE l.numero = :numero")
    , @NamedQuery(name = "Lojista.findByComplemento", query = "SELECT l FROM Lojista l WHERE l.complemento = :complemento")
    , @NamedQuery(name = "Lojista.findByTelefone", query = "SELECT l FROM Lojista l WHERE l.telefone = :telefone")
    , @NamedQuery(name = "Lojista.findByDddtelefone", query = "SELECT l FROM Lojista l WHERE l.dddtelefone = :dddtelefone")
    , @NamedQuery(name = "Lojista.findBySite", query = "SELECT l FROM Lojista l WHERE l.site = :site")})
public class Lojista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlogista")
    private Integer idlogista;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nome_contato")
    private String nomeContato;
    @Column(name = "tipoContato")
    private Character tipoContato;
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "cep")
    private String cep;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "dddtelefone")
    private String dddtelefone;
    @Column(name = "site")
    private String site;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lojistaIdlogista")
    private List<Veiculo> veiculoList;
    @JoinColumn(name = "cidade_idcidade", referencedColumnName = "idcidade")
    @ManyToOne(optional = false)
    private Cidade cidadeIdcidade;

    public Lojista() {
    }

    public Lojista(Integer idlogista) {
        this.idlogista = idlogista;
    }

    public Integer getIdlogista() {
        return idlogista;
    }

    public void setIdlogista(Integer idlogista) {
        this.idlogista = idlogista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public Character getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(Character tipoContato) {
        this.tipoContato = tipoContato;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDddtelefone() {
        return dddtelefone;
    }

    public void setDddtelefone(String dddtelefone) {
        this.dddtelefone = dddtelefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @XmlTransient
    public List<Veiculo> getVeiculoList() {
        return veiculoList;
    }

    public void setVeiculoList(List<Veiculo> veiculoList) {
        this.veiculoList = veiculoList;
    }

    public Cidade getCidadeIdcidade() {
        return cidadeIdcidade;
    }

    public void setCidadeIdcidade(Cidade cidadeIdcidade) {
        this.cidadeIdcidade = cidadeIdcidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlogista != null ? idlogista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lojista)) {
            return false;
        }
        Lojista other = (Lojista) object;
        if ((this.idlogista == null && other.idlogista != null) || (this.idlogista != null && !this.idlogista.equals(other.idlogista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Lojista[ idlogista=" + idlogista + " ]";
    }
    
}
